package org.usfirst.frc.team1732.robot.commands.testing;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.util.Util;

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
		Robot.drivetrain.velocityGainsLeft.selectGains(Robot.drivetrain.leftMaster);
		Robot.drivetrain.velocityGainsRight.selectGains(Robot.drivetrain.rightMaster);
		Robot.drivetrain.leftMaster.set(ControlMode.Velocity, Robot.drivetrain.velInToUnits(leftVel));
		Robot.drivetrain.rightMaster.set(ControlMode.Velocity, Robot.drivetrain.velInToUnits(rightVel));
	}

	@Override
	protected void execute() {
		Util.logForGraphing("left", leftVel, Robot.drivetrain.leftEncoder.getRate(),
				Robot.drivetrain.leftEncoder.getSensorRate(), Robot.drivetrain.leftMaster.getClosedLoopTarget(0),
				Robot.drivetrain.leftMaster.getClosedLoopError(0));
		Util.logForGraphing("right", rightVel, Robot.drivetrain.rightEncoder.getRate(),
				Robot.drivetrain.rightEncoder.getSensorRate(), Robot.drivetrain.rightMaster.getClosedLoopTarget(0),
				Robot.drivetrain.rightMaster.getClosedLoopError(0));
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
