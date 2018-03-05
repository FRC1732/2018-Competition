package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.sensors.navx.GyroReader;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class TurnAngleSimple extends PIDCommand {

	private PIDController pidController = getPIDController();
	private GyroReader navx;
	private int countOnTarget;

	public TurnAngleSimple(double target, double maxPercent, double rampTime) {
		super(0, 0, 0);
		requires(Robot.drivetrain);
		navx = Robot.sensors.navx.makeReader();
		countOnTarget = 0;
		setSetpoint(target);
		setInputRange(-180.0, 180.0);
		pidController.setContinuous();
		pidController.setOutputRange(-maxPercent, maxPercent);
		pidController.setPercentTolerance(1);
		Robot.drivetrain.setBrake();
		Robot.drivetrain.leftMaster.configOpenloopRamp(rampTime, Robot.CONFIG_TIMEOUT);
		Robot.drivetrain.rightMaster.configOpenloopRamp(rampTime, Robot.CONFIG_TIMEOUT);
	}

	@Override
	protected double returnPIDInput() {
		return navx.getAngle();
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