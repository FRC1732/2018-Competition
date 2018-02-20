package org.usfirst.frc.team1732.robot.commands.primitive;

import static org.usfirst.frc.team1732.robot.Robot.PERIOD_S;
import static org.usfirst.frc.team1732.robot.Robot.drivetrain;
import static org.usfirst.frc.team1732.robot.Robot.sensors;

import org.usfirst.frc.team1732.robot.sensors.encoders.EncoderReader;
import org.usfirst.frc.team1732.robot.sensors.navx.GyroReader;
import org.usfirst.frc.team1732.robot.util.DisplacementPIDSource;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Drives a distance in inches using the encoders
 */
public class DriveDistance extends Command {
	private PIDController trans, rot;
	EncoderReader l = drivetrain.getLeftEncoderReader(), r = drivetrain.getRightEncoderReader();
	GyroReader g = sensors.navx.makeReader();

	public DriveDistance(double dist) {
		requires(drivetrain);
		// need to tune PIDs
		trans = new PIDController(0.1, 0, 0.8, new DisplacementPIDSource() {
			public double pidGet() {
				return (l.getPosition() + r.getPosition()) / 2;
			}
		}, d -> {}, PERIOD_S);
		trans.setSetpoint(dist);
		trans.setAbsoluteTolerance(1);
		rot = new PIDController(0.05, 0, 0, new DisplacementPIDSource() {
			public double pidGet() {
				return g.getTotalAngle();
			}
		}, d -> {}, PERIOD_S);
		rot.setSetpoint(0);
		rot.setAbsoluteTolerance(1);
	}
	protected void initialize() {
		l.zero();
		r.zero();
		trans.enable();
		g.zero();
		rot.enable();
		drivetrain.setBrakeMode(true);
	}
	protected void execute() {
		drivetrain.drive.arcadeDrive(trans.get(), rot.get(), false);
	}
	protected boolean isFinished() {
		return trans.onTarget() && rot.onTarget();
	}
	protected void end() {
		trans.disable();
		rot.disable();
		drivetrain.setStop();
	}
}
