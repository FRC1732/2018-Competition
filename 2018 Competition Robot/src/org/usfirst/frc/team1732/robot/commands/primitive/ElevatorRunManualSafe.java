package org.usfirst.frc.team1732.robot.commands.primitive;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;
import org.usfirst.frc.team1732.robot.util.Debugger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorRunManualSafe extends Command {

	private double percentVolt;

	public ElevatorRunManualSafe(double percentVolt) {
		requires(Robot.elevator);
		this.percentVolt = percentVolt;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.elevator.setManual(percentVolt);
		Debugger.logStart(this, percentVolt);
	}

	@Override
	protected void execute() {
		int range = 75;
		int adjust = range + 100;
		if (Robot.elevator.getEncoderPulses() > Elevator.Positions.MAX.value - range) {
			int val = Elevator.Positions.MAX.value - adjust;
			Robot.elevator.useMagicControl(val);
			Robot.elevator.set(val);
		}
		if (Robot.elevator.getEncoderPulses() < Elevator.Positions.RADIO.value
				&& Robot.arm.getEncoderPulses() > Arm.Positions.TUCK.value + range) {
			int val = Elevator.Positions.RADIO.value + adjust;
			Robot.elevator.useMagicControl(val);
			Robot.elevator.set(val);
		}
		if (Robot.elevator.getEncoderPulses() < Elevator.Positions.INTAKE.value + range) {
			int val = Elevator.Positions.INTAKE.value + adjust;
			Robot.elevator.useMagicControl(val);
			Robot.elevator.set(val);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
