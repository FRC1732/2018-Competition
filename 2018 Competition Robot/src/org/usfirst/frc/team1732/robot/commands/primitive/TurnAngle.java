package org.usfirst.frc.team1732.robot.commands.primitive;

import static org.usfirst.frc.team1732.robot.Robot.PERIOD_S;
import static org.usfirst.frc.team1732.robot.Robot.drivetrain;

import org.usfirst.frc.team1732.robot.util.DisplacementPIDSource;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns the robot an angle using NavX
 */
public class TurnAngle extends Command {
	private PIDController left, right;

	public TurnAngle(double angle) {
		requires(drivetrain);
		// need to tune PIDs
		left = new PIDController(1, 0, 0, new DisplacementPIDSource() {
			public double pidGet() {
				return 0;
			}
		}, d -> drivetrain.setLeft(d), PERIOD_S);
		left.setAbsoluteTolerance(0.05);
		right = new PIDController(1, 0, 0, new DisplacementPIDSource() {
			public double pidGet() {
				return 0;
			}
		}, d -> drivetrain.setRight(d), PERIOD_S);
		right.setAbsoluteTolerance(0.05);
	}
	protected void initialize() {
		left.enable();
		right.enable();
	}
	protected boolean isFinished() {
		return left.onTarget() || right.onTarget();
	}
	protected void end() {
		left.disable();
		right.disable();
		drivetrain.setStop();
	}
}
