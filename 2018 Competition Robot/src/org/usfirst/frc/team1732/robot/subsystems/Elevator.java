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
 * Subsystem to control the elevator
 * 
 * Manages 1 motor
 */
public class Elevator extends Subsystem {

	public final TalonSRX motor;
	private final TalonEncoder encoder;

	public final ClosedLoopProfile magicGains;
	public final ClosedLoopProfile upGains;
	public final ClosedLoopProfile downGains;

	private final int allowedError;
	private int distanceFromStartup;

	private int desiredPosition;
	private boolean desiredIsSet;
	private boolean autoControl = false;
	private static final String key = "Elevator Starting Count";

	private ControlMode controlMode = ControlMode.Position;

	private final int magicVel;
	private final int magicAccel;

	public Elevator(RobotConfig config) {
		motor = MotorUtils.makeTalon(config.elevator, config.elevatorConfig);
		upGains = config.elevatorUpPID;
		downGains = config.elevatorDownPID;
		magicGains = config.elevatorMagicPID;

		upGains.applyToTalon(motor);
		downGains.applyToTalon(motor);
		magicGains.applyToTalon(motor);

		magicVel = config.elevatorMagicVel;
		magicAccel = config.elevatorMagicAccel;
		motor.configMotionCruiseVelocity(magicVel, Robot.CONFIG_TIMEOUT);
		motor.configMotionAcceleration(magicAccel, Robot.CONFIG_TIMEOUT);

		encoder = new TalonEncoder(motor, FeedbackDevice.CTRE_MagEncoder_Absolute);
		encoder.setPhase(config.reverseElevatorSensor);

		allowedError = config.elevatorAllowedErrorCount;

		int startingCount = (int) Preferences.getInstance().getDouble(key, 0.0);
		Preferences.getInstance().putDouble(key, startingCount);
		distanceFromStartup = startingCount - Positions.START.value;

		Robot.dash.add("Elevator Encoder Position", encoder::getPosition);
		Robot.dash.add("Elevator Encoder Pulses", encoder::getPulses);
		Robot.dash.add("Elevator Encoder Rate", encoder::getRate);
		// holdPosition();
		setManual(0);
	}

	public int getValue(int oldValue) {
		return oldValue + distanceFromStartup;
	}

	public int getValue(Positions position) {
		return position.value + distanceFromStartup;
	}

	public static enum Positions {

		// 3311
		// set these in pulses
		MIN(3011), START(3311), INTAKE(3311), SWITCH(3311), RADIO(14904), SCALE_LOW(14904), HIT_RAMP(14357), SCALE_HIGH(
				22137), MAX(33463);

		private final int value;

		private Positions(int value) {
			this.value = value;
		}
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void periodic() {
		int startingCount = (int) Preferences.getInstance().getDouble(key, 0.0);
		Preferences.getInstance().putDouble(key, startingCount);
		distanceFromStartup = startingCount - Positions.START.value;
		// System.out.println("Elevator Encoder: " +
		// motor.getSensorCollection().getPulseWidthRiseToRiseUs());
		if (autoControl) {
			if (desiredPosition < getValue(Positions.RADIO) && !Robot.arm.isElevatorSafeToGoDown() && desiredIsSet) {
				motor.set(controlMode, getValue(Positions.RADIO));
				desiredIsSet = false;
			}
			if (Robot.arm.isElevatorSafeToGoDown() && !desiredIsSet) {
				motor.set(controlMode, desiredPosition);
				desiredIsSet = true;
			}
		}
	}

	@Override
	public void initDefaultCommand() {
	}

	public void set(int position) {
		if (position < getValue(Positions.MIN)) {
			position = getValue(Positions.MIN);
		}
		if (position > getValue(Positions.MAX)) {
			position = getValue(Positions.MAX);
		}
		desiredPosition = position;
		desiredIsSet = true;
		motor.set(controlMode, desiredPosition);
		System.out.println("setting position: " + desiredPosition);
		autoControl = true;
	}

	public void set(Positions position) {
		set(getValue(position));
	}

	public void setManual(double percentVolt) {
		motor.set(ControlMode.PercentOutput, percentVolt);
		autoControl = false;
	}

	public void holdPosition() {
		set(encoder.getPulses());
	}

	public int getDesiredPosition() {
		return desiredPosition;
	}

	public void setStop() {
		motor.neutralOutput();
		autoControl = false;
	}

	public boolean atSetpoint() {
		return Util.epsilonEquals(encoder.getPulses(), desiredPosition, allowedError);
	}

	public boolean isArmSafeToGoUp() {
		return encoder.getPulses() + allowedError > getValue(Positions.RADIO);
	}

	public boolean isArmSafeToGoDown() {
		return encoder.getPulses() - allowedError < getValue(Positions.HIT_RAMP);
	}

	public int getEncoderPulses() {
		return encoder.getPulses();
	}

	public void usePositionControl(int desiredPosition) {
		controlMode = ControlMode.Position;
		int currentPosition = encoder.getPulses();
		if (currentPosition < desiredPosition) {
			upGains.selectGains(motor);
			System.out.println("using up elevator position gains");
		} else {
			downGains.selectGains(motor);
			System.out.println("using down elevator position gains");
		}
	}

	public void useMagicControl(int desiredPosition) {
		controlMode = ControlMode.MotionMagic;
		// int currentPosition = encoder.getPulses();
		magicGains.selectGains(motor);
	}
}
