package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autotools.DriverStationData;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmElevatorSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmHoldPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;
import org.usfirst.frc.team1732.robot.commands.primitive.Wait;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.PointProfile;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScaleLeftDouble extends CommandGroup {

	public ScaleLeftDouble() {
		PointProfile profile;
		double time;
		double percent;

		addSequential(new ArmHoldPosition());

		if (!DriverStationData.scaleIsLeft && DriverStationData.closeSwitchIsLeft) {
			// score in the switch, then cross and score in right scale
			profile = Robot.paths.scaleLeftSwitch;
			time = profile.getTotalTimeSec();
			percent = 0.5;
			addSequential(new CommandGroup() {
				{
					addParallel(new CommandGroup() {
						{
							addSequential(new Wait(time * percent));
							addSequential(new ArmElevatorSetPosition(Arm.Positions.SWITCH, Elevator.Positions.INTAKE));
						}
					});
					addSequential(new FollowVelocityPath(profile));
				}
			});
			addSequential(new ManipAutoEject(0.5));

		} else if (!DriverStationData.scaleIsLeft && !DriverStationData.closeSwitchIsLeft) {
			// score in the right scale, then right switch
		} else if (DriverStationData.scaleIsLeft && !DriverStationData.closeSwitchIsLeft) {
			// score in the left scale, pickup 3rd from the right cube
		} else if (DriverStationData.scaleIsLeft && DriverStationData.closeSwitchIsLeft) {
			// score in the left scale, score in the left switch
		} else {
			// should never get here - it hopefully isn't possible
			// put in a drive forward just in case
		}
	}
}
