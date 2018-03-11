package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.util.Debugger;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class Wait extends TimedCommand {

	public Wait(double timeout) {
		super(timeout);
		Debugger.logStart(this, timeout + " seconds");
	}
}
