package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.util.Debugger;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ElevatorHoldPosition extends InstantCommand {

	public ElevatorHoldPosition() {
		requires(Robot.elevator);
	}

	@Override
	protected void initialize() {
		Debugger.logStart(this);
		int pos = Robot.elevator.getEncoderPulses();
		Robot.elevator.useMagicControl(pos);
		Robot.elevator.holdPosition();
	}

}
