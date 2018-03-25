package org.usfirst.frc.team1732.robot.commands.testing;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.drivercontrol.DifferentialDrive;
import org.usfirst.frc.team1732.robot.sensors.navx.GyroReader;
import org.usfirst.frc.team1732.robot.util.NotifierCommand;

public class TestGyroReader extends NotifierCommand {

	private final DifferentialDrive drive;
	private final GyroReader gyro;

	public TestGyroReader() {
		super(20);
		requires(Robot.drivetrain);
		this.drive = Robot.drivetrain.drive;
		gyro = Robot.sensors.navx.makeReader();
	}

	@Override
	protected void init() {
		Robot.drivetrain.setCoast();
		gyro.zero();
		System.out.println("Start: " + Robot.sensors.navx.getAngle() + ", " + gyro.getAngle());
	}

	@Override
	protected void exec() {
		System.out.println(Robot.sensors.navx.getAngle() + ", " + gyro.getAngle());
		drive.tankDrive(-Robot.joysticks.getRight(), -Robot.joysticks.getLeft(), false);
	}

	@Override
	protected boolean isDone() {
		return false;
	}

	@Override
	protected void whenEnded() {
		System.out.println("End: " + Robot.sensors.navx.getAngle() + ", " + gyro.getAngle());
	}

}
