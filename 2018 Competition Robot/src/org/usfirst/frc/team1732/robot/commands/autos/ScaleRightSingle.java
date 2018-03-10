package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autotools.DriverStationData;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmElevatorSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmMagicPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;
import org.usfirst.frc.team1732.robot.commands.primitive.Wait;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.PointProfile;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScaleRightSingle extends CommandGroup {

	public ScaleRightSingle() {
		PointProfile profile;
		double time;
		double percent;

		addParallel(new ArmMagicPosition(Arm.Positions.TUCK));
		// addParallel(new PreAuto(Arm.Positions.TUCK));

		if (DriverStationData.scaleIsLeft) {
			profile = Robot.paths.scaleRightSingleLeftProfile;
			time = profile.getTotalTimeSec();
			percent = 0.8;
		} else {
			profile = Robot.paths.scaleRightSingleRightProfile;
			time = profile.getTotalTimeSec();
			percent = 0.7;
		}

		// makes sure it propertly waits before shooting cube
		addSequential(new CommandGroup() {
			{
				addParallel(new CommandGroup() {
					{
						addSequential(new Wait(time * percent));
						addSequential(
								new ArmElevatorSetPosition(Arm.Positions.SCALE_HIGH, Elevator.Positions.SCALE_HIGH));
					}
				});
				addSequential(new FollowVelocityPath(profile));
			}
		});

		// addSequential(new PreAuto(Arm.Positions.TUCK));
		// addSequential(new ArmElevatorSetPosition(Arm.Positions.SCALE,
		// Elevator.Positions.SCALE_HIGH));
		addSequential(new ManipAutoEject(0.5));
	}
}