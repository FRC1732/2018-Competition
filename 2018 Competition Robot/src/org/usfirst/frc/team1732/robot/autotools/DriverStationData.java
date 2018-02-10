package org.usfirst.frc.team1732.robot.autotools;

import java.util.concurrent.FutureTask;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class DriverStationData {

	// volatile means variable writes are done straight to memory rather than CPU
	// cache. This ensures other threads will get the new value as soon as it's
	// written.

	public static volatile boolean closeSwitchIsLeft;
	public static volatile boolean scaleIsLeft;
	public static volatile boolean farSwitchIsLeft;
	private static volatile String platePosition = "";

	private static Thread thread;
	private static FutureTask<Boolean> task;

	public static void startPolling() {
		task = new FutureTask<>(() -> {
			while (platePosition == null || platePosition == "") {
				platePosition = DriverStation.getInstance().getGameSpecificMessage();
			}
			closeSwitchIsLeft = platePosition.charAt(0) == 'L';
			scaleIsLeft = platePosition.charAt(1) == 'L';
			farSwitchIsLeft = platePosition.charAt(2) == 'L';
			return true;
		});
		thread = new Thread(() -> {
			while (!task.isDone())
				task.run();
		});
		thread.setDaemon(true);
		thread.start();
	}

	public static boolean gotPlatePositions() {
		return task.isDone(); // makes sure that thread finished writing to IsLeft variables
	}

	public static void cancelPolling() {
		task.cancel(true);
	}

	public static Alliance getAlliance() {
		return DriverStation.getInstance().getAlliance();
	}

}