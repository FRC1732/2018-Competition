package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.Robot;
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

	public final TalonSRX motor;
	public final EncoderBase encoder;

	public final ClosedLoopProfile upGains;
	public final ClosedLoopProfile downGains;

	public final double degreesPerPulse;

	private int desiredPosition;
	private boolean desiredIsSet;

	public Arm(RobotConfig config) {
		motor = MotorUtils.makeTalon(config.arm, config.armConfig);
		upGains = config.armDownPID;
		downGains = config.armDownPID;
		upGains.applyToTalon(motor);
		downGains.applyToTalon(motor);
		// ClosedLoopProfile.applyZeroGainToTalon(upGains.feedback, upGains.slotIdx, 1,
		// motor);
		// ClosedLoopProfile.applyZeroGainToTalon(downGains.feedback, downGains.slotIdx,
		// 1, motor);
		encoder = new TalonEncoder(motor, FeedbackDevice.CTRE_MagEncoder_Absolute);
		degreesPerPulse = config.armDegreesPerPulse;
		encoder.setDistancePerPulse(config.armDegreesPerPulse);
		motor.configForwardSoftLimitThreshold(Positions.MAX.value, 0);
	}

	public static enum Positions {

		// set these in pulses
		MIN(0), INTAKE(0), SWITCH(0), TUCK(0), SCALE(0), MAX(0);

		public final int value;

		private Positions(int value) {
			this.value = value;
		}
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void periodic() {
		SmartDashboard.putNumber("Arm Encoder Position", encoder.getPosition());
		SmartDashboard.putNumber("Arm Encoder Pulses", encoder.getPulses());
		if (desiredPosition > Positions.TUCK.value && !Robot.elevator.isArmSafe() && desiredIsSet) {
			motor.set(ControlMode.Position, Positions.TUCK.value);
			desiredIsSet = false;
		}
		if (Robot.elevator.isArmSafe() && !desiredIsSet) {
			motor.set(ControlMode.Position, desiredPosition);
			desiredIsSet = true;
		}
	}

	@Override
	public void initDefaultCommand() {
	}

	public EncoderReader getEncoderReader() {
		return encoder.makeReader();
	}

	public void set(double pos) {
		int position = (int) (pos / degreesPerPulse);
		if (position < Positions.MIN.value) {
			position = Positions.MIN.value;
		}
		if (position > Positions.MAX.value) {
			position = Positions.MAX.value;
		}
		desiredPosition = position;
		desiredIsSet = true;
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

	public boolean atSetpoint(int allowableError) {
		return Math.abs(motor.getClosedLoopError(0)) < allowableError;
	}

}