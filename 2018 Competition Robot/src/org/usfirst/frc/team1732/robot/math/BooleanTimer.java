package org.usfirst.frc.team1732.robot.math;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;

public class BooleanTimer {

	private final Timer t;
	private final double timeOut;
	private final Supplier<Boolean> checker;
	private final Runnable end;

	public BooleanTimer(double timeOutSeconds, Supplier<Boolean> checker, Runnable end) {
		t = new Timer();
		t.reset();
		t.start();
		this.timeOut = timeOutSeconds;
		this.checker = checker;
		this.end = end;
	}

	/**
	 * 
	 * @return true if checker returns true, or if timed outs
	 */
	public boolean moveOn() {
		if (t.hasPeriodPassed(timeOut) || checker.get()) {
			end.run();
			return true;
		}
		return false;
	}

}
