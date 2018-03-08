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
		setIn();
	}

	public void setOut() {
		solenoid.set(rampOutValue);
	}

	public void setIn() {
		solenoid.set(!rampOutValue);
	}

	@Override
	protected void initDefaultCommand() {

	}

}
