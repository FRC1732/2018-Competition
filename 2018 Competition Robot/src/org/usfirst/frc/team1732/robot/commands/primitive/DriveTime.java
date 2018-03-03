package org.usfirst.frc.team1732.robot.commands.primitive;

import static org.usfirst.frc.team1732.robot.Robot.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * DO NOT USE IN A REAL MATCH, JUST FOR TESTING
 */
public class DriveTime extends Command {
	private final double S, T;

	public DriveTime(double speed, double time) {
		requires(drivetrain);
		S = speed;
		T = time;
	}

	protected void initialize() {
		drivetrain.drive.tankDrive(S, S);
		drivetrain.setBrake();
		super.setTimeout(T);
	}
	protected boolean isFinished() {
		return super.isTimedOut();
	}
	protected void end() {
		drivetrain.setStop();
	}
}
