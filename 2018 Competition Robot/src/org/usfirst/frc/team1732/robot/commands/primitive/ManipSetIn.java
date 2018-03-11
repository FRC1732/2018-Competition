package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.util.Debugger;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ManipSetIn extends InstantCommand {

	public ManipSetIn() {
		requires(Robot.manip);
	}

	@Override
	protected void initialize() {
		Robot.manip.setIn();
		Debugger.logStart(this);
	}
}