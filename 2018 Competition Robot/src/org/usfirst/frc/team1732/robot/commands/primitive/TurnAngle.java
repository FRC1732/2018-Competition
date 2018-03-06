package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.sensors.navx.GyroReader;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class TurnAngle extends PIDCommand {

	private PIDController pidController = getPIDController();
	private final double rampTime;
	private final boolean isAbsolute;
	private GyroReader navx;
	private int countOnTarget;

	public static TurnAngle absolute(double target, double rampTime) {
		return new TurnAngle(target, 1, rampTime, true);
	}

	public static TurnAngle absolute(double target, double maxPercent, double rampTime) {
		return new TurnAngle(target, maxPercent, rampTime, true);
	}

	public static TurnAngle relative(double target, double rampTime) {
		return new TurnAngle(target, 1, rampTime, false);
	}

	public static TurnAngle relative(double target, double maxPercent, double rampTime) {
		return new TurnAngle(target, maxPercent, rampTime, false);
	}

	private TurnAngle(double target, double maxPercent, double rampTime, boolean absolute) {
		super(0, 0, 0);
		requires(Robot.drivetrain);
		navx = Robot.sensors.navx.makeReader();
		countOnTarget = 0;
		setSetpoint(target);
		setInputRange(-180.0, 180.0);
		pidController.setContinuous();
		pidController.setOutputRange(-maxPercent, maxPercent);
		pidController.setPercentTolerance(1);
		isAbsolute = absolute;
		this.rampTime = rampTime;
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.setBrake();
		navx.zero(); // onlt affects relative
		Robot.drivetrain.leftMaster.configOpenloopRamp(rampTime, Robot.CONFIG_TIMEOUT);
		Robot.drivetrain.rightMaster.configOpenloopRamp(rampTime, Robot.CONFIG_TIMEOUT);
	}

	@Override
	protected double returnPIDInput() {
		if (isAbsolute) {
			return navx.getAngle();
		} else {
			return navx.getTotalAngle();
		}
	}

	@Override
	protected void usePIDOutput(double output) {
		Robot.drivetrain.drive.tankDrive(-output, output);
	}

	@Override
	protected boolean isFinished() {
		if (pidController.onTarget()) {
			if (countOnTarget == 10) {
				return true;
			}
			countOnTarget++;

		} else {
			countOnTarget = 0;
		}
		return false;
	}

	@Override
	protected void end() {
		Robot.drivetrain.setStop();
		Robot.drivetrain.leftMaster.configOpenloopRamp(0, Robot.CONFIG_TIMEOUT);
		Robot.drivetrain.rightMaster.configOpenloopRamp(0, Robot.CONFIG_TIMEOUT);
	}

}