package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class HooksSetDown extends InstantCommand {

	public HooksSetDown() {
		requires(Robot.hooks);
	}

	@Override
	protected void initialize() {
		Robot.hooks.setDown();
	}

}
