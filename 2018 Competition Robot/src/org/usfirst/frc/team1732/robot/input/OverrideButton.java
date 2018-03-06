package org.usfirst.frc.team1732.robot.input;

import edu.wpi.first.wpilibj.buttons.Button;

public class OverrideButton {

	public final Button whenOverriden;
	public final Button whenNotOverriden;

	private final Button button;
	private final Button override;

	public OverrideButton(Button button, Button override) {
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
