package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ElevatorRunManual extends InstantCommand {

	private double percentVolt;

	public ElevatorRunManual(double percentVolt) {
		requires(Robot.elevator);
		this.percentVolt = percentVolt;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.elevator.setManual(percentVolt);
	}
}
