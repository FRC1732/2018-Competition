package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.util.Debugger;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ManipSetStop extends InstantCommand {

	public ManipSetStop() {
		requires(Robot.manip);
	}

	@Override
	protected void initialize() {
		Robot.manip.setStop();
		Debugger.logStart(this);
	}
}