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
		switchCenterFrontScoreLeftProfile = makeSwitchCenterFrontScoreLeftProfile();
	}

	private PointProfile makeSwitchCenterFrontScoreLeftProfile() {
		double startingX = Field.Switch.BOUNDARY.getMaxX() - robotWidth / 2.0;
		double startingY = robotLength / 2.0;
		Path path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		double endingX = Field.Switch.BOUNDARY.getX();
		double endingY = Field.Switch.BOUNDARY.getY();
		path.addWaypoint(new Waypoint(endingX - 5, endingY, Math.PI / 2, 0), 0.1);
		path.generateProfile(maxVelocity * 0.5, maxAcceleration * 0.5);
		return path.getVelocityProfile(effectiveWidth);
	}

	public final PointProfile switchCenterFrontScoreLeftProfile;

}
