package org.usfirst.frc.team1732.robot.commands.autos.base;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmElevatorSetPosition;
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
public class RightScaleLeftTwice extends CommandGroup {

	public RightScaleLeftTwice() {
		// score in the left scale 2x
		System.out.println("Left scale 2x");
		PointProfile profile1 = Robot.paths.rightScaleCross;
		double time1 = profile1.getTotalTimeSec();
		double percent1 = 0.8;
		// score in the scale
		addSequential(new CommandGroup() {
			{
				addParallel(new CommandGroup() {
					{
						addSequential(new Wait(time1 * percent1));
						addSequential(new ArmElevatorSetPosition(Arm.Positions.TUCK, Elevator.Positions.SCALE_AUTO));
					}
				});
				addSequential(new FollowVelocityPath(profile1));
			}
		});
		addSequential(new ManipAutoEject(0.5));
		// get cube
		PointProfile profile2 = Robot.paths.rightCubeGrabStraightLeft;
		// addSequential(new ManipSetOut(0));
		addSequential(new CommandGroup() {
			{
				addParallel(new ArmElevatorSetPosition(Arm.Positions.INTAKE, Elevator.Positions.INTAKE));
				addSequential(new ManipSetOut(0));
				addSequential(new ManipSetIn());
				// addSequential(new FollowVelocityPathLimelight(profile2, 0.3));
				addSequential(new FollowVelocityPathLimelight(profile2, 0.4));
				addSequential(new DriveVoltage(0, 0, NeutralMode.Brake));
				addSequential(new ManipSetStop(0.5));
			}
		});
	}
}
