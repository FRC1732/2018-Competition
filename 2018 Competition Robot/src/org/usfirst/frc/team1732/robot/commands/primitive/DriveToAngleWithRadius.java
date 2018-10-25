package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToAngleWithRadius extends Command {

	// Variable Declaration - PID
	public static double P = 0.026;
	public static double I = 0.00008;
	public static double D = 0.20;

	// Variable Declaration - Misc
	public double angle;
	public double radius;
	public boolean forwards;
	public double direction = 1.0;
	public double leftSpeed = 0.0;
	public double rightSpeed = 0.0;
	public boolean stop = false;
	public final double TOLERANCE = 1.0;

	private static PIDController angleController = new PIDController(P, I, D, new PIDSource() {

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {

		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			return Robot.ahrs.getAngle();
		}

	}, d -> {
	});

	public DriveToAngleWithRadius(double angle, double radius, boolean forwards) {
		requires(Robot.drivetrain);
		this.angle = angle;
		this.radius = radius;
		this.forwards = forwards;
	}

	@Override
	protected void initialize() {

		// Hardware Reading
		Robot.ahrs.zeroYaw();

		// Initialize Clockwise/CounterClockwise
		if (angle < 0) {
			rightSpeed = -1.0;
			leftSpeed = -calculateInnerSpeed(radius);
		} else {
			leftSpeed = 1.0;
			rightSpeed = calculateInnerSpeed(radius);
		}

		// Initialize Forwards/Backwards
		if (!forwards) {
			angle = -angle;
		}

		// PID
		angleController.setSetpoint(angle);
		angleController.setAbsoluteTolerance(TOLERANCE);
		angleController.enable();
		Robot.drivetrain.drive(0, 0);
	}

	@Override
	protected void execute() {
		Robot.drivetrain.drive(angleController.get() * leftSpeed * direction,
				angleController.get() * rightSpeed * direction);
		stop = whenCloseEnough();
	}

	@Override
	protected boolean isFinished() {
		return stop;
	}

	@Override
	protected void end() {
		angleController.disable();
		Robot.drivetrain.drive(0, 0);
	}

	private static double calculateInnerSpeed(double radius) {
		// Only pass positive radii to avoid outerDistance being 0
		double innerCircleRadius = radius - 33.5;
		double outerCircleRadius = radius + 33.5;
		double innerDistance = Math.PI * Math.pow(innerCircleRadius, 2);
		double outerDistance = Math.PI * Math.pow(outerCircleRadius, 2);
		if (innerCircleRadius < 0) {
			innerDistance = -innerDistance;
		}
		if (outerCircleRadius < 0) {
			outerDistance = -outerDistance;
		}
		// Speed = InnerDistance / OuterDistance
		double innerSpeed = innerDistance / outerDistance;
		return innerSpeed;
	}

	protected boolean whenCloseEnough() {
		boolean isClose = Math.abs(Robot.navx.getHeading() - angle) <= TOLERANCE;
		return isClose;
	}
}
