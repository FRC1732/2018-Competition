package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.autotools.DriverStationData;
import org.usfirst.frc.team1732.robot.commands.autos.base.RightScaleRightTwice;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightScaleScaleNoCrossStraight extends CommandGroup {

	public RightScaleScaleNoCrossStraight() {
		if (DriverStationData.scaleIsLeft) {
			addSequential(new DefaultDriveForward());
			// if (DriverStationData.closeSwitchIsLeft) {
			// addSequential(new DefaultDriveForward());
			// } else {
			// addSequential(new RightSwitchRightSide());
			// }
		} else {
			addSequential(new RightScaleRightTwice());
		}
	}
}
