package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Use this when you want to move both in parallel
 */
public class ArmElevatorSetPosition extends CommandGroup {

	public ArmElevatorSetPosition(Arm.Positions armPosition, Elevator.Positions elevatorPosition) {
		this(armPosition.value, elevatorPosition.value);
	}

	public ArmElevatorSetPosition(int armPosition, int elevatorPosition) {
		addParallel(new ArmSetPosition(armPosition));
		addParallel(new ElevatorSetPosition(elevatorPosition));
	}
}
