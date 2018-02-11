package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.util.DisplacementPIDSource;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Drives a distance in inches using the encoders
 */
public class DriveDistance extends Command {
	private PIDController left, right;

	public DriveDistance(double dist) {
		requires(Robot.drivetrain);
		left = new PIDController(0, 0, 0, new DisplacementPIDSource() {
			public double pidGet() {
				return 0;
			}
		}, d -> {});
		right = new PIDController(0, 0, 0, new DisplacementPIDSource() {
			public double pidGet() {
				return 0;
			}
		}, d -> {});
	}

	// Called just before this Command runs the first time
	protected void initialize() {}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}
	protected void end() {
		Robot.drivetrain.setStop();
	}
}
