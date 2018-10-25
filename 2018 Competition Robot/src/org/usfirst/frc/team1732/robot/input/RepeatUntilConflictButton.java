package org.usfirst.frc.team1732.robot.input;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.buttons.Button;

public class RepeatUntilConflictButton extends Button {

	private final Button main;
	private final Button[] otherButton;

	public RepeatUntilConflictButton(Button main, Button... otherButton) {
		this.main = main;
		this.otherButton = otherButton;
	}

	private boolean keepMainPressed = false;

	private boolean anotherIsPressed() {
		for (Button b : otherButton) {
			if (b.get())
				return true;
		}
		return false;
	}

	@Override
	public boolean get() {
		boolean mainIsPressed = main.get();
		boolean anotherIsPressed = anotherIsPressed();
		if (anotherIsPressed) {
			keepMainPressed = false;
		} else if (mainIsPressed) {// only if another isn't pressed
			keepMainPressed = true;
		}
		if (!DriverStation.getInstance().isOperatorControl()) {
			keepMainPressed = false;
		}
		return keepMainPressed;
	}

}
