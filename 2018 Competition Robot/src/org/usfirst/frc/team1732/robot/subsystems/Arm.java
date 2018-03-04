package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.config.MotorUtils;
import org.usfirst.frc.team1732.robot.config.RobotConfig;
import org.usfirst.frc.team1732.robot.controlutils.ClosedLoopProfile;
import org.usfirst.frc.team1732.robot.sensors.encoders.TalonEncoder;
import org.usfirst.frc.team1732.robot.util.Util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem to control the arm
 * 
 * Manages 1 motor
 */
public class Arm extends Subsystem {

	public final TalonSRX motor;
	private final TalonEncoder encoder;

	private final ClosedLoopProfile magicGains;
	private final ClosedLoopProfile upGains;
	private final ClosedLoopProfile downGains;

	private int desiredPosition;
	private boolean desiredIsSet;
	private boolean autoControl = false;

	private final int allowedError;

	private static final String key = "Arm Starting Count";

	private ControlMode controlMode = ControlMode.Position;

	private final int magicVel;
	private final int magicAccel;

	private final DigitalInput button;

	public Arm(RobotConfig config) {
		motor = MotorUtils.makeTalon(config.arm, config.armConfig);
		upGains = config.armUpPID;
		downGains = config.armDownPID;
		magicGains = config.armMagicPID;

		upGains.applyToTalon(motor);
		downGains.applyToTalon(motor);
		magicGains.applyToTalon(motor);

		magicVel = config.armMagicVel;
		magicAccel = config.armMagicAccel;
		motor.configMotionCruiseVelocity(magicVel, Robot.CONFIG_TIMEOUT);
		motor.configMotionAcceleration(magicAccel, Robot.CONFIG_TIMEOUT);

		encoder = new TalonEncoder(motor, FeedbackDevice.CTRE_MagEncoder_Absolute);
		encoder.setPhase(config.reverseArmSensor);

		allowedError = config.armAllowedErrorCount;

		int startingCount = (int) Preferences.getInstance().getDouble(key, 0.0);
		Preferences.getInstance().putDouble(key, startingCount);

		Robot.dash.add("Arm Encoder Position", encoder::getPosition);
		Robot.dash.add("Arm Encoder Pulses", encoder::getPulses);
		Robot.dash.add("Arm Encoder Rate", encoder::getRate);
		button = new DigitalInput(1);
		Robot.dash.add("Arm Button Pressed", this::isButtonPressed);
	}

	public static enum Positions {

		// set these in pulses
		BUTTON_POS(-6577), INTAKE(-6577), SWITCH(-3577), AVOID_RAMP(-1911), TUCK(-383), MAX_LOW(-403), START(
				-413), SCALE(1030);

		public final int value;

		private Positions(int value) {
			this.value = value;
		}
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void periodic() {
		// Util.logForGraphing(Robot.arm.getEncoderPulses(),
		// Robot.arm.getDesiredPosition(),
		// Robot.arm.motor.getClosedLoopTarget(0),
		// Robot.arm.motor.getClosedLoopError(0),
		// Robot.arm.motor.getMotorOutputPercent());
		if (isButtonPressed()) {
			motor.setSelectedSensorPosition(Positions.BUTTON_POS.value, 0, Robot.CONFIG_TIMEOUT);
		}
		int startingCount = (int) Preferences.getInstance().getDouble(key, 0.0);
		Preferences.getInstance().putDouble(key, startingCount);
		if (autoControl) {
			if (desiredPosition > Positions.MAX_LOW.value && !Robot.elevator.isArmSafeToGoUp() && desiredIsSet) {
				motor.set(controlMode, Positions.TUCK.value);
				desiredIsSet = false;
			}
			// if (desiredPosition < getValue(Positions.AVOID_RAMP) &&
			// !Robot.elevator.isArmSafeToGoDown()
			// && desiredIsSet) {
			// motor.set(controlMode, getValue(Positions.AVOID_RAMP) + 100);
			// desiredIsSet = false;
			// }
			if (Robot.elevator.isArmSafeToGoUp() && !desiredIsSet) {
				motor.set(controlMode, desiredPosition);
				desiredIsSet = true;
			}
		}
	}

	@Override
	public void initDefaultCommand() {
	}

	public void set(int position) {
		desiredPosition = position;
		desiredIsSet = true;
		motor.set(controlMode, position);
		System.out.println("setting position: " + desiredPosition);
		autoControl = true;
	}

	public void setManual(double percentVolt) {
		System.out.println("Manual control" + percentVolt);
		motor.set(ControlMode.PercentOutput, percentVolt);
		autoControl = false;
	}

	public void holdPosition() {
		// motor.config_kP(magicGains.slotIdx, magicGains.kP * 5, Robot.CONFIG_TIMEOUT);
		set(encoder.getPulses());
	}

	public void setStop() {
		motor.neutralOutput();
		autoControl = false;
	}

	public boolean atSetpoint() {
		return Util.epsilonEquals(encoder.getPulses(), desiredPosition, allowedError);
	}

	public boolean atSetpoint(int error) {
		return Util.epsilonEquals(encoder.getPulses(), desiredPosition, error);
	}

	public boolean isElevatorSafeToGoDown() {
		return encoder.getPulses() - allowedError < Positions.MAX_LOW.value;
	}

	public int getEncoderPulses() {
		return encoder.getPulses();
	}

	public int getDesiredPosition() {
		return desiredPosition;
	}

	public void usePositionControl(int desiredPosition) {
		controlMode = ControlMode.Position;
		int currentPosition = encoder.getPulses();
		if (currentPosition < desiredPosition) {
			upGains.selectGains(motor);
			System.out.println("using up arm position gains");
		} else {
			downGains.selectGains(motor);
			System.out.println("using down arm position gains");
		}
	}

	public void useMagicControl(int desiredPosition) {
		controlMode = ControlMode.MotionMagic;
		int currentPosition = encoder.getPulses();
		int maxLow = Positions.MAX_LOW.value;
		// motor.config_kP(magicGains.slotIdx, magicGains.kP, Robot.CONFIG_TIMEOUT);
		if (desiredPosition > maxLow && currentPosition < maxLow) {
			motor.configMotionAcceleration((int) (magicAccel * 0.6), Robot.CONFIG_TIMEOUT);
		} else if (desiredPosition < maxLow && currentPosition > maxLow) {
			motor.configMotionAcceleration((int) (magicAccel * 0.2), Robot.CONFIG_TIMEOUT);
			motor.configMotionCruiseVelocity((int) (magicVel * 0.5), Robot.CONFIG_TIMEOUT);
		} else {
			motor.configMotionAcceleration(magicAccel, Robot.CONFIG_TIMEOUT);
		}
		magicGains.selectGains(motor);
	}

	public boolean isButtonPressed() {
		return !button.get();
	}
}
