package org.usfirst.frc.team1732.robot.input;

import org.usfirst.frc.team1732.robot.commands.primitive.ArmSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetIn;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetOut;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetStop;
import org.usfirst.frc.team1732.robot.commands.primitive.ToggleLED;
import org.usfirst.frc.team1732.robot.config.RobotConfig;
import org.usfirst.frc.team1732.robot.subsystems.Arm;

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
		JoystickButton limelightToggle = new JoystickButton(left, 2);

		JoystickButton resetArmEncoder = new JoystickButton(left, 6);
		JoystickButton resetElevatorEncoder = new JoystickButton(right, 11);

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

		posIntake.whenPressed(new ArmSetPosition(Arm.Positions.INTAKE));
		posTuck.whenPressed(new ArmSetPosition(Arm.Positions.TUCK));
		posSwitch.whenPressed(new ArmSetPosition(Arm.Positions.SWITCH));
		posScaleLow.whenPressed(new ArmSetPosition(Arm.Positions.SCALE));
		posScaleHigh.whenPressed(new ArmSetPosition(Arm.Positions.SCALE));

		leftTrigger.whenPressed(new ManipSetIn());
		leftTrigger.whenReleased(new ManipSetStop());
		rightTrigger.whenPressed(new ManipSetOut());
		rightTrigger.whenReleased(new ManipSetStop());

		limelightToggle.whenPressed(new ToggleLED());

		// resetArmEncoder.whenPressed(new ArmResetEncoder());
		// resetElevatorEncoder.whenPressed(new ElevatorResetEncoder());
		// temporary testing
		// posIntake.whileHeld(new ArmTest(0.2));
		// posSwitch.whileHeld(new ArmTest(-0.2));
		// posScaleLow.whileHeld(new ElevatorTest(0.2));
		// posScaleHigh.whileHeld(new ElevatorTest(-0.2));
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