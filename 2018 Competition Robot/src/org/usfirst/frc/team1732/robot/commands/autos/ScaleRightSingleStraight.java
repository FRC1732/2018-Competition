package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autotools.DriverStationData;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmElevatorSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmHoldPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmMagicPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveTime;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;
import org.usfirst.frc.team1732.robot.commands.primitive.Wait;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.PointProfile;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScaleRightSingleStraight extends CommandGroup {

	public ScaleRightSingleStraight() {
		PointProfile profile;
		double time;
		double percent;

		// addParallel(new ArmMagicPosition(Arm.Positions.TUCK));
		// addParallel(new PreAuto(Arm.Positions.TUCK));

		if (!DriverStationData.scaleIsLeft) {
			profile = Robot.paths.scaleRightStraight;
			time = profile.getTotalTimeSec();
			percent = 0.7;
			addSequential(new CommandGroup() {
				{
					addParallel(new CommandGroup() {
						{
							addSequential(new ArmHoldPosition());
							addSequential(new Wait(time * percent));
							addSequential(
									new ArmElevatorSetPosition(Arm.Positions.TUCK, Elevator.Positions.SCALE_HIGH));
						}
					});
					addSequential(new FollowVelocityPath(profile));
				}
			});

			// addSequential(new PreAuto(Arm.Positions.TUCK));
			// addSequential(new ArmElevatorSetPosition(Arm.Positions.SCALE,
			// Elevator.Positions.SCALE_HIGH));
			// addSequential(new Wait(0.5));
			addSequential(new ManipAutoEject(0.5));
			addSequential(new ArmMagicPosition(Arm.Positions.SWITCH));
		} else if (!DriverStationData.closeSwitchIsLeft) {
			profile = Robot.paths.scaleRightSwitch;
			time = profile.getTotalTimeSec();
			percent = 0.5;
			addParallel(new CommandGroup() {
				{
					addSequential(new ArmHoldPosition());
					addSequential(new Wait(time * percent));
					addSequential(new ArmElevatorSetPosition(Arm.Positions.TUCK, Elevator.Positions.INTAKE));
				}
			});
			addSequential(new FollowVelocityPath(profile));
			// addSequential(new ArmElevatorSetPosition(Arm.Positions.SCALE,
			// Elevator.Positions.SCALE_HIGH));
			addParallel(new CommandGroup() {
				{
					addParallel(new ArmMagicPosition(Arm.Positions.TUCK));
					addSequential(new Wait(2));
					addSequential(new ManipAutoEject(0.5));
				}
			});
			addSequential(new DriveTime(0.3, 0.3, NeutralMode.Coast, 4));
		} else {
			addSequential(new DriveTime(0.25, 0.25, NeutralMode.Brake, 5));
		}

		// // makes sure it propertly waits before shooting cube
		// addSequential(new CommandGroup() {
		// {
		// addParallel(new CommandGroup() {
		// {
		// addSequential(new Wait(time * percent));
		// addSequential(
		// new ArmElevatorSetPosition(Arm.Positions.SCALE_HIGH,
		// Elevator.Positions.SCALE_HIGH));
		// }
		// });
		// addSequential(new FollowVelocityPath(profile));
		// }
		// });
		//
		// // addSequential(new PreAuto(Arm.Positions.TUCK));
		// // addSequential(new ArmElevatorSetPosition(Arm.Positions.SCALE,
		// // Elevator.Positions.SCALE_HIGH));
		// addSequential(new ManipAutoEject(0.5));
	}
}