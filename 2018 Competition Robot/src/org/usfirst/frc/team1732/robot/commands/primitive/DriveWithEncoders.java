package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithEncoders extends Command {

	// Variable Declaration - PID
	public static double P = 0.026;
	public static double I = 0.00012;
	public static double D = 0.20;

	public static final double JANK_AMP = 0.89;

	public double inches;
	public double angleControl;
	public double L_JANK;
	public double R_JANK;
	public boolean stop = false;
	public final double TOLERANCE = 1.0;

	private static PIDController leftDistance = new PIDController(P, I, D, new PIDSource() {

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {

		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			return Robot.drivetrain.leftEncoder.getPosition();
		}

	}, d -> {
	});

	private static PIDController rightDistance = new PIDController(P, I, D, new PIDSource() {

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {

		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			return Robot.drivetrain.rightEncoder.getPosition();
		}

	}, d -> {
	});

	public DriveWithEncoders(double inches) {
		requires(Robot.drivetrain);
		this.inches = inches;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.drivetrain.resetEncoders();
		leftDistance.setSetpoint(inches);
		leftDistance.setAbsoluteTolerance(TOLERANCE);
		leftDistance.enable();
		rightDistance.setSetpoint(inches);
		rightDistance.setAbsoluteTolerance(TOLERANCE);
		rightDistance.enable();

		Robot.ahrs.zeroYaw();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		angleControl = calculateAngleCorrection() * JANK_AMP;
		if (angleControl < 0.5) {
			R_JANK = 1.0;
			L_JANK = 1.0 - angleControl;
		} else if (angleControl > 0.5) {
			R_JANK = angleControl;
			L_JANK = 1.0;
		} else {
			R_JANK = 0;
			L_JANK = 1.0;
		}
		Robot.drivetrain.drive(leftDistance.get() * L_JANK, leftDistance.get() * R_JANK);
		stop = whenCloseEnough();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return stop;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		leftDistance.disable();
		rightDistance.disable();
		Robot.drivetrain.drive(0, 0);
	}

	private static double calculateAngleCorrection() {
		double angleControl = Robot.navx.getHeading() / 360.0;
		return angleControl;
	}

	protected boolean whenCloseEnough() {
		boolean closeLeft = Math.abs(Robot.drivetrain.leftEncoder.getPosition() - inches) <= TOLERANCE;
		return closeLeft;
	}
}
