package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.util.Debugger;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ManipSetIn extends InstantCommand {

	private double rampTime;

	public ManipSetIn(double rampTime) {
		requires(Robot.manip);
		this.rampTime = rampTime;
	}

	public ManipSetIn() {
		this(0);
	}

	@Override
	protected void initialize() {
		Robot.manip.setRampTime(rampTime);
		Robot.manip.setIn();
		Debugger.logStart(this);
	}
}