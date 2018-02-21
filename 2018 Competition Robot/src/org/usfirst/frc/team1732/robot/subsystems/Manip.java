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

	public final VictorSPX master;

	public final double stopCurrent;

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

	public void setIn() {
		master.set(ControlMode.PercentOutput, -0.5);
	}

	public void setOut() {
		master.set(ControlMode.PercentOutput, 0.5);
	}

	public void setStop() {
		master.neutralOutput();
	}
}
