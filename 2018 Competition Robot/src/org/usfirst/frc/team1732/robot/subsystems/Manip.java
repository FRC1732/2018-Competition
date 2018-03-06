package org.usfirst.frc.team1732.robot.subsystems;

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

	public static final double STOP_TIME = 0.25;
	public static final double BASE_IN_SPEED = 0.7;
	public static final double BASE_OUT_SPEED = 0.5;

	public final VictorSPX master;
	public final double stopCurrent;

	private double absVariableOut = BASE_OUT_SPEED;

	public Manip(RobotConfig config) {
		master = MotorUtils.makeVictor(config.manipMaster, config.manipConfig);
		MotorUtils.makeVictor(config.manipFollower, config.manipConfig);
		stopCurrent = config.manipStopCurrent;
	}

	@Override
	public void initDefaultCommand() {
	}

	// might end up using a sensor for this
	public boolean aboveStopCurrent() {
		return master.getOutputCurrent() > stopCurrent;
	}

	public void setVariableOutSpeed(double absVariableOut) {
		this.absVariableOut = Math.abs(absVariableOut);
	}

	public void setOutVariable() {
		setOut(absVariableOut);
	}

	public void setIn() {
		master.set(ControlMode.PercentOutput, -BASE_IN_SPEED);
	}

	public void setOut(double absOutSpeed) {
		master.set(ControlMode.PercentOutput, absOutSpeed);
	}

	public void setStop() {
		master.neutralOutput();
	}
}
