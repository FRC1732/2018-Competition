package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autotools.DriverStationData;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmElevatorSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmHoldPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveVoltage;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPathLimelight;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetIn;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetStop;
import org.usfirst.frc.team1732.robot.commands.primitive.Wait;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.PointProfile;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScaleLeftDouble extends CommandGroup {

	public ScaleLeftDouble() {
		addSequential(new ArmHoldPosition());
		addSequential(new ManipSetIn());
		addSequential(new ManipSetStop());
		if (!DriverStationData.scaleIsLeft && DriverStationData.closeSwitchIsLeft) {
			// //score in the switch, then cross and score in right scale
			// PointProfile profile1 = Robot.paths.scaleLeftSwitch;
			// double time1 = profile1.getTotalTimeSec();
			// double percent1 = 0.5;
			// // score in the switch
			// addSequential(new CommandGroup() {
			// {
			// addParallel(new CommandGroup() {
			// {
			// addSequential(new Wait(time1 * percent1));
			// addSequential(new ArmElevatorSetPosition(Arm.Positions.SWITCH,
			// Elevator.Positions.INTAKE));
			// }
			// });
			// addSequential(new FollowVelocityPath(profile1));
			// }
			// });
			// addSequential(new ManipAutoEject(0.5));
			// // pick up next cube
			// PointProfile profile2 = Robot.paths.leftCubeGrabAfterSwitch;
			// double time2 = profile2.getTotalTimeSec();
			// double percent2 = 0.1;
			// addSequential(new CommandGroup() {
			// {
			// addSequential(new ManipSetIn());
			// addParallel(new CommandGroup() {
			// {
			// addSequential(new Wait(time2 * percent2));
			// addSequential(new ArmElevatorSetPosition(Arm.Positions.INTAKE,
			// Elevator.Positions.INTAKE));
			// }
			// });
			// addSequential(new FollowVelocityPath(profile2));
			// addSequential(new ManipSetStop());
			// }
			// });
			// addSequential(new ManipAutoEject(0.5));
			// // score in the other scale
		} else if (!DriverStationData.scaleIsLeft && !DriverStationData.closeSwitchIsLeft) {
			// score in the right scale, then right switch
		} else if (DriverStationData.scaleIsLeft && !DriverStationData.closeSwitchIsLeft) {
			// score in the left scale, pickup 3rd from the right cube
		} else if (DriverStationData.scaleIsLeft && DriverStationData.closeSwitchIsLeft) {
			// score in the left scale, score in the left switch
			PointProfile profile1 = Robot.paths.scaleLeftStraight;
			double time1 = profile1.getTotalTimeSec();
			double percent1 = 0.4;
			// score in the scale
			addSequential(new CommandGroup() {
				{
					addParallel(new CommandGroup() {
						{
							addSequential(new Wait(time1 * percent1));
							addSequential(
									new ArmElevatorSetPosition(Arm.Positions.TUCK, Elevator.Positions.SCALE_HIGH));
						}
					});
					addSequential(new FollowVelocityPath(profile1));
				}
			});
			addSequential(new ManipAutoEject(0.5));
			// get cube
			PointProfile profile2 = Robot.paths.leftCubeGrabStraight;
			addSequential(new CommandGroup() {
				{
					addParallel(new CommandGroup() {
						{
							addSequential(new ArmElevatorSetPosition(Arm.Positions.INTAKE, Elevator.Positions.INTAKE));
							addSequential(new ManipSetIn());
						}
					});
					addSequential(new FollowVelocityPathLimelight(profile2, 0.3));
				}
			});
			// score in switch
			PointProfile profile3 = Robot.paths.driveBackwardSlightly;
			addSequential(new CommandGroup() {
				{
					addParallel(new CommandGroup() {
						{
							addSequential(new Wait(0.2));
							addSequential(new ManipSetStop());
						}
					});
					addSequential(new ArmElevatorSetPosition(Arm.Positions.SWITCH, Elevator.Positions.INTAKE));
					addSequential(new FollowVelocityPath(profile3));
				}
			});
			addSequential(new DriveVoltage(0, 0, NeutralMode.Brake));
			addSequential(new ManipAutoEject(0.8));
		} else {
			// should never get here - it hopefully isn't possible
			// put in a drive forward just in case
		}
	}
}
