package org.usfirst.frc.team1732.robot.commands.primitive;

import static org.usfirst.frc.team1732.robot.Robot.drivetrain;

import java.util.function.Supplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.sensors.navx.GyroReader;
import org.usfirst.frc.team1732.robot.util.Debugger;
import org.usfirst.frc.team1732.robot.util.NotifierCommand;
import org.usfirst.frc.team1732.robot.util.Util;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class TurnAngle extends NotifierCommand {

	private static final double DEADBAND_TIME = 0.5;
	private static final double ANGLE_DEADBAND = 1;
	private static final double HEADING_P = 2.5;
	private static final double startMinVel = 20;
	private static final double endMinVel = 8;
	private static final double endMaxVel = 30;

	private final Supplier<Double> angle;
	private final Timer deadbandTimer;
	private boolean inDeadband = false;
	private double goalAngle;
	private double maxVel;
	private double k;
	private GyroReader navx;
	private double endZone = 20;

	public TurnAngle(Supplier<Double> a) {
		super(5);
		requires(drivetrain);
		navx = Robot.sensors.navx.makeReader();
		deadbandTimer = new Timer();
		angle = a;
	}

	public TurnAngle(double angle) {
		this(() -> angle);
	}

	private double getVelocity(double angle) {
		return maxVel * (-0.5 * Math.cos(angle * k) + 0.5);
	}

	@Override
	protected void init() {
		double a = angle.get();
		deadbandTimer.reset();
		deadbandTimer.stop();
		this.goalAngle = a + Math.signum(a);
		this.maxVel = Math.abs(a) * 2.5 / 3.0;
		this.k = Math.PI * 2 / (Math.abs(a));
		endZone = (25 * 90) / (60) * (maxVel / a);
		navx.zero();
		// pid.setSetpoint(goalAngle);
		drivetrain.setBrake();
		Robot.drivetrain.velocityGains.selectGains(drivetrain.leftMaster, drivetrain.rightMaster);
		Debugger.logStart(this, goalAngle + " degrees");
		Debugger.logDetailedInfo("Endzone : " + endZone);
	}

	@Override
	protected void exec() {
		double currentHeading = navx.getTotalAngle();
		double headingError = goalAngle - currentHeading;
		double headingAdjustment = headingError * HEADING_P;

		double vel = getVelocity(currentHeading) * Math.signum(headingError);

		// bump start vel
		if (Math.abs(currentHeading) < Math.abs(goalAngle / 2) && Math.abs(vel) < startMinVel) {
			Debugger.logDetailedInfo("bumping start velocity");
			vel = startMinVel * Math.signum(headingError);
		}

		if (Math.abs(currentHeading) > Math.abs(goalAngle / 2) && Math.abs(vel) < endMinVel) {
			vel = endMinVel * Math.signum(headingError);
			Debugger.logDetailedInfo("bumping end velocity");
		}

		if (Math.abs(currentHeading) > Math.abs(goalAngle - endZone * Math.signum(goalAngle))
				&& Math.abs(vel) > endMaxVel) {
			Debugger.logDetailedInfo("capping end velocity");
			vel = endMaxVel * Math.signum(headingError);
		}

		// stuff during second half of turn (after maxVel has been reached
		// theoeretically)
		// if (Math.abs(currentHeading) > Math.abs(goalAngle / 2)) {
		// // if we're towards the end, cap velocity so we start slowing down
		// if (Math.abs(currentHeading) > Math.abs(goalAngle - endZone *
		// Math.signum(goalAngle))
		// && Math.abs(vel) > endMaxVel) {
		// System.out.println("capping end velocity");
		// vel = endMaxVel * Math.signum(headingError);
		// }
		// // // if we've past the setpoint, agressivly go back with PID
		// // if (Math.abs(currentHeading) > Math.abs(goalAngle)) {
		// // System.out.println("Using heading P");
		// // vel = headingAdjustment;
		// // }
		// // make sure we don't go too slow at the end
		// if (Math.abs(vel) < endMinVel) {
		// vel = endMinVel * Math.signum(headingError);
		// System.out.println("bumping end velocity");
		// }
		// }

		if (Util.epsilonEquals(goalAngle, currentHeading, ANGLE_DEADBAND + 0.5)) {
			Debugger.logDetailedInfo("trying to stop robot");
			drivetrain.leftMaster.set(ControlMode.Velocity, drivetrain.velInToUnits(0));
			drivetrain.rightMaster.set(ControlMode.Velocity, drivetrain.velInToUnits(0));
		} else {
			drivetrain.leftMaster.set(ControlMode.Velocity, drivetrain.velInToUnits(vel));
			drivetrain.rightMaster.set(ControlMode.Velocity, drivetrain.velInToUnits(-vel));
		}
		// drivetrain.leftTalon1.set(ControlMode.Velocity,
		// drivetrain.convertVelocitySetpoint(headingAdjustment));
		// drivetrain.rightTalon1.set(ControlMode.Velocity,
		// drivetrain.convertVelocitySetpoint(-headingAdjustment));

		if (!inDeadband && Math.abs(goalAngle - currentHeading) < ANGLE_DEADBAND) {
			deadbandTimer.start();
			inDeadband = true;
		} else if (inDeadband && !(Math.abs(goalAngle - currentHeading) < ANGLE_DEADBAND)) {
			inDeadband = false;
			deadbandTimer.reset();
			deadbandTimer.stop();
		}
		Debugger.logDetailedInfo("angle: " + currentHeading + " " + headingError + " "
				+ (Math.abs(headingError) < ANGLE_DEADBAND) + " " + headingAdjustment);
		Debugger.logDetailedInfo("left: " + drivetrain.leftMaster.getClosedLoopError(0) + " "
				+ drivetrain.leftMaster.getClosedLoopTarget(0) + " " + vel);
		Debugger.logDetailedInfo("right: " + drivetrain.rightMaster.getClosedLoopError(0) + " "
				+ drivetrain.rightMaster.getClosedLoopTarget(0) + " " + -vel);
		Debugger.logDetailedInfo("");
	}

	@Override
	protected boolean isDone() {
		return Math.abs(goalAngle - Math.signum(goalAngle) - navx.getTotalAngle()) < ANGLE_DEADBAND
				&& deadbandTimer.get() > DEADBAND_TIME;
	}

	@Override
	protected void whenEnded() {
		drivetrain.setStop();
		Debugger.logEnd(this, navx.getTotalAngle());
	}
}