package org.usfirst.frc.team1732.robot.commands.autos.base;

import org.usfirst.frc.team1732.robot.commands.primitive.ArmHoldPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetIn;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetStop;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PreAuto extends CommandGroup {

	public PreAuto() { // Arm.Positions armPos) {
		addSequential(new ManipSetIn());
		addSequential(new ManipSetStop());
		addSequential(new ArmHoldPosition(35));
		// addSequential(new ArmMagicPosition(Arm.Positions.START));
		// addParallel(new CommandGroup() {
		// {
		// addSequential(new ManipSetIn());
		// addSequential(new ArmElevatorSetPosition(Arm.Positions.EXCHANGE,
		// Elevator.Positions.INTAKE));
		// if (armPos == Arm.Positions.INTAKE) {
		// addSequential(new ArmMagicIntake());
		// } else {
		// addSequential(new ArmMagicPosition(armPos));
		// }
		// addSequential(new ManipSetStop());
		// }
		// });
	}
}
