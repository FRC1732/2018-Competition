package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.autos.base.PreAuto;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DefaultDriveForward extends CommandGroup {

	public DefaultDriveForward() {
		addSequential(new PreAuto());
		addSequential(new FollowVelocityPath(Robot.paths.defaultDriveStraight));
	}
}
