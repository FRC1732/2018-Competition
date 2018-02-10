package org.usfirst.frc.team1732.robot.input;

import org.usfirst.frc.team1732.robot.config.RobotConfig;

import edu.wpi.first.wpilibj.Joystick;

public class Joysticks {

	public final Joystick left;
	public final Joystick right;
	public final Joystick buttons;

	public Joysticks(RobotConfig robotConfig) {
		left = new Joystick(robotConfig.leftJoystickPort);
		right = new Joystick(robotConfig.rightJoystickPort);
		buttons = new Joystick(robotConfig.buttonJoystickPort);
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