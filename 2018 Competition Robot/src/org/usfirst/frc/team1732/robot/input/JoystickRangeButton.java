package org.usfirst.frc.team1732.robot.input;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class JoystickRangeButton extends Button {
	private final double minActivation;
	private final GenericHID joystick;
	private final int axisNumber;

	public JoystickRangeButton(GenericHID joystick, int axisNumber, double minActivation) {
		this.joystick = joystick;
		this.axisNumber = axisNumber;
		this.minActivation = minActivation;
	}

	public boolean get() {
		if (minActivation > 0) {
			return joystick.getRawAxis(axisNumber) > minActivation;
		} else if (minActivation < 0) {
			return joystick.getRawAxis(axisNumber) < minActivation;
		} else {
			return joystick.getRawAxis(axisNumber) != 0;
		}
	}
}
