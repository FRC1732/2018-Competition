package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autotools.Field;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveDistance;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveVoltage;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetOut;
import org.usfirst.frc.team1732.robot.commands.primitive.TurnAngle;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchCenterFront extends CommandGroup {

	public SwitchCenterFront(boolean switchIsLeft) {
		double startingX = Field.Switch.BOUNDARY.getMaxX() - 3 - Robot.drivetrain.robotWidth / 2.0;
		if (switchIsLeft) {
			double forwardDistance = Field.Zones.POWER_CUBE_ZONE.getY() - Robot.drivetrain.robotLength - 24;
			System.out.println("ForwardDistance: " + forwardDistance);

			addSequential(new DriveDistance(forwardDistance));
			addSequential(new TurnAngle(-90));
			double sideDistance = startingX - Field.Zones.POWER_CUBE_ZONE.getX() + Robot.drivetrain.robotLength / 2.0
					+ 15.0;
			addSequential(new DriveDistance(sideDistance));
			addSequential(new TurnAngle(90));
			double secondForward = Field.Switch.BOUNDARY.getY() - forwardDistance + 2.0 - Robot.drivetrain.robotLength;
			System.out.println("seconds forward distance: " + secondForward);
			addSequential(new DriveDistance(secondForward));
			addSequential(new ManipSetOut());
		} else {
			double distance1 = Field.Switch.BOUNDARY.getY() - Robot.drivetrain.robotLength + 2.0;
			addSequential(new DriveDistance(distance1));
			addSequential(new ManipSetOut());
		}
		addSequential(new DriveVoltage(0.2, 0.2, NeutralMode.Coast));
		addSequential(new ManipSetOut());
	}
}