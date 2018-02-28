package org.usfirst.frc.team1732.robot.input;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

// assumes we mount our 3 pos switches vertically most of the time
public class ThreePosSwitch extends Trigger {

	private final JoystickButton upButton;
	private final JoystickButton downButton;

	public ThreePosSwitch(JoystickButton upButton, JoystickButton downButton) {
		this.upButton = upButton;
		this.downButton = downButton;
	}

	public boolean isUpPressed() {
		return upButton.get();
	}

	public boolean isDownPressed() {
		return downButton.get();
	}

	public boolean isOff() {
		return !isUpPressed() && !isDownPressed();
	}

	@Override
	public boolean get() {
		return !isOff();
	}

}