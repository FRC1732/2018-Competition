package org.usfirst.frc.team1732.robot.commands.testing;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Waypoint;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestProfile extends CommandGroup {

	public TestProfile() {
		// drive forward motion
		// Path path;
		// double startingX = 0;
		// double startingY = 0;
		// path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		// double endingX = startingX;
		// double endingY = 100;
		// path.addWaypoint(new Waypoint(endingX, endingY, Math.PI / 2, 0));
		//
		// path.generateProfile(100, 100);
		// addSequential(new
		// FollowVelocityPath(path.getVelocityProfile(Robot.drivetrain.effectiveRobotWidth)));

		// drive backward motion
		// Path path;
		// double startingX = 0;
		// double startingY = 0;
		// path = new Path(new Waypoint(startingX, startingY, -Math.PI / 2, 0), true);
		// double endingX = startingX;
		// double endingY = -100;
		// path.addWaypoint(new Waypoint(endingX, endingY, -Math.PI / 2, 0));
		// path.generateProfile(100, 100);
		// addSequential(new
		// FollowVelocityPath(path.getVelocityProfile(Robot.drivetrain.effectiveRobotWidth)));

		// test mirror unmirrored
		Path path;
		double startingX = 0;
		double startingY = 0;
		path = new Path(new Waypoint(startingX, startingY, -Math.PI / 2, 0), false);
		double endingX = -50;
		double endingY = -50;
		path.addWaypoint(new Waypoint(endingX, endingY, Math.toRadians(-180), 0));

		path.generateProfile(100, 50);
		addSequential(new FollowVelocityPath(path.getVelocityProfile(Robot.drivetrain.effectiveRobotWidth), false));

		// test mirror mirrored
		// Path path;
		// double startingX = 0;
		// double startingY = 0;
		// path = new Path(new Waypoint(startingX, startingY, -Math.PI / 2, 0), false);
		// double endingX = -50;
		// double endingY = -50;
		// path.addWaypoint(new Waypoint(endingX, endingY, Math.toRadians(-180), 0));
		//
		// path.generateProfile(100, 50);
		// addSequential(new
		// FollowVelocityPath(path.getVelocityProfile(Robot.drivetrain.effectiveRobotWidth),
		// true));

		// test cross field
		// addSequential(new FollowVelocityPath(Robot.paths.scaleLeftCross, true));
	}
}
