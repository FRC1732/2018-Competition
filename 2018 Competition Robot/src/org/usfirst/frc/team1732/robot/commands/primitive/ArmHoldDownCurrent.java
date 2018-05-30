package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmHoldDownCurrent extends Command {

	public ArmHoldDownCurrent() {
		requires(Robot.arm);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.arm.setManual(0);
		System.out.println("Starting to hold arm down current!");
		Robot.arm.motor.set(ControlMode.Current, -10);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// System.out.println("Holding arm down!");
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		System.out.println("Stopped holding arm down current!");
		Robot.arm.holdPosition();
	}
}
