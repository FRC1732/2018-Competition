package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmSetPosition extends Command {

	private double position;

	public ArmSetPosition(Arm.Positions position) {
		requires(Robot.arm);
		this.position = position.value;
	}

	public ArmSetPosition(double position) {
		requires(Robot.arm);
		this.position = position;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.arm.set(position);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// shouldn't need to do anything
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.arm.atSetpoint();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// shouldn't need to do anything
	}

}
