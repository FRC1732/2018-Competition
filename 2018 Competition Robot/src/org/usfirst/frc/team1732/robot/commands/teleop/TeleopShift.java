package org.usfirst.frc.team1732.robot.commands.teleop;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopShift extends Command {

	public TeleopShift() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.drivetrain.shiftLow();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.drivetrain.shiftHigh();
	}
}
