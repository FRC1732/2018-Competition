package org.usfirst.frc.team1732.robot.commands.testing;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.util.Debugger;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestMotors extends Command {

	private final double l;
	private final double r;
	private final double rampRate;
	private final NeutralMode mode;

	private double maxL = 0;
	private double maxR = 0;

	public TestMotors(double leftValue, double rightValue) {
		this(leftValue, rightValue, NeutralMode.Coast, 0);
	}

	public TestMotors(double leftValue, double rightValue, NeutralMode mode, double rampRate) {
		requires(Robot.drivetrain);
		l = leftValue;
		r = rightValue;
		this.mode = mode;
		this.rampRate = rampRate;
		Debugger.logStart(this);
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.setNeutralMode(mode);
		Robot.drivetrain.leftMaster.configOpenloopRamp(rampRate, Robot.CONFIG_TIMEOUT);
		Robot.drivetrain.rightMaster.configOpenloopRamp(rampRate, Robot.CONFIG_TIMEOUT);
		Robot.drivetrain.setLeft(l);
		Robot.drivetrain.setRight(r);
	}

	@Override
	protected void execute() {
		maxL = Math.max(maxL, Math.abs(Robot.drivetrain.leftEncoder.getRate()));
		maxR = Math.max(maxR, Math.abs(Robot.drivetrain.rightEncoder.getRate()));
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Debugger.logEnd(this, "Max left: %.2f, Max right: %.2f", maxL, maxR);
		Robot.drivetrain.leftMaster.configOpenloopRamp(0, Robot.CONFIG_TIMEOUT);
		Robot.drivetrain.rightMaster.configOpenloopRamp(0, Robot.CONFIG_TIMEOUT);
		Robot.drivetrain.setStop();
	}

}