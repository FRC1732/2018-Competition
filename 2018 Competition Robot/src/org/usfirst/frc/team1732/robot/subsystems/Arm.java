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
 * Subsystem to control the arm
 * 
 * Manages 1 motor
 */
public class Arm extends Subsystem {

	public final TalonSRX motor;
	private final TalonEncoder encoder;

	public final ClosedLoopProfile upGains;
	public final ClosedLoopProfile downGains;

	private int desiredPosition;
	private boolean desiredIsSet;
	private boolean autoControl = false;

	private final int allowedError;
	private final int distanceFromStartup = 0;

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
		encoder.setPhase(config.reverseArmSensor);

		allowedError = config.armAllowedErrorCount;

		Robot.dash.add("Arm Encoder Position", encoder::getPosition);
		Robot.dash.add("Arm Encoder Pulses", encoder::getPulses);
//		 if (encoder.getPulses() > 4092 || encoder.getPulses() < 0) {
//			distanceFromStartup = encoder.getPulses() - Positions.START.value;
//		 } else {
//		 	distanceFromStartup = 0;
//		 }
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
		// System.out.println("Arm Encoder: " +
		// motor.getSensorCollection().getPulseWidthRiseToRiseUs());
		if (autoControl) {
			if (desiredPosition > getValue(Positions.MAX_LOW) && !Robot.elevator.isArmSafeToGoUp() && desiredIsSet) {
				motor.set(ControlMode.Position, getValue(Positions.TUCK));
				desiredIsSet = false;
			}
			if (Robot.elevator.isArmSafeToGoUp() && !desiredIsSet) {
				motor.set(ControlMode.Position, desiredPosition);
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

}
