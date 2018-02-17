package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.commands.teleop.DriveWithJoysticks;
import org.usfirst.frc.team1732.robot.config.MotorUtils;
import org.usfirst.frc.team1732.robot.config.RobotConfig;
import org.usfirst.frc.team1732.robot.controlutils.ClosedLoopProfile;
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

	public static final double MIN_OUTPUT = 0.0;
	public static final double MAX_OUTPUT = 1.0;

	public final TalonSRX leftMaster;
	public final TalonSRX rightMaster;

	private final TalonEncoder leftEncoder;
	private final TalonEncoder rightEncoder;

	private final DifferentialDrive drive;

	public final double inchesPerPulse;
	public final double robotLength;
	public final double robotWidth;
	public final double effectiveRobotWidth;
	public final double maxInPerSec;
	public final double maxInPerSecSq;

	public final ClosedLoopProfile motionGains;
	public final ClosedLoopProfile velocityGains;

	public Drivetrain(RobotConfig config) {
		leftMaster = MotorUtils.makeTalon(config.leftMaster, config.drivetrainConfig);
		MotorUtils.makeTalon(config.leftFollower1, config.drivetrainConfig);
		MotorUtils.makeTalon(config.leftFollower2, config.drivetrainConfig);

		rightMaster = MotorUtils.makeTalon(config.rightMaster, config.drivetrainConfig);
		MotorUtils.makeTalon(config.rightFollower1, config.drivetrainConfig);
		MotorUtils.makeTalon(config.rightFollower2, config.drivetrainConfig);

		motionGains = config.drivetrainMotionPID;
		motionGains.applyToTalon(leftMaster, rightMaster);
		velocityGains = config.drivetrainVelocityPID;
		velocityGains.applyToTalon(leftMaster, rightMaster);
		maxInPerSec = config.maxInPerSec;
		maxInPerSecSq = config.maxInPerSecSq;

		ClosedLoopProfile.applyZeroGainToTalon(FeedbackDevice.QuadEncoder, motionGains.slotIdx, 1, leftMaster,
				rightMaster);
		ClosedLoopProfile.applyZeroGainToTalon(FeedbackDevice.QuadEncoder, velocityGains.slotIdx, 1, leftMaster,
				rightMaster);

		inchesPerPulse = config.drivetrainInchesPerPulse;
		robotLength = config.robotLength;
		robotWidth = config.robotWidth;
		effectiveRobotWidth = config.effectiveRobotWidth;

		drive = new DifferentialDrive(leftMaster, rightMaster, ControlMode.PercentOutput, MIN_OUTPUT, MAX_OUTPUT,
				config.inputDeadband);

		leftEncoder = new TalonEncoder(leftMaster, FeedbackDevice.QuadEncoder, true);
		rightEncoder = new TalonEncoder(rightMaster, FeedbackDevice.QuadEncoder, true);
		leftEncoder.setPhase(true);
		rightEncoder.setPhase(true);
		leftEncoder.setDistancePerPulse(config.drivetrainInchesPerPulse);
		rightEncoder.setDistancePerPulse(config.drivetrainInchesPerPulse);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks(drive));
	}

	@Override
	public void periodic() {
	}

	public EncoderReader getRightEncoderReader() {
		return getRightEncoderReader(false);
	}

	public EncoderReader getRightEncoderReader(boolean zero) {
		EncoderReader r = rightEncoder.makeReader();
		if (zero)
			r.zero();
		return r;
	}

	public EncoderReader getLeftEncoderReader() {
		return getLeftEncoderReader(false);
	}

	public EncoderReader getLeftEncoderReader(boolean zero) {
		EncoderReader r = leftEncoder.makeReader();
		if (zero)
			r.zero();
		return r;
	}

	public void setStop() {
		leftMaster.neutralOutput();
		rightMaster.neutralOutput();
	}

	public void setLeft(double percentVolt) {
		leftMaster.set(ControlMode.PercentOutput, Utils.constrain(percentVolt, -1, 1));
	}

	public void setRight(double percentVolt) {
		rightMaster.set(ControlMode.PercentOutput, Utils.constrain(percentVolt, -1, 1));
	}

	public void selectGains(ClosedLoopProfile gains) {
		gains.selectGains(leftMaster, rightMaster);
	}
}