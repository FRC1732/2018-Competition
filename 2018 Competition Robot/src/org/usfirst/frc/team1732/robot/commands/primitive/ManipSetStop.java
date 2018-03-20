package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.util.Debugger;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ManipSetStop extends InstantCommand {

	private double rampTime;

	public ManipSetStop(double rampTime) {
		requires(Robot.manip);
		this.rampTime = rampTime;
	}

	public ManipSetStop() {
		this(0);
	}

	@Override
	protected void initialize() {
		Robot.manip.setRampTime(rampTime);
		Robot.manip.setStop();
		Debugger.logStart(this);
	}
}