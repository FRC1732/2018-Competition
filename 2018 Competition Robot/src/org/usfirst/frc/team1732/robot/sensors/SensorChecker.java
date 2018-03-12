package org.usfirst.frc.team1732.robot.sensors;

import java.util.LinkedList;
import java.util.function.BooleanSupplier;

import org.usfirst.frc.team1732.robot.util.Debugger;

public class SensorChecker {

	private static LinkedList<Runnable> sensors = new LinkedList<>();

	public static void addSensor(BooleanSupplier s, String name) {
		sensors.add(() -> {
			if (!s.getAsBoolean()) {
				Debugger.logSimpleInfo("Sensor error: " + name);
			}
		});
	}

	public static void checkSensors() {
		for (Runnable s : sensors) {
			s.run();
		}
	}

}
