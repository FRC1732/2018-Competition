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

		// new JoystickButton(buttons, 9)
		// .whenPressed(new ArmElevatorSetPosition(Arm.Positions.INTAKE,
		// Elevator.Positions.INTAKE));
		// new JoystickButton(buttons, 8)
		// .whenPressed(new ArmElevatorSetPosition(Arm.Positions.INTAKE,
		// Elevator.Positions.SWITCH));
		// new JoystickButton(buttons, 7)
		// .whenPressed(new ArmElevatorSetPosition(Arm.Positions.SCALE,
		// Elevator.Positions.INTAKE));
		// new JoystickButton(buttons, 6)
		// .whenPressed(new ArmElevatorSetPosition(Arm.Positions.SCALE,
		// Elevator.Positions.SCALE));
		new JoystickButton(buttons, 9).whileHeld(new ArmTest(0.5));
		new JoystickButton(buttons, 8).whileHeld(new ArmTest(-0.5));
		new JoystickButton(buttons, 7).whileHeld(new ElevatorTest(0.5));
		new JoystickButton(buttons, 6).whileHeld(new ElevatorTest(-0.5));
		new JoystickButton(left, 1).whenPressed(new ManipSetIn());
		new JoystickButton(left, 1).whenReleased(new ManipSetStop());
		new JoystickButton(right, 1).whenPressed(new ManipSetOut());
		new JoystickButton(right, 1).whenReleased(new ManipSetStop());
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