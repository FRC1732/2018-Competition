package org.usfirst.frc.team1732.robot.autotools;

import java.util.function.Supplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.autos.DefaultDriveForward;
import org.usfirst.frc.team1732.robot.commands.autos.RightScaleScale;
import org.usfirst.frc.team1732.robot.commands.autos.RightScaleScaleNoCross;
import org.usfirst.frc.team1732.robot.commands.autos.RightScaleScaleNoCrossStraight;
import org.usfirst.frc.team1732.robot.commands.autos.RightScaleSwitch;
import org.usfirst.frc.team1732.robot.commands.autos.RightScaleSwitchNoCross;
import org.usfirst.frc.team1732.robot.commands.autos.SwitchCenterFront;
import org.usfirst.frc.team1732.robot.commands.autos.base.RightScaleRightTwice;
import org.usfirst.frc.team1732.robot.commands.testing.TestProfile;
import org.usfirst.frc.team1732.robot.commands.testing.TestVelocityFollowing;
import org.usfirst.frc.team1732.robot.input.Input;
import org.usfirst.frc.team1732.robot.util.Debugger;
import org.usfirst.frc.team1732.robot.util.Util;

import edu.wpi.first.wpilibj.command.Command;

public final class AutoChooser {
	public static enum AutoModes {
		SwitchCenterFront(() -> new SwitchCenterFront()), //
		RightScaleSwitch(() -> new RightScaleSwitch()), //
		RightScaleScale(() -> new RightScaleScale()), //
		RightScaleSwitchNoCross(() -> new RightScaleSwitchNoCross()), //
		RightScaleScaleNoCross(() -> new RightScaleScaleNoCross()), //
		DriveForward(() -> new DefaultDriveForward()), //
		RightScaleScaleNoCrossStraight(() -> new RightScaleScaleNoCrossStraight()), //
		ALWAYS_RIGHT_SCALE_SCALE(() -> new RightScaleRightTwice()), //
		TestProfile(() -> new TestProfile()), //
		// TestCubePickup(() -> new TestCubePickup()), //
		// DriveTime(() -> new DriveTime(0.4, 0.4, NeutralMode.Brake, 20, 0.5)), //
		DriveVelocity(() -> new TestVelocityFollowing(30, 30)); //

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

	public static void addListener(Input joysticks) {
		int first = (int) Util.limit(joysticks.autoDial.get(), 0, AutoModes.values().length - 1);
		System.err.println("ERROR " + first + " AUTO: " + AutoModes.values()[first]);
		joysticks.autoDial.addValueChangeListener(d -> {
			int i = (int) Util.limit(d, 0, AutoModes.values().length - 1);
			System.err.println("ERROR " + i + " AUTO: " + AutoModes.values()[i]);
		});
	}

	public static Command getSelectedAuto() {
		int i = (int) Util.limit(Robot.joysticks.autoDial.get(), 0, AutoModes.values().length - 1);
		Debugger.logDetailedInfo("Running auto: " + AutoModes.values()[i].name());
		return AutoModes.values()[i].getCommand();
	}
}
