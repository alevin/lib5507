package org.team5507.lib.wrappers;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import org.team5507.lib.util.Constants;

/**
 * Wraps a standard TalonSRX.
 *
 * <p>taken from https://github.com/HarkerRobo/HarkerRoboLib (team 1072)
 *
 * @author Chirag Kaushik
 * @author Ada Praun-Petrovic
 */
public class Falcon5507 extends WPI_TalonFX implements MotorController5507 {
    /**
     * Constructs a TalonSRXWrapper with the default timeout {{@link Constants#DEFAULT_TIMEOUT}.
     *
     * @param deviceNumber The CAN device ID of the Talon.
     */
    public Falcon5507(final int deviceNumber) {
        super(deviceNumber);
    }

    /**
     * Constructs a TalonSRXWrapper with the default timeout {{@link Constants#DEFAULT_TIMEOUT}.
     *
     * @param deviceNumber The CAN device ID of the Talon.
     */
    public Falcon5507(final int deviceNumber, String busId) {
        super(deviceNumber, busId);
    }

    @Override
    public ErrorCode configSelectedFeedbackSensor(RemoteFeedbackDevice feedbackDevice, int pidIdx) {
        return super.configSelectedFeedbackSensor(
                feedbackDevice, pidIdx, Constants.DEFAULT_TIMEOUT);
    }

    @Override
    public ErrorCode configSelectedFeedbackSensor(FeedbackDevice feedbackDevice, int pidIdx) {
        return super.configSelectedFeedbackSensor(
                feedbackDevice, pidIdx, Constants.DEFAULT_TIMEOUT);
    }
}
