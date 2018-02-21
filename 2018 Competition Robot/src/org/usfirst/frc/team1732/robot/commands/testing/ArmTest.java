package org.usfirst.frc.team1732.robot.commands.testing;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmTest extends Command {

	private double percentVolt;

	public ArmTest(double percentVolt) {
		requires(Robot.arm);
		this.percentVolt = percentVolt;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.arm.setManual(percentVolt);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.arm.setManual(0);
	}

}
