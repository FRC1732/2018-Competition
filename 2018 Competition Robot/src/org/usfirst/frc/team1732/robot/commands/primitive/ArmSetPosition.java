package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.util.Util;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmSetPosition extends Command {

	private int position;
	// private boolean goingUp;

	public ArmSetPosition(Arm.Positions position) {
		// super(5);
		requires(Robot.arm);
		this.position = Robot.arm.getValue(position);
	}

	public ArmSetPosition(int position) {
		// super(5);
		requires(Robot.arm);
		this.position = position;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("Running arm set command");
		int currentPosition = Robot.arm.getEncoderPulses();
		if (currentPosition < position) {
			Robot.arm.upGains.selectGains(Robot.arm.motor);
			// goingUp = true;
			System.out.println("using up arm gains");
		} else {
			Robot.arm.downGains.selectGains(Robot.arm.motor);
			// goingUp = false;
			System.out.println("using down arm gains");
		}
		Robot.arm.set(position);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// shouldn't need to do anything
		// int currentPosition = Robot.arm.getEncoderPulses();
		Util.logForGraphing(Robot.arm.getEncoderPulses(), Robot.arm.getDesiredPosition(),
				Robot.arm.motor.getClosedLoopError(0), Robot.arm.motor.getMotorOutputPercent());
		// if (goingUp && currentPosition > position) {
		// Robot.arm.disableRamping();
		// }
		// if (!goingUp && currentPosition < position) {
		// Robot.arm.disableRamping();
		// }
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;// Robot.arm.atSetpoint();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		System.out.println("ending arm set command");
		// Robot.arm.setManual(0);
		// shouldn't need to do anything
	}

}
