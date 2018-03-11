package org.usfirst.frc.team1732.robot.commands.testing;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.util.Debugger;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestVelocityFollowing extends Command {

	private double leftVel, rightVel;

	public TestVelocityFollowing(double leftVel, double rightVel) {
		requires(Robot.drivetrain);
		this.leftVel = leftVel;
		this.rightVel = rightVel;
	}

	@Override
	protected void initialize() {
		Robot.drivetrain.velocityGains.selectGains(Robot.drivetrain.leftMaster, Robot.drivetrain.rightMaster);
		Robot.drivetrain.leftMaster.set(ControlMode.Velocity, Robot.drivetrain.velInToUnits(leftVel));
		Robot.drivetrain.rightMaster.set(ControlMode.Velocity, Robot.drivetrain.velInToUnits(rightVel));
	}

	@Override
	protected void execute() {
		Debugger.logDetailedInfo("Left error: " + Robot.drivetrain.leftMaster.getClosedLoopError(0));
		Debugger.logDetailedInfo("Right error: " + Robot.drivetrain.rightMaster.getClosedLoopError(0));
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.drivetrain.setStop();
	}
}
