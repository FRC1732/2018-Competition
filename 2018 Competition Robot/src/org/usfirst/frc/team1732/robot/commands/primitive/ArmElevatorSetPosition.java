package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Use this when you want to move both in parallel
 */
public class ArmElevatorSetPosition extends CommandGroup {

	public ArmElevatorSetPosition(Arm.Positions armPosition, Elevator.Positions elevatorPosition) {
		if (armPosition == Arm.Positions.INTAKE) {
			addParallel(new ArmMagicIntake());
		} else {
			addParallel(new ArmMagicPosition(armPosition));
		}
		if (elevatorPosition == Elevator.Positions.INTAKE) {
			addParallel(new ElevatorMagicIntake());
		} else {
			addParallel(new ElevatorMagicPosition(elevatorPosition));
		}
	}

}
