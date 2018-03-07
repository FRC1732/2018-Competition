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
import org.usfirst.frc.team1732.robot.commands.primitive.ElevatorRunManualSafe;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetIn;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetStop;
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

		OverrideButton posIntake = new OverrideButton(new JoystickButton(buttons, 1), override);
		OverrideButton posExchange = new OverrideButton(new JoystickButton(buttons, 2), override);
		OverrideButton posHuman = new OverrideButton(new JoystickButton(buttons, 3), override);
		OverrideButton posSwitch = new OverrideButton(new JoystickButton(buttons, 4), override);
		OverrideButton posTuck = new OverrideButton(new JoystickButton(buttons, 5), override);
		OverrideButton posScaleLow = new OverrideButton(new JoystickButton(buttons, 6), override);
		OverrideButton posScaleHigh = new OverrideButton(new JoystickButton(buttons, 7), override);

		OverrideButton redButton = new OverrideButton(new JoystickButton(buttons, 8), override);
		OverrideButton greenButton1 = new OverrideButton(new JoystickButton(buttons, 9), override);
		OverrideButton greenButton2 = new OverrideButton(new JoystickButton(buttons, 10), override);
		ThreePosSwitch climbButton = new ThreePosSwitch(redButton.whenOverriden, greenButton2.whenOverriden);

		OverrideButton manipHiSpeed = new OverrideButton(new JoystickRangeButton(buttons, 0, 0.1), override);
		OverrideButton manipLowSpeed = new OverrideButton(new JoystickRangeButton(buttons, 0, -0.1), override);
		ThreePosSwitch manipSpeed = new ThreePosSwitch(manipHiSpeed.whenNotOverriden, manipLowSpeed.whenNotOverriden);

		JoystickButton rockerUp = new JoystickButton(autoDial, 11);
		JoystickButton rockerDown = new JoystickButton(autoDial, 12);
		ThreePosSwitch rocker = new ThreePosSwitch(rockerUp, rockerDown);

		JoystickButton leftTrigger = new JoystickButton(left, 1);
		JoystickButton rightTrigger = new JoystickButton(right, 1);
		ThreePosSwitch triggerSwitch = new ThreePosSwitch(leftTrigger, rightTrigger);

		JoystickButton leftIntake = new JoystickButton(left, 3);
		JoystickButton rightTuck = new JoystickButton(right, 3);

		JoystickButton shifting = new JoystickButton(right, 5);
		JoystickButton limelightToggle = new JoystickButton(left, 7);

		// Add commands here
		posIntake.whenNotOverriden
				.whenPressed(new ArmElevatorSetPosition(Arm.Positions.INTAKE, Elevator.Positions.INTAKE));
		posExchange.whenNotOverriden
				.whenPressed(new ArmElevatorSetPosition(Arm.Positions.EXCHANGE, Elevator.Positions.INTAKE));
		posHuman.whenNotOverriden
				.whenPressed(new ArmElevatorSetPosition(Arm.Positions.HUMAN_PLAYER, Elevator.Positions.HUMAN));
		posSwitch.whenNotOverriden
				.whenPressed(new ArmElevatorSetPosition(Arm.Positions.SWITCH, Elevator.Positions.INTAKE));
		posTuck.whenNotOverriden.whenPressed(new ArmElevatorSetPosition(Arm.Positions.TUCK, Elevator.Positions.INTAKE));
		posScaleLow.whenNotOverriden
				.whenPressed(new ArmElevatorSetPosition(Arm.Positions.SCALE, Elevator.Positions.SCALE_LOW));
		posScaleHigh.whenNotOverriden
				.whenPressed(new ArmElevatorSetPosition(Arm.Positions.SCALE, Elevator.Positions.SCALE_HIGH));

		posIntake.whenOverriden.whenPressed(makeCommand(arm, () -> arm.setManual(-0.3)));
		posExchange.whenOverriden.whenPressed(makeCommand(arm, () -> arm.setManual(0.4)));
		posIntake.whenOverriden.whenReleased(makeCommand(arm, arm::setStop));
		posExchange.whenOverriden.whenReleased(makeCommand(arm, arm::setStop));
		posHuman.whenOverriden.whenPressed(makeCommand(elevator, () -> elevator.setManual(-0.3)));
		posSwitch.whenOverriden.whenPressed(makeCommand(elevator, () -> elevator.setManual(0.4)));
		posHuman.whenOverriden.whenReleased(makeCommand(elevator, elevator::setStop));
		posSwitch.whenOverriden.whenReleased(makeCommand(elevator, elevator::setStop));

		posScaleHigh.whenOverriden
				.whenPressed(new ArmElevatorSetPosition(Arm.Positions.SWITCH, Elevator.Positions.MAX));
		posScaleLow.whenOverriden
				.whenPressed(new ArmElevatorSetPosition(Arm.Positions.SWITCH, Elevator.Positions.SCALE_HIGH));

		rockerUp.whenPressed(new ElevatorRunManualSafe(0.4));
		rockerDown.whenPressed(new ElevatorRunManualSafe(-0.3));
		rocker.whenReleased(new ElevatorHoldPosition());

		manipHiSpeed.whenNotOverriden.whenPressed(new SetOuttakeSpeed(0.8));
		manipSpeed.whenReleased(new SetOuttakeSpeed(Manip.BASE_OUT_SPEED));
		manipLowSpeed.whenNotOverriden.whenPressed(new SetOuttakeSpeed(0.3));

		leftTrigger.whenPressed(new ManipSetIn());
		rightTrigger.whenPressed(makeCommand(manip, manip::setOutVariable));
		triggerSwitch.whenReleased(new ManipSetStop());

		leftIntake.whenPressed(new ArmElevatorSetPosition(Arm.Positions.INTAKE, Elevator.Positions.INTAKE));
		rightTuck.whenPressed(new ArmElevatorSetPosition(Arm.Positions.TUCK, Elevator.Positions.INTAKE));

		shifting.whileHeld(new TeleopShift());

		greenButton1.whenOverriden.whenPressed(makeCommand(hooks, hooks::setUp));
		greenButton1.whenOverriden.whenPressed(makeCommand(ramp, ramp::setOut));
		posTuck.whenOverriden.whenPressed(makeCommand(hooks, hooks::setDown));

		redButton.whenOverriden.whenPressed(makeCommand(climber, climber::climb));
		greenButton2.whenOverriden.whenPressed(makeCommand(climber, climber::reverseClimb));
		climbButton.whenReleased(makeCommand(climber, climber::stop));

		limelightToggle.whenPressed(makeCommand(sensors.limelight::toggleLED));

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