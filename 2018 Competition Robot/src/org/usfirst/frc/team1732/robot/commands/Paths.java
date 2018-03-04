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

	public Paths() {
		robotLength = Robot.drivetrain.robotLength;
		robotWidth = Robot.drivetrain.robotWidth;
		switchCenterFrontScoreLeftProfile = makeSwitchCenterFrontScoreLeftProfile();
	}

	private PointProfile makeSwitchCenterFrontScoreLeftProfile() {
		double startingX = Field.Switch.BOUNDARY.getMaxX() - robotWidth / 2.0;
		Path path = new Path(new Waypoint(startingX, robotLength / 2.0, Math.PI / 2, 0), false);
		path.addWaypoint(new Waypoint(Field.Switch.BOUNDARY.getMinX() + robotWidth / 2.0,
				Field.Switch.BOUNDARY.getMinY() - robotLength / 2.0, Math.PI / 2, 0), 0.2);
		path.generateProfile(maxVelocity * 0.5, maxAcceleration * 0.5);
		return path.getVelocityProfile(Robot.drivetrain.robotWidth);
	}

	public final PointProfile switchCenterFrontScoreLeftProfile;

}
