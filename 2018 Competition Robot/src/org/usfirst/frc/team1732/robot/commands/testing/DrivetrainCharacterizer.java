package org.usfirst.frc.team1732.robot.commands.testing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.sensors.encoders.EncoderReader;

import edu.wpi.first.wpilibj.CircularBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DrivetrainCharacterizer extends Command {

	public static enum TestMode {
		QUASI_STATIC, STEP_VOLTAGE;
	}

	public static enum Direction {
		Forward, Backward;
	}

	private final TestMode mode;
	private final Direction direction;
	private final EncoderReader leftEncoder;
	private final EncoderReader rightEncoder;

	public DrivetrainCharacterizer(TestMode mode, Direction direction) {
		requires(Robot.drivetrain);
		this.mode = mode;
		this.direction = direction;
		this.leftEncoder = Robot.drivetrain.getLeftEncoderReader();
		this.rightEncoder = Robot.drivetrain.getRightEncoderReader();
	}

	private FileWriter fw;

	@Override
	protected void initialize() {
		leftEncoder.zero();
		rightEncoder.zero();

		String name;
		double scale;
		if (direction.equals(Direction.Forward)) {
			name = "Forward";
			scale = 1;
		} else {
			name = "Backward";
			scale = -1;
		}

		String path = "/U/DriveCharacterization/" + name;

		if (mode.equals(TestMode.QUASI_STATIC)) {
			System.out.println("QUASI STATIC");
			System.out.println(Robot.drivetrain.rightMaster.configOpenloopRamp(90, 10).name());
			System.out.println(Robot.drivetrain.leftMaster.configOpenloopRamp(90, 10).name());
			path = path + "QuasiStatic.csv";
			// voltageStep = 1 / 24.0 / 100.0 * scale;
			Robot.drivetrain.drive.tankDrive(1 * scale, 1 * scale);
		} else {
			System.out.println("STEP");
			System.out.println(Robot.drivetrain.rightMaster.configOpenloopRamp(0, 10).name());
			System.out.println(Robot.drivetrain.leftMaster.configOpenloopRamp(0, 10).name());
			path = path + "StepVoltage.csv";
			double speed = 0.7;
			Robot.drivetrain.drive.tankDrive(speed * scale, speed * scale);
		}
		try {
			File f = new File(path);
			if (f.exists()) {
				f.delete();
			}
			fw = new FileWriter(f, true);
			fw.write("");
			fw.flush();
			fw.write("LeftVolt, LeftVel, LeftAcc, RightVolt, RightVel, RightAcc\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int i = 0;
	private int length = 3;
	private final CircularBuffer timeBuff = new CircularBuffer(length);
	private final CircularBuffer leftVelBuff = new CircularBuffer(length);
	private final CircularBuffer rightVelBuff = new CircularBuffer(length);

	@Override
	protected void execute() {
		double time = Timer.getFPGATimestamp();
		double leftVel = leftEncoder.getRate();
		double rightVel = rightEncoder.getRate();
		double leftVolt = Robot.drivetrain.leftMaster.getMotorOutputVoltage();
		double rightVolt = Robot.drivetrain.rightMaster.getMotorOutputVoltage();
		timeBuff.addLast(time);
		leftVelBuff.addLast(leftVel);
		rightVelBuff.addLast(rightVel);
		if (i < length - 1) {
			i++;
			return;
		}
		double dt = time - timeBuff.removeFirst();
		double leftDv = leftVel - leftVelBuff.removeFirst();
		double rightDv = rightVel - rightVelBuff.removeFirst();
		double leftAcc = leftDv / dt;
		double rightAcc = rightDv / dt;
		String result = leftVolt + ", " + leftVel + ", " + leftAcc + ", " + rightVolt + ", " + rightVel + ", "
				+ rightAcc + "\n";
		try {
			fw.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.drivetrain.drive.tankDrive(0, 0);
		System.out.println(Robot.drivetrain.leftMaster.configOpenloopRamp(0, 10).name());
		System.out.println(Robot.drivetrain.rightMaster.configOpenloopRamp(0, 10).name());
		try {
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
