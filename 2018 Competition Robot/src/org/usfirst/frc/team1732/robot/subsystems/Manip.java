package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.config.MotorUtils;
import org.usfirst.frc.team1732.robot.config.RobotConfig;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem to control the intakes
 * 
 * Manages 2 VictorSPX (right, left)
 */
public class Manip extends Subsystem {

	public static final double RAMP_TIME = 0.6;
	public static final double CURRENT_STOP_TIME = 0.25;
	public static final double A_BASE_IN_SPEED = 1;
	public static final double B_BASE_IN_SPEED = 1;
	public static final double BASE_OUT_SPEED = 0.5;

	public final VictorSPX victorA;
	public final VictorSPX victorB;
	public final double stopCurrent;

	private double absVariableOut = BASE_OUT_SPEED;

	private boolean assumeCube = true;

	public Manip(RobotConfig config) {
		victorA = MotorUtils.makeVictor(config.manipA, config.manipConfig);
		victorB = MotorUtils.makeVictor(config.manipB, config.manipConfig);
		stopCurrent = config.manipStopCurrent;
	}

	@Override
	public void initDefaultCommand() {
	}

	// might end up using a sensor for this (cube detection)

	public boolean aboveStopCurrent() {
		return false;
		// return victor.getOutputCurrent() > stopCurrent;
	}

	public void setVariableOutSpeed(double absVariableOut) {
		System.out.println("variable out: " + absVariableOut);
		this.absVariableOut = Math.abs(absVariableOut);
	}

	public void setOutVariable() {
		System.out.println("variable out set: " + absVariableOut);
		setRampTime(0);
		setOut(absVariableOut);
	}

	public void setIn() {
		victorA.set(ControlMode.PercentOutput, -A_BASE_IN_SPEED);
		victorB.set(ControlMode.PercentOutput, -B_BASE_IN_SPEED);
		// System.out.println("MANIP SET IN");
		assumeCube = true;
	}

	public void setOut(double absOutSpeed) {
		victorA.set(ControlMode.PercentOutput, absOutSpeed);
		victorB.set(ControlMode.PercentOutput, absOutSpeed);
		// System.out.println("MANIP SET OUT");
		assumeCube = false;
	}

	public void setStop() {
		victorA.neutralOutput();
		victorB.neutralOutput();
	}

	public void setRampTime(double rampTime) {
		victorA.configOpenloopRamp(rampTime, Robot.CONFIG_TIMEOUT);
		victorB.configOpenloopRamp(rampTime, Robot.CONFIG_TIMEOUT);
	}

	public boolean assumedCube() {
		return assumeCube;
	}
}
