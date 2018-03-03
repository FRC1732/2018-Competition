package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.util.Debugger;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ToggleLED extends InstantCommand {
	public ToggleLED() {
		super();
	}

	protected void initialize() {
		Robot.sensors.limelight.toggleLED();
		Debugger.logStart(this);
	}
}
