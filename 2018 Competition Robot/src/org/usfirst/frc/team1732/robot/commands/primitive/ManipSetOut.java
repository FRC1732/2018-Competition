package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ManipSetOut extends InstantCommand {

	public ManipSetOut() {
		requires(Robot.manip);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.manip.setOut();
		System.out.println("ManipSetOut: Ran");
	}
}