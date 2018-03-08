package org.usfirst.frc.team1732.robot.commands.teleop;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopShift extends Command {
	@Override
	protected void initialize() {
		Robot.drivetrain.shiftLow();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.drivetrain.shiftHigh();
	}
}
