package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class DriveVoltage extends InstantCommand {

	private final NeutralMode mode;
	private final double leftVolt;
	private final double rightVolt;

	public DriveVoltage(double leftPercentVolt, double rightPercentVolt, NeutralMode mode) {
		requires(Robot.drivetrain);
		this.mode = mode;
		this.leftVolt = leftPercentVolt;
		this.rightVolt = rightPercentVolt;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.drivetrain.setNeutralMode(mode);
		Robot.drivetrain.setLeft(leftVolt);
		Robot.drivetrain.setLeft(rightVolt);
	}
}
