package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.config.MotorUtils;
import org.usfirst.frc.team1732.robot.config.RobotConfig;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem to control the elevator
 * 
 * Manages 1 motor
 */
public class Elevator extends Subsystem {
	public TalonSRX motor;

	public static enum Positions {

		DOWN(0.0); // determine these later

		public final double value;

		private Positions(double value) {
			this.value = value;
		}
	}

	public Elevator(RobotConfig config) {
		motor = MotorUtils.makeTalon(config.elevator, config.elevatorConfig);
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
		set(Positions.DOWN);
	}

	public void setStop() {

	}
}
