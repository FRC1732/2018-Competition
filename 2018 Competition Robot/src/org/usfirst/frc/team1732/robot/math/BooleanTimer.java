package org.usfirst.frc.team1732.robot.math;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;

public class BooleanTimer {

	private final Timer t;
	private final double timeOut;
	private final Supplier<Boolean> checker;
	private final Runnable end;
	private final Runnable ifTimedOut;
	private final Runnable ifFinished;

	public BooleanTimer(double timeOutSeconds, Supplier<Boolean> checker) {
		this(timeOutSeconds, checker, () -> {
		}, () -> {
		}, () -> {
		});
	}

	public BooleanTimer(double timeOutSeconds, Supplier<Boolean> checker, Runnable end, Runnable ifTimedOut,
			Runnable ifFinished) {
		t = new Timer();
		this.timeOut = timeOutSeconds;
		this.checker = checker;
		this.end = end;
		this.ifTimedOut = ifTimedOut;
		this.ifFinished = ifFinished;
	}

	public void start() {
		t.reset();
		t.start();
	}

	private boolean isTimedOut() {
		return t.get() > timeOut;
	}

	public boolean checkIfDone() {
		boolean timedOut = isTimedOut();
		boolean finished = checker.get();
		return timedOut || finished;
	}

	public void finish() {
		end.run();
		if (isTimedOut())
			ifTimedOut.run();
		if (checker.get())
			ifFinished.run();
	}

}
