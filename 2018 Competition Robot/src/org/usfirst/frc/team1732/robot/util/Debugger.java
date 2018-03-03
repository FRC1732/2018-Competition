package org.usfirst.frc.team1732.robot.util;

import edu.wpi.first.wpilibj.command.Command;

public class Debugger {
	private static Mode mode = Mode.OFF;

	// ----- SIMPLE INFO -----
	public static void logSimpleInfo(Command c, String info) {
		println(c, info, Mode.SIMPLE);
	}
	public static void logSimpleInfo(String info) {
		logSimpleInfo(null, info);
	}
	public static void logSimpleInfo(Command c, String format, Object... args) {
		logSimpleInfo(c, String.format(format, args));
	}
	public static void logSimpleInfo(String format, Object... args) {
		logSimpleInfo(null, format, args);
	}
	// ----- LOG START -----
	public static void logStart(Command c, String info) {
		logSimpleInfo(c, "Starting " + info);
	}
	public static void logStart(Command c) {
		logStart(c, "");
	}
	public static void logStart(Command c, String format, Object... args) {
		logStart(c, String.format(format, args));
	}
	// ----- LOG END -----
	public static void logEnd(Command c, String info) {
		logSimpleInfo(c, "Finished " + info);
	}
	public static void logEnd(Command c) {
		logEnd(c, "");
	}
	public static void logEnd(Command c, String format, Object... args) {
		logEnd(c, String.format(format, args));
	}
	// ----- DETAILED INFO -----
	public static void logDetailedInfo(Command c, String info) {
		println(c, info, Mode.DETAILED);
	}
	public static void logDetailedInfo(String info) {
		logDetailedInfo(null, info);
	}
	public static void logDetailedInfo(Command c, String format, Object... args) {
		logDetailedInfo(c, String.format(format, args));
	}
	public static void logDetailedInfo(String format, Object... args) {
		logDetailedInfo(null, format, args);
	}
	// ----- PRINT -----
	private static void println(Command c, String info, Mode level) {
		if (mode.val >= level.val)
			System.out.println(c == null ? "" : (c.getClass().getSimpleName() + ": ") + info);
	}
	// ----- SET MODE -----
	public static void disable() {
		mode = Mode.OFF;
	}
	public static void enableSimple() {
		mode = Mode.SIMPLE;
	}
	public static void enableDetailed() {
		mode = Mode.DETAILED;
	}

	private enum Mode {
		OFF(0), SIMPLE(1), DETAILED(2);

		public int val;

		private Mode(int n) {
			val = n;
		}
	}
}
