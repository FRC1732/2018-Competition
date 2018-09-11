package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autotools.DriverStationData;
import org.usfirst.frc.team1732.robot.commands.autos.base.ManipAutoEject;
import org.usfirst.frc.team1732.robot.commands.autos.base.PostAuto;
import org.usfirst.frc.team1732.robot.commands.autos.base.PreAuto;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmMagicPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveTime;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;
import org.usfirst.frc.team1732.robot.commands.primitive.Wait;
import org.usfirst.frc.team1732.robot.subsystems.Arm;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchCenterFront extends CommandGroup {

	public SwitchCenterFront() {
		addSequential(new PreAuto());
		if (DriverStationData.closeSwitchIsLeft) {
			addSequential(new FollowVelocityPath(Robot.paths.centerSwitchFrontLeft));
		} else {
			addSequential(new FollowVelocityPath(Robot.paths.centerSwitchFrontStraight));
		}
		addParallel(new DriveTime(0.2, 0.2, NeutralMode.Coast, 2, 0));
		addSequential(new CommandGroup() {
			{
				addParallel(new ArmMagicPosition(Arm.Positions.TUCK));
				addSequential(new Wait(2));
				addSequential(new ManipAutoEject(0.7));
			}
		});

		/*
		 * New Auto Pathing if (DriverStationData.closeSwitchIsLeft) { addSequential(new
		 * CommandGroup() { { addParallel(new
		 * ArmElevatorSetPosition(Arm.Positions.INTAKE, Elevator.Positions.INTAKE));
		 * addParallel(new CommandGroup() { { addSequential(new Wait(0.25));
		 * addSequential(new ManipSetIn()); } }); addSequential(new
		 * DriveToAngleWithRadius(90, 60, false)); addParallel(new
		 * DriveWithEncoders(-30)); addSequential(new ManipSetStop(Manip.RAMP_TIME));
		 * addParallel(new DriveToAngleWithRadius(90, 70, true)); addParallel(new
		 * CommandGroup() { { addParallel(new ArmMagicPosition(Arm.Positions.TUCK));
		 * addSequential(new Wait(2)); addSequential(new ManipAutoEject(0.7)); } }); }
		 * }); } else { addSequential(new CommandGroup() { { addParallel(new
		 * ArmElevatorSetPosition(Arm.Positions.INTAKE, Elevator.Positions.INTAKE));
		 * addParallel(new CommandGroup() { { addSequential(new Wait(0.25));
		 * addSequential(new ManipSetIn()); } }); addSequential(new
		 * DriveToAngleWithRadius(-90, 60, false)); addParallel(new
		 * DriveWithEncoders(-30)); addSequential(new ManipSetStop(Manip.RAMP_TIME));
		 * addParallel(new DriveoAngleWithRadius(-90, 70, true)); addParallel(new
		 * CommandGroup() { { addParallel(new ArmMagicPosition(Arm.Positions.TUCK));
		 * addSequential(new Wait(2)); addSequential(new ManipAutoEject(0.7)); } }); }
		 * }); }
		 */
		addSequential(new PostAuto());
	}
}