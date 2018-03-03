package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.util.NotifierCommand;
import org.usfirst.frc.team1732.robot.util.Util;

/**
 *
 */
public class ArmWPIControl extends NotifierCommand {

	private final int position;

	public ArmWPIControl(int position) {
		super(5);
		requires(Robot.arm);
		this.position = position;
	}

	// Called just before this Command runs the first time
	@Override
	protected void init() {
		System.out.println("Running arm simple control command");
		int currentPosition = Robot.arm.getEncoderPulses();
		if (currentPosition < position) {
			Robot.arm.setManual(0.2);
			Robot.arm.enableUpWpiPID();
			System.out.println("running up");
		} else {
			Robot.arm.setManual(-0.2);
			Robot.arm.enableDownWpiPID();
			System.out.println("running down");
		}
		Robot.arm.set(position);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void exec() {
		// int currentPosition = Robot.arm.getEncoderPulses();
		Util.logForGraphing("Arm Set Pos", Robot.arm.getEncoderPulses(), Robot.arm.getDesiredPosition(),
				Robot.arm.upPID.getError(), Robot.arm.upPID.get(), Robot.arm.motor.getMotorOutputPercent());
		// if (currentPosition < position) {
		// Robot.arm.setManual(0.2);
		// } else {
		// Robot.arm.setManual(-0.2);
		// }
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isDone() {
		return Robot.arm.atSetpoint();
	}

	// Called once after isFinished returns true
	@Override
	protected void whenEnded() {
		System.out.println("Ending command");
	}

}
