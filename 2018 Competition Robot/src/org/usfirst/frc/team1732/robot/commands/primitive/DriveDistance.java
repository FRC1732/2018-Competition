package org.usfirst.frc.team1732.robot.commands.primitive;

import static org.usfirst.frc.team1732.robot.Robot.PERIOD_S;
import static org.usfirst.frc.team1732.robot.Robot.drivetrain;

import java.util.function.Supplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.sensors.encoders.EncoderReader;
import org.usfirst.frc.team1732.robot.sensors.navx.GyroReader;
import org.usfirst.frc.team1732.robot.util.Debugger;
import org.usfirst.frc.team1732.robot.util.DisplacementPIDSource;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Drives a distance in inches using the encoders
 */
public class DriveDistance extends Command {
	private Supplier<Double> dist;
	private PIDController trans, rot;
	private EncoderReader l, r;
	private GyroReader g = Robot.sensors.navx.makeReader();

	public DriveDistance(Supplier<Double> dist, EncoderReader left, EncoderReader right) {
		requires(drivetrain);
		this.dist = dist;
		l = left;
		r = right;
		// need to tune PIDs
		trans = new PIDController(1.0 / 20, 0, 0.74, new DisplacementPIDSource() {
			@Override
			public double pidGet() {
				return (l.getPosition() + r.getPosition()) * 0.5;
			}
		}, d -> {}, PERIOD_S);
		rot = new PIDController(0.05, 0, 0, new DisplacementPIDSource() {
			@Override
			public double pidGet() {
				return g.getTotalAngle();
			}
		}, d -> {}, PERIOD_S);
	}
	// Drive the reverse of what the encoders read
	public DriveDistance(EncoderReader left, EncoderReader right) {
		this(() -> (left.getPosition() + right.getPosition()) * -0.5);
	}
	public DriveDistance(Supplier<Double> dist) {
		this(dist, drivetrain.getLeftEncoderReader(), drivetrain.getRightEncoderReader());
	}
	public DriveDistance(double dist) {
		this(() -> dist);
	}

	@Override
	protected void initialize() {
		l.zero();
		r.zero();
		trans.setSetpoint(dist.get());
		trans.setAbsoluteTolerance(1);
		trans.enable();
		g.zero();
		rot.setSetpoint(0);
		rot.setAbsoluteTolerance(1);
		rot.enable();
		drivetrain.setBrake();
		Debugger.logStart(this, trans.getSetpoint() + " inches");
	}

	@Override
	protected void execute() {
		drivetrain.drive.arcadeDrive(trans.get(), rot.get(), false);
	}

	@Override
	protected boolean isFinished() {
		return trans.onTarget() && rot.onTarget();
	}

	@Override
	protected void end() {
		trans.disable();
		rot.disable();
		drivetrain.setStop();
		Debugger.logEnd(this, "%.2f inches", (l.getPosition() + r.getPosition()) * 0.5);
	}
}
