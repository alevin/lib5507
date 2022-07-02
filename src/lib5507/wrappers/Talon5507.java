package lib5507.wrappers;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;

import lib5507.util.Constants;

/**
 * Wraps a standard TalonSRX.
 * 
 * taken from https://github.com/HarkerRobo/HarkerRoboLib (team 1072)
 * @author Chirag Kaushik
 * @author Ada Praun-Petrovic
 */
public class Talon5507 extends TalonSRX implements MotorController5507 {
    /**
     * Constructs a TalonSRXWrapper with the default timeout
     * {{@link Constants#DEFAULT_TIMEOUT}.
     * 
     * @param deviceNumber The CAN device ID of the Talon.
     */
    public Talon5507(int deviceNumber) {
        super(deviceNumber);
    }

    @Override
    public ErrorCode configStatorCurrentLimit(StatorCurrentLimitConfiguration currLimitCfg, int timeoutMs) {
        configStatorCurrentLimit(currLimitCfg);
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode configStatorCurrentLimit(StatorCurrentLimitConfiguration currLimitCfg) {
        enableCurrentLimit(currLimitCfg.enable);
        configPeakCurrentLimit((int)currLimitCfg.triggerThresholdCurrent);
        configPeakCurrentDuration((int)(currLimitCfg.triggerThresholdTime * 1000));
        configContinuousCurrentLimit((int)currLimitCfg.currentLimit);
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode configGetSupplyCurrentLimit(SupplyCurrentLimitConfiguration currLimitConfigsToFill,
            int timeoutMs) {
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode configGetSupplyCurrentLimit(SupplyCurrentLimitConfiguration currLimitConfigsToFill) {
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode configGetStatorCurrentLimit(StatorCurrentLimitConfiguration currLimitConfigsToFill,
            int timeoutMs) {
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode configGetStatorCurrentLimit(StatorCurrentLimitConfiguration currLimitConfigsToFill) {
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode configIntegratedSensorAbsoluteRange(AbsoluteSensorRange absoluteSensorRange, int timeoutMs) {
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode configIntegratedSensorAbsoluteRange(AbsoluteSensorRange absoluteSensorRange) {
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode configIntegratedSensorOffset(double offsetDegrees, int timeoutMs) {
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode configIntegratedSensorOffset(double offsetDegrees) {
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode configIntegratedSensorInitializationStrategy(SensorInitializationStrategy initializationStrategy,
            int timeoutMs) {
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode configIntegratedSensorInitializationStrategy(SensorInitializationStrategy initializationStrategy) {
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode configSelectedFeedbackSensor(RemoteFeedbackDevice feedbackDevice, int pidIdx) {
        return super.configSelectedFeedbackSensor(feedbackDevice, pidIdx, Constants.DEFAULT_TIMEOUT);
    }

    @Override
    public ErrorCode configSelectedFeedbackSensor(FeedbackDevice feedbackDevice, int pidIdx) {
        return super.configSelectedFeedbackSensor(feedbackDevice, pidIdx, Constants.DEFAULT_TIMEOUT);
    }
}