package org.usfirst.frc.team1732.robot.config;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

// this class uses suppliers that way only the config we're using gets instantiated

public class RobotConfig {

	private static RobotConfig competitionConfig() {
		RobotConfig config = new RobotConfig();
		// config stuff

		return config;
	}

	private static RobotConfig practiceConfig() {
		RobotConfig config = new RobotConfig();
		// config stuff

		return config;
	}

	// default configuration:

	public CTREConfig drivetrainConfig = CTREConfig.getDefaultConfig();
	{
		// make default changes to drivetrainConfig here
	}
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

	public Port navxPort = SPI.Port.kMXP;

	public final int leftJoystickPort = 0;
	public final int rightJoystickPort = 0;
	public final int buttonJoystickPort = 0;

	private static RobotConfig defaultConfig() {
		RobotConfig config = new RobotConfig();
		return config;
	}

	public static enum ROBOTS {
		DEFAULT(RobotConfig::defaultConfig), COMPETITION(RobotConfig::competitionConfig), PRACTICE(
				RobotConfig::practiceConfig);

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

	private RobotConfig() {
	}

}