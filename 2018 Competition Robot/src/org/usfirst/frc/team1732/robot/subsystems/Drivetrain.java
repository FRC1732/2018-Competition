package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.commands.teleop.DriveWithJoysticks;
import org.usfirst.frc.team1732.robot.config.MotorUtils;
import org.usfirst.frc.team1732.robot.config.RobotConfig;
import org.usfirst.frc.team1732.robot.controlutils.ClosedLoopProfile;
import org.usfirst.frc.team1732.robot.controlutils.Feedforward;
import org.usfirst.frc.team1732.robot.drivercontrol.DifferentialDrive;
import org.usfirst.frc.team1732.robot.sensors.encoders.EncoderReader;
import org.usfirst.frc.team1732.robot.sensors.encoders.TalonEncoder;
import org.usfirst.frc.team1732.robot.util.Util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
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

	private final TalonSRX leftTalon1;
	private final TalonSRX leftTalon2;
	private final TalonSRX rightTalon1;
	private final TalonSRX rightTalon2;

	private final TalonEncoder leftEncoder;
	private final TalonEncoder rightEncoder;

	public final DifferentialDrive drive;

	public final double inchesPerPulse;
	public final double robotLength;
	public final double robotWidth;
	public final double effectiveRobotWidth;
	public final double maxInPerSec;
	public final double maxInPerSecSq;

	// Feedforward
	public final Feedforward leftFF;
	public final Feedforward rightFF;

	public final ClosedLoopProfile motionGains;
	public final ClosedLoopProfile velocityGains;

	public Drivetrain(RobotConfig config) {
		leftMaster = MotorUtils.makeTalon(config.leftMaster, config.drivetrainConfig);
		leftTalon1 = MotorUtils.makeTalon(config.leftFollower1, config.drivetrainConfig);
		leftTalon2 = MotorUtils.makeTalon(config.leftFollower2, config.drivetrainConfig);

		rightMaster = MotorUtils.makeTalon(config.rightMaster, config.drivetrainConfig);
		rightTalon1 = MotorUtils.makeTalon(config.rightFollower1, config.drivetrainConfig);
		rightTalon2 = MotorUtils.makeTalon(config.rightFollower2, config.drivetrainConfig);

		leftFF = config.leftFF;
		rightFF = config.rightFF;

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
	public void periodic() {}

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

	public void setNeutralMode(NeutralMode mode) {
		leftMaster.setNeutralMode(mode);
		rightMaster.setNeutralMode(mode);
		leftTalon1.setNeutralMode(mode);
		leftTalon2.setNeutralMode(mode);
		rightTalon1.setNeutralMode(mode);
		rightTalon2.setNeutralMode(mode);
	}

	public void setLeft(double percentVolt) {
		leftMaster.set(ControlMode.PercentOutput, Util.limit(percentVolt, -1, 1));
	}

	public void setRight(double percentVolt) {
		rightMaster.set(ControlMode.PercentOutput, Util.limit(percentVolt, -1, 1));
	}

	public void selectGains(ClosedLoopProfile gains) {
		gains.selectGains(leftMaster, rightMaster);
	}

	public void setBrakeMode(boolean enabled) {
		NeutralMode mode = enabled ? NeutralMode.Brake : NeutralMode.Coast;
		leftMaster.setNeutralMode(mode);
		leftTalon1.setNeutralMode(mode);
		leftTalon2.setNeutralMode(mode);
		rightMaster.setNeutralMode(mode);
		rightTalon1.setNeutralMode(mode);
		rightTalon2.setNeutralMode(mode);
	}
}