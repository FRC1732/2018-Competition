package org.usfirst.frc.team1732.robot.math;

public class Utils {

	public static double interpolate(double low, double high, double mu) {
		return (high - low) * mu + low;
	}

}
