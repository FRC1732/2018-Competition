package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.Robot;
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
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
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

	private final Solenoid shifter;
	private final boolean highGearValue;

	private final VictorSPX leftVictor1;
	private final VictorSPX leftVictor2;
	private final VictorSPX rightVictor1;
	private final VictorSPX rightVictor2;

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
		shifter = new Solenoid(config.shiftingSolenoidID);
		highGearValue = config.highGearValue;

		leftMaster = MotorUtils.makeTalon(config.leftMaster, config.drivetrainConfig);
		leftVictor1 = MotorUtils.makeVictor(config.leftFollower1, config.drivetrainConfig);
		leftVictor2 = MotorUtils.makeVictor(config.leftFollower2, config.drivetrainConfig);

		rightMaster = MotorUtils.makeTalon(config.rightMaster, config.drivetrainConfig);
		rightVictor1 = MotorUtils.makeVictor(config.rightFollower1, config.drivetrainConfig);
		rightVictor2 = MotorUtils.makeVictor(config.rightFollower2, config.drivetrainConfig);

		leftFF = config.leftFF;
		rightFF = config.rightFF;

		motionGains = config.drivetrainMotionPID;
		motionGains.applyToTalon(leftMaster, rightMaster);
		velocityGains = config.drivetrainVelocityPID;
		velocityGains.applyToTalon(leftMaster, rightMaster);
		maxInPerSec = config.maxInPerSec;
		maxInPerSecSq = config.maxInPerSecSq;

		// ClosedLoopProfile.applyZeroGainToTalon(FeedbackDevice.QuadEncoder,
		// motionGains.slotIdx, 1, leftMaster,
		// rightMaster);
		// ClosedLoopProfile.applyZeroGainToTalon(FeedbackDevice.QuadEncoder,
		// velocityGains.slotIdx, 1, leftMaster,
		// rightMaster);

		inchesPerPulse = config.drivetrainInchesPerPulse;
		robotLength = config.robotLength;
		robotWidth = config.robotWidth;
		effectiveRobotWidth = config.effectiveRobotWidth;

		drive = new DifferentialDrive(leftMaster, rightMaster, ControlMode.PercentOutput, MIN_OUTPUT, MAX_OUTPUT,
				config.inputDeadband);
		defaultCommand = new DriveWithJoysticks(drive);

		leftEncoder = new TalonEncoder(leftMaster, FeedbackDevice.QuadEncoder);
		rightEncoder = new TalonEncoder(rightMaster, FeedbackDevice.QuadEncoder);
		leftEncoder.zero();
		rightEncoder.zero();
		leftEncoder.setPhase(config.reverseLeftSensor);
		rightEncoder.setPhase(config.reverseRightSensor);
		leftEncoder.setDistancePerPulse(config.drivetrainInchesPerPulse);
		rightEncoder.setDistancePerPulse(config.drivetrainInchesPerPulse);
		shiftHigh();

		Robot.dash.add("Left Pos", leftEncoder::getPosition);
		Robot.dash.add("Left Pulses", leftEncoder::getPulses);
		Robot.dash.add("Left Vel", leftEncoder::getRate);
		Robot.dash.add("Left Rate", this::getLeftSensorVelocity);
		Robot.dash.add("Right Pos", rightEncoder::getPosition);
		Robot.dash.add("Right Pulses", rightEncoder::getPulses);
		Robot.dash.add("Right Vel", rightEncoder::getRate);
		Robot.dash.add("Right Rate", this::getRightSensorVelocity);
	}

	private double getLeftSensorVelocity() {
		return leftMaster.getSelectedSensorVelocity(0);
	}

	private double getRightSensorVelocity() {
		return rightMaster.getSelectedSensorVelocity(0);
	}

	private Command defaultCommand;

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(defaultCommand);
	}

	public EncoderReader getRightEncoderReader() {
		return rightEncoder.makeReader();
	}

	public EncoderReader getLeftEncoderReader() {
		return leftEncoder.makeReader();
	}

	public void setStop() {
		leftMaster.neutralOutput();
		rightMaster.neutralOutput();
	}

	public void setNeutralMode(NeutralMode mode) {
		leftMaster.setNeutralMode(mode);
		rightMaster.setNeutralMode(mode);
		leftVictor1.setNeutralMode(mode);
		leftVictor2.setNeutralMode(mode);
		rightVictor1.setNeutralMode(mode);
		rightVictor2.setNeutralMode(mode);
	}

	public void setBrake() {
		setNeutralMode(NeutralMode.Brake);
	}

	public void setCoast() {
		setNeutralMode(NeutralMode.Coast);
	}

	public void setLeft(double percentVolt) {
		leftMaster.set(ControlMode.PercentOutput, Util.limit(percentVolt, -MAX_OUTPUT, MAX_OUTPUT));
	}

	public void setRight(double percentVolt) {
		rightMaster.set(ControlMode.PercentOutput, Util.limit(percentVolt, -MAX_OUTPUT, MAX_OUTPUT));
	}

	public void selectGains(ClosedLoopProfile gains) {
		gains.selectGains(leftMaster, rightMaster);
	}

	public void shiftHigh() {
		shifter.set(highGearValue);
	}

	public void shiftLow() {
		shifter.set(!highGearValue);
	}
}