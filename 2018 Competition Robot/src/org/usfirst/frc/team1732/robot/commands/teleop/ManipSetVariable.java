package org.usfirst.frc.team1732.robot.commands.teleop;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ManipSetVariable extends InstantCommand {

	public ManipSetVariable() {
		super();
		requires(Robot.manip);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.manip.setOutVariable();
	}

}
