package org.usfirst.frc.team1732.robot.commands.teleop;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HooksToggle extends Command {

	public HooksToggle() {
		requires(Robot.hooks);
	}

	@Override
	protected void initialize() {
		Robot.hooks.setUp();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.hooks.setDown();
	}

}
