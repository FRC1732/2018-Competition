package org.usfirst.frc.team1732.robot.commands.primitive;

import static org.usfirst.frc.team1732.robot.Robot.drivetrain;

import org.usfirst.frc.team1732.robot.util.Debugger;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 * DO NOT USE IN A REAL MATCH, JUST FOR TESTING
 */
public class DriveTime extends Command {
	private final double leftVolt, rightVolt, time;
	private final NeutralMode mode;

	public DriveTime(double leftVolt, double rightVolt, NeutralMode mode, double time) {
		requires(drivetrain);
		this.leftVolt = leftVolt;
		this.rightVolt = rightVolt;
		this.time = time;
		this.mode = mode;
	}

	@Override
	protected void initialize() {
		drivetrain.setNeutralMode(mode);
		drivetrain.drive.tankDrive(leftVolt, rightVolt);
		drivetrain.setBrake();
		Debugger.logStart(this, "lV = %.2f, rV = %.2f, T = %.2f", leftVolt, rightVolt, time);
		super.setTimeout(time);
	}

	@Override
	protected boolean isFinished() {
		return super.isTimedOut();
	}

	@Override
	protected void end() {
		drivetrain.setStop();
		Debugger.logEnd(this);
	}
}
