package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ClimberRunReverse extends InstantCommand {

	public ClimberRunReverse() {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.climber);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.climber.reverseClimb();
	}

}
