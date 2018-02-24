package org.usfirst.frc.team1732.robot.commands.teleop;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.drivercontrol.DifferentialDrive;
import org.usfirst.frc.team1732.robot.util.ThreadCommand;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithJoysticks extends ThreadCommand {

	private final DifferentialDrive drive;

	public DriveWithJoysticks(DifferentialDrive drive) {
		requires(Robot.drivetrain);
		this.drive = drive;
	}

	// Called just before this Command runs the first time
	@Override
	protected void init() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void exec() {
		drive.tankDrive(Robot.joysticks.getLeft(), Robot.joysticks.getRight(), false);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
