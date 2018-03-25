package org.usfirst.frc.team1732.robot.commands.autos.base;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmElevatorSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;
import org.usfirst.frc.team1732.robot.commands.primitive.Wait;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.PointProfile;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightSwitchRightSide extends CommandGroup {

	public RightSwitchRightSide() {
		// right switch (from side)
		System.out.println("Right switch from right");
		PointProfile profile1 = Robot.paths.rightSwitchRightSide;
		double time1 = profile1.getTotalTimeSec();
		double percent1 = 0.7;
		// score in the scale
		addSequential(new PreAuto());
		addSequential(new CommandGroup() {
			{
				addParallel(new CommandGroup() {
					{
						addSequential(new Wait(time1 * percent1));
						addSequential(new ArmElevatorSetPosition(Arm.Positions.TUCK, Elevator.Positions.INTAKE));
					}
				});
				addSequential(new FollowVelocityPath(profile1));
			}
		});
		addSequential(new Wait(2));
		addSequential(new ManipAutoEject(1, 0.4));
		addSequential(new PostAuto());
	}
}
