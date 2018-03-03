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
	public final double robotLength = 38.5;
	public final double robotWidth = 33.5;

	public double effectiveRobotWidth = 0;
	public double drivetrainInchesPerPulse = 0;
	public double maxUnitsPer100Ms = 0;

	public final CTREConfig drivetrainConfig = CTREConfig.getDefaultConfig();

	public final int shiftingSolenoidID = 0;
	public final boolean highGearValue = true; // setting the shifter solenoid to this value should make the drivetrain
												// go in high gear

	private final int leftMasterID = 0;
	private final boolean reverseLeft = true;
	public final boolean reverseLeftSensor = false;
	public final CTREParam leftMaster = new CTREParam(leftMasterID, reverseLeft);
	public final CTREParam leftFollower1 = new CTREParam(1, reverseLeft, leftMasterID);
	public final CTREParam leftFollower2 = new CTREParam(2, reverseLeft, leftMasterID);

	private final int rightMasterID = 15;
	private final boolean reverseRight = false;
	public final boolean reverseRightSensor = false;
	public final CTREParam rightMaster = new CTREParam(rightMasterID, reverseRight);
	public final CTREParam rightFollower1 = new CTREParam(14, reverseRight, rightMasterID);
	public final CTREParam rightFollower2 = new CTREParam(13, reverseRight, rightMasterID);

	public final ClosedLoopProfile drivetrainVelocityPID = new ClosedLoopProfile("Drivetrain Velocity PID",
			FeedbackDevice.QuadEncoder, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);

	// arm
	public final CTREConfig armConfig = CTREConfig.getDefaultConfig();
	public final double armRampTime = 0.2;
	{
		armConfig.openLoopRamp = armRampTime;
		armConfig.peakOutputForward = 0.4;
		armConfig.peakOutputReverse = -0.4;
	}
	private final boolean reverseArm = true;
	public final boolean reverseArmSensor = false;
	public final CTREParam arm = new CTREParam(12, reverseArm);
	public final ClosedLoopProfile armUpPID = new ClosedLoopProfile("Arm Up PID",
			FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0, 0, 0, 0, 0, 0, 0, 0, armRampTime);
	public final ClosedLoopProfile armDownPID = new ClosedLoopProfile("Arm Down PID",
			FeedbackDevice.CTRE_MagEncoder_Absolute, 1, 0, 0, 0, 0, 0, 0, 0, 0, armRampTime);
	public final int armAllowedErrorCount = 80;
	public int armStartingCount = 0;

	// climber
	public final CTREConfig climberConfig = CTREConfig.getDefaultConfig();
	private final int climberMasterID = 4;
	private final boolean reverseClimberMaster = false;
	private final boolean reverseClimberFollower = false;
	public final CTREParam climberMaster = new CTREParam(climberMasterID, reverseClimberMaster);
	public final CTREParam climberFollower = new CTREParam(5, reverseClimberFollower, climberMasterID);

	// elevator
	public final CTREConfig elevatorConfig = CTREConfig.getDefaultConfig();
	public final double elevatorRampTime = 0.80;
	{
		elevatorConfig.openLoopRamp = elevatorRampTime;
		elevatorConfig.peakOutputForward = 0.6;
		elevatorConfig.peakOutputReverse = -0.6;
	}
	private final boolean reverseElevator = true;
	public final boolean reverseElevatorSensor = true;
	public final CTREParam elevator = new CTREParam(3, reverseElevator);
	public final ClosedLoopProfile elevatorUpPID = new ClosedLoopProfile("Elevator Up PID",
			FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0, 0, 0, 0, 0, 0, 0, 0, elevatorRampTime);
	public final ClosedLoopProfile elevatorDownPID = new ClosedLoopProfile("Elevator Down PID",
			FeedbackDevice.CTRE_MagEncoder_Absolute, 1, 0, 0, 0, 0, 0, 0, 0, 0, elevatorRampTime);
	public final int elevatorAllowedErrorCount = 50;
	public int elevatorStartingCount = 0;

	// cube manip (intake)
	public final CTREConfig manipConfig = CTREConfig.getDefaultConfig();
	private final int manipMasterID = 10;
	private final boolean reverseManipMaster = false;
	private final boolean reverseManipFollower = true;
	public final CTREParam manipMaster = new CTREParam(manipMasterID, reverseManipMaster);
	public final CTREParam manipFollower = new CTREParam(11, reverseManipFollower, manipMasterID);
	public double manipStopCurrent = 0;

	// ramp
	public final int rampSolenoidID = 1;
	public final boolean rampOutValue = true;// setting the ramp solenoid to this value should make it go out

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
		Preferences.getInstance().putString(PREF_KEY, robot);
		RobotConfig config = ROBOTS.valueOf(ROBOTS.class, robot).getConfig();
		System.out.println("Loaded robot: " + config.getClass().getName());
		return config;
	}

	protected RobotConfig() {
	}

}