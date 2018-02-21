package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ManipSetInUntilCube extends Command {

	public ManipSetInUntilCube() {
		requires(Robot.manip);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.manip.setIn();
	}

	protected boolean isFinished() {
		return Robot.manip.hasCube();
	}

	protected void end() {
		Robot.manip.setStop();
	}
}