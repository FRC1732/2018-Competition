package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ArmRunManual extends InstantCommand {

	private double percentVolt;

	public ArmRunManual(double percentVolt) {
		requires(Robot.arm);
		this.percentVolt = percentVolt;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.arm.setManual(percentVolt);
	}

}
