package org.usfirst.frc.team1732.robot.config.robots;

import org.usfirst.frc.team1732.robot.config.RobotConfig;

public class CompetitionConfig extends RobotConfig {

	{
		// drivetrain
		effectiveRobotWidth = 25; // calculate
		drivetrainInchesPerPulse = 100.0 / ((51310 - (12) + 51034 - (117)) / 2.0); // = 0.001957
		maxUnitsPer100Ms = 0; // measure
		// drivetrainConfig. Change stuff like this:
		drivetrainConfig.enableVoltageCompensation = true;
		// change PID values like this:
		drivetrainVelocityPID.kP = 0;
		drivetrainVelocityPID.kI = 0;
		drivetrainVelocityPID.kD = 0;
		drivetrainVelocityPID.integralZone = 0;
		drivetrainVelocityPID.allowableError = 0;
		drivetrainVelocityPID.maxIntegralAccumulated = 0;
		drivetrainVelocityPID.secondsFromNeutralToFull = 0;

		// arm
		armConfig.enableVoltageCompensation = true;

		// elevator
		elevatorConfig.enableVoltageCompensation = true;

		// climber
		climberConfig.enableVoltageCompensation = true;

		// manip
		manipConfig.enableVoltageCompensation = true;
		manipConfig.continousCurrentLimit = 0; // figure out how talon current limiting works
		manipStopCurrent = 0;
	}

}
