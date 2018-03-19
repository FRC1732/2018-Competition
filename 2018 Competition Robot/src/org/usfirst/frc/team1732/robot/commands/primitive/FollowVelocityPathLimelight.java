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

public class FollowVelocityPathLimelight extends NotifierCommand {

	// private static final double HEADING_P = 0.2 / 5; // with an error of 5
	// degrees, use 20% of velocity
	private static final double HEADING_P = 1;

	private final GyroReader navx;
	private final EncoderReader leftE;
	private final EncoderReader rightE;
	private final PointProfile profile;
	private final double percentToStartUsingLimelight;
	private final boolean mirror;

	/**
	 * 
	 * @param iterator
	 *            point supplier
	 * @param initialHeading
	 *            initial heading of the robot according to the path
	 */
	public FollowVelocityPathLimelight(PointProfile profile, double percentToStartUsingLimelight) {
		this(profile, percentToStartUsingLimelight, false);
	}

	/**
	 * 
	 * @param iterator
	 *            point supplier
	 * @param initialHeading
	 *            initial heading of the robot according to the path
	 */
	public FollowVelocityPathLimelight(PointProfile profile, double percentToStartUsingLimelight, boolean mirror) {
		super(5);
		requires(Robot.drivetrain);
		this.navx = Robot.sensors.navx.makeReader();
		leftE = Robot.drivetrain.getLeftEncoderReader();
		rightE = Robot.drivetrain.getRightEncoderReader();
		this.profile = profile;
		this.percentToStartUsingLimelight = percentToStartUsingLimelight;
		this.mirror = mirror;
	}

	@Override
	protected void init() {
		navx.zero();
		leftE.zero();
		rightE.zero();
		System.out.println(profile.totalTimeSec);
		Debugger.logStart(this, "%.3f, Final Center Pos: %.3f", profile.totalTimeSec, profile.finalAbsCenterPos);
		Robot.drivetrain.velocityGainsLeft.selectGains(Robot.drivetrain.leftMaster);
		Robot.drivetrain.velocityGainsRight.selectGains(Robot.drivetrain.rightMaster);
	}

	private boolean hasSeenCube = false;

	@Override
	protected void exec() {
		// Debugger.logDetailedInfo("Time: " + timer.get());
		// timer.reset();
		// timer.start();
		PointPair<VelocityPoint> pair = profile.getCeilingPoint(timeSinceStarted());
		VelocityPoint left = pair.left;
		VelocityPoint right = pair.right;
		double headingError;
		double targetArea = Robot.sensors.limelight.getTargetArea();
		if (timeSinceStarted() < percentToStartUsingLimelight * profile.totalTimeSec
				|| (targetArea < 0.1 && !hasSeenCube)) {
			// System.out.println("using navx");
			double desiredHeading = left.headingDeg;
			if (mirror)
				desiredHeading = -desiredHeading;
			double currentHeading = navx.getTotalAngle();
			headingError = Util.getContinuousError(desiredHeading, currentHeading, 360);
		} else if (targetArea < 80 && targetArea > 0.1) {
			hasSeenCube = true;
			// System.out.println("using limelight");
			headingError = Robot.sensors.limelight.getFilteredHorizontalOffset(); // get heading error from limelight
		} else {
			headingError = 0;
		}
		double headingAdjustment = headingError * HEADING_P;

		double leftVel;
		double rightVel;
		if (mirror) {
			leftVel = right.velocity;
			rightVel = left.velocity;
		} else {
			leftVel = left.velocity;
			rightVel = right.velocity;
		}
		// double leftNew = leftVel + leftVel * headingAdjustment;
		// double rightNew = rightVel - rightVel * headingAdjustment;
		double leftNew = leftVel + headingAdjustment;
		double rightNew = rightVel - headingAdjustment;

		int leftSensor = Robot.drivetrain.velInToUnits(leftNew);
		int rightSensor = Robot.drivetrain.velInToUnits(rightNew);

		// Util.logForGraphing(headingError);
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
