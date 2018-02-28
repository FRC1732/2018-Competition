package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.config.MotorUtils;
import org.usfirst.frc.team1732.robot.config.RobotConfig;
import org.usfirst.frc.team1732.robot.controlutils.ClosedLoopProfile;
import org.usfirst.frc.team1732.robot.sensors.encoders.EncoderBase;
import org.usfirst.frc.team1732.robot.sensors.encoders.EncoderReader;
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
	public final EncoderBase encoder;

	public final ClosedLoopProfile upGains;
	public final ClosedLoopProfile downGains;

	public final double inchesPerPulse;
	private final int allowedError;

	private int desiredPosition;
	private boolean desiredIsSet;
	private boolean autoControl = false;

	public Elevator(RobotConfig config) {
		motor = MotorUtils.makeTalon(config.arm, config.armConfig);
		upGains = config.elevatorUpPID;
		downGains = config.elevatorDownPID;
		upGains.applyToTalon(motor);
		downGains.applyToTalon(motor);
		// ClosedLoopProfile.applyZeroGainToTalon(upGains.feedback, upGains.slotIdx, 1,
		// motor);
		// ClosedLoopProfile.applyZeroGainToTalon(downGains.feedback, downGains.slotIdx,
		// 1, motor);
		encoder = new TalonEncoder(motor, FeedbackDevice.QuadEncoder);
		inchesPerPulse = config.elevatorInchesPerPulse;
		encoder.setDistancePerPulse(config.elevatorInchesPerPulse);

		allowedError = config.elevatorAllowedErrorCount;

		Robot.dash.add("Elevator Encoder Position", encoder::getPosition);
		Robot.dash.add("Elevator Encoder Pulses", encoder::getPulses);
	}

	public static enum Positions {

		// set these in pulses
		MIN(0), INTAKE(0), SWITCH(0), RADIO(0), SCALE(0), MAX(0);

		public final int value;

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
		if (autoControl) {
			if (desiredPosition < Positions.RADIO.value && !Robot.arm.isElevatorSafeToGoDown() && desiredIsSet) {
				motor.set(ControlMode.Position, Positions.RADIO.value);
				desiredIsSet = false;
			}
			if (Robot.arm.isElevatorSafeToGoDown() && !desiredIsSet) {
				motor.set(ControlMode.Position, desiredPosition);
				desiredIsSet = true;
			}
		}
	}

	@Override
	public void initDefaultCommand() {
	}

	public EncoderReader getEncoderReader() {
		return encoder.makeReader();
	}

	public void set(double posInches) {
		int position = (int) (posInches / inchesPerPulse);
		if (position < Positions.MIN.value) {
			position = Positions.MIN.value;
		}
		if (position > Positions.MAX.value) {
			position = Positions.MAX.value;
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
		return encoder.getPosition() - allowedError > Positions.RADIO.value;
	}
}
