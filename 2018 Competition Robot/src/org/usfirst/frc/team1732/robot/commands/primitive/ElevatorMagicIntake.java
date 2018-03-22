package org.usfirst.frc.team1732.robot.commands.primitive;

import static org.usfirst.frc.team1732.robot.Robot.elevator;

import org.usfirst.frc.team1732.robot.subsystems.Elevator.Positions;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ElevatorMagicIntake extends CommandGroup {

	private boolean hitButton = false;

	public ElevatorMagicIntake() {
		addSequential(new ElevatorMagicIntakeUntilButton(this));
		addSequential(new ElevatorUntilButton(this));
	}

	private static class ElevatorMagicIntakeUntilButton extends Command {

		private final ElevatorMagicIntake parentCommand;

		public ElevatorMagicIntakeUntilButton(ElevatorMagicIntake parentCommand) {
			requires(elevator);
			this.parentCommand = parentCommand;
		}

		@Override
		protected void initialize() {
			System.out.println("Running elevator intake command");
			parentCommand.hitButton = false;
			int position = Positions.BUTTON_POS.value;
			elevator.useMagicControl(position);
			elevator.set(position);
		}

		@Override
		protected void execute() {
			if (!parentCommand.hitButton) {
				parentCommand.hitButton = elevator.isButtonPressed();
			}

			// Util.logForGraphing(elevator.getEncoderPulses(),
			// elevator.getDesiredPosition(),
			// elevator.motor.getClosedLoopTarget(0), elevator.motor.getClosedLoopError(0),
			// elevator.motor.getMotorOutputPercent());
		}

		@Override
		protected boolean isFinished() {
			return elevator.atSetpoint(120) || parentCommand.hitButton;
		}

		@Override
		protected void end() {
			if (parentCommand.hitButton) {
				elevator.resetElevatorPos();
			}
			System.out.println("ending elevator intake command");
			System.out.println("hit button: " + parentCommand.hitButton);
		}

	}

	private static class ElevatorUntilButton extends Command {

		private final ElevatorMagicIntake parentCommand;

		public ElevatorUntilButton(ElevatorMagicIntake parentCommand) {
			requires(elevator);
			this.parentCommand = parentCommand;
		}

		@Override
		protected void initialize() {
			System.out.println("Running elevator intake command 2");
			elevator.setManual(-0.15);
		}

		@Override
		protected void execute() {
			// Util.logForGraphing(elevator.getEncoderPulses(),
			// elevator.getDesiredPosition(),
			// elevator.motor.getClosedLoopTarget(0), elevator.motor.getClosedLoopError(0),
			// elevator.motor.getMotorOutputPercent());
			if (!parentCommand.hitButton) {
				parentCommand.hitButton = elevator.isButtonPressed();
			}
		}

		@Override
		protected boolean isFinished() {
			return parentCommand.hitButton;
		}

		@Override
		protected void end() {
			System.out.println("ending elevator intake command 2");
			System.out.println("hit button: " + parentCommand.hitButton);
			if (parentCommand.hitButton) {
				elevator.resetElevatorPos();
			}
			int position = Positions.INTAKE.value;
			elevator.useMagicControl(position);
			elevator.set(position);
		}

	}
}
