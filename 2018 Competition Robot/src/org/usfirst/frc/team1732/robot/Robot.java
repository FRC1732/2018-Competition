/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.commands.OperatorControl;
import org.usfirst.frc.team1732.robot.conf.Config;
import org.usfirst.frc.team1732.robot.conf.ConfigNotFoundException;
import org.usfirst.frc.team1732.robot.input.Joysticks;
import org.usfirst.frc.team1732.robot.sensors.Sensors;
import org.usfirst.frc.team1732.robot.sensors.navx.NavXData;
import org.usfirst.frc.team1732.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static Config config;

	// subsystems
	public static Drivetrain drivetrain;
	public static Sensors sensors;
	// input
	public static Joysticks joysticks;
	// config
	public static final int PERIOD_MS = 10;
	public static final int CONFIG_TIMEOUT = 10; // recommended timeout by CTRE

	// Commands
	public static Command teleop;// = new OperatorControl();
	// Auto command Choices
	public static Command autoCommand;
	public static SendableChooser<Command> autoCommands = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		setPeriod(PERIOD_MS / 1000.0); // periodic methods will loop every 10 ms (1/100 sec)
		config = Config.load();

		initIO();
		initSubsystems();
		initCommands();

	}

	public void initSubsystems() {
		try {
			drivetrain = new Drivetrain();
			sensors = new Sensors();
		} catch (ConfigNotFoundException e) {
			System.out.println("Failed to Init Subsystems");
		}
	}

	public void initCommands() {
		try {
			autoCommands.addDefault("Defualt", null);// defualt auto command, null
			// autoCommands.addObject("NAME", new Command());// adds [Command] [NAME] to
			// dashboard selection
			// auto commands should not require subsytems, but rather instantiate commands
			// to control subsystems
		} catch (ConfigNotFoundException e) {
			System.out.println("Failed to Init Commands");
		}
	}

	public void initIO() {
		try {
			joysticks = new Joysticks();
		} catch (ConfigNotFoundException e) {
			System.out.println("Failed to Init Commands");
		}
	}

	private static boolean shouldReload = false;

	public static void reload() {
		shouldReload = true;
	}

	@Override
	public void robotPeriodic() {
		Scheduler.getInstance().run();
		NavXData.sendNavXData(sensors.navX);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		if(shouldReload) {
			robotInit();
		}
		
		// guarantees that the teleop and auto commands are not running
		teleop.cancel();
		if(autoCommand != null) {
			autoCommand.cancel();
		}

		drivetrain.setStop();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		if(shouldReload) {
			robotInit();
		}
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		if (Config.isLoaded()) {// Guarantees that the subsystems have initialized correctly
			/*
			 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
			 * switch(autoSelected) { case "My Auto": autonomousCommand = new
			 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
			 * ExampleCommand(); break; }
			 */

			// schedule the autonomous command
			autoCommand = autoCommands.getSelected();
			if(autoCommand != null) {
				autoCommand.start();
			}else {
				System.out.println("No auto command Selected");
			}
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		if (Config.isLoaded()) {// Guarantees that the subsystems have initialized correctly
			Scheduler.getInstance().run();
		}
	}

	@Override
	public void teleopInit() {
		if (Config.isLoaded()) {// Guarantees that the subsystems have initialized correctly
			// This makes sure that the autonomous stops running when
			// teleop starts running. If you want the autonomous to
			// continue until interrupted by another command, remove
			// this line or comment it out.
			
			teleop.start();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		if (Config.isLoaded()) {// Guarantees that the subsystems have initialized correctly
			Scheduler.getInstance().run();
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		if (Config.isLoaded()) {// Guarantees that the subsystems have initialized correctly

		}
	}
}
