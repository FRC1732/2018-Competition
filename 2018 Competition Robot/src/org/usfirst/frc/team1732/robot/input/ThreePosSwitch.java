package org.usfirst.frc.team1732.robot.input;

import edu.wpi.first.wpilibj.buttons.Button;

// assumes we mount our 3 pos switches vertically most of the time
public class ThreePosSwitch extends Button {

	private final Button upButton;
	private final Button downButton;

	public ThreePosSwitch(Button upButton, Button downButton) {
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