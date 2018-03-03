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

	public final ClosedLoopProfile magicGains;
	public final ClosedLoopProfile upGains;
	public final ClosedLoopProfile downGains;

	private int desiredPosition;
	private boolean desiredIsSet;
	private boolean autoControl = false;

	private final int allowedError;
	private int distanceFromStartup;

	private static final String key = "Arm Starting Count";

	private ControlMode controlMode = ControlMode.Position;

	public Arm(RobotConfig config) {
		motor = MotorUtils.makeTalon(config.arm, config.armConfig);
		upGains = config.armUpPID;
		downGains = config.armDownPID;
		magicGains = config.armMagicPID;

		upGains.applyToTalon(motor);
		downGains.applyToTalon(motor);
		magicGains.applyToTalon(motor);

		motor.configMotionCruiseVelocity(config.armMagicVel, Robot.CONFIG_TIMEOUT);
		motor.configMotionAcceleration(config.armMagicAccel, Robot.CONFIG_TIMEOUT);

		// ClosedLoopProfile.applyZeroGainToTalon(upGains.feedback, upGains.slotIdx, 1,
		// motor);
		// ClosedLoopProfile.applyZeroGainToTalon(downGains.feedback, downGains.slotIdx,
		// 1, motor);
		encoder = new TalonEncoder(motor, FeedbackDevice.CTRE_MagEncoder_Absolute);
		encoder.setPhase(config.reverseArmSensor);

		allowedError = config.armAllowedErrorCount;

		int startingCount = (int) Preferences.getInstance().getDouble(key, 0.0);
		Preferences.getInstance().putDouble(key, startingCount);
		distanceFromStartup = startingCount - Positions.START.value;

		Robot.dash.add("Arm Encoder Position", encoder::getPosition);
		Robot.dash.add("Arm Encoder Pulses", encoder::getPulses);
		Robot.dash.add("Arm Encoder Rate", encoder::getRate);

		setManual(0);
	}

	public int getValue(int oldPos) {
		return oldPos + distanceFromStartup;
	}

	public int getValue(Positions position) {
		return position.value + distanceFromStartup;
	}

	public static enum Positions {

		// set these in pulses
		MIN(-8551), INTAKE(-8334), SWITCH(-8334), TUCK(-4641), MAX_LOW(-1969), START(-2332), SCALE(-427), MAX(-427);

		private final int value;

		private Positions(int value) {
			this.value = value;
		}
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void periodic() {
		// Util.logForGraphing(Robot.arm.getEncoderPulses(),
		// Robot.arm.motor.getSensorCollection().getPulseWidthPosition(),
		// Robot.arm.getDesiredPosition(), Robot.arm.motor.getClosedLoopError(0),
		// Robot.arm.motor.getMotorOutputPercent());
		// System.out.println("Arm Encoder: " +
		// motor.getSensorCollection().getPulseWidthRiseToRiseUs());
		int startingCount = (int) Preferences.getInstance().getDouble(key, 0.0);
		Preferences.getInstance().putDouble(key, startingCount);
		distanceFromStartup = startingCount - Positions.START.value;
		if (autoControl) {
			if (desiredPosition > getValue(Positions.MAX_LOW) && !Robot.elevator.isArmSafeToGoUp() && desiredIsSet) {
				motor.set(controlMode, getValue(Positions.TUCK));
				desiredIsSet = false;
			}
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
		position = getValue(position);
		setRawPosition(position);
	}

	private void setRawPosition(int position) {
		if (position < getValue(Positions.MIN)) {
			position = getValue(Positions.MIN);
		}
		if (position > getValue(Positions.MAX)) {
			position = getValue(Positions.MAX);
		}
		desiredPosition = position;
		desiredIsSet = true;
		motor.set(controlMode, position);
		System.out.println("setting position: " + desiredPosition);
		autoControl = true;
	}

	public void set(Positions position) {
		set(position.value);
	}

	public void setManual(double percentVolt) {
		// System.out.println("Manual control" + percentVolt);
		motor.set(ControlMode.PercentOutput, percentVolt);
		autoControl = false;
	}

	public void holdPosition() {
		setRawPosition(encoder.getPulses());
	}

	public void setStop() {
		motor.neutralOutput();
		autoControl = false;
	}

	public boolean atSetpoint() {
		return Util.epsilonEquals(encoder.getPulses(), desiredPosition, allowedError);
	}

	public boolean isElevatorSafeToGoDown() {
		return encoder.getPulses() + allowedError < getValue(Positions.TUCK);
	}

	public int getEncoderPulses() {
		return encoder.getPulses();
	}

	public int getDesiredPosition() {
		return desiredPosition;
	}

	public void usePositionControl() {
		controlMode = ControlMode.Position;
	}

	public void useMagicControl() {
		controlMode = ControlMode.MotionMagic;
	}
}
