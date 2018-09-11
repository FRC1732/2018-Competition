package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.teleop.DriveWithJoysticks;
import org.usfirst.frc.team1732.robot.config.MotorUtils;
import org.usfirst.frc.team1732.robot.config.RobotConfig;
import org.usfirst.frc.team1732.robot.controlutils.ClosedLoopProfile;
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

	public final TalonEncoder leftEncoder;
	public final TalonEncoder rightEncoder;

	public final DifferentialDrive drive;

	public final double inchesPerPulse;
	public final double robotLength;
	public final double robotWidth;
	public final double effectiveRobotWidth;
	public final double maxUnitsPer100Ms;

	public final ClosedLoopProfile velocityGainsLeft;
	public final ClosedLoopProfile velocityGainsRight;

	public Drivetrain(RobotConfig config) {
		shifter = new Solenoid(config.shiftingSolenoidID);
		highGearValue = config.highGearValue;

		leftMaster = MotorUtils.makeTalon(config.leftMaster, config.drivetrainConfig);
		leftVictor1 = MotorUtils.makeVictorFollower(config.leftFollower1, config.drivetrainConfig, leftMaster);
		leftVictor2 = MotorUtils.makeVictorFollower(config.leftFollower2, config.drivetrainConfig, leftMaster);

		rightMaster = MotorUtils.makeTalon(config.rightMaster, config.drivetrainConfig);
		rightVictor1 = MotorUtils.makeVictorFollower(config.rightFollower1, config.drivetrainConfig, rightMaster);
		rightVictor2 = MotorUtils.makeVictorFollower(config.rightFollower2, config.drivetrainConfig, rightMaster);

		velocityGainsLeft = config.drivetrainVelocityLeftPID;
		velocityGainsRight = config.drivetrainVelocityRightPID;
		velocityGainsLeft.applyToTalon(leftMaster);
		velocityGainsRight.applyToTalon(rightMaster);

		maxUnitsPer100Ms = config.maxUnitsPer100Ms;

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

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks(drive));
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
		double d = Util.limit(percentVolt, -MAX_OUTPUT, MAX_OUTPUT);
		System.out.println("set left: " + d);
		leftMaster.set(ControlMode.PercentOutput, Util.limit(percentVolt, -MAX_OUTPUT, MAX_OUTPUT));
	}

	public void setRight(double percentVolt) {
		double d = Util.limit(percentVolt, -MAX_OUTPUT, MAX_OUTPUT);
		System.out.println("set right: " + d);
		rightMaster.set(ControlMode.PercentOutput, d);
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

	public int velInToUnits(double desiredInPerSec) {
		return (int) (desiredInPerSec / 10 / inchesPerPulse);
	}

	public double velUnitsToIn(double desiredUnitsPer100Ms) {
		return desiredUnitsPer100Ms * 10 * inchesPerPulse;
	}

	// New Drive Method
	public void drive(double left, double right) {
		leftMaster.set(ControlMode.PercentOutput, left);
		rightMaster.set(ControlMode.PercentOutput, right);
	}

	// New Encoder Methods
	public void resetEncoders() {
		rightEncoder.zero();
		leftEncoder.zero();
	}

}