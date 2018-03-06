package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autotools.Field;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmElevatorSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveDistance;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetOut;
import org.usfirst.frc.team1732.robot.commands.primitive.TurnAngleOvercomplicated;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScaleLeftSingle extends CommandGroup {

	public ScaleLeftSingle(boolean scaleIsLeft) {
		double startCenterX = Field.Switch.BOUNDARY.getX() - 5 - Robot.drivetrain.robotWidth / 2.0;
		if (scaleIsLeft) {
			double forwardDistance = Field.Scale.LEFT_NULL_ZONE.getY() - Robot.drivetrain.robotLength;
			addSequential(new DriveDistance(forwardDistance));
			addParallel(new ArmElevatorSetPosition(Arm.Positions.SCALE, Elevator.Positions.SCALE_HIGH));
			addSequential(new TurnAngleOvercomplicated(35));
			addSequential(new ManipSetOut());
		} else {
			double forwardDistance = Field.Switch.BOUNDARY.getMaxY() + Robot.drivetrain.robotLength + 4;
			double middlePartY = forwardDistance;
			addSequential(new DriveDistance(forwardDistance));
			addSequential(new TurnAngleOvercomplicated(90));
			double forward2 = Field.Switch.BOUNDARY.getMaxX() - startCenterX + 10;
			addSequential(new DriveDistance(forward2));
			addSequential(new TurnAngleOvercomplicated(-90));
			double forward3 = Field.Scale.RIGHT_NULL_ZONE.getMinY() - middlePartY;
			addSequential(new DriveDistance(forward3));
			addParallel(new ArmElevatorSetPosition(Arm.Positions.SCALE, Elevator.Positions.SCALE_HIGH));
			addSequential(new TurnAngleOvercomplicated(-30));
			addSequential(new ManipSetOut());
		}
	}
}
