package org.usfirst.frc.team1732.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Arm extends Subsystem {

	public static enum Positions {

		DOWN(0.0); // determine these later

		public final double value;

		private Positions(double value) {
			this.value = value;
		}
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void set(double position) {

	}

	public void set(Positions position) {
		set(position.value);
	}

	public void setManual(double percentVolt) {

	}

	public void setStop() {

	}
}
