package org.usfirst.frc.team1732.robot.controlutils;

import org.usfirst.frc.team1732.robot.Robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class ClosedLoopProfile {

	public final String name;
	public final FeedbackDevice feedback;
	public final int slotIdx;
	public final int pidIdx;
	public double kP;
	public double kI;
	public double kD;
	public double kF;
	public int integralZone;
	public int allowableError;
	public int maxIntegralAccumulated;
	public double secondsFromNeutralToFull;

	public ClosedLoopProfile(String name, FeedbackDevice feedback, int slotIdx, double kP, double kI, double kD,
			double kF, int integralZone, int allowableError, int maxIntegralAccumulated,
			double secondsFromNeutralToFull) {
		this(name, feedback, slotIdx, 0, kP, kI, kD, kF, integralZone, allowableError, maxIntegralAccumulated,
				secondsFromNeutralToFull);
	}

	public ClosedLoopProfile(String name, FeedbackDevice feedback, int slotIdx, int pidIdx, double kP, double kI,
			double kD, double kF, int integralZone, int allowableError, int maxIntegralAccumulated,
			double secondsFromNeutralToFull) {
		this.name = name;
		this.feedback = feedback;
		this.slotIdx = slotIdx;
		this.pidIdx = pidIdx;
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kF = kF;
		this.integralZone = integralZone;
		this.allowableError = allowableError;
		this.maxIntegralAccumulated = maxIntegralAccumulated;
		this.secondsFromNeutralToFull = secondsFromNeutralToFull;
	}

	public void putOntoDashboard() {

	}

	public void updateFromDashboard() {

	}

	public void selectGains(TalonSRX... talons) {
		for (TalonSRX talon : talons) {
			talon.selectProfileSlot(slotIdx, pidIdx);
		}
	}

	public void applyToTalon(TalonSRX... talons) {
		for (TalonSRX talon : talons) {
			talon.selectProfileSlot(slotIdx, pidIdx);
			talon.configSelectedFeedbackSensor(feedback, pidIdx, Robot.CONFIG_TIMEOUT);
			talon.config_IntegralZone(slotIdx, integralZone, Robot.CONFIG_TIMEOUT);
			talon.config_kP(slotIdx, kP, Robot.CONFIG_TIMEOUT);
			talon.config_kI(slotIdx, kI, Robot.CONFIG_TIMEOUT);
			talon.config_kD(slotIdx, kD, Robot.CONFIG_TIMEOUT);
			talon.config_kF(slotIdx, Feedforward.TALON_SRX_FF_GAIN, Robot.CONFIG_TIMEOUT);
			talon.configAllowableClosedloopError(slotIdx, allowableError, Robot.CONFIG_TIMEOUT);
			talon.configMaxIntegralAccumulator(slotIdx, maxIntegralAccumulated, Robot.CONFIG_TIMEOUT);
			talon.configClosedloopRamp(secondsFromNeutralToFull, Robot.CONFIG_TIMEOUT);
		}
	}

	public static void applyZeroGainToTalon(FeedbackDevice feedback, int slotIdx, int pidIdx, TalonSRX... talons) {
		ClosedLoopProfile zero = new ClosedLoopProfile("ZERO", feedback, slotIdx, pidIdx, 0, 0, 0, 0, 0, 0, 0, 0);
		zero.applyToTalon(talons);
	}

	@Override
	public ClosedLoopProfile clone() {
		return new ClosedLoopProfile(name, feedback, slotIdx, pidIdx, kP, kI, kD, kF, integralZone, allowableError,
				maxIntegralAccumulated, secondsFromNeutralToFull);
	}

}