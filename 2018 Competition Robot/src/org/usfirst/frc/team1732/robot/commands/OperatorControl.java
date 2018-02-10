package org.usfirst.frc.team1732.robot.commands;

import java.util.LinkedList;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to manage the teleop commands
 * 
 * Manages no subsystems / sensors
 * 
 * CONFIG/
 */
public class OperatorControl extends Command {

	private LinkedList<Command> commands = new LinkedList<>();

	public OperatorControl() {
		// DO NOT REQUIRE ANYTHING
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		commands.add(new TeleopDrive());
		beginCommands();
	}

	private void beginCommands() {
		commands.forEach((c) -> c.start());
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		commands.removeIf((c)->c.isCompleted());
		if(commands.isEmpty()) {
			System.out.println("All Telop Commands Completeds");
		}
	}

	// Should not complete until disabled
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		commands.removeIf((c)->true);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
