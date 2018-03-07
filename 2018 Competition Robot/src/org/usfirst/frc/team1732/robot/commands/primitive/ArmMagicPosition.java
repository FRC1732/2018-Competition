package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.util.Debugger;
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

	@Override
	protected void initialize() {
		Debugger.logStart(this);
		Robot.arm.useMagicControl(position);
		Robot.arm.set(position);
		// timer.reset();
		// timer.stop();
	}

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

	@Override
	protected boolean isFinished() {
		return Robot.arm.atSetpoint();// && timer.get() > onTargetTime;
	}

	@Override
	protected void end() {
		Debugger.logEnd(this);
		// Robot.arm.holdPosition();
	}

}
