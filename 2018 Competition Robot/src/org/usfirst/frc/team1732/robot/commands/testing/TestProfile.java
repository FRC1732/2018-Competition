package org.usfirst.frc.team1732.robot.commands.testing;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveVoltage;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.PointProfile;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestProfile extends CommandGroup {

	public TestProfile() {
		PointProfile profile;

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
		// Path path;
		// double startingX = 0;
		// double startingY = 0;
		// path = new Path(new Waypoint(startingX, startingY, -Math.PI / 2, 0), false);
		// double endingX = -100;
		// double endingY = -100;
		// path.addWaypoint(new Waypoint(endingX, endingY, Math.toRadians(-180), 0));
		//
		// path.generateProfile(100, 50);
		// addSequential(new
		// FollowVelocityPath(path.getVelocityProfile(Robot.drivetrain.effectiveRobotWidth),
		// false));

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

		// test straight
		// Path path;
		// double startingX = Field.Switch.BOUNDARY.getMaxX() +
		// Robot.drivetrain.robotWidth / 2.0 + 5;
		// double startingY = Robot.drivetrain.robotLength / 2.0;
		// path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		// double middleX = startingX;
		// double middleY = Field.Switch.BOUNDARY.getMaxY() -
		// Robot.drivetrain.robotLength / 2.0;
		// path.addWaypoint(new Waypoint(middleX, middleY, Math.PI / 2, 0));
		// path.generateProfile(90, 300 * 0.7);

		// double robotLength = Robot.drivetrain.robotLength;
		// double robotWidth = Robot.drivetrain.robotWidth;
		//
		// Path path;
		// double startingX = Field.Switch.BOUNDARY.getMaxX() + robotWidth / 2.0 + 10;
		// double startingY = robotLength / 2.0;
		// path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
		// double endingX = Field.Scale.RIGHT_PLATE.getCenterX() + robotWidth / 2;
		// double endingY = Field.Scale.RIGHT_PLATE.getY() - robotLength;
		// path.addWaypoint(new Waypoint(endingX - 2.5, endingY - 15, 4 * Math.PI / 7,
		// 0));
		// path.generateProfile(110, 70);
		//
		// addSequential(new
		// FollowVelocityPath(path.getVelocityProfile(Robot.drivetrain.effectiveRobotWidth)));
		// Path path;
		// double startingX = 0;
		// double startingY = 0;
		// path = new Path(new Waypoint(startingX, startingY, -3 * Math.PI / 7, 0),
		// false);
		// // double middle0X = 0;
		// // double middle0Y = -12;
		// // path.addWaypoint(new Waypoint(middle0X, middle0Y, Math.toRadians(-90),
		// 110));
		// double middle0X = -30;
		// double middle0Y = -38;
		// path.addWaypoint(new Waypoint(middle0X, middle0Y, Math.toRadians(-180), 70));
		// double middle1X = -90;
		// double middle1Y = -38;
		// path.addWaypoint(new Waypoint(middle1X, middle1Y, Math.toRadians(-180), 70));
		// double endingX = -115;
		// double endingY = -45;
		// path.addWaypoint(new Waypoint(endingX, endingY, Math.toRadians(-90), 0));
		// path.generateProfile(70, 80 * 0.5);

		profile = Robot.paths.scaleRightCross;
		addSequential(new FollowVelocityPath(profile));
		addSequential(new DriveVoltage(0, 0, NeutralMode.Brake));
	}
}
