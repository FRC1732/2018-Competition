package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autotools.Field;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmElevatorSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveDistance;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetOut;
import org.usfirst.frc.team1732.robot.commands.primitive.TurnAngle;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScaleRightSingle extends CommandGroup {

	public ScaleRightSingle(boolean scaleIsLeft) {
		if (scaleIsLeft) {
			double forwardDistance = Field.Scale.LEFT_NULL_ZONE.getY() - Robot.drivetrain.robotLength;
			addSequential(new DriveDistance(forwardDistance));
			addParallel(new ArmElevatorSetPosition(Arm.Positions.SCALE, Elevator.Positions.SCALE_HIGH));
			addSequential(TurnAngle.absolute(-35, 0.25));
			addSequential(new ManipSetOut());
		} else {
		}
	}
}
