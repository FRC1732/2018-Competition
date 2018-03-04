package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.Arm.Positions;
import org.usfirst.frc.team1732.robot.util.Util;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ArmMagicIntake extends CommandGroup {

	private boolean hitButton = false;

	public ArmMagicIntake() {
		addSequential(new ArmMagicIntakeUntilButton(this));
		addSequential(new ArmUntilButton(this));
	}

	private static class ArmMagicIntakeUntilButton extends Command {

		private final ArmMagicIntake parentCommand;

		public ArmMagicIntakeUntilButton(ArmMagicIntake parentCommand) {
			requires(Robot.arm);
			this.parentCommand = parentCommand;
		}

		@Override
		protected void initialize() {
			System.out.println("Running arm intake command");
			int position = Robot.arm.getValue(Positions.INTAKE);
			Robot.arm.useMagicControl(position);
			Robot.arm.set(position);
		}

		@Override
		protected void execute() {
			parentCommand.hitButton = Robot.arm.isButtonPressed();
			Util.logForGraphing(Robot.arm.getEncoderPulses(), Robot.arm.getDesiredPosition(),
					Robot.arm.motor.getClosedLoopTarget(0), Robot.arm.motor.getClosedLoopError(0),
					Robot.arm.motor.getMotorOutputPercent());
		}

		@Override
		protected boolean isFinished() {
			return Robot.arm.atSetpoint(120) || parentCommand.hitButton;
		}

		@Override
		protected void end() {
			System.out.println("ending arm intake command");
			System.out.println("hit button: " + parentCommand.hitButton);
		}

	}

	private static class ArmUntilButton extends Command {

		private final ArmMagicIntake parentCommand;

		private int accumulationRate = 20;
		private int accumulatedPosition = 0;

		public ArmUntilButton(ArmMagicIntake parentCommand) {
			requires(Robot.arm);
			this.parentCommand = parentCommand;
		}

		@Override
		protected void initialize() {
			System.out.println("Running arm intake command 2");
			accumulatedPosition = Robot.arm.getValue(Positions.INTAKE);
			Robot.arm.useMagicControl(accumulatedPosition);
			Robot.arm.set(accumulatedPosition);
		}

		@Override
		protected void execute() {
			Util.logForGraphing(Robot.arm.getEncoderPulses(), Robot.arm.getDesiredPosition(),
					Robot.arm.motor.getClosedLoopTarget(0), Robot.arm.motor.getClosedLoopError(0),
					Robot.arm.motor.getMotorOutputPercent());
			Robot.arm.set(accumulatedPosition);
			accumulatedPosition = accumulatedPosition - accumulationRate;
			if (!parentCommand.hitButton) {
				parentCommand.hitButton = Robot.arm.isButtonPressed();
			}
		}

		@Override
		protected boolean isFinished() {
			return parentCommand.hitButton;
		}

		@Override
		protected void end() {
			System.out.println("ending arm intake command 2");
			System.out.println("hit button: " + parentCommand.hitButton);
			Robot.arm.set(Robot.arm.getEncoderPulses());
		}

	}
}
