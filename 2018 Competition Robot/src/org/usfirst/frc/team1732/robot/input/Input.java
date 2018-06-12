package org.usfirst.frc.team1732.robot.input;

import static org.usfirst.frc.team1732.robot.Robot.arm;
import static org.usfirst.frc.team1732.robot.Robot.climber;
import static org.usfirst.frc.team1732.robot.Robot.elevator;
import static org.usfirst.frc.team1732.robot.Robot.hooks;
import static org.usfirst.frc.team1732.robot.Robot.manip;
import static org.usfirst.frc.team1732.robot.Robot.ramp;
import static org.usfirst.frc.team1732.robot.Robot.sensors;
import static org.usfirst.frc.team1732.robot.util.InstantLambda.makeCommand;

import org.usfirst.frc.team1732.robot.commands.primitive.ArmElevatorSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.ElevatorHoldPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetIn;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetStop;
import org.usfirst.frc.team1732.robot.commands.teleop.ElevatorRunManualSafe;
import org.usfirst.frc.team1732.robot.commands.teleop.SetOuttakeSpeed;
import org.usfirst.frc.team1732.robot.commands.teleop.TeleopShift;
import org.usfirst.frc.team1732.robot.config.RobotConfig;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;
import org.usfirst.frc.team1732.robot.subsystems.Manip;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Input {

	public final Joystick left;
	public final Joystick right;
	public final Joystick buttons;
	public final Dial autoDial;

	public Input(RobotConfig robotConfig) {
		left = new Joystick(robotConfig.leftJoystickPort);
		right = new Joystick(robotConfig.rightJoystickPort);
		buttons = new Joystick(robotConfig.buttonJoystickPort);
		autoDial = new Dial(robotConfig.dialJoystickPort);

		// Define all the buttons here
		JoystickButton override = new JoystickButton(buttons, 12);

		OverrideButton posExchange = new OverrideButton(new JoystickButton(buttons, 2), override);
		OverrideButton posHuman = new OverrideButton(new JoystickButton(buttons, 3), override);
		OverrideButton posSwitch = new OverrideButton(new JoystickButton(buttons, 4), override);
		OverrideButton posTuck = new OverrideButton(new JoystickButton(buttons, 5), override);
		OverrideButton posScaleLow = new OverrideButton(new JoystickButton(buttons, 6), override);
		OverrideButton posScaleHigh = new OverrideButton(new JoystickButton(buttons, 7), override);

		OverrideButton redButton = new OverrideButton(new JoystickButton(buttons, 8), override);
		OverrideButton greenButton1 = new OverrideButton(new JoystickButton(buttons, 9), override);
		OverrideButton greenButton2 = new OverrideButton(new JoystickButton(buttons, 10), override);

		OverrideButton manipHiSpeed = new OverrideButton(new JoystickRangeButton(buttons, 0, -0.1), override);
		OverrideButton manipLowSpeed = new OverrideButton(new JoystickRangeButton(buttons, 0, 0.1), override);
		// ThreePosSwitch manipSpeed = new ThreePosSwitch(manipHiSpeed.whenNotOverriden,
		// manipLowSpeed.whenNotOverriden);

		JoystickButton rockerUp = new JoystickButton(autoDial, 11);
		JoystickButton rockerDown = new JoystickButton(autoDial, 12);
		// ThreePosSwitch rocker = new ThreePosSwitch(rockerUp, rockerDown);

		RepeatUntilConflictButton posIntakeRepeatUntilConflict = new RepeatUntilConflictButton(
				new JoystickButton(buttons, 1), posExchange.whenNotOverriden, posHuman.whenNotOverriden,
				posSwitch.whenNotOverriden, posTuck.whenNotOverriden, posScaleLow.whenNotOverriden,
				posScaleHigh.whenNotOverriden, redButton.whenNotOverriden, greenButton1.whenNotOverriden,
				greenButton2.whenNotOverriden, rockerUp, rockerDown);
		OverrideButton posIntake = new OverrideButton(posIntakeRepeatUntilConflict, override);

		JoystickButton leftTrigger = new JoystickButton(left, 1);
		JoystickButton rightTrigger = new JoystickButton(right, 1);

		JoystickButton leftIntake = new JoystickButton(left, 3);
		JoystickButton rightTuck = new JoystickButton(right, 3);

		JoystickButton shifting = new JoystickButton(right, 5);
		JoystickButton limelightToggle = new JoystickButton(left, 7);

		// Add commands here
		// posIntake.whenNotOverriden.whenPressed(new ArmMagicPosition(5000));
		posIntake.whenNotOverriden
				.whileHeld(new ArmElevatorSetPosition(Arm.Positions.INTAKE, Elevator.Positions.INTAKE));// );
		posExchange.whenNotOverriden
				.whenPressed(new ArmElevatorSetPosition(Arm.Positions.EXCHANGE, Elevator.Positions.INTAKE));
		posHuman.whenNotOverriden
				.whenPressed(new ArmElevatorSetPosition(Arm.Positions.HUMAN_PLAYER, Elevator.Positions.HUMAN));
		posSwitch.whenNotOverriden
				.whenPressed(new ArmElevatorSetPosition(Arm.Positions.SWITCH, Elevator.Positions.INTAKE));
		posTuck.whenNotOverriden.whenPressed(new ArmElevatorSetPosition(Arm.Positions.TUCK, Elevator.Positions.INTAKE));
		posScaleLow.whenNotOverriden
				.whenPressed(new ArmElevatorSetPosition(Arm.Positions.SCALE_LOW, Elevator.Positions.SCALE_LOW));
		posScaleHigh.whenNotOverriden
				.whenPressed(new ArmElevatorSetPosition(Arm.Positions.SCALE_HIGH, Elevator.Positions.SCALE_HIGH));

		posIntake.whenOverriden.whenPressed(makeCommand(arm, () -> arm.setManual(-0.3)));
		posIntake.whenOverriden.whenReleased(makeCommand(arm, arm::setStop));
		posExchange.whenOverriden.whenPressed(makeCommand(arm, () -> arm.setManual(0.4)));
		posExchange.whenOverriden.whenReleased(makeCommand(arm, arm::setStop));

		posHuman.whenOverriden.whenPressed(makeCommand(elevator, () -> elevator.setManual(-0.3)));
		posHuman.whenOverriden.whenReleased(makeCommand(elevator, elevator::setStop));
		posSwitch.whenOverriden.whenPressed(makeCommand(elevator, () -> elevator.setManual(0.4)));
		posSwitch.whenOverriden.whenReleased(makeCommand(elevator, elevator::setStop));

		posScaleHigh.whenOverriden.whenPressed(new ArmElevatorSetPosition(Arm.Positions.CLIMB, Elevator.Positions.MAX));
		posScaleLow.whenOverriden
				.whenPressed(new ArmElevatorSetPosition(Arm.Positions.CLIMB, Elevator.Positions.CLIMB));

		rockerUp.whenPressed(new ElevatorRunManualSafe(0.4));
		rockerUp.whenReleased(new ElevatorHoldPosition());
		rockerDown.whenPressed(new ElevatorRunManualSafe(-0.3));
		rockerDown.whenReleased(new ElevatorHoldPosition());

		manipHiSpeed.whenNotOverriden.whenPressed(new SetOuttakeSpeed(1));
		manipHiSpeed.whenNotOverriden.whenReleased(new SetOuttakeSpeed(0.5));
		manipLowSpeed.whenNotOverriden.whenPressed(new SetOuttakeSpeed(0.4));
		manipLowSpeed.whenNotOverriden.whenReleased(new SetOuttakeSpeed(0.5));

		leftTrigger.whenPressed(new ManipSetIn());
		leftTrigger.whenReleased(new ManipSetStop(Manip.RAMP_TIME));
		rightTrigger.whenPressed(makeCommand(manip, manip::setOutVariable));
		rightTrigger.whenReleased(new ManipSetStop());
		leftIntake.whenPressed(new ArmElevatorSetPosition(Arm.Positions.INTAKE, Elevator.Positions.INTAKE));
		rightTuck.whenPressed(new ArmElevatorSetPosition(Arm.Positions.TUCK, Elevator.Positions.INTAKE));

		shifting.whileHeld(new TeleopShift());

		redButton.whenOverriden.whenPressed(makeCommand(climber, climber::climb));
		redButton.whenOverriden.whenReleased(makeCommand(climber, climber::setStop));
		posTuck.whenOverriden.whenPressed(makeCommand(climber, climber::reverseClimb));
		posTuck.whenOverriden.whenReleased(makeCommand(climber, climber::setStop));

		greenButton1.whenOverriden.whenPressed(makeCommand(hooks, hooks::setUp));
		// greenButton1.whenOverriden.whenReleased(makeCommand(hooks, hooks::setDown));
		greenButton2.whenOverriden.whenPressed(makeCommand(ramp, ramp::setOut));
		greenButton2.whenOverriden.whenReleased(makeCommand(ramp, ramp::setIn));

		limelightToggle.whenPressed(makeCommand(sensors.limelight::toggleLED));
		greenButton1.whenNotOverriden.whenPressed(makeCommand(sensors.limelight::takeSnapshot));
		// magicArm.whenPressed(new ArmTest(0.3));

		// magicElevator.whenPressed(new ElevatorTest(0.3));
		// magicArm.whenPressed(new ArmMagicPosition(-5000));
		// magicElevator.whenPressed(new ElevatorMagicPosition(13000));
	}

	// joysticks are reversed from the start, so we negate here to avoid confusion
	// later

	public double getLeft() {
		return -left.getRawAxis(1);
	}

	public double getRight() {
		return -right.getRawAxis(1);
	}

}