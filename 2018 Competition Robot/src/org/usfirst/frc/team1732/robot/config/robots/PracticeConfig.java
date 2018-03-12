package org.usfirst.frc.team1732.robot.config.robots;

import org.usfirst.frc.team1732.robot.config.RobotConfig;

public class PracticeConfig extends RobotConfig {

	{
		// drivetrain
		effectiveRobotWidth = 28.1877; // calculate
		drivetrainInchesPerPulse = 136 / 70679.5; // calculate
		maxUnitsPer100Ms = 7500; // measure
		// drivetrainConfig. Change stuff like this:
		drivetrainConfig.enableVoltageCompensation = true;
		drivetrainConfig.openLoopRamp = 0;
		// change PID values like this:
		drivetrainVelocityPID.kP = 0.45/* 0.4 */;
		drivetrainVelocityPID.kI = 1;// 0.5;
		drivetrainVelocityPID.kD = 0;
		drivetrainVelocityPID.kF = 1023 / 7500/* 5000 */;
		drivetrainVelocityPID.integralZone = 100;// zone is sensor units per 100m
		drivetrainVelocityPID.allowableError = 0;
		drivetrainVelocityPID.maxIntegralAccumulated = 100;
		drivetrainVelocityPID.secondsFromNeutralToFull = 0;

		// arm
		armConfig.enableVoltageCompensation = true;
		armMagicVel = 1203;
		armMagicAccel = 1200;
		armMagicPID.kF = 1023 / 1378; // 1023 / max sensor units per 100 ms
		armMagicPID.kP = 4.1;
		armMagicPID.kI = 0.1;
		armMagicPID.kD = 0;
		armMagicPID.integralZone = 100;
		armMagicPID.maxIntegralAccumulated = 100;
		reverseArmButton = true;

		// elevator
		elevatorConfig.enableVoltageCompensation = true;
		elevatorMagicVel = 3000;
		elevatorMagicAccel = 3000;
		elevatorMagicPID.kF = 1023 / 4366; // 1023 / max sensor units per 100 ms
		elevatorMagicPID.kP = 1.2;
		elevatorMagicPID.kI = 0;
		elevatorMagicPID.kD = 0;
		elevatorMagicPID.integralZone = 0;
		elevatorMagicPID.maxIntegralAccumulated = 0;
		reverseElevatorButton = false;

		// climber
		climberConfig.enableVoltageCompensation = true;

		// manip
		manipConfig.enableVoltageCompensation = true;
		manipConfig.continousCurrentLimit = 0; // figure out how talon current limiting works
		manipStopCurrent = 0;
	}

}
