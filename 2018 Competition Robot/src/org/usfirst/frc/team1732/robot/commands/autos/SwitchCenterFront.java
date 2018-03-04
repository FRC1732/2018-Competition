package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autotools.Field;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveDistance;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveVoltage;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetOut;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchCenterFront extends CommandGroup {

	public SwitchCenterFront(boolean switchIsLeft) {
		if (switchIsLeft) {
			addSequential(new FollowVelocityPath(Robot.paths.switchCenterFrontScoreLeftProfile));
		} else {
			double distance1 = Field.Switch.BOUNDARY.getY() - Robot.drivetrain.robotLength + 2.0;
			addSequential(new DriveDistance(distance1));
		}
		addSequential(new DriveVoltage(0.15, 0.15, NeutralMode.Coast));
		addSequential(new ManipSetOut());
	}
}