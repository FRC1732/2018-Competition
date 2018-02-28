package org.usfirst.frc.team1732.robot.input;

import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetIn;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetOut;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetStop;
import org.usfirst.frc.team1732.robot.commands.testing.ArmTest;
import org.usfirst.frc.team1732.robot.commands.testing.ElevatorTest;
import org.usfirst.frc.team1732.robot.config.RobotConfig;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Input {

	public final Joystick left;
	public final Joystick right;
	public final Joystick buttons;

	public Input(RobotConfig robotConfig) {
		left = new Joystick(robotConfig.leftJoystickPort);
		right = new Joystick(robotConfig.rightJoystickPort);
		buttons = new Joystick(robotConfig.buttonJoystickPort);

		// Define all the buttons here
		JoystickButton posIntake = new JoystickButton(buttons, 9);
		JoystickButton posTuck = new JoystickButton(buttons, 5);
		JoystickButton posSwitch = new JoystickButton(buttons, 8);
		JoystickButton posScaleLow = new JoystickButton(buttons, 7);
		JoystickButton posScaleHigh = new JoystickButton(buttons, 6);
		JoystickButton rockerUp = new JoystickButton(buttons, 1);
		JoystickButton rockerDown = new JoystickButton(buttons, 2);
		ThreePosSwitch rocker = new ThreePosSwitch(rockerUp, rockerDown);
		JoystickButton leftTrigger = new JoystickButton(left, 1);
		JoystickButton rightTrigger = new JoystickButton(right, 1);

		// Add commands here
		// posIntake.whenPressed(new ArmElevatorSetPosition(Arm.Positions.INTAKE,
		// Elevator.Positions.INTAKE));
		// posTuck.whenPressed(new ArmElevatorSetPosition(Arm.Positions.TUCK,
		// Elevator.Positions.INTAKE));
		// posSwitch.whenPressed(new ArmElevatorSetPosition(Arm.Positions.SWITCH,
		// Elevator.Positions.SWITCH));
		// posScaleLow.whenPressed(new ArmElevatorSetPosition(Arm.Positions.SCALE,
		// Elevator.Positions.SCALE_LOW));
		// posScaleHigh.whenPressed(new ArmElevatorSetPosition(Arm.Positions.SCALE,
		// Elevator.Positions.SCALE_HIGH));
		// rocker.whenActive(new ElevatorRockerControl(rocker));
		leftTrigger.whenPressed(new ManipSetIn());
		leftTrigger.whenReleased(new ManipSetStop());
		rightTrigger.whenPressed(new ManipSetOut());
		rightTrigger.whenReleased(new ManipSetStop());

		// temporary testing
		posIntake.whileHeld(new ArmTest(0.5));
		posSwitch.whileHeld(new ArmTest(-0.5));
		posScaleLow.whileHeld(new ElevatorTest(0.5));
		posScaleHigh.whileHeld(new ElevatorTest(-0.5));
	}

	// joysticks are reversed from the start, so we negate here to avoid confusion
	// later

	public double getLeft() {
		return -left.getRawAxis(1);
	}

	public double getRight() {
		return -right.getRawAxis(1);
	}

	public boolean isReversed() {
		return buttons.getRawButton(5);
	}
}