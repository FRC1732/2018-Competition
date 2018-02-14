package org.usfirst.frc.team1732.robot.commands.teleop;

import org.usfirst.frc.team1732.robot.commands.primitive.ArmSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.ElevatorSetPosition;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ArmElevatorSetPosition extends CommandGroup {

	public ArmElevatorSetPosition(Arm.Positions armPosition, Elevator.Positions elevatorPosition) {
		this(armPosition.value, elevatorPosition.value);
	}

	public ArmElevatorSetPosition(double armPosition, double elevatorPosition) {
		addParallel(new ArmSetPosition(armPosition));
		addParallel(new ElevatorSetPosition(elevatorPosition));
	}
}
