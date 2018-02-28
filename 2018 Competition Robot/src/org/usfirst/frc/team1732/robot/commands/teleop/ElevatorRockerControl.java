package org.usfirst.frc.team1732.robot.commands.teleop;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.input.ThreePosSwitch;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorRockerControl extends Command {

	private static final int ACCUMULATION_RATE = 1;

	private final ThreePosSwitch rocker;

	private int accumulatedCounts = 0;

	public ElevatorRockerControl(ThreePosSwitch rocker) {
		requires(Robot.elevator);
		this.rocker = rocker;
	}

	@Override
	protected void initialize() {
		accumulatedCounts = 0;
		// don't do anything yet
	}

	@Override
	protected void execute() {
		double direction = 0;
		if (rocker.isUpPressed()) {
			direction = 1;
		}
		if (rocker.isDownPressed()) {
			direction = -1;
		}
		accumulatedCounts += ACCUMULATION_RATE * direction;
		Robot.elevator.set(Robot.elevator.getDesiredPosition() + accumulatedCounts);
	}

	@Override
	protected boolean isFinished() {
		// we don't want to stop this command unless something else needs the elevator
		return false;
	}

	@Override
	protected void end() {
		// hold whatever the last set position was
	}

}
