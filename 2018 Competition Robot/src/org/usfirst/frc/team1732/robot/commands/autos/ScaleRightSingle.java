package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autotools.DriverStationData;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmElevatorSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;
import org.usfirst.frc.team1732.robot.commands.primitive.Wait;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.PointProfile;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScaleRightSingle extends CommandGroup {

	public ScaleRightSingle() {
		PointProfile profile;
		addParallel(new PreAuto(Arm.Positions.TUCK));
		if (DriverStationData.scaleIsLeft) {
			profile = Robot.paths.scaleRightSingleLeftProfile;
			double time = profile.getTotalTimeSec();
			addParallel(new CommandGroup() {
				{
					addSequential(new Wait(time * 0.9));
					addSequential(new ArmElevatorSetPosition(Arm.Positions.SCALE, Elevator.Positions.SCALE_HIGH));
				}
			});
			addSequential(new FollowVelocityPath(profile));
		} else {
			profile = Robot.paths.scaleRightSingleRightProfile;
			double time = profile.getTotalTimeSec();
			addParallel(new CommandGroup() {
				{
					addSequential(new Wait(time * 0.7));
					addSequential(new ArmElevatorSetPosition(Arm.Positions.SCALE, Elevator.Positions.SCALE_HIGH));
				}
			});
			addSequential(new FollowVelocityPath(profile));
		}
		addSequential(new ManipAutoEject(0.5));
	}
}