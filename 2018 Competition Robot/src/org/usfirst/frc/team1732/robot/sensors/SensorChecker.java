package org.usfirst.frc.team1732.robot.sensors;

import java.util.LinkedList;

import org.usfirst.frc.team1732.robot.util.Debugger;

public class SensorChecker {
	
	private static boolean enabled = false;
	private static LinkedList<SensorChecker> sensors = new LinkedList<>();
	
	public static void addSensor(Sensor s, String name) {
		if (enabled) {
			throw new RuntimeException(
					"Sensor Checking loop is already started, so you can't add new sensors.");
		}
		sensors.add(new SensorChecker(s, name));
	}
	
	public static void run() {
		enabled = true;
		while (true) {
			for(SensorChecker s : sensors) {
				s.check();
				Thread.yield();
			}
		}
	}
	
	private Sensor s;
	private String name;
	
	private SensorChecker(Sensor s, String name) {
		this.s = s;
		this.name = name;
	}
	
	private void check() {
		if (!s.isWorking()) {
			Debugger.logSimpleInfo("Sensor error: " + name);
		}
	}
	
}
