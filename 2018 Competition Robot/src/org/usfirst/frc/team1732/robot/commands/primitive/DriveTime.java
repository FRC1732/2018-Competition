package org.usfirst.frc.team1732.robot.commands.primitive;

import static org.usfirst.frc.team1732.robot.Robot.drivetrain;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.util.Debugger;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 * DO NOT USE IN A REAL MATCH, JUST FOR TESTING
 */
public class DriveTime extends Command {
	private final double leftVolt, rightVolt, time, rampTime;
	private final NeutralMode mode;

	public DriveTime(double leftVolt, double rightVolt, NeutralMode mode, double time, double rampTime) {
		requires(drivetrain);
		this.leftVolt = leftVolt;
		this.rightVolt = rightVolt;
		this.time = time;
		this.rampTime = rampTime;
		this.mode = mode;
	}

	@Override
	protected void initialize() {
		drivetrain.setNeutralMode(mode);
		drivetrain.leftMaster.configOpenloopRamp(rampTime, Robot.CONFIG_TIMEOUT);
		drivetrain.rightMaster.configOpenloopRamp(rampTime, Robot.CONFIG_TIMEOUT);
		drivetrain.setLeft(leftVolt);
		drivetrain.setRight(rightVolt);
		drivetrain.setBrake();
		Debugger.logStart(this, "lV = %.2f, rV = %.2f, T = %.2f", leftVolt, rightVolt, time);
		super.setTimeout(time);
	}

	@Override
	protected void execute() {
		System.out.println("left: " + drivetrain.leftEncoder.getSensorRate() + ", "
				+ drivetrain.leftMaster.getMotorOutputPercent());
		System.out.println("right: " + drivetrain.rightEncoder.getSensorRate() + ", "
				+ drivetrain.rightMaster.getMotorOutputPercent());
	}

	@Override
	protected boolean isFinished() {
		return super.isTimedOut();
	}

	@Override
	protected void end() {
		drivetrain.setStop();
		drivetrain.leftMaster.configOpenloopRamp(0, Robot.CONFIG_TIMEOUT);
		drivetrain.rightMaster.configOpenloopRamp(0, Robot.CONFIG_TIMEOUT);
		Debugger.logEnd(this);
	}
}
