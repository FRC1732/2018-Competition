package org.usfirst.frc.team1732.robot.commands;

import java.util.LinkedList;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to manage the teleop commands
 * 
 * Manages no subsystems / sensors
 * 
 * CONFIG/
 */
public class OperatorControl extends Command {

	public OperatorControl() {
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
	}

	// Should not complete until disabled
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
