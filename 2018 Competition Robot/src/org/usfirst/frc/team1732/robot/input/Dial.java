package org.usfirst.frc.team1732.robot.input;

import edu.wpi.first.wpilibj.Joystick;

public class Dial extends Joystick {

	public Dial(final int port) {
		super(port);
	}
	/**
	 * @return The current value of the dial, starting at 0
	 */
	public int get() {
		for (int i = 1; i <= this.getButtonCount(); i++)
			if (this.getRawButton(i)) return i - 1;
		return -1;
	}
}
