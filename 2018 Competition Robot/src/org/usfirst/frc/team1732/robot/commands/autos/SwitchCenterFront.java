package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autotools.Field;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveDistance;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetOut;
import org.usfirst.frc.team1732.robot.commands.primitive.TurnAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchCenterFront extends CommandGroup {

	public SwitchCenterFront(boolean switchIsRight) {
		double startingX = Field.Switch.BOUNDARY.getMaxX() - 3 - Robot.drivetrain.robotWidth / 2.0;
		if (switchIsRight) {
			double distance1 = Field.Switch.BOUNDARY.getY() - Robot.drivetrain.robotLength + 2.0;
			addSequential(new DriveDistance(distance1));
			addSequential(new ManipSetOut());
		} else {
			double forwardDistance = Field.Zones.POWER_CUBE_ZONE.getY() - Robot.drivetrain.robotLength - 24;
			addSequential(new DriveDistance(forwardDistance));
			addSequential(new TurnAngle(-90));
			double sideDistance = startingX - Field.Zones.POWER_CUBE_ZONE.getX() + Robot.drivetrain.robotLength / 2.0
					+ 10.0;
			addSequential(new DriveDistance(sideDistance));
			addSequential(new TurnAngle(90));
			addSequential(new DriveDistance(Field.Switch.BOUNDARY.getY() - forwardDistance + 2.0));
			addSequential(new ManipSetOut());
		}
	}
}