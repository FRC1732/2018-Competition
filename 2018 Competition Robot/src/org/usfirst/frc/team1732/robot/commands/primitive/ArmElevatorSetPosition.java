package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Use this when you want to move both in parallel
 */
public class ArmElevatorSetPosition extends CommandGroup {

	public ArmElevatorSetPosition(Arm.Positions armPosition, Elevator.Positions elevatorPosition) {
		this(Robot.arm.getValue(armPosition), Robot.elevator.getValue(elevatorPosition));
	}

	public ArmElevatorSetPosition(int armPosition, int elevatorPosition) {
		addParallel(new ArmSetPosition(armPosition));
		addParallel(new ElevatorSetPosition(elevatorPosition));
	}
}
