package org.usfirst.frc.team1732.robot.sensors;

import java.util.LinkedList;

import org.usfirst.frc.team1732.robot.util.Debugger;

public class SensorChecker {

	private static LinkedList<Sensor> sensors = new LinkedList<>();

	public static void addSensor(Sensor s, String name) {
		sensors.add(s);
	}

	public static void checkSensors() {
		for (Sensor s : sensors) {
			if (!s.isWorking()) {
				Debugger.logSimpleInfo("Sensor error: " + s.getName());
			}
		}
	}

}
