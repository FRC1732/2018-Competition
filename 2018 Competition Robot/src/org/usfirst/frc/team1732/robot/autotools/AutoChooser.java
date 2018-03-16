package org.usfirst.frc.team1732.robot.autotools;

import java.util.function.Supplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.autos.ManipAutoEject;
import org.usfirst.frc.team1732.robot.commands.autos.ScaleLeftSingleStraight;
import org.usfirst.frc.team1732.robot.commands.autos.ScaleRightSingleStraight;
import org.usfirst.frc.team1732.robot.commands.autos.SwitchCenterFront;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmElevatorSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveTime;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveVoltage;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPathLimelight;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetIn;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetOut;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetStop;
import org.usfirst.frc.team1732.robot.commands.primitive.Wait;
import org.usfirst.frc.team1732.robot.commands.testing.TestGyroReader;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Path.PointProfile;
import org.usfirst.frc.team1732.robot.controlutils.motionprofiling.pathing.Waypoint;
import org.usfirst.frc.team1732.robot.input.Input;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;
import org.usfirst.frc.team1732.robot.util.Debugger;
import org.usfirst.frc.team1732.robot.util.Util;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public final class AutoChooser {
	public static enum AutoModes {
		SWITCH_CENTER_FRONT(() -> new SwitchCenterFront()), //
		ScaleLeftSingle(() -> new ScaleLeftSingleStraight()), //
		ScaleRightSingle(() -> new ScaleRightSingleStraight()), //
		DriveForwardMotion(() -> {
			Path path;
			double startingX = 0;
			double startingY = 0;
			path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), true);
			double endingX = startingX;
			double endingY = 100;
			path.addWaypoint(new Waypoint(endingX, endingY, Math.PI / 2, 0));

			path.generateProfile(100, 100);
			return new FollowVelocityPath(path.getVelocityProfile(Robot.drivetrain.effectiveRobotWidth));
		}), //
		DriveBackwardMotion(() -> {
			Path path;
			double startingX = 0;
			double startingY = 0;
			path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), false);
			double endingX = startingX;
			double endingY = -100;
			path.addWaypoint(new Waypoint(endingX, endingY, Math.PI / 2, 0));

			path.generateProfile(50, 100);
			return new FollowVelocityPath(path.getVelocityProfile(Robot.drivetrain.effectiveRobotWidth));
		}), //
		DRIVE_TIME(() -> new DriveTime(0.25, 0.25, NeutralMode.Brake, 5)), //
		TEST_CUBE_GETTING(() -> {
			Path path;
			double startingX = 0;
			double startingY = 0;
			path = new Path(new Waypoint(startingX, startingY, -Math.PI / 2, 0), false);
			double endingX = -50;
			double endingY = -100;
			path.addWaypoint(new Waypoint(endingX, endingY, 5 * Math.PI / 4, 0));

			path.generateProfile(100, 50);
			return new FollowVelocityPathLimelight(path.getVelocityProfile(Robot.drivetrain.effectiveRobotWidth), 0.5);
		}), //
		TEST_CUBE_GRAB_AND_SHOOT(() -> {
			// return null;
			return new CommandGroup() {
				{
					Path path;
					double startingX = 0;
					double startingY = 0;
					path = new Path(new Waypoint(startingX, startingY, -Math.PI / 2, 0), false);
					double endingX = -50;
					double endingY = -100;
					path.addWaypoint(new Waypoint(endingX, endingY, 5 * Math.PI / 4, 0));
					//
					path.generateProfile(100, 50);
					PointProfile profile2 = path.getVelocityProfile(Robot.drivetrain.effectiveRobotWidth);
					double time2 = profile2.getTotalTimeSec();
					double percent2 = 0.1;
					addSequential(new ManipSetOut(0));
					addSequential(new CommandGroup() {
						{
							addParallel(new CommandGroup() {
								{
									addSequential(new Wait(time2 * percent2));
									addSequential(new ArmElevatorSetPosition(Arm.Positions.INTAKE,
											Elevator.Positions.INTAKE));
									addSequential(new ManipSetIn());
								}
							});
							addSequential(new FollowVelocityPathLimelight(profile2, 0.5));
							addSequential(new DriveVoltage(0, 0, NeutralMode.Brake));
						}
					});
					// // score in switch
					addSequential(new ManipSetStop());
					addSequential(new ArmElevatorSetPosition(Arm.Positions.SWITCH, Elevator.Positions.INTAKE));
					addSequential(new Wait(0.2));
					addSequential(new ManipAutoEject(0.5));
				}
			};
		}), //
		TEST_MIRROR(() -> {
			Path path;
			double startingX = 0;
			double startingY = 0;
			path = new Path(new Waypoint(startingX, startingY, Math.PI / 2, 0), false);
			double endingX = -50;
			double endingY = -50;
			path.addWaypoint(new Waypoint(endingX, endingY, Math.PI / 4, 0));

			path.generateProfile(100, 50);
			return new FollowVelocityPath(path.getVelocityProfile(Robot.drivetrain.effectiveRobotWidth), true);
		}), TEST_GYRO_READER(() -> {
			return new TestGyroReader();
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
