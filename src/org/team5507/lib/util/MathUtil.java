package org.team5507.lib.util;

import java.util.Arrays;
import java.util.List;

/**
 * Contains a series of useful mathematical functions.
 *
 * @author Finn Frankis
 * @author Angela Jia
 * @version 10/16/18
 */
public final class MathUtil {
    /**
     * Constrains one value into a given range.
     *
     * @param value the value to be constrained.
     * @param minValue the minimum value this value can take on.
     * @param maxValue the maximum value this value can take on.
     * @precondition minValue < maxValue
     * @return the constrained value.
     */
    public static double constrain(double value, double minValue, double maxValue) {
        return Math.max(Math.min(value, maxValue), minValue);
    }

    /**
     * Linearly maps a value currently within a given range to another range.
     *
     * @param value the value to be mapped.
     * @param currentMin the current minimum possible value that value can take on.
     * @param currentMax the current maximum possible value that value can take on.
     * @param desiredMin the desired minimum possible value that value can take on.
     * @param desiredMax the desired maximum possible value that value can take on.
     * @return
     */
    public static double map(
            double value,
            double currentMin,
            double currentMax,
            double desiredMin,
            double desiredMax) {
        return (value - currentMin) * (desiredMax - desiredMin) / (currentMax - currentMin)
                + desiredMin;
    }

    /**
     * Maps a joystick input value between [-1, 1] to one where any input value between [-deadband,
     * deadband] is zero and anything outside of that range is mapped linearly from [0,1].
     *
     * @param inputValue the measured input value.
     * @param deadband the joystick's deadband.
     * @return the mapped joystick input.
     */
    public static double mapJoystickOutput(double inputValue, double deadband) {
        if (Math.abs(inputValue) <= deadband) {
            return 0;
        }
        return inputValue > 0
                ? map(inputValue, deadband, 1, 0, 1)
                : map(inputValue, -1, -deadband, -1, 0);
    }

    /**
     * Calculates setpoint of the robot's left and right side for turning in place based on the
     * desired angle and width of the robot.
     *
     * @param angleDegrees desired angle in degrees to turn the robot
     * @param robotWidth width of the robot in feet
     * @return array of the left and right setpoint in feet
     */
    public List<Double> turnInPlace(double angleDegrees, double robotWidth) {
        double angleRadians = angleDegrees * Math.PI / 180;
        return Arrays.asList(new Double[] {angleRadians * robotWidth, -angleRadians * robotWidth});
    }
}
