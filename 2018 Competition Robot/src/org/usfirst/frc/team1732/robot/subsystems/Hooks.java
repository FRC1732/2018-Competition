package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.config.RobotConfig;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Hooks extends Subsystem {

	private final Solenoid hooks;
	public final boolean hooksUpValue;

	public Hooks(RobotConfig config) {
		hooks = new Solenoid(2);
		this.hooksUpValue = false;
	}

	public void setHooksUp() {
		hooks.set(hooksUpValue);
	}

	public void setHooksDown() {
		hooks.set(!hooksUpValue);
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
