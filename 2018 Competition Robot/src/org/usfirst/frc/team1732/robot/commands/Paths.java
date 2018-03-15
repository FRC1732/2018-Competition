package org.usfirst.frc.team1732.robot.commands;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autotools.Field;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.PointProfile;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Waypoint;

public class Paths {

	public final double maxVelocity = 150;
	public final double maxAcceleration = 300;
	public final double robotLength;
	public final double robotWidth;
	public final double effectiveWidth;

	public Paths() {
		robotLength = Robot.drivetrain.robotLength;
		robotWidth = Robot.drivetrain.robotWidth;
		effectiveWidth = Robot.drivetrain.effectiveRobotWidth;

		defaultDriveStraight = makeDefaultDriveStraight();
		switchCenterFrontLeft = makeSwitchCenterFrontLeft();
		switchCenterFrontStraight = makeSwitchCenterFrontStraight();
		scaleLeftStraight = makeScaleLeftStraight();
		scaleLeftCross = makeScaleLeftCross();
		scaleRightCross = makeScaleRightCross();
		scaleRightStraight = makeScaleRightStraight();
		scaleLeftSwitch = makeScaleLeftSwitch();
		scaleRightSwitch = makeScaleRightSwitch();
		leftCubeGrabStraight = makeLeftCubeGrabStraight();
		leftCubeGrabRightSwitch = makeLeftCubeGrabRightSwitch();
		leftCubeGrabAfterSwitch = makeLeftCubeGrabAfterSwitch();
	}

	// naming convention is [autoName][side]Profile
	// side means which side our plate is

	public final PointProfile defaultDriveStraight;

	public PointProfile makeDefaultDriveStraight() {
		Path path;
		double startingX = 0;
		double startingY = 0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = startingX;
		double endingY = 150;
		path.addWaypoint(new Waypoint(endingX, endingY, Math.PI / 2, 0));
		path.generateProfile(maxVelocity * 0.5, maxAcceleration * 0.5);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile switchCenterFrontLeft;

	public PointProfile makeSwitchCenterFrontLeft() {
		Path path;
		double startingX = Field.Switch.BOUNDARY.getMaxX() - robotWidth / 2.0;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		// double endingX = Field.Switch.BOUNDARY.getX() + robotWidth / 2.0;
		// double endingY = Field.Switch.BOUNDARY.getY() - robotLength / 2.0;
		// path.addWaypoint(new Waypoint(endingX, endingY, Math.PI / 2, 0), 0.5);
		double endingX = Field.Switch.BOUNDARY.getX();
		double endingY = Field.Switch.BOUNDARY.getY();
		path.addWaypoint(new Waypoint(endingX - 5, endingY + 10, Math.PI / 2, 5));
		path.generateProfile(maxVelocity * 0.5, maxAcceleration * 0.5);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile switchCenterFrontStraight;

	public PointProfile makeSwitchCenterFrontStraight() {
		Path path;
		double startingX = Field.Switch.BOUNDARY.getMaxX() - robotWidth / 2.0;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingY = Field.Switch.BOUNDARY.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(startingX, endingY + 35, Math.PI / 2, 5));
		path.generateProfile(maxVelocity, maxAcceleration * 0.4);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile scaleLeftStraight;

	public PointProfile makeScaleLeftStraight() {
		Path path;
		double startingX = Field.Switch.BOUNDARY.getX() - robotWidth / 2.0 - 5;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = Field.Scale.LEFT_PLATE.getCenterX() - robotWidth / 2.0;
		double endingY = Field.Scale.LEFT_PLATE.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(endingX + 5 + 35, endingY + 90, 3 * Math.PI / 7, 0));

		path.generateProfile(maxVelocity, maxAcceleration * 0.9);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile scaleLeftCross;

	public PointProfile makeScaleLeftCross() {
		Path path;
		double startingX = Field.Switch.BOUNDARY.getX() - robotWidth / 2.0 - 5;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);

		double turnVel = maxVelocity * 0.7;

		double middle0X = startingX;
		double middle0Y = Field.Switch.BOUNDARY.getMaxY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(middle0X, middle0Y, Math.PI / 2, turnVel));

		double middle1X = Field.Scale.PLATFORM.getX() + robotLength / 2.0;
		double middle1Y = Field.Scale.PLATFORM.getY() - robotWidth / 2.0;
		path.addWaypoint(new Waypoint(middle1X, middle1Y, 0, turnVel), true);
		// don't accelerate through this ^ turn to cross

		double middle2X = Field.Scale.PLATFORM.getMaxX() - robotLength / 2.0;
		double middle2Y = middle1Y;
		path.addWaypoint(new Waypoint(middle2X, middle2Y, 0, maxVelocity));

		double endingX = Field.Scale.RIGHT_PLATE.getMaxX() - 5;
		double endingY = Field.Scale.RIGHT_PLATE.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(endingX, endingY, Math.PI / 2, 0));

		path.generateProfile(maxVelocity, maxAcceleration * 0.9);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile scaleRightCross;

	public PointProfile makeScaleRightCross() {
		Path path;

		double maxVelocity = this.maxVelocity;

		double startingX = Field.Switch.BOUNDARY.getMaxX() + robotWidth / 2.0 + 5;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);

		double turnVel = maxVelocity * 0.7;

		double middle0X = startingX;
		double middle0Y = Field.Switch.BOUNDARY.getMaxY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(middle0X, middle0Y, Math.PI / 2, turnVel));

		double middle1X = Field.Scale.PLATFORM.getMaxX() - robotLength / 2.0;
		double middle1Y = Field.Scale.PLATFORM.getY() - robotWidth / 2.0;
		path.addWaypoint(new Waypoint(middle1X, middle1Y, Math.PI, turnVel), true);
		// don't accelerate through this ^ turn to cross

		double middle2X = Field.Scale.PLATFORM.getX() + robotLength / 2.0;
		double middle2Y = middle1Y;
		path.addWaypoint(new Waypoint(middle2X, middle2Y, Math.PI, maxVelocity)); // waypoint before we turn to scale

		double endingX = Field.Scale.LEFT_PLATE.getX() + 5;
		double endingY = Field.Scale.LEFT_PLATE.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(endingX, endingY + 90, Math.PI / 2, 0));

		path.generateProfile(maxVelocity, maxAcceleration * 0.9);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile scaleRightStraight;

	public PointProfile makeScaleRightStraight() {
		Path path;
		double startingX = Field.Switch.BOUNDARY.getMaxX() + robotWidth / 2.0 + 5;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = Field.Scale.RIGHT_PLATE.getCenterX() + robotWidth / 2.0;
		double endingY = Field.Scale.RIGHT_PLATE.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(endingX - 5 - 35, endingY + 90, 4 * Math.PI / 7, 0));

		path.generateProfile(maxVelocity, maxAcceleration * 0.9);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile scaleRightSwitch;

	public PointProfile makeScaleRightSwitch() {
		Path path;
		double startingX = Field.Switch.BOUNDARY.getMaxX() + robotWidth / 2.0 + 5;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = Field.Switch.BOUNDARY.getMaxX() + robotWidth / 2.0;
		double endingY = Field.Switch.BOUNDARY.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(endingX + 5, endingY + 70, Math.toRadians(160), 5));

		path.generateProfile(maxVelocity, maxAcceleration * 0.5);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile scaleLeftSwitch;

	public PointProfile makeScaleLeftSwitch() {
		Path path;
		double startingX = Field.Switch.BOUNDARY.getMinX() + robotWidth / 2.0 - 5;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = Field.Switch.BOUNDARY.getX() - robotWidth / 2.0 + 10;
		double endingY = Field.Switch.BOUNDARY.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(endingX, endingY, Math.toRadians(120), 0));

		path.generateProfile(maxVelocity, maxAcceleration * 0.5);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile leftCubeGrabStraight;

	public PointProfile makeLeftCubeGrabStraight() { // these ones will be all fudging
		Path path;
		double startingX = Field.Scale.LEFT_PLATE.getCenterX() - robotWidth / 2.0;
		double startingY = Field.Scale.LEFT_PLATE.getY() - robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, 3 * Math.PI / 7, 0), false);
		double endingX = 0;
		double endingY = -40;
		path.addWaypoint(new Waypoint(endingX, endingY, 4 * Math.PI / 7, 0));

		path.generateProfile(maxVelocity, maxAcceleration * 0.4);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile leftCubeGrabRightSwitch;

	public PointProfile makeLeftCubeGrabRightSwitch() { // these ones will be all fudging
		Path path;
		double startingX = 0;
		double startingY = 0;
		path = new Path(new Waypoint(startingX, startingY, 3 * Math.PI / 7, 0), false);
		double endingX = 0;
		double endingY = -40;
		path.addWaypoint(new Waypoint(endingX, endingY, 4 * Math.PI / 7, 0));

		path.generateProfile(maxVelocity, maxAcceleration * 0.4);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile leftCubeGrabAfterSwitch;

	public PointProfile makeLeftCubeGrabAfterSwitch() { // these ones will be all fudging
		Path path;
		double startingX = 0;
		double startingY = 0;
		path = new Path(new Waypoint(startingX, startingY, 3 * Math.PI / 7, 0), false);
		double endingX = 0;
		double endingY = -40;
		path.addWaypoint(new Waypoint(endingX, endingY, 4 * Math.PI / 7, 0));

		path.generateProfile(maxVelocity, maxAcceleration * 0.4);
		return path.getVelocityProfile(effectiveWidth);
	}

}
