package org.usfirst.frc.team1732.robot.input;

import java.util.ArrayList;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.util.ValueChangeListener;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Notifier;

public class Dial extends Joystick {
	private final ArrayList<ValueChangeListener> listeners;

	private int prevVal = get();

	public Dial(final int port) {
		super(port);
		listeners = new ArrayList<>();
		Notifier thread = new Notifier(() -> {
			int currentVal = get();
			if (currentVal != prevVal) {
				listeners.forEach(l -> l.valueChanged(currentVal));
				prevVal = currentVal;
			}
		});
		thread.startPeriodic(Robot.PERIOD_S);
	}

	/**
	 * @return The current value of the dial, starting at 0
	 */
	public int get() {
		for (int i = 1; i <= this.getButtonCount(); i++)
			if (this.getRawButton(i))
				return i - 1;
		return -1;
	}

	public void addValueChangeListener(ValueChangeListener l) {
		listeners.add(l);
	}
}
