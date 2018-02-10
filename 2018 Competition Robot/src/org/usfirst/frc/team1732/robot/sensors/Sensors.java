package org.usfirst.frc.team1732.robot.sensors;

import org.usfirst.frc.team1732.robot.config.RobotConfig;

import com.kauailabs.navx.frc.AHRS;

public class Sensors {

	public final AHRS navx;

	public Sensors(RobotConfig robotConfig) {
		navx = new AHRS(robotConfig.navxPort);
	}

	public static double convertTotalAngle(double angle) {
		angle = angle % 360;
		if (Math.abs(angle) > 180) {
			angle = angle - Math.signum(angle) * 360;
		}
		return angle;
	}
}
