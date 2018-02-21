package org.usfirst.frc.team1732.robot.sensors.navx;

import com.kauailabs.navx.frc.AHRS;

public class NavX extends GyroBase {

	private final AHRS navx;

	public NavX(AHRS navx) {
		this.navx = navx;
	}

	@Override
	public double getAngle() {
		return navx.getYaw();
	}

	@Override
	public double getTotalAngle() {
		return navx.getAngle();
	}

	@Override
	public void zero() {
		navx.zeroYaw();
	}

}
