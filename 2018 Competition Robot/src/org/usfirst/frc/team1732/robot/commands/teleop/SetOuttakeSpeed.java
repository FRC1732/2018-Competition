package org.usfirst.frc.team1732.robot.commands.teleop;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetOuttakeSpeed extends InstantCommand {

	private final double variableOut;

	public SetOuttakeSpeed(double variableOut) {
		this.variableOut = variableOut;
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.manip.setVariableOutSpeed(variableOut);
	}

}
