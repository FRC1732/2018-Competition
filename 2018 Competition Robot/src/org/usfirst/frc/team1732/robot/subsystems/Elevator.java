package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.conf.RobotConfig;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {

	public static enum Positions {

		DOWN(0.0); // determine these later

		public final double value;

		private Positions(double value) {
			this.value = value;
		}
	}

	public Elevator(RobotConfig robotConfig) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	// use motion magic to control the elevator
	public void set(double position) {

	}

	public void set(Positions position) {
		set(position.value);
	}

	public void setManual(double percentVolt) {

	}

	public void setUp() {

	}

	public void setDown() {

	}

	public void setStop() {

	}
}
