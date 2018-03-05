package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.util.Debugger;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ManipSetOut extends InstantCommand {

	private double speed;

	public ManipSetOut(double absSpeed) {
		requires(Robot.manip);
		speed = absSpeed;
	}

	public ManipSetOut() {
		this(-1);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if (speed == -1) {
			Robot.manip.setOut();
		} else if (Robot.arm.getDesiredPosition() == Arm.Positions.TUCK.value) {
			Robot.manip.setOut(0.7);
		} else {
			Robot.manip.setOut();
		}
		System.out.println("ManipSetOut: Ran");
		Debugger.logStart(this);
	}
}