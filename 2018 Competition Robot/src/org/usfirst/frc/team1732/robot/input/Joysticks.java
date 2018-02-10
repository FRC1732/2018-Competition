package org.usfirst.frc.team1732.robot.input;

import edu.wpi.first.wpilibj.Joystick;

public class Joysticks {

	public static final int LEFT_PORT = 0;
	public static final int RIGHT_PORT = 1;
	public static final int BUTTONS_PORT = 2;

	public final Joystick left;
	public final Joystick right;
	public final Joystick buttons;

	public Joysticks() {
		left = new Joystick(LEFT_PORT);
		right = new Joystick(RIGHT_PORT);
		buttons = new Joystick(BUTTONS_PORT);
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