package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HooksToggle extends Command {

	public HooksToggle() {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.hooks);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.hooks.setHooksUp();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.hooks.setHooksDown();
	}

}
