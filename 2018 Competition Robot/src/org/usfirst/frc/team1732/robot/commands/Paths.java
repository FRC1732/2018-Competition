package org.usfirst.frc.team1732.robot.commands;

import org.usfirst.frc.team1732.robot.autotools.Field;
import org.usfirst.frc.team1732.robot.config.RobotConfig;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.PointProfile;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Waypoint;

public class Paths {

	public final double waypointScaler;
	public final double maxVelocity = 110;
	public final double maxAcceleration = 70;
	public final double robotLength;
	public final double robotWidth;
	public final double effectiveWidth;

	public Paths(RobotConfig config) {
		waypointScaler = config.waypointScaler;
		robotLength = config.robotLength;
		robotWidth = config.robotWidth;
		effectiveWidth = config.effectiveRobotWidth;

		defaultDriveStraight = makeDefaultDriveStraight(); // default drive straight
		centerSwitchFrontLeft = makeCenterSwitchFrontLeft(); // drives to the left switch plate to score in the front
		centerSwitchFrontStraight = makeCenterSwitchFrontStraight(); // drives to the right switch plate to score in the
																		// front
		// scaleLeftStraight = makeScaleLeftStraight();
		// scaleLeftCross = makeScaleLeftCross();
		rightScaleCross = makeRightScaleCross(); // drives to the left scale starting on the right, ending at the left
		rightScaleStraight = makeRightScaleStraight(); // drives to the right scale starting on the right
		// scaleLeftSwitch = makeScaleLeftSwitch();
		rightSwitchRightSide = makeRightSwitchRightSide(); // drives to the right switch scoring into the side
		// leftCubeGrabStraight = makeLeftCubeGrabStraight();
		rightCubeGrabStraightRight = makeRightCubeGrabStraightRight(); // picks up the second cube after not crossing
																		// the middle and scoring in the right switch
		rightCubeGrabStraightLeft = makeRightCubeStraightLeft();
		rightScaleRightReturn = makeRightScaleRightReturn();
		rightScaleLeftReturn = makeRightScaleLeftReturn();
		// leftCubeGrabRightSwitch = makeLeftCubeGrabRightSwitch();
		// rightCubeGrabLeftSwitch = makeRightCubeGrabLeftSwitch();
		driveBackwardSlightly = makeDriveBackwardSlightly();
		driveBackwardSlightlyMore = makeDriveBackwardSlightlyMore();
		driveForwardSlightly = makeDriveForwardSlightly();
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
		path.generateProfile(maxVelocity * 0.5, maxAcceleration * 0.3);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile centerSwitchFrontLeft;

	public PointProfile makeCenterSwitchFrontLeft() {
		Path path;
		double startingX = Field.Switch.BOUNDARY.getMaxX() - robotWidth / 2.0;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		//
		double middleX = Field.Switch.BOUNDARY.getCenterX();
		double middleY = Field.Zones.POWER_CUBE_ZONE.getY() - robotLength / 2.0 - 10;
		path.addWaypoint(new Waypoint(middleX, middleY, Math.PI, maxVelocity * 0.5), 0.5);
		//
		double endingX = Field.Switch.BOUNDARY.getX() + robotWidth;
		double endingY = Field.Switch.BOUNDARY.getY() - robotLength / 2;
		path.addWaypoint(new Waypoint(endingX - 12, endingY - 5, Math.PI / 2, 5));
		path.generateProfile(maxVelocity * 0.5, maxAcceleration * 0.5);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile centerSwitchFrontStraight;

	public PointProfile makeCenterSwitchFrontStraight() {
		Path path;
		double startingX = Field.Switch.BOUNDARY.getMaxX() - robotWidth / 2.0;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingY = Field.Switch.BOUNDARY.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(startingX, endingY, Math.PI / 2, 5));
		path.generateProfile(maxVelocity, maxAcceleration * 0.4);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile rightScaleCross;

	public PointProfile makeRightScaleCross() {
		Path path;

		double maxVelocity = this.maxVelocity * 0.7;

		double startingX = Field.Switch.BOUNDARY.getMaxX() + robotWidth / 2.0 + 10;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);

		double middle0X = startingX;
		double middle0Y = Field.Switch.BOUNDARY.getMaxY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(middle0X, middle0Y - 45, Math.PI / 2, maxVelocity));

		double middle1X = Field.Scale.PLATFORM.getMaxX() - robotLength / 2.0;
		double middle1Y = Field.Scale.PLATFORM.getY() - robotWidth / 2.0 - 27;
		path.addWaypoint(new Waypoint(middle1X - 22, middle1Y, Math.PI, maxVelocity));

		double middle2X = Field.Scale.PLATFORM.getX() + robotLength / 2.0;
		double middle2Y = middle1Y;
		path.addWaypoint(new Waypoint(middle2X + 27, middle2Y, Math.PI, maxVelocity)); // waypoint before we turn to
																						// scale

		double endingX = Field.Scale.LEFT_PLATE.getX() + 5;
		double endingY = Field.Scale.LEFT_PLATE.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(endingX - 5 + 12, endingY - 3 - 11, Math.PI / 2, 0));

		path.generateProfile(maxVelocity, maxAcceleration * 0.9);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile rightScaleStraight;

	public PointProfile makeRightScaleStraight() {
		Path path;
		double startingX = Field.Switch.BOUNDARY.getMaxX() + robotWidth / 2.0 + 10 + 14; // mathy position
		double startingY = robotLength / 2.0; // mathy position
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = Field.Scale.RIGHT_PLATE.getCenterX() + robotWidth / 2;
		double endingY = Field.Scale.RIGHT_PLATE.getY() - robotLength;
		// Math.toRadians(angrad);
		path.addWaypoint(new Waypoint(endingX + 10 /* fudge numbers here [36] */, endingY + 38, 4 * Math.PI / 7, 0));
		path.generateProfile(maxVelocity, maxAcceleration);

		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile rightSwitchRightSide;

	public PointProfile makeRightSwitchRightSide() {
		Path path;
		double startingX = Field.Switch.BOUNDARY.getMaxX() + robotWidth / 2.0 + 20;
		double startingY = robotLength / 2.0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = Field.Switch.BOUNDARY.getMaxX() + robotWidth / 2.0;
		double endingY = Field.Switch.BOUNDARY.getCenterY();
		path.addWaypoint(new Waypoint(endingX - 10, endingY + 20, Math.toRadians(180), 5));

		path.generateProfile(maxVelocity, maxAcceleration * 0.4);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile rightCubeGrabStraightRight;

	public PointProfile makeRightCubeGrabStraightRight() {
		Path path;
		double startingX = 0;
		double startingY = 0;
		path = new Path(new Waypoint(startingX, startingY, -Math.PI / 2, 0), false);
		double endingX = -26;
		double endingY = -48;
		// New (- 25 [- 23])
		path.addWaypoint(new Waypoint(endingX, endingY - 25, Math.toRadians(-125), 0));

		path.generateProfile(maxVelocity * 0.8, maxAcceleration * 0.15);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile rightCubeGrabStraightLeft;

	public PointProfile makeRightCubeStraightLeft() {
		Path path;
		double startingX = 0;
		double startingY = 0;
		path = new Path(new Waypoint(startingX, startingY, -Math.PI / 2, 0), false);
		double endingX = 0;
		double endingY = -57;
		path.addWaypoint(new Waypoint(endingX, endingY, Math.toRadians(-90), 0));

		path.generateProfile(maxVelocity * 0.8, maxAcceleration * 0.2);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile rightScaleRightReturn;

	public PointProfile makeRightScaleRightReturn() {
		Path path;
		double startingX = 0;
		double startingY = 0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = -15;
		double endingY = 50;
		path.addWaypoint(new Waypoint(endingX - 5, endingY + 22, Math.toRadians(130), 0));

		path.generateProfile(maxVelocity * 0.8, maxAcceleration * 0.25);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile rightScaleLeftReturn;

	public PointProfile makeRightScaleLeftReturn() {
		Path path;
		double startingX = 0;
		double startingY = 0;
		path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = 5;
		double endingY = 47;
		path.addWaypoint(new Waypoint(endingX, endingY, Math.toRadians(80), 0));

		path.generateProfile(maxVelocity * 0.8, maxAcceleration * 0.4);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile driveBackwardSlightly;

	public final PointProfile makeDriveBackwardSlightly() {
		Path path;
		path = new Path(new Waypoint(0, 0, Math.toRadians(-90), 0), false);
		double endingX = 0;
		double endingY = -12;
		path.addWaypoint(new Waypoint(endingX, endingY - 5, Math.toRadians(-90), 0));

		path.generateProfile(maxVelocity, maxAcceleration * 0.6);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile driveBackwardSlightlyMore;

	public final PointProfile makeDriveBackwardSlightlyMore() {
		Path path;
		path = new Path(new Waypoint(0, 0, Math.toRadians(-90), 0), false);
		double endingX = 0;
		double endingY = -16;
		path.addWaypoint(new Waypoint(endingX, endingY, Math.toRadians(-90), 0));

		path.generateProfile(maxVelocity, maxAcceleration * 0.6);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile driveForwardSlightly;

	public final PointProfile makeDriveForwardSlightly() {
		Path path;
		path = new Path(new Waypoint(0, 0, Math.toRadians(90), 0), true);
		double endingX = 0;
		double endingY = 10;
		path.addWaypoint(new Waypoint(endingX, endingY + 5 + 3, Math.toRadians(90), 0));

		path.generateProfile(maxVelocity, maxAcceleration * 0.7);
		return path.getVelocityProfile(effectiveWidth);
	}

	// public final PointProfile scaleLeftStraight;
	//
	// public PointProfile makeScaleLeftStraight() {
	// Path path;
	// double startingX = Field.Switch.BOUNDARY.getX() - robotWidth / 2.0 - 5;
	// double startingY = robotLength / 2.0;
	// path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
	// double endingX = Field.Scale.LEFT_PLATE.getCenterX() - robotWidth / 2.0;
	// double endingY = Field.Scale.LEFT_PLATE.getY() - robotLength / 2.0;
	// path.addWaypoint(new Waypoint(endingX + 5 + 25, endingY + 90, 3 * Math.PI /
	// 7, 0));
	//
	// path.generateProfile(maxVelocity, maxAcceleration * 0.80);
	// return path.getVelocityProfile(effectiveWidth);
	// }

	// public final PointProfile scaleLeftCross;
	//
	// public PointProfile makeScaleLeftCross() {
	// Path path;
	// double startingX = Field.Switch.BOUNDARY.getX() - robotWidth / 2.0 - 5;
	// double startingY = robotLength / 2.0;
	// path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
	//
	// double turnVel = maxVelocity * 0.7;
	//
	// double middle0X = startingX;
	// double middle0Y = Field.Switch.BOUNDARY.getMaxY() - robotLength / 2.0;
	// path.addWaypoint(new Waypoint(middle0X, middle0Y + 70, Math.PI / 2,
	// turnVel));
	//
	// double middle1X = Field.Scale.PLATFORM.getX() + robotLength / 2.0;
	// double middle1Y = Field.Scale.PLATFORM.getY() - robotWidth / 2.0;
	// path.addWaypoint(new Waypoint(middle1X, middle1Y + 70, 0, turnVel));
	// // don't accelerate through this ^ turn to cross
	//
	// double middle2X = Field.Scale.PLATFORM.getMaxX() - robotLength / 2.0;
	// double middle2Y = middle1Y;
	// path.addWaypoint(new Waypoint(middle2X + 100, middle2Y + 70, 0,
	// maxVelocity));
	//
	// double endingX = Field.Scale.RIGHT_PLATE.getMaxX() - 5;
	// double endingY = Field.Scale.RIGHT_PLATE.getY() - robotLength / 2.0;
	// path.addWaypoint(new Waypoint(endingX + 135, endingY + 125, 4 * Math.PI / 7,
	// 0));
	//
	// path.generateProfile(maxVelocity, maxAcceleration * 0.9);
	// return path.getVelocityProfile(effectiveWidth);
	// }

	// public final PointProfile scaleLeftSwitch;
	//
	// public PointProfile makeScaleLeftSwitch() {
	// Path path;
	// double startingX = Field.Switch.BOUNDARY.getMinX() + robotWidth / 2.0 - 5;
	// double startingY = robotLength / 2.0;
	// path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
	// double endingX = Field.Switch.BOUNDARY.getX() - robotWidth / 2.0 + 10;
	// double endingY = Field.Switch.BOUNDARY.getY() - robotLength / 2.0;
	// path.addWaypoint(new Waypoint(endingX, endingY, Math.toRadians(120), 0));
	//
	// path.generateProfile(maxVelocity, maxAcceleration * 0.5);
	// return path.getVelocityProfile(effectiveWidth);
	// }

	// public final PointProfile leftCubeGrabStraight;
	//
	// public PointProfile makeLeftCubeGrabStraight() {
	// Path path;
	// double startingX = 0;
	// double startingY = 0;
	// path = new Path(new Waypoint(startingX, startingY, -Math.PI / 2, 0), false);
	// double endingX = 35;
	// double endingY = -55;
	// path.addWaypoint(new Waypoint(endingX, endingY, Math.toRadians(-40), 0));
	//
	// path.generateProfile(maxVelocity, maxAcceleration * 0.25);
	// return path.getVelocityProfile(effectiveWidth);
	// }

	// public final PointProfile leftCubeGrabRightSwitch;
	//
	// public PointProfile makeLeftCubeGrabRightSwitch() {
	// Path path;
	// double startingX = 0;
	// double startingY = 0;
	// path = new Path(new Waypoint(startingX, startingY, Math.toRadians(-100), 0),
	// false);
	// double middleX = 60;
	// double middleY = -38;
	// path.addWaypoint(new Waypoint(middleX, middleY, 0, maxVelocity));
	// double endingX = 180;
	// double endingY = -70;
	// path.addWaypoint(new Waypoint(endingX, endingY, Math.toRadians(-50), 0));
	//
	// path.generateProfile(maxVelocity, maxAcceleration * 0.3);
	// return path.getVelocityProfile(effectiveWidth);
	// }

	// public final PointProfile rightCubeGrabLeftSwitch;
	//
	// public PointProfile makeRightCubeGrabLeftSwitch() {
	// double vel = 70;
	// Path path;
	// double startingX = 0;
	// double startingY = 0;
	// path = new Path(new Waypoint(startingX, startingY, -3 * Math.PI / 7, 0),
	// false);
	// double middle0X = -30;
	// double middle0Y = -38;
	// path.addWaypoint(new Waypoint(middle0X, middle0Y, Math.toRadians(-180),
	// vel));
	// double middle1X = -90;
	// double middle1Y = -38;
	// path.addWaypoint(new Waypoint(middle1X, middle1Y, Math.toRadians(-180),
	// vel));
	// double endingX = -115;
	// double endingY = -45;
	// path.addWaypoint(new Waypoint(endingX, endingY, Math.toRadians(-90), 0));
	// path.generateProfile(vel, 80 * 0.5);
	// return path.getVelocityProfile(Robot.drivetrain.effectiveRobotWidth);
	// }

}
