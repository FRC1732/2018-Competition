package org.usfirst.frc.team1732.robot.commands.testing;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.autos.ManipAutoEject;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmElevatorSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveVoltage;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;
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
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
		addSequential(new ManipSetOut(0));
		PointProfile profile2 = Robot.paths.leftCubeGrabRightSwitch;
		addSequential(new CommandGroup() {
			{
				addParallel(new CommandGroup() {
					{
						addSequential(new ArmElevatorSetPosition(Arm.Positions.INTAKE, Elevator.Positions.INTAKE));
						addSequential(new ManipSetIn());
					}
				});
				addSequential(new FollowVelocityPath(profile2));// Limelight(profile2, 0.3));
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
	}
}
