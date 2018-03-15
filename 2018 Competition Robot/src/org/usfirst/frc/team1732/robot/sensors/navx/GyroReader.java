package org.usfirst.frc.team1732.robot.sensors.navx;

import org.usfirst.frc.team1732.robot.util.Util;

public class GyroReader {

	private final GyroBase gyro;
	private double totalAngle = 0;
	private double continuousAngle = 0;

	public GyroReader(GyroBase gyro) {
		this.gyro = gyro;
	}

	/**
	 * @return current angle from -180 to 180 degrees. Does not get zeroed.
	 */
	public double getAngle() {
		return Util.getContinuousError(gyro.getAngle(), continuousAngle, 360);
	}

	/**
	 * @return total angle in degrees. Use this instead of getAngle.
	 */
	public double getTotalAngle() {
		return gyro.getTotalAngle() - totalAngle;
	}

	public void zero() {
		continuousAngle = gyro.getAngle();
		totalAngle = gyro.getTotalAngle();
	}

	public boolean atZero() {
		return getAngle() < GyroBase.ANGLE_EPSILON;
	}
}
