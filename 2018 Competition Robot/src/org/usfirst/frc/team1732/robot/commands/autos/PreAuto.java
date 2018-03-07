package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.commands.primitive.ArmElevatorSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmMagicPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetIn;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetStop;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PreAuto extends CommandGroup {

	public PreAuto(Arm.Positions armPos) {
		addSequential(new ArmMagicPosition(Arm.Positions.START));
		addParallel(new CommandGroup() {
			{
				addSequential(new ManipSetIn());
				addSequential(new ArmElevatorSetPosition(Arm.Positions.EXCHANGE, Elevator.Positions.INTAKE));
				addSequential(new ArmMagicPosition(armPos));
				addSequential(new ManipSetStop());
			}
		});
	}
}
