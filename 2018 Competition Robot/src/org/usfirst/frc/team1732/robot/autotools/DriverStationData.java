package org.usfirst.frc.team1732.robot.autotools;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class DriverStationData {

	public static volatile TeeterTotter closeSwitch;
	public static volatile TeeterTotter scale;
	public static volatile TeeterTotter farSwitch;

	private static volatile String platePosition = "";

	private static final Thread thread;

	static {
		thread = new Thread(() -> {
			while (!gotPlatePositions()) {
				platePosition = DriverStation.getInstance().getGameSpecificMessage();
			}
			closeSwitch = new TeeterTotter(platePosition.charAt(0));
			scale = new TeeterTotter(platePosition.charAt(1));
			farSwitch = new TeeterTotter(platePosition.charAt(2));
		});
		// thread.setPriority(newPriority);
		thread.setDaemon(true);
		thread.start();
	}

	public static boolean gotPlatePositions() {
		return platePosition != null && !platePosition.equals("");
	}

	public static void cancelPolling() {
		thread.interrupt();
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