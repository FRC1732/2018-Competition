package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.commands.DriveWithJoysticks;
import org.usfirst.frc.team1732.robot.config.MotorUtils;
import org.usfirst.frc.team1732.robot.config.RobotConfig;
import org.usfirst.frc.team1732.robot.drivercontrol.DifferentialDrive;
import org.usfirst.frc.team1732.robot.sensors.encoders.EncoderReader;
import org.usfirst.frc.team1732.robot.sensors.encoders.TalonEncoder;
import org.usfirst.frc.team1732.robot.util.Utils;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem to control the drivetrain
 * 
 * Manages 6 TalonSPXs (3 right, 3 left), and associated Encoders
 * 
 */
public class Drivetrain extends Subsystem {

	public TalonSRX leftMaster;
	public TalonSRX rightMaster;

	public DifferentialDrive drive;

	private final TalonEncoder leftEncoder;
	private final TalonEncoder rightEncoder;

	public static final double INPUT_DEADBAND = 0.025; // 2.5%.
	public static final double MIN_OUTPUT = 0.0;
	public static final double MAX_OUTPUT = 1.0;
	public static final double ENCODER_INCHES_PER_PULSE = 0.002099;

	public Drivetrain(RobotConfig config) {
		leftMaster = MotorUtils.makeTalon(config.leftMaster, config.drivetrainConfig);
		MotorUtils.makeTalon(config.leftFollower1, config.drivetrainConfig);
		MotorUtils.makeTalon(config.leftFollower2, config.drivetrainConfig);

		rightMaster = MotorUtils.makeTalon(config.rightMaster, config.drivetrainConfig);
		MotorUtils.makeTalon(config.rightFollower1, config.drivetrainConfig);
		MotorUtils.makeTalon(config.rightFollower2, config.drivetrainConfig);

		drive = new DifferentialDrive(leftMaster, rightMaster, ControlMode.PercentOutput, MIN_OUTPUT, MAX_OUTPUT,
				INPUT_DEADBAND);

		leftEncoder = new TalonEncoder(leftMaster, FeedbackDevice.QuadEncoder, true);
		rightEncoder = new TalonEncoder(rightMaster, FeedbackDevice.QuadEncoder, true);
		leftEncoder.setPhase(true);
		rightEncoder.setPhase(true);
		leftEncoder.setDistancePerPulse(ENCODER_INCHES_PER_PULSE);
		rightEncoder.setDistancePerPulse(ENCODER_INCHES_PER_PULSE);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
	}

	@Override
	public void periodic() {
	}

	public EncoderReader getRightEncoderReader() {
		return getRightEncoderReader(false);
	}
	public EncoderReader getRightEncoderReader(boolean zero) {
		EncoderReader r = rightEncoder.makeReader();
		if (zero) r.zero();
		return r;
	}
	public EncoderReader getLeftEncoderReader() {
		return getLeftEncoderReader(false);
	}
	public EncoderReader getLeftEncoderReader(boolean zero) {
		EncoderReader r = leftEncoder.makeReader();
		if (zero) r.zero();
		return r;
	}

	public void setStop() {
		drive.tankDrive(0, 0);
	}

	public void setLeft(double speed) {
		leftMaster.set(ControlMode.PercentOutput, Utils.constrain(speed, -1, 1));
	}

	public void setRight(double speed) {
		rightMaster.set(ControlMode.PercentOutput, Utils.constrain(speed, -1, 1));
	}
}