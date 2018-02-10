package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.config.RobotConfig;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem to control the intakes
 * 
 * Manages 2 TalonSPX (right, left)
 */
public class CubeManip extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public CubeManip(RobotConfig robotConfig) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void setIn() {

	}

	public void setHold() {
		// use current control to hold it (current is proportional to torque)
	}

	public void setOut() {

	}

	public void setStop() {

	}
}
