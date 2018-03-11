package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.config.MotorUtils;
import org.usfirst.frc.team1732.robot.config.RobotConfig;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem to control the climber
 * 
 * Manages 2 motors
 */
public class Climber extends Subsystem {
	public final VictorSPX master;

	public Climber(RobotConfig config) {
		master = MotorUtils.makeVictor(config.climberMaster, config.climberConfig);
		MotorUtils.makeVictor(config.climberFollower, config.climberConfig);
	}

	public void climb() {
		master.set(ControlMode.PercentOutput, 1);
	}

	public void reverseClimb() {
		master.set(ControlMode.PercentOutput, -0.5);
	}

	public void hold() {
		// ???
	}

	public void stop() {
		master.set(ControlMode.PercentOutput, 0);
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

}
