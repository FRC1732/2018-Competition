package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetOut;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetStop;
import org.usfirst.frc.team1732.robot.commands.primitive.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ManipAutoEject extends CommandGroup {

	public ManipAutoEject(double shoottime, double manipSpeed) {
		addSequential(new ManipSetOut(manipSpeed));
		addSequential(new Wait(shoottime));
		addSequential(new ManipSetStop());
	}

	public ManipAutoEject(double manipSpeed) {
		this(0.25, manipSpeed);
	}

}
