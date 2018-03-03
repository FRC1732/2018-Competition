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

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem to control the elevator
 * 
 * Manages 1 motor
 */
public class Elevator extends Subsystem {

	public final TalonSRX motor;
	private final TalonEncoder encoder;

	public final ClosedLoopProfile upGains;
	public final ClosedLoopProfile downGains;

	private final int allowedError;
	private final int distanceFromStartup;

	private int desiredPosition;
	private boolean desiredIsSet;
	private boolean autoControl = false;

	public Elevator(RobotConfig config) {
		motor = MotorUtils.makeTalon(config.elevator, config.elevatorConfig);
		upGains = config.elevatorUpPID;
		downGains = config.elevatorDownPID;
		upGains.applyToTalon(motor);
		downGains.applyToTalon(motor);
		// ClosedLoopProfile.applyZeroGainToTalon(upGains.feedback, upGains.slotIdx, 1,
		// motor);
		// ClosedLoopProfile.applyZeroGainToTalon(downGains.feedback, downGains.slotIdx,
		// 1, motor);
		encoder = new TalonEncoder(motor, FeedbackDevice.CTRE_MagEncoder_Absolute);
		encoder.setPhase(config.reverseElevatorSensor);

		allowedError = config.elevatorAllowedErrorCount;
		distanceFromStartup = 0;// encoder.getPulses() - Positions.START.value;

		Robot.dash.add("Elevator Encoder Position", encoder::getPosition);
		Robot.dash.add("Elevator Encoder Pulses", encoder::getPulses);
		// holdPosition();
		setManual(0);
	}

	public int getValue(Positions position) {
		return position.value + distanceFromStartup;
	}

	public static enum Positions {

		// 3311
		// set these in pulses
		MIN(3311), START(3311), INTAKE(4588), SWITCH(24713), RADIO(14904), SCALE_LOW(14904), SCALE_HIGH(22137), MAX(
				33463);

		private final int value;

		private Positions(int value) {
			this.value = value;
		}
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void periodic() {
		// System.out.println("Elevator Encoder: " +
		// motor.getSensorCollection().getPulseWidthRiseToRiseUs());
		// if (autoControl) {
		// if (desiredPosition < getValue(Positions.RADIO) &&
		// !Robot.arm.isElevatorSafeToGoDown() && desiredIsSet) {
		// motor.set(ControlMode.Position, getValue(Positions.RADIO));
		// desiredIsSet = false;
		// }
		// if (Robot.arm.isElevatorSafeToGoDown() && !desiredIsSet) {
		// motor.set(ControlMode.Position, desiredPosition);
		// desiredIsSet = true;
		// }
		// }
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
		motor.set(ControlMode.Position, desiredPosition);
		autoControl = true;
	}

	public void set(Positions position) {
		desiredPosition = position.value;
		desiredIsSet = true;
		motor.set(ControlMode.Position, desiredPosition);
		autoControl = true;
	}

	public void setManual(double percentVolt) {
		motor.set(ControlMode.PercentOutput, percentVolt);
		autoControl = false;
	}

	public void holdPosition() {
		desiredPosition = encoder.getPulses();
		desiredIsSet = true;
		motor.set(ControlMode.Position, desiredPosition);
		autoControl = true;
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
		return encoder.getPulses() - allowedError > getValue(Positions.RADIO);
	}

	public int getEncoderPulses() {
		return encoder.getPulses();
	}
}
