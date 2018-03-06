package org.usfirst.frc.team1732.robot.input;

import org.usfirst.frc.team1732.robot.commands.primitive.ArmElevatorSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.ElevatorHoldPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.ElevatorRunManualSafe;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetIn;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetOut;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetStop;
import org.usfirst.frc.team1732.robot.commands.primitive.ToggleLED;
import org.usfirst.frc.team1732.robot.commands.teleop.TeleopShift;
import org.usfirst.frc.team1732.robot.config.RobotConfig;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
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

		JoystickButton posIntake = new JoystickButton(buttons, 1);
		JoystickButton posExchange = new JoystickButton(buttons, 2);
		JoystickButton posHuman = new JoystickButton(buttons, 3);
		JoystickButton posSwitch = new JoystickButton(buttons, 4);
		JoystickButton posTuck = new JoystickButton(buttons, 5);
		JoystickButton posScaleLow = new JoystickButton(buttons, 6);
		JoystickButton posScaleHigh = new JoystickButton(buttons, 7);

		JoystickButton redButton = new JoystickButton(buttons, 8);
		JoystickButton greenButton1 = new JoystickButton(buttons, 9);
		JoystickButton greenButton2 = new JoystickButton(buttons, 10);

		Button manipHiSpeed = new JoystickRangeButton(buttons, 0, 0.1);
		Button manipLowSpeed = new JoystickRangeButton(buttons, 0, -0.1);
		ThreePosSwitch manipSpeed = new ThreePosSwitch(manipHiSpeed, manipLowSpeed);

		JoystickButton rockerUp = new JoystickButton(autoDial, 11);
		JoystickButton rockerDown = new JoystickButton(autoDial, 12);
		ThreePosSwitch rocker = new ThreePosSwitch(rockerUp, rockerDown);

		JoystickButton leftTrigger = new JoystickButton(left, 1);
		JoystickButton rightTrigger = new JoystickButton(right, 1);
		JoystickButton leftIntake = new JoystickButton(left, 3);
		JoystickButton rightTuck = new JoystickButton(right, 3);

		JoystickButton shifting = new JoystickButton(right, 5);
		JoystickButton limelightToggle = new JoystickButton(left, 7);

		// Add commands here
		posIntake.whenPressed(new ArmElevatorSetPosition(Arm.Positions.INTAKE, Elevator.Positions.INTAKE));
		posExchange.whenPressed(new ArmElevatorSetPosition(Arm.Positions.EXCHANGE, Elevator.Positions.INTAKE));
		posHuman.whenPressed(new ArmElevatorSetPosition(Arm.Positions.HUMAN_PLAYER, Elevator.Positions.HUMAN));
		posSwitch.whenPressed(new ArmElevatorSetPosition(Arm.Positions.SWITCH, Elevator.Positions.INTAKE));
		posTuck.whenPressed(new ArmElevatorSetPosition(Arm.Positions.TUCK, Elevator.Positions.INTAKE));
		posScaleLow.whenPressed(new ArmElevatorSetPosition(Arm.Positions.SCALE, Elevator.Positions.SCALE_LOW));
		posScaleHigh.whenPressed(new ArmElevatorSetPosition(Arm.Positions.SCALE, Elevator.Positions.SCALE_HIGH));

		rockerUp.whenPressed(new ElevatorRunManualSafe(0.4));
		rockerDown.whenPressed(new ElevatorRunManualSafe(-0.3));
		rocker.whenReleased(new ElevatorHoldPosition());
		leftTrigger.whenPressed(new ManipSetIn());
		leftTrigger.whenReleased(new ManipSetStop());
		rightTrigger.whenPressed(new ManipSetOut());
		rightTrigger.whenReleased(new ManipSetStop());

		leftIntake.whenPressed(new ArmElevatorSetPosition(Arm.Positions.INTAKE, Elevator.Positions.INTAKE));
		rightTuck.whenPressed(new ArmElevatorSetPosition(Arm.Positions.TUCK, Elevator.Positions.INTAKE));
		shifting.whileHeld(new TeleopShift());
		limelightToggle.whenPressed(new ToggleLED());

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