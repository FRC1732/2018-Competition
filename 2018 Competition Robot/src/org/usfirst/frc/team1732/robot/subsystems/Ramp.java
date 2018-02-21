package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.config.RobotConfig;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem to control the ramp
 * 
 * Manages
 */
public class Ramp extends Subsystem {

	private final Solenoid solenoid;
	public final boolean rampOutValue;

	public Ramp(RobotConfig config) {
		solenoid = new Solenoid(config.rampSolenoidID);
		rampOutValue = config.rampOutValue;
		setRampIn();
	}

	public void setRampOut() {
		solenoid.set(rampOutValue);
	}

	public void setRampIn() {
		solenoid.set(!rampOutValue);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
