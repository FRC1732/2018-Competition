package org.usfirst.frc.team1732.robot.commands.autos;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autotools.DriverStationData;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmElevatorSetPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.ArmMagicPosition;
import org.usfirst.frc.team1732.robot.commands.primitive.DriveTime;
import org.usfirst.frc.team1732.robot.commands.primitive.FollowVelocityPath;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetOut;
import org.usfirst.frc.team1732.robot.commands.primitive.ManipSetStop;
import org.usfirst.frc.team1732.robot.commands.primitive.Wait;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.Elevator;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchCenterFront extends CommandGroup {

	public SwitchCenterFront() {
		addSequential(new ArmMagicPosition(Arm.Positions.START));
		addParallel(new CommandGroup() {
			{
				addSequential(new ArmElevatorSetPosition(Arm.Positions.SWITCH, Elevator.Positions.INTAKE));
				addSequential(new ArmMagicPosition(Arm.Positions.TUCK));
			}
		});
		if (DriverStationData.closeSwitchIsLeft) {
			addSequential(new FollowVelocityPath(Robot.paths.switchCenterFrontLeftProfile));
		} else {
			addSequential(new FollowVelocityPath(Robot.paths.switchCenterFrontStraightProfile));
		}
		addParallel(new CommandGroup() {
			{
				addSequential(new Wait(0.25));
				addSequential(new ManipSetOut(0.7));
				addSequential(new Wait(0.5));
				addSequential(new ManipSetStop());
			}
		});
		addSequential(new DriveTime(0.3, 0.3, NeutralMode.Coast, 4));
	}
}