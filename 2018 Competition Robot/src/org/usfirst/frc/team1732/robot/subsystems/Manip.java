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
	public static final double ABS_IN_SPEED = 0.7;
	public static final double ABS_OUT_SPEED = 0.4;

	public final VictorSPX master;

	public final double stopCurrent;

	private double outSpeed;

	public Manip(RobotConfig config) {
		master = MotorUtils.makeVictor(config.manipMaster, config.manipConfig);
		MotorUtils.makeVictor(config.manipFollower, config.manipConfig);
		stopCurrent = config.manipStopCurrent;
		outSpeed = ABS_OUT_SPEED;
	}

	public void setOutSpeed(double outSpeed) {
		this.outSpeed = outSpeed;
	}

	@Override
	public void initDefaultCommand() {
	}

	// might end up using a sensor for this
	public boolean aboveStopCurrent() {
		return master.getOutputCurrent() > stopCurrent;
	}

	public void setIn() {
		master.set(ControlMode.PercentOutput, -ABS_IN_SPEED);
	}

	public void setOut() {
		master.set(ControlMode.PercentOutput, ABS_OUT_SPEED);
	}

	public void setOut(double absOutSpeed) {
		master.set(ControlMode.PercentOutput, absOutSpeed);
	}

	public void setStop() {
		master.neutralOutput();
	}
}
