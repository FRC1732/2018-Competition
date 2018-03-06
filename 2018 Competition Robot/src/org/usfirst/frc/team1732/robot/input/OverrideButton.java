package org.usfirst.frc.team1732.robot.input;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OverrideButton extends Button {

	private final JoystickButton button;
	private final JoystickButton override;

	/**
	 * Makes an override button, which only triggers when override is triggered and
	 * the button is triggered
	 */
	public OverrideButton(JoystickButton button, JoystickButton override) {
		this.button = button;
		this.override = override;
	}

	@Override
	public boolean get() {
		return override.get() && button.get();
	}

}
