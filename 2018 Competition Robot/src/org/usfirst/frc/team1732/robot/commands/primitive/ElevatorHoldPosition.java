package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ElevatorHoldPosition extends InstantCommand {

	public ElevatorHoldPosition() {
		requires(Robot.elevator);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("Running elevator hold command");
		int pos = Robot.elevator.getEncoderPulses();
		Robot.elevator.useMagicControl(pos);
		Robot.elevator.holdPosition();
	}

}
