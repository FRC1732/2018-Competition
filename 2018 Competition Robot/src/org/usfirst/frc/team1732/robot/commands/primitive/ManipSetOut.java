package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ManipSetOut extends InstantCommand {

	public ManipSetOut() {
		requires(Robot.manip);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if (Robot.arm.getDesiredPosition() == Arm.Positions.TUCK.value) {
			Robot.manip.setOut(0.7);
		} else {
			Robot.manip.setOut();
		}
		System.out.println("ManipSetOut: Ran");
	}
}