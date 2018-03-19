package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autotools.DriverStationData;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmElevatorSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmHoldPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveVoltage;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPathLimelight;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetIn;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetOut;
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
public class ScaleRightDouble extends CommandGroup {

	public ScaleRightDouble() {
		addSequential(new ArmHoldPosition(15));
		addSequential(new ManipSetIn());
		addSequential(new ManipSetStop());
		if (DriverStationData.scaleIsLeft && !DriverStationData.closeSwitchIsLeft) {
			// cross and score in the left scale, then cross and score in right switch
		} else if (DriverStationData.scaleIsLeft && !DriverStationData.closeSwitchIsLeft) {
			// score in the left scale, then left switch
		} else if (!DriverStationData.scaleIsLeft && DriverStationData.closeSwitchIsLeft) {
			// score in the right scale, pickup 2nd from the left cube
			System.out.println("Scale straight, switch opposite");
			PointProfile profile1 = Robot.paths.scaleRightStraight;
			double time1 = profile1.getTotalTimeSec();
			double percent1 = 0.5;
			// score in the scale
			addSequential(new CommandGroup() {
				{
					addParallel(new CommandGroup() {
						{
							addSequential(new Wait(time1 * percent1));
							addSequential(
									new ArmElevatorSetPosition(Arm.Positions.TUCK, Elevator.Positions.SCALE_AUTO));
						}
					});
					addSequential(new FollowVelocityPath(profile1));
				}
			});
			addSequential(new ManipAutoEject(0.5));
			PointProfile profile2 = Robot.paths.rightCubeGrabLeftSwitch;
			// addSequential(new ManipSetOut(0));
			addSequential(new ManipSetOut(0));
			addSequential(new CommandGroup() {
				{
					addParallel(new ArmElevatorSetPosition(Arm.Positions.INTAKE, Elevator.Positions.INTAKE));
					addSequential(new FollowVelocityPath(profile2));
					addSequential(new DriveVoltage(0, 0, NeutralMode.Brake));
				}
			});
			PointProfile profile20 = Robot.paths.driveBackwardSlightlyMore;
			addSequential(new ManipSetIn());
			addSequential(new FollowVelocityPathLimelight(profile20, 0.2));
			addSequential(new DriveVoltage(0, 0, NeutralMode.Brake));
			PointProfile profile3 = Robot.paths.driveForwardSlightly;
			addSequential(new CommandGroup() {
				{
					addParallel(new CommandGroup() {
						{
							addSequential(new ArmElevatorSetPosition(Arm.Positions.SWITCH, Elevator.Positions.HUMAN));
							addSequential(new ManipSetStop());
						}
					});
					// addSequential(new ArmElevatorSetPosition(Arm.Positions.SWITCH,
					// Elevator.Positions.HUMAN));
					// addSequential(new ArmMagicPosition(Arm.Positions.SWITCH));
					// addSequential(new ElevatorMagicPosition(Elevator.Positions.HUMAN));
					addSequential(new FollowVelocityPath(profile3));
					addSequential(new DriveVoltage(0, 0, NeutralMode.Brake));
				}
			});
			PointProfile profile4 = Robot.paths.driveBackwardSlightlyMore;
			double time4 = profile4.getTotalTimeSec();
			addParallel(new CommandGroup() {
				{
					addSequential(new Wait(0.7 * time4));
					addSequential(new ManipAutoEject(0.5));
				}
			});
			addSequential(new FollowVelocityPath(profile4));
			addSequential(new DriveVoltage(0, 0, NeutralMode.Brake));
		} else if (!DriverStationData.scaleIsLeft && !DriverStationData.closeSwitchIsLeft) {
			// score in the right scale, score in the right switch
			System.out.println("Scale straight, switch straight");
			PointProfile profile1 = Robot.paths.scaleRightStraight;
			double time1 = profile1.getTotalTimeSec();
			double percent1 = 0.5;
			// score in the scale
			addSequential(new CommandGroup() {
				{
					addParallel(new CommandGroup() {
						{
							addSequential(new Wait(time1 * percent1));
							addSequential(
									new ArmElevatorSetPosition(Arm.Positions.TUCK, Elevator.Positions.SCALE_AUTO));
						}
					});
					addSequential(new FollowVelocityPath(profile1));
				}
			});
			addSequential(new ManipAutoEject(0.5));
			// get cube
			PointProfile profile2 = Robot.paths.rightCubeGrabStraight;
			// addSequential(new ManipSetOut(0));
			addSequential(new CommandGroup() {
				{
					addParallel(new ArmElevatorSetPosition(Arm.Positions.INTAKE, Elevator.Positions.INTAKE));
					addSequential(new ManipSetOut(0));
					addSequential(new ManipSetIn());
					// addSequential(new FollowVelocityPathLimelight(profile2, 0.3));
					addSequential(new FollowVelocityPathLimelight(profile2, 0.4));
					addSequential(new DriveVoltage(0, 0, NeutralMode.Brake));
				}
			});
			PointProfile profile3 = Robot.paths.driveForwardSlightly;
			addSequential(new CommandGroup() {
				{
					addParallel(new CommandGroup() {
						{
							addSequential(new ArmElevatorSetPosition(Arm.Positions.SWITCH, Elevator.Positions.HUMAN));
							addSequential(new ManipSetStop());
						}
					});
					// addSequential(new ArmElevatorSetPosition(Arm.Positions.SWITCH,
					// Elevator.Positions.HUMAN));
					// addSequential(new ArmMagicPosition(Arm.Positions.SWITCH));
					// addSequential(new ElevatorMagicPosition(Elevator.Positions.HUMAN));
					addSequential(new FollowVelocityPath(profile3));
				}
			});
			PointProfile profile4 = Robot.paths.driveBackwardSlightly;
			addSequential(new FollowVelocityPath(profile4));
			addSequential(new DriveVoltage(0, 0, NeutralMode.Brake));
			addSequential(new ManipAutoEject(0.5));
		} else {
			// should never get here - it hopefully isn't possible
			// put in a drive forward just in case
		}
	}
}
