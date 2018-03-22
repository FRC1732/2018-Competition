package org.usfirst.frc.team1732.robot.sensors.encoders;

public abstract class EncoderBase {

	public EncoderBase() {
	}

	public EncoderReader makeReader() {
		return new EncoderReader(this);
	}

	/**
	 * @return current position in units determined by setDistancePerPulse
	 */
	public abstract double getPosition();

	/**
	 * @return current rate in units per second
	 */
	public abstract double getRate();

	public abstract int getPulses();

	/**
	 * @param distancePerPulse
	 *            the distance per sensor unit
	 */
	public abstract void setDistancePerPulse(double distancePerPulse);

	public abstract void zero();

	public boolean isConnected() {
		return getPulses() > 10;
	}
}