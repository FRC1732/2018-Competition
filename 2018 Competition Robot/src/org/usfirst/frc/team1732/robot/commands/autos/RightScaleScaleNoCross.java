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
public class RightScaleScaleNoCross extends CommandGroup {

	public RightScaleScaleNoCross() {
		addSequential(new ArmHoldPosition(15));
		addSequential(new ManipSetIn());
		addSequential(new ManipSetStop());
		if (DriverStationData.scaleIsLeft) {
			if (DriverStationData.closeSwitchIsLeft) {
				// drive forward auto
			} else {
				// score in right switch
			}
		} else { // score 2x in right scale
			System.out.println("Right scale 2x");
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
		}
	}
}
