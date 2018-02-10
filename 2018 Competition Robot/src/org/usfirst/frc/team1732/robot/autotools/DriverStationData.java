package org.usfirst.frc.team1732.robot.autotools;

import java.util.concurrent.FutureTask;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class DriverStationData {

	public static TeeterTotter closeSwitch;
	public static TeeterTotter scale;
	public static TeeterTotter farSwitch;

	private static volatile String platePosition = "";

	private static final FutureTask<Boolean> task;

	static {
		task = new FutureTask<>(() -> {
			while (!gotPlatePositions()) {
				platePosition = DriverStation.getInstance().getGameSpecificMessage();
			}
			closeSwitch = new TeeterTotter(platePosition.charAt(0));
			scale = new TeeterTotter(platePosition.charAt(1));
			farSwitch = new TeeterTotter(platePosition.charAt(2));
		}, true);
		Thread t = new Thread(() -> {
			while (!task.isDone())
				task.run();
		});
		t.setDaemon(true);
		t.start();
	}

	public static boolean gotPlatePositions() {
		return platePosition != null && !platePosition.equals("");
	}

	public static void cancelPolling() {
		task.cancel(true);
	}

	public static Alliance getAlliance() {
		return DriverStation.getInstance().getAlliance();
	}

	public static class TeeterTotter {

		private final boolean goLeft;

		public TeeterTotter(char side) {
			if (side == 'L') {
				goLeft = true;
			} else {
				goLeft = false;
			}
		}

		public boolean goLeft() {
			return goLeft;
		}
	}
}