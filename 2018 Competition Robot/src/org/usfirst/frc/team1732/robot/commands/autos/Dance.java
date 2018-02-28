package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.commands.primitive.DriveDistance;
import org.usfirst.frc.team1732.robot.commands.primitive.TurnAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Dance extends CommandGroup {

	public static final double DISTANCE = 30;

	private final int dance;

	public Dance(int dance) {
		this.dance = dance;
	}

	@Override
	protected void initialize() {
		for (int i = 0; i < dance; i++) {
			if ((int) (Math.random() * 2) == 0) {
				addSequential(new DriveDistance(DISTANCE));
				addSequential(new DriveDistance(-DISTANCE));
			} else {
				addSequential(new TurnAngle((int) (Math.random() * 360)));
			}
		}
	}
}
