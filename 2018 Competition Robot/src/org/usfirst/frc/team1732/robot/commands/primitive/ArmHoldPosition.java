package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ArmHoldPosition extends InstantCommand {

	public ArmHoldPosition() {
		requires(Robot.arm);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("Running arm hold command");
		int pos = Robot.arm.getEncoderPulses();
		Robot.arm.useMagicControl(pos);
		Robot.arm.holdPosition();
	}

}
