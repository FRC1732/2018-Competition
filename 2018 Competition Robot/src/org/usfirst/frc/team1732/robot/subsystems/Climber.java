package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.config.MotorUtils;
import org.usfirst.frc.team1732.robot.config.RobotConfig;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem to control the climber
 * 
 * Manages 2 motors
 */
public class Climber extends Subsystem {
	public VictorSPX master;

	public Climber(RobotConfig config) {
		master = MotorUtils.makeVictor(config.climberMaster, config.climberConfig);
		MotorUtils.makeVictor(config.climberFollower, config.climberConfig);
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

}
