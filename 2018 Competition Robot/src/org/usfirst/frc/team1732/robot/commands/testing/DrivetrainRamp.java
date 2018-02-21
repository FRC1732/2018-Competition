package org.usfirst.frc.team1732.robot.commands.testing;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DrivetrainRamp extends Command {

	public DrivetrainRamp() {
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		System.out.println(Robot.drivetrain.rightMaster.configOpenloopRamp(90, 10).name());
		System.out.println(Robot.drivetrain.leftMaster.configOpenloopRamp(90, 10).name());
		Robot.drivetrain.setLeft(1);
		Robot.drivetrain.setRight(1);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.drive.tankDrive(0, 0);
		System.out.println(Robot.drivetrain.leftMaster.configOpenloopRamp(0, 10).name());
		System.out.println(Robot.drivetrain.rightMaster.configOpenloopRamp(0, 10).name());
	}
}
