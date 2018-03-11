package org.usfirst.frc.team1732.robot.commands.testing;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.util.Debugger;

import edu.wpi.first.wpilibj.command.Command;

public class DrivetrainRamp extends Command {

	public DrivetrainRamp() {
		requires(Robot.drivetrain);
	}

	protected void initialize() {
		Debugger.logDetailedInfo(Robot.drivetrain.leftMaster.configOpenloopRamp(90, 10).name());
		Debugger.logDetailedInfo(Robot.drivetrain.rightMaster.configOpenloopRamp(90, 10).name());
		Robot.drivetrain.setLeft(1);
		Robot.drivetrain.setRight(1);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.drivetrain.drive.tankDrive(0, 0);
		Debugger.logDetailedInfo(Robot.drivetrain.leftMaster.configOpenloopRamp(0, 10).name());
		Debugger.logDetailedInfo(Robot.drivetrain.rightMaster.configOpenloopRamp(0, 10).name());
	}
}
