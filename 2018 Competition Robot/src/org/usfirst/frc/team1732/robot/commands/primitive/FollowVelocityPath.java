package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.PointPair;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.PointProfile;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.VelocityPoint;
import org.usfirst.frc.team1732.robot.sensors.encoders.EncoderReader;
import org.usfirst.frc.team1732.robot.sensors.navx.GyroReader;
import org.usfirst.frc.team1732.robot.util.Debugger;
import org.usfirst.frc.team1732.robot.util.NotifierCommand;
import org.usfirst.frc.team1732.robot.util.Util;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class FollowVelocityPath extends NotifierCommand {

	// private static final double HEADING_P = 0.2 / 5; // with an error of 5
	// degrees, use 20% of velocity
	private static final double HEADING_P = 2;

	private final GyroReader navx;
	private final EncoderReader leftE;
	private final EncoderReader rightE;
	private final PointProfile profile;

	/**
	 * 
	 * @param iterator
	 *            point supplier
	 * @param initialHeading
	 *            initial heading of the robot according to the path
	 */
	public FollowVelocityPath(PointProfile profile) {
		super(5);
		requires(Robot.drivetrain);
		this.navx = Robot.sensors.navx.makeReader();
		leftE = Robot.drivetrain.getLeftEncoderReader();
		rightE = Robot.drivetrain.getRightEncoderReader();
		this.profile = profile;
		// timer = new Timer();
	}

	// private Timer timer;

	@Override
	protected void init() {
		navx.zero();
		leftE.zero();
		rightE.zero();
		Debugger.logStart(this, "Initial heading: %.3f, Final Center Pos: %.3f", profile.initialHeading,
				profile.finalAbsCenterPos);
		Robot.drivetrain.velocityGains.selectGains(Robot.drivetrain.leftMaster, Robot.drivetrain.rightMaster);
		// timer.reset();
		// timer.start();
	}

	@Override
	protected void exec() {
		// Debugger.logDetailedInfo("Time: " + timer.get());
		// timer.reset();
		// timer.start();
		PointPair<VelocityPoint> pair = profile.getCeilingPoint(timeSinceStarted());
		VelocityPoint left = pair.left;
		VelocityPoint right = pair.right;
		double desiredHeading = left.headingDeg - profile.initialHeading;
		double currentHeading = navx.getTotalAngle();
		double headingError = desiredHeading - currentHeading;
		double headingAdjustment = headingError * HEADING_P;

		double leftVel = left.velocity;
		double rightVel = right.velocity;
		// double leftNew = leftVel + leftVel * headingAdjustment;
		// double rightNew = rightVel - rightVel * headingAdjustment;
		double leftNew = leftVel + Math.signum(leftVel) * headingAdjustment;
		double rightNew = rightVel - Math.signum(rightVel) * headingAdjustment;

		int leftSensor = Robot.drivetrain.velInToUnits(leftNew);
		int rightSensor = Robot.drivetrain.velInToUnits(rightNew);

		// System.out.println();
		// Util.logForGraphing("heading", desiredHeading, currentHeading, headingError,
		// headingAdjustment);
		// Util.logForGraphing("left",
		// Robot.drivetrain.leftMaster.getClosedLoopTarget(0),
		// Robot.drivetrain.leftMaster.getClosedLoopError(0));
		// Util.logForGraphing("right",
		// Robot.drivetrain.rightMaster.getClosedLoopTarget(0),
		// Robot.drivetrain.rightMaster.getClosedLoopError(0));
		// Util.logForGraphing("left", leftE.getRate(), leftVel, leftNew, leftSensor,
		// Robot.drivetrain.leftMaster.getClosedLoopTarget(0),
		// Robot.drivetrain.leftMaster.getClosedLoopError(0),
		// rightE.getPosition());
		// Util.logForGraphing("right", rightE.getRate(), rightVel, rightNew,
		// rightSensor,
		// Robot.drivetrain.rightMaster.getClosedLoopTarget(0),
		// Robot.drivetrain.rightMaster.getClosedLoopError(0),
		// rightE.getPosition());

		Robot.drivetrain.leftMaster.set(ControlMode.Velocity, leftSensor);
		Robot.drivetrain.rightMaster.set(ControlMode.Velocity, rightSensor);
		// SmartDashboard.putNumber("Rate: ", timer.get());
		// timer.reset();
		// timer.get();
	}

	@Override
	protected boolean isDone() {
		return timeSinceStarted() > profile.getTotalTimeSec()
				&& Util.epsilonEquals(profile.finalAbsCenterPos, Math.abs(leftE.getPosition()), 120)
				&& Util.epsilonEquals(profile.finalAbsCenterPos, Math.abs(rightE.getPosition()), 120);
	}

	@Override
	protected void whenEnded() {
		System.out.println("Consumed all points from iterator. Holding last velocity");
	}

}
