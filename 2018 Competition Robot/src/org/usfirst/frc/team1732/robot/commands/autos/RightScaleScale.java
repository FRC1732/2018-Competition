package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.autotools.DriverStationData;
import org.usfirst.frc.team1732.robot.commands.autos.base.RightScaleLeftTwice;
import org.usfirst.frc.team1732.robot.commands.autos.base.RightScaleRightTwice;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightScaleScale extends CommandGroup {

	public RightScaleScale() {
		if (DriverStationData.scaleIsLeft) {
			addSequential(new RightScaleLeftTwice());
		} else {
			addSequential(new RightScaleRightTwice());
		}
	}
}
