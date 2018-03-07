package org.usfirst.frc.team1732.robot.autotools;

import java.util.function.Supplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.autos.ScaleLeftSingle;
import org.usfirst.frc.team1732.robot.commands.autos.ScaleRightSingle;
import org.usfirst.frc.team1732.robot.commands.autos.SwitchCenterFront;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveDistance;

import edu.wpi.first.wpilibj.command.Command;

public final class AutoChooser {
	public static enum AutoModes {
		SWITCH_CENTER_FRONT(() -> new SwitchCenterFront()), ScaleLeftSingle(
				() -> new ScaleLeftSingle()), ScaleRightSingle(() -> new ScaleRightSingle()), DRIVE30(
						() -> new DriveDistance(30)), DRIVE60(() -> new DriveDistance(60));

		private final Supplier<Command> commandSupplier;

		private AutoModes(Supplier<Command> commandSupplier) {
			this.commandSupplier = commandSupplier;
		}

		public Command getCommand() {
			return commandSupplier.get();
		}
	}

	private AutoChooser() {
	}

	public static Command getSelectedAuto() {
		return AutoModes.values()[Robot.joysticks.autoDial.get()].getCommand();
	}
}
