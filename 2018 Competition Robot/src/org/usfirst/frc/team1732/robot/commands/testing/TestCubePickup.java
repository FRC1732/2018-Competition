package org.usfirst.frc.team1732.robot.commands.testing;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.autos.ManipAutoEject;
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
public class TestCubePickup extends CommandGroup {

	public TestCubePickup() {
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
	}
}
