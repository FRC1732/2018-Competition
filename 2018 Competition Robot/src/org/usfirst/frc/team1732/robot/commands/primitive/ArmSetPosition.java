package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.controlutils.ClosedLoopProfile;
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

	private ClosedLoopProfile gains;

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		double currentPosition = Robot.arm.encoder.getPosition();
		if (currentPosition < position) {
			gains = Robot.arm.upGains;
			Robot.arm.upGains.selectGains(Robot.arm.motor);
		} else {
			gains = Robot.arm.downGains;
			Robot.arm.downGains.selectGains(Robot.arm.motor);
		}
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
		return Robot.arm.atSetpoint(gains.allowableError);
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// shouldn't need to do anything
	}

}
