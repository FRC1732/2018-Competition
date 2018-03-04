package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;
import org.usfirst.frc.team1732.robot.util.Util;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorSetPosition extends Command {

	private int position;

	public ElevatorSetPosition(Elevator.Positions position) {
		requires(Robot.elevator);
		this.position = Robot.elevator.getValue(position);
	}

	public ElevatorSetPosition(int position) {
		requires(Robot.elevator);
		this.position = position;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("Running eleavtor set command");
		Robot.elevator.usePositionControl(position);
		Robot.elevator.set(position);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// shouldn't need to do anything
		Util.logForGraphing(Robot.elevator.getEncoderPulses(), Robot.elevator.getDesiredPosition(),
				Robot.elevator.motor.getClosedLoopError(0), Robot.elevator.motor.getMotorOutputPercent());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.elevator.atSetpoint();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		System.out.println("ending elevator set command");
		// shouldn't need to do anything
	}

}
