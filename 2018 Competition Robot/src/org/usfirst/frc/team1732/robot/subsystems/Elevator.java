package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.config.MotorUtils;
import org.usfirst.frc.team1732.robot.config.RobotConfig;
import org.usfirst.frc.team1732.robot.controlutils.ClosedLoopProfile;
import org.usfirst.frc.team1732.robot.sensors.encoders.EncoderBase;
import org.usfirst.frc.team1732.robot.sensors.encoders.EncoderReader;
import org.usfirst.frc.team1732.robot.sensors.encoders.TalonEncoder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

	public final double degreesPerPulse;

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
		encoder = new TalonEncoder(motor, FeedbackDevice.CTRE_MagEncoder_Absolute);
		encoder.zero();
		degreesPerPulse = config.elevatorDegreesPerPulse;
		encoder.setDistancePerPulse(config.elevatorDegreesPerPulse);
	}

	public static enum Positions {

		// set these in
		MIN(0.0), INTAKE(0.0), SWITCH(0.0), SCALE(0.0), MAX(0.0);

		public final double value;

		private Positions(double value) {
			this.value = value;
		}
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Elevator Encoder Position", encoder.getPosition());
		SmartDashboard.putNumber("Elevator Encoder Position", encoder.getPosition());
	}

	@Override
	public void initDefaultCommand() {
	}

	public EncoderReader getEncoderReader() {
		return encoder.makeReader();
	}

	public void set(double position) {
		if (position < Positions.MIN.value) {
			position = Positions.MIN.value;
		}
		if (position > Positions.MAX.value) {
			position = Positions.MAX.value;
		}
		motor.set(ControlMode.Position, position);
	}

	public void set(Positions position) {
		motor.set(ControlMode.Position, position.value);
	}

	public void setManual(double percentVolt) {
		motor.set(ControlMode.PercentOutput, percentVolt);
	}

	public void holdPosition() {
		motor.set(ControlMode.Position, encoder.getPulses());
	}

	public void setStop() {
		motor.neutralOutput();
	}

	public boolean atSetpoint(double allowableError) {
		return Math.abs(motor.getClosedLoopError(0)) < allowableError;
	}
}
