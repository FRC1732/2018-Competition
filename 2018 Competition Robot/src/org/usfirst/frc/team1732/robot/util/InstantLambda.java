package org.usfirst.frc.team1732.robot.util;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class InstantLambda {
	public static InstantCommand makeCommand(Subsystem dependancy, Runnable initialize) {
		return new InstantCommand() {
			{
				requires(dependancy);
			}

			protected void initialize() {
				initialize.run();
			}
		};
	}

	public static InstantCommand makeCommand(Runnable initialize) {
		return new InstantCommand() {
			protected void initialize() {
				initialize.run();
			}
		};
	}

}
