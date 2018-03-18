package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Arm.Positions;
import org.usfirst.frc.team1732.robot.util.Util;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmResetPos extends Command {

	public ArmResetPos() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.arm);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (Robot.arm.isButtonPressed()
				&& Util.epsilonEquals(Robot.arm.getDesiredPosition(), Positions.INTAKE.value, 100)
				&& Robot.arm.isAutoControl()) {
			Robot.arm.set(Arm.Positions.INTAKE.value + 50);
			Robot.arm.resetArmPos();
		}
		if (!Robot.arm.isButtonPressed()
				&& Util.epsilonEquals(Robot.arm.getDesiredPosition(), Positions.INTAKE.value, 100)
				&& Robot.arm.isAutoControl()) {
			Robot.arm.set(Arm.Positions.INTAKE.value);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
