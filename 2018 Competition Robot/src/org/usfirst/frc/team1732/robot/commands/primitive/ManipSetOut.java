package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.util.Debugger;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ManipSetOut extends InstantCommand {

	private double speed;

	public ManipSetOut(double absSpeed) {
		requires(Robot.manip);
		speed = absSpeed;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.manip.setOut(speed);
		Debugger.logStart(this);
	}
}