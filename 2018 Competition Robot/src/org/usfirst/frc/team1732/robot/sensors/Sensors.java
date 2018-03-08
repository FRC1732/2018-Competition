package org.usfirst.frc.team1732.robot.sensors;

import org.usfirst.frc.team1732.robot.config.RobotConfig;
import org.usfirst.frc.team1732.robot.sensors.navx.NavX;

import com.kauailabs.navx.frc.AHRS;

public class Sensors {

	public final NavX navx;
	public final Limelight limelight;

	public Sensors(RobotConfig robotConfig) {
		navx = new NavX(new AHRS(robotConfig.navxPort));
		navx.zero();
		limelight = new Limelight();
	}

	public static double convertTotalAngle(double angle) {
		angle = angle % 360;
		if (Math.abs(angle) > 180) {
			angle = angle - Math.signum(angle) * 360;
		}
		return angle;
	}
}
