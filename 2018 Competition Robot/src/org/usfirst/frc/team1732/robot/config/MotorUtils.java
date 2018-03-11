package org.usfirst.frc.team1732.robot.config;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlFrame;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Spark;

public class MotorUtils {

	public static TalonSRX makeTalon(CTREParam param, CTREConfig config) {
		TalonSRX motor = configureBaseMotorController(new TalonSRX(param.id), param, config);

		motor.enableCurrentLimit(config.enableCurrentLimit);
		printError(motor.configContinuousCurrentLimit(config.continousCurrentLimit, CTREConfig.CONFIG_TIMEOUT));
		printError(motor.configPeakCurrentDuration(config.peakCurrentDuration, CTREConfig.CONFIG_TIMEOUT));
		printError(motor.configPeakCurrentLimit(config.peakCurrentLimit, CTREConfig.CONFIG_TIMEOUT));

		printError(motor.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, config.quadratureStatusPeriod,
				CTREConfig.CONFIG_TIMEOUT));
		printError(motor.setStatusFramePeriod(StatusFrameEnhanced.Status_8_PulseWidth, config.pulseWidthStatusPeriod,
				CTREConfig.CONFIG_TIMEOUT));
		return motor;
	}

	public static VictorSPX makeVictor(CTREParam param, CTREConfig config) {
		return configureBaseMotorController(new VictorSPX(param.id), param, config);
	}
	
	public static VictorSPX makeVictorFollower(CTREParam param, CTREConfig config, IMotorController masterToFollow) {
		VictorSPX v = configureBaseMotorController(new VictorSPX(param.id), param, config);
		v.follow(masterToFollow);
		return v;
	}

	private static <T extends BaseMotorController> T configureBaseMotorController(T motor, CTREParam param,
			CTREConfig config) {
		motor.setInverted(param.inverted);
		if (param.isFollower()) {
			motor.set(ControlMode.Follower, param.masterID);
		}
		motor.setNeutralMode(config.neutralMode);

		printError(motor.configNeutralDeadband(config.neutralDeadbandPercent, CTREConfig.CONFIG_TIMEOUT));
		printError(motor.configNominalOutputForward(config.nominalForward, CTREConfig.CONFIG_TIMEOUT));
		printError(motor.configNominalOutputReverse(config.nominalReverse, CTREConfig.CONFIG_TIMEOUT));
		printError(motor.configPeakOutputForward(config.peakOutputForward, CTREConfig.CONFIG_TIMEOUT));
		printError(motor.configPeakOutputReverse(config.peakOutputReverse, CTREConfig.CONFIG_TIMEOUT));

		printError(motor.configOpenloopRamp(config.openLoopRamp, CTREConfig.CONFIG_TIMEOUT));

		motor.enableVoltageCompensation(config.enableVoltageCompensation);
		printError(motor.configVoltageCompSaturation(config.voltageCompensationSaturation, CTREConfig.CONFIG_TIMEOUT));

		printError(motor.configVoltageMeasurementFilter(config.voltageMeasurementWindow, CTREConfig.CONFIG_TIMEOUT));

		printError(motor.setStatusFramePeriod(StatusFrame.Status_1_General, config.generalStatusPeriod,
				CTREConfig.CONFIG_TIMEOUT));
		printError(motor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, config.generalStatusPeriod,
				CTREConfig.CONFIG_TIMEOUT));
		printError(motor.setStatusFramePeriod(StatusFrame.Status_4_AinTempVbat,
				config.analogTemperatureBatteryStatusPeriod, CTREConfig.CONFIG_TIMEOUT));
		printError(motor.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, config.currentMPtargetStatusPeriod,
				CTREConfig.CONFIG_TIMEOUT));
		printError(motor.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, config.PIDerrorStatusPeriod,
				CTREConfig.CONFIG_TIMEOUT));
		printError(motor.setControlFramePeriod(ControlFrame.Control_3_General, config.controlFramePeriod));

		printError(motor.configVelocityMeasurementPeriod(config.velocityMeasurementPeriod, CTREConfig.CONFIG_TIMEOUT));
		printError(motor.configVelocityMeasurementWindow(config.velocityMeasurementWindow, CTREConfig.CONFIG_TIMEOUT));
		printError(motor.changeMotionControlFramePeriod(config.motionControlFramePeriod));
		return motor;
	}

	private static void printError(ErrorCode e) {
		if (!e.equals(ErrorCode.OK)) {
			System.err.println("Error, see docs: " + e.value + "= " + e.name());
		}
	}

	public static Spark configSpark(int pwmChannel, boolean isInverted) {
		Spark spark = new Spark(pwmChannel);
		spark.setInverted(isInverted);
		return spark;
	}
}
