/**
 * Util.java
 */
package rampancy_old.util;

import java.awt.geom.Point2D;

import rampancy_old.util.kdTree.KDDistanceFunction;
import robocode.AdvancedRobot;
import robocode.util.Utils;

import java.util.*;

/**
 * This class contains utility methods used by Durandal
 * @author Matthew Chun-Lum
 *
 */
public abstract class Util {
    
    /**
     * Credit: Voidious
     * Handles movement in an efficient manner.
     * @param robot
     * @param goAngle
     * @param dist
     */
    public static void setBackAsFront(AdvancedRobot robot, double goAngle, double dist) {
        double angle = Utils.normalRelativeAngle(goAngle - robot.getHeadingRadians());
        if (Math.abs(angle) > (Math.PI/2)) {
            if (angle < 0) {
                robot.setTurnRightRadians(Math.PI + angle);
            } else {
                robot.setTurnLeftRadians(Math.PI - angle);
            }
            robot.setBack(dist);
        } else {
            if (angle < 0) {
                robot.setTurnLeftRadians(-1*angle);
           } else {
               robot.setTurnRightRadians(angle);
           }
            robot.setAhead(dist);
        }
    }
    
    public static double computeDensity(List<Double> distances, double h) {
        double factor = 1.0 / (distances.size() * h);
        double k_factor = 1.0 / Math.sqrt(Math.PI * 2);
        
        double sigma = 0;
        for(int i = 0; i < distances.size(); i++)
            sigma += Math.exp(-0.5 * Math.pow(distances.get(i) / h, 2));
        
        return sigma * factor * k_factor;
    }
    
    /**
     * Rounds the specified value to the passed float precision
     * @param value
     * @param precision
     * @return the rounded value
     */
    public static double roundToPrecision(double value, int precision) {
        int temp = (int) Math.round((value * Math.max(1, Math.pow(10, precision))));
        return ((double) temp) / Math.pow(10, precision);
    }
    
    /**
     * Scales a given value to fall within the given min and max values
     * @param min
     * @param max
     * @param minExpected
     * @param maxExpected
     * @param value
     * @return
     */
    public static double scaleToRange(double min, double max, double minExpected, double maxExpected, double value) {
        return scaleToRange(min, max, minExpected, maxExpected, value, 0);
    }
    
    /**
     * Scales a given value to fall within the given min and max values
     * @param min
     * @param max
     * @param minExpected
     * @param maxExpected
     * @param value
     * @param offset
     * @return
     */
    public static double scaleToRange(double min, double max, double minExpected, double maxExpected, double value, double offset) {
        double scaleRange = max - min;
        double valueRange = maxExpected - minExpected;
        double percentDiff = scaleRange / valueRange;
        return Util.limit(min, (value + offset) * percentDiff, max);
    }
    
    /**
     * Determines if the passed value is within the range
     * @param min
     * @param max
     * @param value
     * @return
     */
    public static boolean inRange(double min, double max, double value) {
        return (value >= min && value <= max);
    }
    
    /**
     * Computes the rolling average
     * @param value
     * @param newEntry
     * @param n
     * @param weighting
     * @return
     */
    public static double rollingAvg(double value, double newEntry, double n, double weighting ) {
        return (value * n + newEntry * weighting)/(n + weighting);
    } 
    
    /**
     * @param values
     * @return the lowest value from the list of values
     */
    public static double lowest(double[] values) {
        double lowest = values[0];
        for(int i = 1; i < values.length; i++)
            if(lowest > values[i])
                lowest = values[i];
        return lowest;
    }
    
    /**
     * Limits the passed value to the range between the passed max
     * and min values
     * @param min
     * @param value
     * @param max
     * @return the limited value
     */
    public static double limit(double min, double value, double max) {
        return Math.max(min, Math.min(value, max));
    }
    
    /**
     * Computes the absolute bearing from the source to the target
     * @param source
     * @param target
     * @return the absolute bearing in radians
     */
    public static double computeAbsoluteBearing(Point2D.Double source, Point2D.Double target) {
        return Math.atan2(target.x - source.x, target.y - source.y);
    }
    
    /**
     * Determines the velocity of a bullet given its power
     * @param bulletPower
     * @return the velocity of the bullet
     */
    public static double computeBulletVelocity(double bulletPower) {
        return (20.0 - (3.0 * bulletPower));
    }
    
    /**
     * Determines the max escape angle given velocity
     * @param velocity
     * @return the max escape angle
     */
    public static double computeMaxEscapeAngle(double velocity) {
        return Math.asin(8.0 / velocity);
    }
    
    /**
     * Determines if the enemy is advancing (mainly to counter ram bots)
     * @param absoluteBearing
     * @param enemyHeading
     * @param deltaHeading
     * @param velocity
     * @return {@code true} if the enemy is advancing
     */
    public static boolean isAdvancing(double absoluteBearing, double enemyHeading, double deltaHeading, double velocity) {
        if(inRange(-Constants.RAM_TOLERANCE, Constants.RAM_TOLERANCE, Math.abs(enemyHeading - absoluteBearing) - Math.PI)) {
            return (Math.abs(deltaHeading) < Constants.DELTA_TOLERANCE && velocity > 1);
        }
        return false;
    }
    
    /**
     * Returns 1 or -1 given the passed value
     * @param d
     * @return
     */
    public static int nonZeroSign(double d) {
        if (d < 0) { return -1; }
        return 1;
    }
    
    /**
     * Projects a point location given distance and angle
     */
    public static Point2D.Double project(Point2D.Double source, double angle, double length) {
        double x = source.x + Math.sin(angle) * length;
        double y = source.y + Math.cos(angle) * length;
        return new Point2D.Double(x, y);
    }
}
