package org.usfirst.frc.team1732.robot.config;

import java.util.function.Supplier;

import org.usfirst.frc.team1732.robot.config.robots.CompetitionConfig;
import org.usfirst.frc.team1732.robot.config.robots.PracticeConfig;
import org.usfirst.frc.team1732.robot.controlutils.ClosedLoopProfile;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

// this class uses suppliers that way only the config we're using gets instantiated

public class RobotConfig {

	// drivetrain
	public final double robotLength = 0;
	public final double robotWidth = 0;

	public double effectiveRobotWidth = 0;
	public double drivetrainInchesPerPulse = 0;
	public double maxInPerSec = 0;
	public double maxInPerSecSq = 0;

	public final CTREConfig drivetrainConfig = CTREConfig.getDefaultConfig();

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

	public final ClosedLoopProfile drivetrainMotionPID = new ClosedLoopProfile("Drivetrain Motion PID",
			FeedbackDevice.QuadEncoder, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	public final ClosedLoopProfile drivetrainVelocityPID = new ClosedLoopProfile("Drivetrain Velocity PID",
			FeedbackDevice.QuadEncoder, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);

	// arm
	public final CTREConfig armConfig = CTREConfig.getDefaultConfig();
	private final boolean reverseArm = false;
	public final CTREParam arm = new CTREParam(-1, reverseArm);
	public final ClosedLoopProfile armUpPID = new ClosedLoopProfile("Arm Up PID",
			FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	public final ClosedLoopProfile armDownPID = new ClosedLoopProfile("Arm Down PID",
			FeedbackDevice.CTRE_MagEncoder_Absolute, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	public double armDegreesPerPulse = 0;

	// climber
	public final CTREConfig climberConfig = CTREConfig.getDefaultConfig();
	private final int climberMasterID = -1;
	private final boolean reverseClimb = false;
	public final CTREParam climberMaster = new CTREParam(climberMasterID, reverseClimb);
	public final CTREParam climberFollower = new CTREParam(-1, reverseClimb, climberMasterID);

	// elevator
	public final CTREConfig elevatorConfig = CTREConfig.getDefaultConfig();
	private final boolean reverseElevator = false;
	public final CTREParam elevator = new CTREParam(-1, reverseElevator);
	public final ClosedLoopProfile elevatorUpPID = new ClosedLoopProfile("Elevator Up PID",
			FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	public final ClosedLoopProfile elevatorDownPID = new ClosedLoopProfile("Elevator Down PID",
			FeedbackDevice.CTRE_MagEncoder_Absolute, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	public double elevatorDegreesPerPulse = 0;

	// cube manip (intake)
	public final CTREConfig manipConfig = CTREConfig.getDefaultConfig();
	private final int manipMasterID = -1;
	private final boolean reverseManip = false;
	public final CTREParam manipMaster = new CTREParam(manipMasterID, reverseManip);
	public final CTREParam manipFollower = new CTREParam(-1, reverseManip, manipMasterID);
	public double manipStopCurrent = 0;
	public double manipHoldCurrent = 0;

	public final Port navxPort = SPI.Port.kMXP;

	public final double inputDeadband = 0.025; // 2.5%
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