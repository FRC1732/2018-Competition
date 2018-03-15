package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.config.MotorUtils;
import org.usfirst.frc.team1732.robot.config.RobotConfig;
import org.usfirst.frc.team1732.robot.controlutils.ClosedLoopProfile;
import org.usfirst.frc.team1732.robot.sensors.encoders.TalonEncoder;
import org.usfirst.frc.team1732.robot.util.Debugger;
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

	private int desiredPosition;
	private boolean desiredIsSet;
	private boolean autoControl = false;

	private final int allowedError;

	private static final String key = "Arm Starting Count";

	private final int magicVel;
	private final int magicAccel;

	private final DigitalInput button;
	private final boolean reverseButton;

	public Arm(RobotConfig config) {
		motor = MotorUtils.makeTalon(config.arm, config.armConfig);
		magicGains = config.armMagicPID;

		magicGains.applyToTalon(motor);

		magicVel = config.armMagicVel;
		magicAccel = config.armMagicAccel;
		motor.configMotionCruiseVelocity(magicVel, Robot.CONFIG_TIMEOUT);
		motor.configMotionAcceleration(magicAccel, Robot.CONFIG_TIMEOUT);

		encoder = new TalonEncoder(motor, FeedbackDevice.QuadEncoder);
		encoder.setPhase(config.reverseArmSensor);

		allowedError = config.armAllowedErrorCount;

		// int startingCount = (int) Preferences.getInstance().getDouble(key, 0.0);
		// Preferences.getInstance().putDouble(key, startingCount);

		Robot.dash.add("Arm Encoder Position", encoder::getPosition);
		Robot.dash.add("Arm Encoder Pulses", encoder::getPulses);
		Robot.dash.add("Arm Encoder Rate", encoder::getRate);
		button = new DigitalInput(config.armButtonDIO);
		reverseButton = config.reverseArmButton;
		Robot.dash.add("Arm Button Pressed", this::isButtonPressed);
	}

	public static enum Positions {

		// set these in pulses
		// BUTTON_POS(0), INTAKE(0), EXCHANGE(269), HUMAN_PLAYER(570), SWITCH(2642),
		// CLIMB(5000), START(4093), TUCK(
		// 6432), SCALE_LOW(7622), SCALE_HIGH(7622);
		BUTTON_POS(0), INTAKE(0), EXCHANGE(179), HUMAN_PLAYER(380), SWITCH(1716), CLIMB(3333), START(2729), TUCK(
				4288), SCALE_LOW(5081), SCALE_HIGH(5081);

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
		int startingCount = (int) Preferences.getInstance().getDouble(key, 0.0);
		Preferences.getInstance().putDouble(key, startingCount);
		if (autoControl) {
			if (desiredPosition > Positions.TUCK.value) {
				if (!Robot.elevator.isArmSafeToGoUp() && desiredIsSet) {
					motor.set(ControlMode.MotionMagic, Positions.TUCK.value);
					desiredIsSet = false;
				}
				if (Robot.elevator.isArmSafeToGoUp() && !desiredIsSet) {
					motor.set(ControlMode.MotionMagic, desiredPosition);
					desiredIsSet = true;
				}
			}
			if (desiredPosition < Positions.INTAKE.value + 10) {
				if (!Robot.elevator.isArmSafeToGoDown() && desiredIsSet) {
					motor.set(ControlMode.MotionMagic, Positions.EXCHANGE.value);
					desiredIsSet = false;
				}
				if (Robot.elevator.isArmSafeToGoDown() && !desiredIsSet) {
					motor.set(ControlMode.MotionMagic, desiredPosition);
					desiredIsSet = true;
				}
			}
		}

	}

	@Override
	public void initDefaultCommand() {
	}

	public void set(int position) {
		desiredPosition = position;
		desiredIsSet = true;
		motor.set(ControlMode.MotionMagic, position);
		Debugger.logDetailedInfo("Setting Arm position: " + desiredPosition);
		autoControl = true;
	}

	public void setManual(double percentVolt) {
		Debugger.logDetailedInfo("Manual Arm control: " + percentVolt);
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
		return encoder.getPulses() - allowedError < Positions.TUCK.value;
	}

	public int getEncoderPulses() {
		return encoder.getPulses();
	}

	public int getDesiredPosition() {
		return desiredPosition;
	}

	public void useMagicControl(int desiredPosition) {
		int currentPosition = encoder.getPulses();
		int maxLow = Positions.TUCK.value;
		// motor.config_kP(magicGains.slotIdx, magicGains.kP, Robot.CONFIG_TIMEOUT);
		if (desiredPosition >= maxLow && currentPosition < maxLow + 100) {
			motor.configMotionAcceleration((int) (magicAccel * 0.4), Robot.CONFIG_TIMEOUT);
		} else if (desiredPosition <= maxLow && currentPosition > maxLow - 100) {
			motor.configMotionAcceleration((int) (magicAccel * 0.2), Robot.CONFIG_TIMEOUT); // 0.2
			motor.configMotionCruiseVelocity((int) (magicVel * 0.5), Robot.CONFIG_TIMEOUT); // 0.5
		} else {
			motor.configMotionAcceleration((int) (magicAccel * 0.7), Robot.CONFIG_TIMEOUT);
		}
		magicGains.selectGains(motor);
	}

	public boolean isButtonPressed() {
		return !reverseButton == button.get();
	}

	public void resetArmPos() {
		motor.setSelectedSensorPosition(Positions.BUTTON_POS.value, 0, Robot.CONFIG_TIMEOUT);
	}
}
