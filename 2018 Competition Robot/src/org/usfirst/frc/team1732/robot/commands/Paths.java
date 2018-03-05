package org.usfirst.frc.team1732.robot.commands;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autotools.Field;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.PointProfile;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Waypoint;

public class Paths {

	private final double maxVelocity = 150;
	private final double maxAcceleration = 300;
	private final double robotLength;
	private final double robotWidth;
	private final double effectiveWidth;

	public Paths() {
		robotLength = Robot.drivetrain.robotLength;
		robotWidth = Robot.drivetrain.robotWidth;
		effectiveWidth = Robot.drivetrain.effectiveRobotWidth;

		switchCenterFrontLeftProfile = makeSwitchCenterFrontLeftProfile();
		switchCenterFrontStraightProfile = makeSwitchCenterFrontStraightProfile();
		scaleLeftSingleLeftProfile = makeScaleLeftSingleLeftProfile();
		scaleLeftSingleRightProfile = makeScaleLeftSingleRightProfile();
		scaleRightSingleLeftProfile = makeScaleRightSingleLeftProfile();
		scaleRightSingleRightProfile = makeScaleRightSingleRightProfile();
	}

	// naming convention is [autoName][side]Profile
	// side means which side our plate is

	public final PointProfile switchCenterFrontLeftProfile;

	private PointProfile makeSwitchCenterFrontLeftProfile() {
		double startingX = Field.Switch.BOUNDARY.getMaxX() - robotWidth / 2.0;
		double startingY = robotLength / 2.0;
		Path path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = Field.Switch.BOUNDARY.getX() + robotWidth / 2.0;
		double endingY = Field.Switch.BOUNDARY.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(endingX, endingY, Math.PI / 2, 0), 0.1);
		path.generateProfile(maxVelocity * 0.5, maxAcceleration * 0.5);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile switchCenterFrontStraightProfile;

	private PointProfile makeSwitchCenterFrontStraightProfile() {
		double startingX = Field.Switch.BOUNDARY.getMaxX() - robotWidth / 2.0;
		double startingY = robotLength / 2.0;
		Path path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingY = Field.Switch.BOUNDARY.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(startingX, endingY, Math.PI / 2, 0), 0.1);
		path.generateProfile(maxVelocity, maxAcceleration);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile scaleLeftSingleLeftProfile;

	private PointProfile makeScaleLeftSingleLeftProfile() {
		double startingX = Field.Switch.BOUNDARY.getX() - robotWidth / 2.0 - 5;
		double startingY = robotLength / 2.0;
		Path path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = Field.Scale.LEFT_PLATE.getX() - robotWidth / 2.0;
		double endingY = Field.Scale.LEFT_PLATE.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(endingX, endingY, Math.PI / 3, 0), 0.1);
		path.generateProfile(maxVelocity, maxAcceleration);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile scaleLeftSingleRightProfile;

	private PointProfile makeScaleLeftSingleRightProfile() {
		double startingX = Field.Switch.BOUNDARY.getMaxX() - robotWidth / 2.0;
		double startingY = robotLength / 2.0;
		Path path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = Field.Switch.BOUNDARY.getX();
		double endingY = Field.Switch.BOUNDARY.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(endingX, endingY, Math.PI / 2, 0), 0.1);
		path.generateProfile(maxVelocity, maxAcceleration);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile scaleRightSingleLeftProfile;

	private PointProfile makeScaleRightSingleLeftProfile() {
		double startingX = Field.Switch.BOUNDARY.getMaxX() + robotWidth / 2.0 + 5;
		double startingY = robotLength / 2.0;
		Path path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = Field.Scale.RIGHT_PLATE.getX() + robotWidth / 2.0;
		double endingY = Field.Scale.RIGHT_PLATE.getY() + robotLength / 2.0;
		path.addWaypoint(new Waypoint(endingX, endingY, 2 * Math.PI / 3, 0), 0.1);
		path.generateProfile(maxVelocity, maxAcceleration);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile scaleRightSingleRightProfile;

	private PointProfile makeScaleRightSingleRightProfile() {
		double startingX = Field.Switch.BOUNDARY.getMaxX() - robotWidth / 2.0;
		double startingY = robotLength / 2.0;
		Path path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = Field.Switch.BOUNDARY.getX();
		double endingY = Field.Switch.BOUNDARY.getY() - robotLength / 2.0;
		path.addWaypoint(new Waypoint(endingX, endingY, Math.PI / 2, 0), 0.1);
		path.generateProfile(maxVelocity, maxAcceleration);
		return path.getVelocityProfile(effectiveWidth);
	}
}
