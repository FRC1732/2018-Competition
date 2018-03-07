package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;
import org.usfirst.frc.team1732.robot.util.Debugger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorMagicPosition extends Command {

	private int position;

	public ElevatorMagicPosition(Elevator.Positions position) {
		this(position.value);
	}

	public ElevatorMagicPosition(int position) {
		requires(Robot.elevator);
		this.position = position;
	}

	@Override
	protected void initialize() {
		Debugger.logStart(this, position);
		Robot.elevator.useMagicControl(position);
		Robot.elevator.set(position);
	}

	@Override
	protected void execute() {
		// Util.logForGraphing(Robot.elevator.getEncoderPulses(),
		// Robot.elevator.getDesiredPosition(),
		// Robot.elevator.motor.getClosedLoopTarget(0),
		// Robot.elevator.motor.getClosedLoopError(0),
		// Robot.elevator.motor.getMotorOutputPercent());
	}

	@Override
	protected boolean isFinished() {
		return Robot.elevator.atSetpoint();
	}

	@Override
	protected void end() {
		Debugger.logEnd(this, Robot.elevator.getEncoderPulses());
		// shouldn't need to do anything
	}

}
