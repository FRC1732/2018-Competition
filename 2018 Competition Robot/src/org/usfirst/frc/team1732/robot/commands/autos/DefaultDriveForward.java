package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.autos.base.PreAuto;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmMagicPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;
import org.usfirst.frc.team1732.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DefaultDriveForward extends CommandGroup {

	public DefaultDriveForward() {
		addSequential(new PreAuto());
		addSequential(new CommandGroup() {
			{
				addParallel(new ArmMagicPosition(Arm.Positions.TUCK));
				addSequential(new FollowVelocityPath(Robot.paths.defaultDriveStraight));
			}
		});
		// addSequential(new ArmMagicPosition(Arm.Positions.INTAKE));
		// addSequential(new PostAuto());
	}
}
