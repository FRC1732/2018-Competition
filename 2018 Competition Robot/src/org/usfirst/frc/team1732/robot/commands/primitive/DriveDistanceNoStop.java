package org.usfirst.frc.team1732.robot.commands.primitive;

import static org.usfirst.frc.team1732.robot.Robot.PERIOD_S;
import static org.usfirst.frc.team1732.robot.Robot.drivetrain;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.sensors.encoders.EncoderReader;
import org.usfirst.frc.team1732.robot.sensors.navx.GyroReader;
import org.usfirst.frc.team1732.robot.util.Debugger;
import org.usfirst.frc.team1732.robot.util.DisplacementPIDSource;
import org.usfirst.frc.team1732.robot.util.Util;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDistanceNoStop extends Command {
	private final PIDController rot;
	private final EncoderReader l = drivetrain.getLeftEncoderReader(), r = drivetrain.getRightEncoderReader();
	private final GyroReader g = Robot.sensors.navx.makeReader();
	private final double distance, endSpeed;

	public DriveDistanceNoStop(double dist, double endSpeed) {
		requires(drivetrain);
		distance = dist;
		this.endSpeed = endSpeed;
		rot = new PIDController(0.05, 0, 0, new DisplacementPIDSource() {
			@Override
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
		g.zero();
		rot.enable();
		drivetrain.setBrake();
		Debugger.logStart(this, "Dist = %.2f, End Speed = %.2f", distance, endSpeed);
	}
	protected void execute() {
		double percentDone = ((l.getPosition() + r.getPosition()) / 2) / distance;
		drivetrain.drive.arcadeDrive(Util.cerp(1, endSpeed, percentDone), rot.get(), false);
	}
	protected boolean isFinished() {
		return ((l.getPosition() + r.getPosition()) / 2) > distance;
	}
}
