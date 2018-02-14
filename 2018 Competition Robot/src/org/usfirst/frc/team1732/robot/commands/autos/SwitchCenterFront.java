package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.autotools.Field;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveDistance;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetOut;
import org.usfirst.frc.team1732.robot.commands.primitive.TurnAngle;
import org.usfirst.frc.team1732.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchCenterFront extends CommandGroup {

	public SwitchCenterFront(boolean switchIsRight) {
		double startingX = Field.Switch.BOUNDARY.getMaxX() - 3 - Drivetrain.ROBOT_WIDTH / 2.0;
		if (switchIsRight) {
			double distance1 = Field.Switch.BOUNDARY.getY() - Drivetrain.ROBOT_LENGTH + 2.0;
			addSequential(new DriveDistance(distance1));
			addSequential(new ManipSetOut());
		} else {
			double forwardDistance = Field.Zones.POWER_CUBE_ZONE.getY() - Drivetrain.ROBOT_LENGTH - 24;
			addSequential(new DriveDistance(forwardDistance));
			addSequential(new TurnAngle(-90));
			double sideDistance = startingX - Field.Zones.POWER_CUBE_ZONE.getX() + Drivetrain.ROBOT_LENGTH / 2.0 + 10.0;
			addSequential(new DriveDistance(sideDistance));
			addSequential(new TurnAngle(90));
			addSequential(new DriveDistance(Field.Switch.BOUNDARY.getY() - forwardDistance + 2.0));
			addSequential(new ManipSetOut());
		}
	}
}