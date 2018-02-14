package org.usfirst.frc.team1732.robot.config;

import java.util.function.Supplier;

import org.usfirst.frc.team1732.robot.config.robots.CompetitionConfig;
import org.usfirst.frc.team1732.robot.config.robots.PracticeConfig;
import org.usfirst.frc.team1732.robot.controlutils.ClosedLoopProfile;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

// this class uses suppliers that way only the config we're using gets instantiated

public class RobotConfig {

	// drivetrain
	public CTREConfig drivetrainConfig = CTREConfig.getDefaultConfig();
	public double drivetrainInchesPerPulse = 0;

	private final int leftMasterID = -1;
	private final boolean reverseLeft = false;
	public final CTREParam leftMaster = new CTREParam(leftMasterID, reverseLeft);
	public final CTREParam leftFollower1 = new CTREParam(-1, reverseLeft, leftMasterID);
	public final CTREParam leftFollower2 = new CTREParam(-1, reverseLeft, leftMasterID);

	private final int rightMasterID = -1;
	private final boolean reverseRight = false;
	public final CTREParam rightMaster = new CTREParam(rightMasterID, reverseRight);
	public final CTREParam rightFollower1 = new CTREParam(-1, reverseRight, rightMasterID);
	public final CTREParam rightFollower2 = new CTREParam(-1, reverseRight, rightMasterID);

	// arm
	public CTREConfig armConfig = CTREConfig.getDefaultConfig();
	private final int armMotorID = -1;
	private final boolean reverseArm = false;
	public final CTREParam arm = new CTREParam(armMotorID, reverseArm);
	public final ClosedLoopProfile armPID = new ClosedLoopProfile("Arm PID", 0, 0, 0, 0, 0, 0, 0, 0);
	public double armDegreesPerPulse = 0;

	// climber
	public CTREConfig climberConfig = CTREConfig.getDefaultConfig();
	private final int climberMasterID = -1;
	private final boolean reverseClimb = false;
	public final CTREParam climberMaster = new CTREParam(climberMasterID, reverseClimb);
	public final CTREParam climberFollower = new CTREParam(-1, reverseClimb, climberMasterID);

	// elevator
	public CTREConfig elevatorConfig = CTREConfig.getDefaultConfig();
	private final int elevatorMotorID = -1;
	private final boolean reverseElevator = false;
	public final CTREParam elevator = new CTREParam(elevatorMotorID, reverseElevator);
	public final ClosedLoopProfile elevatorPID = new ClosedLoopProfile("Elevator PID", 0, 0, 0, 0, 0, 0, 0, 0);
	public double elevatorDegreesPerPulse = 0;

	// cube manip (intake)
	public CTREConfig manipConfig = CTREConfig.getDefaultConfig();
	private final int manipMasterID = -1;
	private final boolean reverseManip = false;
	public final CTREParam manipMaster = new CTREParam(manipMasterID, reverseManip);
	public final CTREParam manipFollower = new CTREParam(-1, reverseManip, manipMasterID);

	public final Port navxPort = SPI.Port.kMXP;

	public final int leftJoystickPort = 0;
	public final int rightJoystickPort = 1;
	public final int buttonJoystickPort = 2;

	public static enum ROBOTS {
		DEFAULT(RobotConfig::new), COMPETITION(CompetitionConfig::new), PRACTICE(PracticeConfig::new);

		private final Supplier<RobotConfig> supplier;

		private ROBOTS(Supplier<RobotConfig> supplier) {
			this.supplier = supplier;
		}

		public RobotConfig getConfig() {
			return supplier.get();
		}
	}

	private static final String PREF_KEY = "ROBOT";

	public static RobotConfig getConfig() {
		String robot = Preferences.getInstance().getString(PREF_KEY, ROBOTS.DEFAULT.name());
		System.out.println("Loaded robot: " + robot);
		return ROBOTS.valueOf(ROBOTS.class, robot).getConfig();
	}

	protected RobotConfig() {
	}

}