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
 * Subsystem to control the arm
 * 
 * Manages 1 motor
 */
public class Arm extends Subsystem {

	public TalonSRX motor;
	public final EncoderBase encoder;

	private static final ClosedLoopProfile pidGains = new ClosedLoopProfile("Arm PID", 0, 0, 0, 0, 0, 0, 0, 0);
	public static final double DEGREES_PER_PULSE = 0.0;

	public Arm(RobotConfig config) {
		motor = MotorUtils.makeTalon(config.arm, config.armConfig);

		ClosedLoopProfile.applyZeroGainToTalon(motor, 0, 1);
		pidGains.applyToTalon(motor, 0, 0);
		encoder = new TalonEncoder(motor, FeedbackDevice.CTRE_MagEncoder_Absolute, false);
		encoder.setDistancePerPulse(DEGREES_PER_PULSE);
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
		SmartDashboard.putNumber("Arm Encoder Position", encoder.getPosition());
		SmartDashboard.putNumber("Arm Encoder Position", encoder.getPosition());
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
		motor.set(ControlMode.PercentOutput, 0);
	}

	public boolean atSetpoint() {
		return Math.abs(motor.getClosedLoopError(0)) < pidGains.allowableError;
	}
}