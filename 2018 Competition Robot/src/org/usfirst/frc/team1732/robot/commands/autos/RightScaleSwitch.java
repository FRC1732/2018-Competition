package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.autotools.DriverStationData;
import org.usfirst.frc.team1732.robot.commands.autos.base.PreAuto;
import org.usfirst.frc.team1732.robot.commands.autos.base.RightScaleLeftTwice;
import org.usfirst.frc.team1732.robot.commands.autos.base.RightScaleRightTwice;
import org.usfirst.frc.team1732.robot.commands.autos.base.RightScaleSwitchBase;
import org.usfirst.frc.team1732.robot.commands.autos.base.RightSwitchRightSide;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightScaleSwitch extends CommandGroup {

	public RightScaleSwitch() {
		addSequential(new PreAuto());
		if (DriverStationData.scaleIsLeft) {
			if (DriverStationData.closeSwitchIsLeft) {
				addSequential(new RightScaleLeftTwice());
			} else {
				addSequential(new RightSwitchRightSide());
			}
		} else {
			if (DriverStationData.closeSwitchIsLeft) {
				addSequential(new RightScaleRightTwice());
			} else {
				addSequential(new RightScaleSwitchBase());
			}
		}
	}
}
