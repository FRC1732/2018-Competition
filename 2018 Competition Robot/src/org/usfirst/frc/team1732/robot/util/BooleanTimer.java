package org.usfirst.frc.team1732.robot.util;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;

public class BooleanTimer {

	private final Timer t;
	private final double timeOut;
	private final Supplier<Boolean> checker;

	public BooleanTimer(double timeOutSeconds, Supplier<Boolean> checker) {
		t = new Timer();
		this.timeOut = timeOutSeconds;
		this.checker = checker;
	}

	public void start() {
		t.reset();
		t.start();
	}

	public boolean isTimedOut() {
		return t.get() > timeOut;
	}

	public boolean checkIfDone() {
		boolean timedOut = isTimedOut();
		boolean finished = checker.get();
		return timedOut || finished;
	}

}
