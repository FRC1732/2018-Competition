package org.usfirst.frc.team1732.robot.input;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OverrideButton {

	public final Button whenOverriden;
	public final Button whenNotOverriden;

	private final JoystickButton button;
	private final JoystickButton override;

	public OverrideButton(JoystickButton button, JoystickButton override) {
		this.button = button;
		this.override = override;
		whenOverriden = new Button() {

			@Override
			public boolean get() {
				return override.get() && button.get();
			}

		};
		whenNotOverriden = new Button() {
			@Override
			public boolean get() {
				return !override.get() && button.get();
			}
		};
	}

}
