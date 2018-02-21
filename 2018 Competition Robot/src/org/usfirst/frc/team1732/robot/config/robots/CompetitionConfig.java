package org.usfirst.frc.team1732.robot.config.robots;

import org.usfirst.frc.team1732.robot.config.RobotConfig;
import org.usfirst.frc.team1732.robot.controlutils.Feedforward;

public class CompetitionConfig extends RobotConfig {

	{
		// drivetrain
		effectiveRobotWidth = 25; // calculate
		drivetrainInchesPerPulse = 100.0 / ((51310 - (12) + 51034 - (117)) / 2.0); // = 0.001957
		maxInPerSec = 0; // calculate
		maxInPerSecSq = 0; // calculate
		// drivetrainConfig. Change stuff like this:
		drivetrainConfig.enableVoltageCompensation = true;
		// change PID values like this:
		drivetrainMotionPID.kF = Feedforward.TALON_SRX_FF_GAIN;
		drivetrainMotionPID.kP = 0;
		drivetrainMotionPID.kI = 0;
		drivetrainMotionPID.kD = 0;
		drivetrainMotionPID.integralZone = 0;
		drivetrainMotionPID.allowableError = 0;
		drivetrainMotionPID.maxIntegralAccumulated = 0;
		drivetrainMotionPID.secondsFromNeutralToFull = 0;
		// FFs are from robot chassis 1. Only gets used for TurnAngle
		leftFF = new Feedforward(0.0574657, 0.0089855, 1.6010019, 0.0574657, 0.0089855,
				-1.6010019);
		rightFF = new Feedforward(0.0560625, 0.0071341, 1.7711541, 0.0560625, 0.0071341,
				-1.7711541);

		// arm
		armConfig.enableVoltageCompensation = true;
		armUpPID.kF = Feedforward.TALON_SRX_FF_GAIN;
		armUpPID.kP = 0;
		armUpPID.kI = 0;
		armUpPID.kD = 0;
		armUpPID.integralZone = 0;
		armUpPID.allowableError = 0;
		armUpPID.maxIntegralAccumulated = 0;
		armUpPID.secondsFromNeutralToFull = 0;
		armDownPID.kF = Feedforward.TALON_SRX_FF_GAIN;
		armDownPID.kP = 0;
		armDownPID.kI = 0;
		armDownPID.kD = 0;
		armDownPID.integralZone = 0;
		armDownPID.allowableError = 0;
		armDownPID.maxIntegralAccumulated = 0;
		armDownPID.secondsFromNeutralToFull = 0;
		armDegreesPerPulse = 0;
		safeElevatorArmPosition = 0;

		// elevator
		elevatorConfig.enableVoltageCompensation = true;
		elevatorUpPID.kF = Feedforward.TALON_SRX_FF_GAIN;
		elevatorUpPID.kP = 0;
		elevatorUpPID.kI = 0;
		elevatorUpPID.kD = 0;
		elevatorUpPID.integralZone = 0;
		elevatorUpPID.allowableError = 0;
		elevatorUpPID.maxIntegralAccumulated = 0;
		elevatorUpPID.secondsFromNeutralToFull = 0;
		elevatorDownPID.kF = Feedforward.TALON_SRX_FF_GAIN;
		elevatorDownPID.kP = 0;
		elevatorDownPID.kI = 0;
		elevatorDownPID.kD = 0;
		elevatorDownPID.integralZone = 0;
		elevatorDownPID.allowableError = 0;
		elevatorDownPID.maxIntegralAccumulated = 0;
		elevatorDownPID.secondsFromNeutralToFull = 0;
		elevatorInchesPerPulse = 0;
		safeArmElevatorPosition = 0;

		// climber
		climberConfig.enableVoltageCompensation = true;

		// manip
		manipConfig.enableVoltageCompensation = true;
		manipConfig.continousCurrentLimit = 0; // figure out how talon current limiting works
		manipStopCurrent = 0;
		manipHoldCurrent = 0;
	}

}
