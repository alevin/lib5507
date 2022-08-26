/**
 * 
 */
package org.team5507.lib.util;

import java.security.InvalidParameterException;

/**
 * Wrapper class for a series of methods allowing for easy unit conversions.
 * 
 * @author Finn Frankis
 * @version 7/5/18
 */
public final class Conversions {
    /**
     * The diameter of the wheel to be used.
     */
    public static double WHEEL_DIAMETER = -1;

    /**
     * The number of pigeon units in a single rotation.
     */
    public static final double PIGEON_UNITS_PER_ROTATION = 8192;

    /**
     * The number of degrees in a single rotation.
     */
    public static final double DEGREES_PER_ROTATION = 360;

    /**
     * The number of radians in a single rotation.
     */
    public static final double RADIANS_PER_ROTATION = 2 * Math.PI;

    /**
     * The number of milliseconds in a second.
     */
    public static final double MS_PER_SEC = 1000;

    /**
     * The number of microseconds in a second.
     */
    public static final double MICROSECS_PER_SEC = Math.pow(10, 6);

    /**
     * The number of nanoseconds in a second.
     */
    public static final double NANOSECS_PER_SEC = Math.pow(10, 9);

    /**
     * The number of ticks per revolution for a CTRE magnetic encoder.
     */
    public static final int DEFAULT_TICKS_PER_REV = 4096;

    /**
     * The number of inches in a foot.
     */
    public static final int INCHES_PER_FOOT = 12;

    public interface Unit {
    }

    /**
     * Represents the various possible units for an angle.
     * 
     * @author Finn Frankis
     * @version 7/5/18
     */
    public enum AngleUnit implements Unit {
        /**
         * The angle unit of radians.
         */
        RADIANS,

        /**
         * The angle unit of degrees.
         */
        DEGREES,

        /**
         * The angle unit of pigeon units (for use with CTRE's Pigeon IMU).
         */
        PIGEON_UNITS;
    }

    /**
     * Represents the various possible units for a position.
     * 
     * @author Finn Frankis
     * @version 7/5/18
     */
    public enum PositionUnit implements Unit {
        /**
         * The position unit of feet.
         */
        FEET,

        /**
         * The position unit of encoder ticks.
         */
        ENCODER_UNITS;
    }

    /**
     * Represents the various possible units for a speed.
     * 
     * @author Finn Frankis
     * @version 7/5/18
     */
    public enum SpeedUnit implements Unit {
        /**
         * The speed unit of feet per second.
         */
        FEET_PER_SECOND,

        /**
         * The speed unit of encoder ticks per 100 ms.
         */
        ENCODER_UNITS;
    }

    /**
     * Represents the various possible units for a time.
     * 
     * @author Finn Frankis
     * @version 7/5/18
     */
    public enum TimeUnit implements Unit {
        /**
         * The time unit of seconds.
         */
        SECONDS,

        /**
         * The time unit of milliseconds.
         */
        MILLISECONDS,

        /**
         * The time unit of microseconds.
         */
        MICROSECONDS,

        /**
         * The time unit of nanoseconds.
         */
        NANOSECONDS;
    }

    /**
     * Sets the wheel diameter for use in position and velocity computations.
     * 
     * @param newDiameter the diameter with which the current one will be replaced
     */
    public static void setWheelDiameter(double newDiameter) {
        WHEEL_DIAMETER = newDiameter;
    }

    /**
     * Converts a value of one unit to a value of another unit.
     * 
     * @param startUnit   the unit of the passed-in value (either AngleUnit,
     *                    SpeedUnit, PositionUnit, TimeUnit).
     * @param startValue  the value to convert.
     * @param desiredUnit the desired unit of the passed-in value.
     * 
     * @precondition startUnit and desiredUnit both measure the same quantity.
     * @return the converted value.
     */
    public static double convert(Unit startUnit, double startValue, Unit desiredUnit) {
        if (startUnit instanceof AngleUnit && desiredUnit instanceof AngleUnit) {
            return convertAngle((AngleUnit) startUnit, startValue, (AngleUnit) desiredUnit);
        } else if (startUnit instanceof SpeedUnit && desiredUnit instanceof SpeedUnit) {
            return convertSpeed((SpeedUnit) startUnit, startValue, (SpeedUnit) desiredUnit);
        } else if (startUnit instanceof PositionUnit && desiredUnit instanceof PositionUnit) {
            return convertPosition((PositionUnit) startUnit, startValue, (PositionUnit) desiredUnit);
        } else if (startUnit instanceof TimeUnit && desiredUnit instanceof TimeUnit) {
            return convertTime((TimeUnit) startUnit, startValue, (TimeUnit) desiredUnit);
        }

        throw new InvalidParameterException("Unit classes are non-equivalent");
    }

    /**
     * Converts an angle in one unit to another.
     * 
     * @param startUnit   the unit of the given value
     * @param startValue  the value to be converted
     * @param desiredUnit the unit desired for the conversion
     * @return the converted value
     */
    public static double convertAngle(AngleUnit startUnit, double startValue, AngleUnit desiredUnit) {
        if (desiredUnit == AngleUnit.RADIANS) {
            if (startUnit == AngleUnit.RADIANS)
                return startValue;
            else if (startUnit == AngleUnit.DEGREES)
                return startValue * RADIANS_PER_ROTATION / DEGREES_PER_ROTATION;
            else
                return startValue * RADIANS_PER_ROTATION / PIGEON_UNITS_PER_ROTATION;
        } else if (desiredUnit == AngleUnit.DEGREES) {
            if (startUnit == AngleUnit.DEGREES)
                return startValue;
            else if (startUnit == AngleUnit.RADIANS)
                return startValue * DEGREES_PER_ROTATION / RADIANS_PER_ROTATION;
            else
                return startValue * DEGREES_PER_ROTATION / PIGEON_UNITS_PER_ROTATION;
        } else {
            if (startUnit == AngleUnit.PIGEON_UNITS)
                return startValue;
            else if (startUnit == AngleUnit.DEGREES)
                return startValue * PIGEON_UNITS_PER_ROTATION / DEGREES_PER_ROTATION;
            else
                return startValue * PIGEON_UNITS_PER_ROTATION / RADIANS_PER_ROTATION;
        }
    }

    public static double convertPosition(PositionUnit startUnit, double startValue, PositionUnit desiredUnit) {
        return convertPosition(startUnit, startValue, desiredUnit, WHEEL_DIAMETER, DEFAULT_TICKS_PER_REV);
    }

    /**
     * Converts a position in one unit to another.
     * 
     * @param startUnit   the unit of the given value
     * @param startValue  the value to be converted
     * @param desiredUnit the unit desired for the conversion
     * @return the converted value
     */
    public static double convertPosition(PositionUnit startUnit, double startValue, PositionUnit desiredUnit,
            double wheelDiameter, int ticksPerRev) {
        if (wheelDiameter != -1) {
            if (desiredUnit == PositionUnit.FEET) {
                if (startUnit == PositionUnit.FEET)
                    return startValue;
                else
                    return startValue / ticksPerRev // convert to revolutions
                            * (wheelDiameter * Math.PI) // convert to inches
                            / INCHES_PER_FOOT; // convert to feet
            } else {
                if (startUnit == PositionUnit.ENCODER_UNITS)
                    return startValue;
                else
                    return startValue * INCHES_PER_FOOT // convert to inches
                            / (wheelDiameter * Math.PI) // convert to revolutions
                            * ticksPerRev; // convert to ticks
            }
        }
        try {
            throw new InterruptedException("You must specify a valid wheel diameter in setWheelDiameter()");
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        }

    }

    public static double convertSpeed(SpeedUnit startUnit, double startValue, SpeedUnit desiredUnit) {
        return convertSpeed(startUnit, startValue, desiredUnit, WHEEL_DIAMETER, DEFAULT_TICKS_PER_REV);
    }

    /**
     * Converts a speed in one unit to another.
     * 
     * @param startUnit   the unit of the given value
     * @param startValue  the value to be converted
     * @param desiredUnit the unit desired for the conversion
     * @return the converted value
     */
    public static double convertSpeed(SpeedUnit startUnit, double startValue, SpeedUnit desiredUnit,
            double wheelDiameter, int ticksPerRev) {
        if (wheelDiameter >= 0) {
            if (desiredUnit == SpeedUnit.FEET_PER_SECOND) {
                if (startUnit == SpeedUnit.FEET_PER_SECOND)
                    return startValue;
                else
                    return startValue * 10.0 // convert to ticks per second
                            / ticksPerRev // convert to revolutions per second
                            * (wheelDiameter * Math.PI) // convert to inches per second
                            / INCHES_PER_FOOT; // convert to feet per second
            } else {
                if (startUnit == SpeedUnit.ENCODER_UNITS)
                    return startValue;
                else
                    return startValue / 10.0 // convert to feet per 100 ms
                            * INCHES_PER_FOOT // convert to inches per 100 ms
                            / (wheelDiameter * Math.PI) // convert to revolutions per 100ms
                            * ticksPerRev; // convert to ticks per 100ms
            }
        }
        try {
            throw new InterruptedException("You must specify a valid wheel diameter in setWheelDiameter()");
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Converts a time in one unit to another.
     * 
     * @param startUnit   the unit of the given value
     * @param startValue  the value to be converted
     * @param desiredUnit the unit desired for the conversion
     * @return the converted value
     */
    public static double convertTime(TimeUnit startUnit, double startValue, TimeUnit desiredUnit) {
        if (desiredUnit == TimeUnit.SECONDS) {
            if (startUnit == TimeUnit.SECONDS)
                return startValue;
            else if (startUnit == TimeUnit.MILLISECONDS)
                return startValue / Conversions.MS_PER_SEC;
            else if (startUnit == TimeUnit.MICROSECONDS)
                return startValue / Conversions.MICROSECS_PER_SEC;
            else
                return startValue / Conversions.NANOSECS_PER_SEC;
        } else if (desiredUnit == TimeUnit.MILLISECONDS) {
            if (startUnit == TimeUnit.MILLISECONDS)
                return startValue;
            else if (startUnit == TimeUnit.SECONDS)
                return startValue * Conversions.MS_PER_SEC;
            else if (startUnit == TimeUnit.MICROSECONDS)
                return startValue / Conversions.MICROSECS_PER_SEC * Conversions.MS_PER_SEC;
            else
                return startValue / Conversions.NANOSECS_PER_SEC * Conversions.MS_PER_SEC;
        } else if (desiredUnit == TimeUnit.MICROSECONDS) {
            if (startUnit == TimeUnit.MICROSECONDS)
                return startValue;
            else if (startUnit == TimeUnit.SECONDS)
                return startValue * Conversions.MICROSECS_PER_SEC;
            else if (startUnit == TimeUnit.MILLISECONDS)
                return startValue / Conversions.MS_PER_SEC * Conversions.MICROSECS_PER_SEC;
            else
                return startValue / Conversions.NANOSECS_PER_SEC * Conversions.MICROSECS_PER_SEC;
        } else // nanoseconds
        {
            if (startUnit == TimeUnit.NANOSECONDS)
                return startValue;
            else if (startUnit == TimeUnit.SECONDS)
                return startValue * Conversions.NANOSECS_PER_SEC;
            else if (startUnit == TimeUnit.MILLISECONDS)
                return startValue / Conversions.MS_PER_SEC * Conversions.NANOSECS_PER_SEC;
            else
                return startValue / Conversions.MICROSECS_PER_SEC * Conversions.NANOSECS_PER_SEC;
        }
    }
}
