package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.controlutils.ClosedLoopProfile;
import org.usfirst.frc.team1732.robot.sensors.navx.GyroReader;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnAngleMagic extends Command {

	// move this to robot config
	private static final double maxAccelIn = 50;
	private static final double maxVelIn = 100;
	private ClosedLoopProfile magic = new ClosedLoopProfile("Drivetrain Magic", FeedbackDevice.QuadEncoder, 3, 0, 0, 0,
			0, 0, 0, 0, 0);

	private final double desiredAngle;
	private GyroReader navx;

	public TurnAngleMagic(double angle) {
		requires(Robot.drivetrain);
		magic.kP = 1;
		magic.kF = Robot.drivetrain.velocityGains.kF;
		this.desiredAngle = angle;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// config motion magic
		magic.applyToTalon(Robot.drivetrain.leftMaster, Robot.drivetrain.rightMaster);
		magic.selectGains(Robot.drivetrain.leftMaster, Robot.drivetrain.rightMaster);
		// convert vel to sensor units per 100ms, and accel to sensor units per 100 ms
		// per sec
		int vel = Robot.drivetrain.velInToUnits(maxVelIn);
		int accel = Robot.drivetrain.velInToUnits(maxAccelIn);

		Robot.drivetrain.leftMaster.configMotionCruiseVelocity(vel, Robot.CONFIG_TIMEOUT);
		Robot.drivetrain.rightMaster.configMotionCruiseVelocity(vel, Robot.CONFIG_TIMEOUT);
		Robot.drivetrain.leftMaster.configMotionAcceleration(accel, Robot.CONFIG_TIMEOUT);
		Robot.drivetrain.rightMaster.configMotionAcceleration(accel, Robot.CONFIG_TIMEOUT);

		double currentAngle = 0;
		double angleError = desiredAngle - currentAngle;
		double arcLength = Math.toRadians(Math.abs(angleError)) * Robot.drivetrain.effectiveRobotWidth / 2.0;
		// arcLength = theta* r
		double leftSetpoint = arcLength * Math.signum(angleError);
		double rightSetpoint = -arcLength * Math.signum(angleError);
		Robot.drivetrain.leftMaster.set(ControlMode.MotionMagic, leftSetpoint / Robot.drivetrain.inchesPerPulse);
		Robot.drivetrain.rightMaster.set(ControlMode.MotionMagic, rightSetpoint / Robot.drivetrain.inchesPerPulse);
		navx = Robot.sensors.navx.makeReader();
		navx.zero();
		Robot.drivetrain.leftEncoder.zero();
		Robot.drivetrain.rightEncoder.zero();
		System.out.println("magic turn angle started");
		System.out.println(vel);
		System.out.println(accel);
		System.out.println(desiredAngle);
		System.out.println(leftSetpoint);
		System.out.println(rightSetpoint);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// double currentAngle = navx.getTotalAngle();
		// double angleError = desiredAngle - currentAngle;
		// double arcLength = Math.toRadians(Math.abs(angleError)) *
		// Robot.drivetrain.effectiveRobotWidth / 2.0;
		// // arcLength = theta* r
		// double leftSetpoint = arcLength * Math.signum(angleError);
		// double rightSetpoint = -arcLength * Math.signum(angleError);
		// double realLeft = leftSetpoint / Robot.drivetrain.inchesPerPulse;
		// double realRight = rightSetpoint / Robot.drivetrain.inchesPerPulse;
		// System.out.println(leftSetpoint + ", " + realLeft + ", " +
		// Robot.drivetrain.leftEncoder.getPulses());
		// System.out.println(rightSetpoint + ", " + realRight + ", " +
		// Robot.drivetrain.rightEncoder.getPulses());
		// Robot.drivetrain.leftMaster.set(ControlMode.MotionMagic, realLeft);
		// Robot.drivetrain.rightMaster.set(ControlMode.MotionMagic, realRight);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		// return Util.epsilonEquals(navx.getTotalAngle(), desiredAngle, 1);
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		System.out.println("magic turn angle ended");
	}

}
