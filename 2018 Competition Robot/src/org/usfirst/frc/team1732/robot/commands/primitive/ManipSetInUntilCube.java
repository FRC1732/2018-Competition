package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.Manip;

import edu.wpi.first.wpilibj.Timer;
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
		stopTimer.reset();
		stopTimer.stop();
	}

	private Timer stopTimer = new Timer();
	private boolean gotAboveStopCurent = false;

	protected void execute() {
		if (Robot.manip.aboveStopCurrent() && !gotAboveStopCurent) {
			gotAboveStopCurent = true;
			stopTimer.start();
		} else if (gotAboveStopCurent && !Robot.manip.aboveStopCurrent()) {
			gotAboveStopCurent = false;
			stopTimer.reset();
			stopTimer.stop();
		}

	}

	protected boolean isFinished() {
		return Robot.manip.aboveStopCurrent() && stopTimer.get() > Manip.STOP_TIME;
	}

	protected void end() {
		Robot.manip.setStop();
	}
}