package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.config.MotorUtils;
import org.usfirst.frc.team1732.robot.config.RobotConfig;
import org.usfirst.frc.team1732.robot.controlutils.ClosedLoopProfile;
import org.usfirst.frc.team1732.robot.sensors.encoders.TalonEncoder;
import org.usfirst.frc.team1732.robot.util.Debugger;
import org.usfirst.frc.team1732.robot.util.Util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem to control the elevator
 * 
 * Manages 1 motor
 */
public class Elevator extends Subsystem {

	public final TalonSRX motor;
	private final TalonEncoder encoder;

	public final ClosedLoopProfile magicGains;

	private final int allowedError;

	private int desiredPosition;
	private boolean desiredIsSet;
	private boolean autoControl = false;

	private final int magicVel;
	private final int magicAccel;

	private final DigitalInput button;
	private final boolean reverseButton;

	public Elevator(RobotConfig config) {
		motor = MotorUtils.makeTalon(config.elevator, config.elevatorConfig);
		magicGains = config.elevatorMagicPID;

		magicGains.applyToTalon(motor);

		magicVel = config.elevatorMagicVel;
		magicAccel = config.elevatorMagicAccel;
		motor.configMotionCruiseVelocity(magicVel, Robot.CONFIG_TIMEOUT);
		motor.configMotionAcceleration(magicAccel, Robot.CONFIG_TIMEOUT);

		encoder = new TalonEncoder(motor, FeedbackDevice.QuadEncoder);
		encoder.setPhase(config.reverseElevatorSensor);
		encoder.setDistancePerPulse(1);

		allowedError = config.elevatorAllowedErrorCount;

		Robot.dash.add("Elevator Encoder Position", encoder::getPosition);
		Robot.dash.add("Elevator Encoder Pulses", encoder::getPulses);
		Robot.dash.add("Elevator Encoder Rate", encoder::getRate);
		// holdPosition();

		button = new DigitalInput(config.elevatorButtonDIO);
		reverseButton = config.reverseElevatorButton;

		Robot.dash.add("Elevator Button Pressed", this::isButtonPressed);

		setManual(0);
	}

	public static enum Positions {

		// 3311
		// set these in pulses
		BUTTON_POS(2025), INTAKE(2025), SWITCH_AUTO(18000), HUMAN(14000), CLIMB(10206), RADIO(13415), HIT_RAMP(
				14228), SCALE_LOW(13840), SCALE_AUTO(20389), SCALE_HIGH(28389), MAX(30958);

		public final int value;

		private Positions(int value) {
			this.value = value;
		}
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void periodic() {
		int currentPosition = encoder.getPulses();
		if (autoControl) {
			if (desiredPosition < Positions.RADIO.value && currentPosition > Positions.RADIO.value - allowedError) {
				if (!Robot.arm.isElevatorSafeToGoDown() && desiredIsSet) {
					motor.set(ControlMode.MotionMagic, currentPosition);
					desiredIsSet = false;
				}
				if (Robot.arm.isElevatorSafeToGoDown() && !desiredIsSet) {
					motor.set(ControlMode.MotionMagic, desiredPosition);
					desiredIsSet = true;
				}
			}
		}
	}

	@Override
	public void initDefaultCommand() {
	}

	public void set(int position) {
		if (position > Positions.MAX.value) {
			position = Positions.MAX.value;
		}
		desiredPosition = position;
		desiredIsSet = true;
		motor.set(ControlMode.MotionMagic, desiredPosition);
		Debugger.logDetailedInfo("Setting Elevator position: " + desiredPosition);
		autoControl = true;
	}

	public void set(Positions position) {
		set(position.value);
	}

	public void setManual(double percentVolt) {
		motor.set(ControlMode.PercentOutput, percentVolt);
		autoControl = false;
	}

	public void holdPosition() {
		set(encoder.getPulses());
	}

	public int getDesiredPosition() {
		return desiredPosition;
	}

	public void setStop() {
		motor.neutralOutput();
		autoControl = false;
	}

	public boolean atSetpoint() {
		return Util.epsilonEquals(encoder.getPulses(), desiredPosition, allowedError);
	}

	public boolean atSetpoint(int error) {
		return Util.epsilonEquals(encoder.getPulses(), desiredPosition, error);
	}

	public boolean isArmSafeToGoUp() {
		return encoder.getPulses() > Positions.RADIO.value - allowedError;
	}

	public boolean isArmSafeToGoDown() {
		if (encoder.getPulses() < Positions.HIT_RAMP.value + allowedError)
			return true;
		else // only if the above is false
			return getDesiredPosition() > Positions.HIT_RAMP.value;
	}

	public int getEncoderPulses() {
		return encoder.getPulses();
	}

	public void useMagicControl(int desiredPosition) {
		// int currentPosition = encoder.getPulses();
		magicGains.selectGains(motor);
	}

	public boolean isButtonPressed() {
		return !reverseButton == button.get();
	}

	public void resetElevatorPos() {
		motor.setSelectedSensorPosition(Positions.BUTTON_POS.value, 0, Robot.CONFIG_TIMEOUT);
	}
}
