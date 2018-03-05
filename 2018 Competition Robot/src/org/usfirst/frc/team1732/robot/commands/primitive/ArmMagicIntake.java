package org.usfirst.frc.team1732.robot.commands.primitive;

import static org.usfirst.frc.team1732.robot.Robot.arm;

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
			requires(arm);
			this.parentCommand = parentCommand;
		}

		@Override
		protected void initialize() {
			System.out.println("Running arm intake command");
			int position = Positions.INTAKE.value;
			arm.useMagicControl(position);
			arm.set(position);
		}

		@Override
		protected void execute() {
			if (!parentCommand.hitButton) {
				parentCommand.hitButton = arm.isButtonPressed();
			}

			Util.logForGraphing(arm.getEncoderPulses(), arm.getDesiredPosition(), arm.motor.getClosedLoopTarget(0),
					arm.motor.getClosedLoopError(0), arm.motor.getMotorOutputPercent());
		}

		@Override
		protected boolean isFinished() {
			return arm.atSetpoint(120) || parentCommand.hitButton;
		}

		@Override
		protected void end() {
			if (parentCommand.hitButton) {
				arm.resetArmPos();
			}
			System.out.println("ending arm intake command");
			System.out.println("hit button: " + parentCommand.hitButton);
		}

	}

	private static class ArmUntilButton extends Command {

		private final ArmMagicIntake parentCommand;

		public ArmUntilButton(ArmMagicIntake parentCommand) {
			requires(arm);
			this.parentCommand = parentCommand;
		}

		@Override
		protected void initialize() {
			System.out.println("Running arm intake command 2");
			arm.setManual(-0.15);
		}

		@Override
		protected void execute() {
			Util.logForGraphing(arm.getEncoderPulses(), arm.getDesiredPosition(), arm.motor.getClosedLoopTarget(0),
					arm.motor.getClosedLoopError(0), arm.motor.getMotorOutputPercent());
			if (!parentCommand.hitButton) {
				parentCommand.hitButton = arm.isButtonPressed();
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
			if (parentCommand.hitButton) {
				arm.resetArmPos();
			}
			int position = Positions.INTAKE.value;
			arm.useMagicControl(position);
			arm.set(position);
		}

	}
}
