package org.usfirst.frc.team1732.robot.commands.primitive;

import static org.usfirst.frc.team1732.robot.Robot.manip;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeWithLimelight extends Command {

	public IntakeWithLimelight() {
		requires(manip);
	}
	protected void initialize() {}
	protected void execute() {}
	protected boolean isFinished() {
		return false;
	}
	protected void end() {
		manip.setStop();
	}
}
