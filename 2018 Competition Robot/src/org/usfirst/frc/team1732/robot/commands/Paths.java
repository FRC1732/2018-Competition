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
		switchCenterFrontLeftProfile = makeSwitchCenterFrontLeftProfile();
		switchCenterFrontStraightProfile = makeSwitchCenterFrontStraightProfile();
		scaleLeftSingleLeftProfile = makeScaleLeftSingleLeftProfile();
		scaleLeftSingleRightProfile = makeScaleLeftSingleRightProfile();
		scaleRightSingleLeftProfile = makeScaleRightSingleLeftProfile();
		scaleRightSingleRightProfile = makeScaleRightSingleRightProfile();
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

	public final PointProfile switchCenterFrontLeftProfile;

	public PointProfile makeSwitchCenterFrontLeftProfile() {
		Path path;
		double startingX = Field.Switch.BOUNDARY.getMaxX() - robotWidth / 2.0;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		// double endingX = Field.Switch.BOUNDARY.getX() + robotWidth / 2.0;
		// double endingY = Field.Switch.BOUNDARY.getY() - robotLength / 2.0;
		// path.addWaypoint(new Waypoint(endingX, endingY, Math.PI / 2, 0), 0.5);
		double endingX = Field.Switch.BOUNDARY.getX();
		double endingY = Field.Switch.BOUNDARY.getY();
		path.addWaypoint(new Waypoint(endingX - 5, endingY + 10, Math.PI / 2, 0), 0.5);
		path.generateProfile(maxVelocity * 0.5, maxAcceleration * 0.5);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile switchCenterFrontStraightProfile;

	public PointProfile makeSwitchCenterFrontStraightProfile() {
		Path path;
		double startingX = Field.Switch.BOUNDARY.getMaxX() - robotWidth / 2.0;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingY = Field.Switch.BOUNDARY.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(startingX, endingY + 25, Math.PI / 2, 0));
		path.generateProfile(maxVelocity, maxAcceleration * 0.8);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile scaleLeftSingleLeftProfile;

	public PointProfile makeScaleLeftSingleLeftProfile() {
		Path path;
		double startingX = Field.Switch.BOUNDARY.getX() - robotWidth / 2.0 - 5;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = Field.Scale.LEFT_PLATE.getCenterX() - robotWidth / 2.0;
		double endingY = Field.Scale.LEFT_PLATE.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(endingX + 5 + 35, endingY + 90, 3 * Math.PI / 7, 0));

		path.generateProfile(maxVelocity, maxAcceleration);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile scaleLeftSingleRightProfile;

	public PointProfile makeScaleLeftSingleRightProfile() {
		Path path;
		double startingX = Field.Switch.BOUNDARY.getX() - robotWidth / 2.0 - 5;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);

		double middle1X = Field.Scale.PLATFORM.getX() + robotLength / 2.0;
		double middle1Y = Field.Scale.PLATFORM.getY() - robotWidth / 2.0;
		path.addWaypoint(new Waypoint(middle1X, middle1Y, 0, maxVelocity));

		double middle2X = Field.Scale.PLATFORM.getMaxX() - robotLength / 2.0;
		double middle2Y = middle1Y;
		path.addWaypoint(new Waypoint(middle2X, middle2Y, 0, maxVelocity));

		double endingX = Field.Scale.RIGHT_PLATE.getMaxX() - 5;
		double endingY = Field.Scale.RIGHT_PLATE.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(endingX, endingY, Math.PI / 2, 0));

		path.generateProfile(maxVelocity, maxAcceleration);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile scaleRightSingleLeftProfile;

	public PointProfile makeScaleRightSingleLeftProfile() {
		Path path;

		double startingX = Field.Switch.BOUNDARY.getMaxX() + robotWidth / 2.0 + 5;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);

		double middle1X = Field.Scale.PLATFORM.getMaxX() - robotLength / 2.0;
		double middle1Y = Field.Scale.PLATFORM.getY() - robotWidth / 2.0;
		path.addWaypoint(new Waypoint(middle1X, middle1Y, Math.PI, maxVelocity));

		double middle2X = Field.Scale.PLATFORM.getX() + robotLength / 2.0;
		double middle2Y = middle1Y;
		path.addWaypoint(new Waypoint(middle2X, middle2Y, Math.PI, maxVelocity));

		double endingX = Field.Scale.LEFT_PLATE.getX() + 5;
		double endingY = Field.Scale.LEFT_PLATE.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(endingX, endingY + 90, Math.PI / 2, 0));

		path.generateProfile(maxVelocity, maxAcceleration);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile scaleRightSingleRightProfile;

	public PointProfile makeScaleRightSingleRightProfile() {
		Path path;
		double startingX = Field.Switch.BOUNDARY.getMaxX() + robotWidth / 2.0 + 5;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = Field.Scale.RIGHT_PLATE.getCenterX() + robotWidth / 2.0;
		double endingY = Field.Scale.RIGHT_PLATE.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(endingX - 5 - 35, endingY + 90, 4 * Math.PI / 7, 0));

		path.generateProfile(maxVelocity, maxAcceleration);
		return path.getVelocityProfile(effectiveWidth);
	}
}
