package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.controlutils.ClosedLoopProfile;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmHoldDownVelocity extends Command {

	public ArmHoldDownVelocity() {
		requires(Robot.arm);
	}

	private static final ClosedLoopProfile armVelocityGains = new ClosedLoopProfile("Arm Velocity Gains",
			FeedbackDevice.CTRE_MagEncoder_Absolute, 3, 20, 0, 0, 0, 0, 0, 0, 0);

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.arm.setManual(0);
		System.out.println("Starting to hold arm down velocity!");
		Robot.arm.motor.set(ControlMode.Velocity, 0);
		armVelocityGains.applyToTalon(Robot.arm.motor);
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
		System.out.println("Stopped holding arm down velocity!");
		Robot.arm.useMagicControl(Robot.arm.getEncoderPulses());
		Robot.arm.holdPosition();
	}
}
