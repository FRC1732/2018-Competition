package org.usfirst.frc.team1732.robot.autotools;

import java.util.function.Supplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveDistance;

import edu.wpi.first.wpilibj.command.Command;

public final class AutoChooser {
	public static enum AutoModes {
		DRIVE30(()->new DriveDistance(30)), DRIVE60(()->new DriveDistance(60));

		private final Supplier<Command> commandSupplier;

		private AutoModes(Supplier<Command> commandSupplier) {
			this.commandSupplier = commandSupplier;
		}
		public Command getCommand() {
			return commandSupplier.get();
		}
	}

	private AutoChooser() {}

	public static Command getSelectedAuto() {
		return AutoModes.values()[Robot.joysticks.autoDial.get()].getCommand();
	}
}
