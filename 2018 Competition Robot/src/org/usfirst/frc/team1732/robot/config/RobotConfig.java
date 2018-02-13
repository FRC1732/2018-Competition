package org.usfirst.frc.team1732.robot.config;

import java.util.function.Supplier;

import org.usfirst.frc.team1732.robot.config.robots.CompetitionConfig;
import org.usfirst.frc.team1732.robot.config.robots.PracticeConfig;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

// this class uses suppliers that way only the config we're using gets instantiated

public class RobotConfig {

	// drivetrain
	public CTREConfig drivetrainConfig = CTREConfig.getDefaultConfig();
	public int leftMasterID = 1;
	boolean reverseLeft = false;
	public CTREParam leftMaster = new CTREParam(leftMasterID, reverseLeft);
	public CTREParam leftFollower1 = new CTREParam(2, reverseLeft, leftMasterID);
	public CTREParam leftFollower2 = new CTREParam(3, reverseLeft, leftMasterID);
	public int rightMasterID = 4;
	boolean reverseRight = false;
	public CTREParam rightMaster = new CTREParam(rightMasterID, reverseRight);
	public CTREParam rightFollower1 = new CTREParam(5, reverseRight, rightMasterID);
	public CTREParam rightFollower2 = new CTREParam(6, reverseRight, rightMasterID);
	// arm
	public CTREConfig armConfig = CTREConfig.getDefaultConfig();
	public int armMotorID = -1;
	boolean reverseArm = false;
	public CTREParam arm = new CTREParam(armMotorID, reverseArm);
	// climber
	public CTREConfig climberConfig = CTREConfig.getDefaultConfig();
	public int climberMasterID = -1;
	boolean reverseClimb = false;
	public CTREParam climberMaster = new CTREParam(climberMasterID, reverseClimb);
	public CTREParam climberFollower = new CTREParam(-1, reverseClimb, climberMasterID);
	// elevator
	public CTREConfig elevatorConfig = CTREConfig.getDefaultConfig();
	public int elevatorMotorID = -1;
	boolean reverseElevator = false;
	public CTREParam elevator = new CTREParam(elevatorMotorID, reverseElevator);
	// cube manip (intake)
	public CTREConfig manipConfig = CTREConfig.getDefaultConfig();
	public int manipMasterID = -1;
	boolean reverseManip = false;
	public CTREParam manipMaster = new CTREParam(manipMasterID, reverseManip);
	public CTREParam manipFollower = new CTREParam(-1, reverseManip, manipMasterID);
	public Port navxPort = SPI.Port.kMXP;

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

	protected RobotConfig() {}

}