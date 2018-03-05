package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.util.Util;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmMagicPosition extends Command {

	private int position;
	// private static final double onTargetTime = 0.25;
	// private boolean startedTimer = false;
	// private final Timer timer;

	public ArmMagicPosition(Arm.Positions position) {
		this(position.value);
	}

	private ArmMagicPosition(int position) {
		requires(Robot.arm);
		this.position = position;
		// timer = new Timer();
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("Running arm set command");
		Robot.arm.useMagicControl(position);
		Robot.arm.set(position);
		// timer.reset();
		// timer.stop();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// if (Robot.arm.atSetpoint() && !startedTimer) {
		// timer.reset();
		// timer.start();
		// startedTimer = true;
		// }
		// if (!Robot.arm.atSetpoint() && startedTimer) {
		// timer.stop();
		// startedTimer = false;
		// }
		Util.logForGraphing(Robot.arm.getEncoderPulses(), Robot.arm.getDesiredPosition(),
				Robot.arm.motor.getClosedLoopTarget(0), Robot.arm.motor.getClosedLoopError(0),
				Robot.arm.motor.getMotorOutputPercent());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.arm.atSetpoint();// && timer.get() > onTargetTime;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		System.out.println("ending arm set command");
		// Robot.arm.holdPosition();
	}

}
