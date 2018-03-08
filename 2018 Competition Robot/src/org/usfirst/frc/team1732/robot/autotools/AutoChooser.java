package org.usfirst.frc.team1732.robot.autotools;

import java.util.function.Supplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.autos.ScaleLeftSingle;
import org.usfirst.frc.team1732.robot.commands.autos.ScaleRightSingle;
import org.usfirst.frc.team1732.robot.commands.autos.SwitchCenterFront;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveDistance;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Waypoint;
import org.usfirst.frc.team1732.robot.input.Input;
import org.usfirst.frc.team1732.robot.util.Debugger;
import org.usfirst.frc.team1732.robot.util.Util;

import edu.wpi.first.wpilibj.command.Command;

public final class AutoChooser {
	public static enum AutoModes {
		SWITCH_CENTER_FRONT(() -> new SwitchCenterFront()), ScaleLeftSingle(
				() -> new ScaleLeftSingle()), ScaleRightSingle(() -> new ScaleRightSingle()), DRIVE30(
						() -> new DriveDistance(30)), DRIVE60(() -> new DriveDistance(60)), DriveForward(() -> {
							Path path;
							double startingX = 0;
							double startingY = 0;
							path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
							double endingX = startingX;
							double endingY = 300;
							path.addWaypoint(new Waypoint(endingX, endingY, Math.PI / 2, 0));

							path.generateProfile(150, 300);
							return new FollowVelocityPath(
									path.getVelocityProfile(Robot.drivetrain.effectiveRobotWidth));
						});

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
		System.err.println("ERROR 0 AUTO: " + AutoModes.values()[first]);
		joysticks.autoDial.addValueChangeListener(d -> {
			int i = (int) Util.limit(d, 0, AutoModes.values().length - 1);
			System.err.println("ERROR 0 AUTO: " + AutoModes.values()[i]);
		});
	}

	public static Command getSelectedAuto() {
		int i = (int) Util.limit(Robot.joysticks.autoDial.get(), 0, AutoModes.values().length - 1);
		Debugger.logDetailedInfo("Running auto: " + AutoModes.values()[i].name());
		return AutoModes.values()[i].getCommand();
	}
}
