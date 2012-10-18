/**
 * Util.java
 */
package epgy.util;

import java.awt.Graphics2D;
import robocode.AdvancedRobot;
import robocode.util.Utils;

/**
 * This class contains utility methods
 * @author Matthew Chun-Lum
 *
 */
public abstract class EPGYUtil {
    
    public static final double WALL_STICK = 150;
    
    /**
     * Will return a random number greater than or equal to 0 and less than 1.0
     * @return a random number greater than or equal to 0 and less than 1.0
     */
    public static double random() {
        return Math.random();
    }
    
    /**
     * Will return an integer greater than or equal to 0 and less than {@code max}
     * @param max
     * @return a integer greater than or equal to 0 and less than {@code max}
     */
    public static int random(int max) {
        return (int) Math.round(Math.random() * max);
    }
    
    /**
     * Computes the absolute bearing from the source to the target
     * @param source
     * @param target
     * @return the absolute bearing in radians
     */
    public static double computeAbsoluteBearing(EPGYPoint source, EPGYPoint target) {
        return Utils.normalAbsoluteAngle(Math.atan2(target.x - source.x, target.y - source.y));
    }
    
    /**
     * Determines the power of a bullet given its velocity
     * @param velocity
     * @return the power of the bullet
     */
    public static double computeBulletPower(double velocity) {
        return (20.0 - velocity) / 3.0;
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
     * Draws an oval using the passed point and radius
     * @param point
     * @param radius
     * @param g the graphics object reference
     */
    public static void drawOval(EPGYPoint point, int radius, Graphics2D g) {
        g.drawOval((int) point.x - radius, (int) point.y - radius, radius * 2, radius * 2);
    }
    
    /**
     * Fills an oval using the passed point and radius
     * @param point
     * @param radius
     * @param g the graphics object reference
     */
    public static void fillOval(EPGYPoint point, int radius, Graphics2D g) {
        g.fillOval((int) point.x - radius, (int) point.y - radius, radius * 2, radius * 2);
    }
    
    /**
     * Returns the index of the largest element in {@code arr}
     * @param arr
     * @return the index of the largest element in {@code arr}
     */
    public static int indexOfLargest(double[] arr) {
        double largest = 0;
        int largestIndex = (arr.length - 1) /2;
        
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] > largest) {
                largest = arr[i];
                largestIndex = i;
            }
        }
        return largestIndex;
    }
    
    /**
     * Returns the index of the smallest element in {@code arr}
     * @param arr
     * @return the index of the smallest element in {@code arr}
     */
    public static int indexOfSmallest(double[] arr) {
        double lowest = 50000;
        int lowestIndex = -1;
        
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] < lowest) {
                lowest = arr[i];
                lowestIndex = i;
            }
        }
        return lowestIndex;
    }
    
    /**
     * Determines if the passed value is within the range
     * @param min
     * @param max
     * @param value
     * @return {@code true} if {@code value} is between {@code min} and {@code max}
     */
    public static boolean inRange(double min, double max, double value) {
        return (value >= min && value <= max);
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
     * Returns the lowest value from a list of values
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
     * Returns {@code 1} if {@code d} is positive, {@code 0} if
     * {@code d} is zero, and {@code -1} if {@code d} is negative
     * @param d
     * @return {@code 1} if {@code d} is positive, {@code 0} if
     * {@code d} is zero, and {@code -1} if {@code d} is negative
     */
    public static int sign(double d) {
        return (d < 0 ? -1 : d > 0 ? 1 : 0);
    }
    
    /**
     * Returns {@code 1} if {@code d} is positive or zero and {@code -1} if {@code d} is negative
     * @param d
     * @return {@code 1} if {@code d} is positive or zero and {@code -1} if {@code d} is negative
     */
    public static int nonZeroSign(double d) {
        if (d < 0) { return -1; }
        return 1;
    }
    
    /**
     * Computes the point produced from the projection of {@code source}
     * using the specified {@code angle} and {@code distance}
     * @param source the source point
     * @param angle the angle, in radians, to project by (referenced where 0 is north)
     * @param distance the distance of the projection
     * @return the projected point
     */
    public static EPGYPoint project(EPGYPoint source, double angle, double distance) {
        double x = source.x + Math.sin(angle) * distance;
        double y = source.y + Math.cos(angle) * distance;
        return new EPGYPoint(x, y);
    }
    
    /**
     * Computes a rolling rolling average
     * @param value
     * @param newEntry
     * @param n
     * @param weighting
     * @return the averaged value
     */
    public static double rollingAvg(double value, double newEntry, double n, double weighting ) {
        return (value * n + newEntry * weighting)/(n + weighting);
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
     * Scales a given value to fall within the given min and max values.
     * @param min
     * @param max
     * @param minExpected
     * @param maxExpected
     * @param value
     * @return the scaled value
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
     * @return the scaled value
     */
    public static double scaleToRange(double min, double max, double minExpected, double maxExpected, double value, double offset) {
        double scaleRange = max - min;
        double valueRange = maxExpected - minExpected;
        double percentDiff = scaleRange / valueRange;
        return EPGYUtil.limit(min, (value + offset) * percentDiff, max);
    }
    
    /**
     * Given an AdvancedRobot (EPGYBot is an extension of AdvancedRobot),
     * a desired angle to turn to, and a distance to move, this method
     * determines the quickest way to achieve the desired movement. This
     * ensures that the turn to the desired angle will be the shortest
     * (clockwise or counter-clockwise), and the move over the desired
     * distance will be either forwards or backwards.
     * Credit: Voidious
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
    
    
    /**
     * Simple wrapper for returning the square of a passed value
     * @param value
     * @return the square of the passed value
     */
    public static double square(double value) {
        return Math.pow(value, 2);
    }
    
    /**
     * Computes the sum of two vectors
     * @param arr1
     * @param arr2
     * @return the sum of the two vectors
     */
    public static double[] sum(double[] arr1, double[] arr2) {
        double[] sum = new double[arr1.length];
        if(arr1.length != arr2.length) {
            System.err.print("Sum error: arrays not the same length!");
            return null;
        }
        
        for(int i = 0; i < arr1.length; i++)
            sum[i] = arr1[i] + arr2[1];
        return sum;
    }

    /**
     * Computes the percent difference between {@code v1} and {@code v2}
     * @param v1
     * @param v2
     * @return the percent difference between {@code v1} and {@code v2}
     */
    public static double percentDifference(double v1, double v2) {
        double sum = (v1 + v2) / 2;
        if(sum == 0)
            return 1;
        
        return Math.abs((v1 - v2) / sum);
    }
    
    /**
     * Determines if {@code point} lies on the enemy robot
     * @param point
     * @param enemy
     * @return {@code true} if {@code point} is on the enemy
     */
    public static boolean pointOnRobot(EPGYPoint point, EPGYEnemyRobot enemy) {
        EPGYPoint enemyLocation = enemy.getCurrentState().location;
        int radius = EPGYEnemyRobot.BOT_RADIUS;
        return (point.x >= enemyLocation.x - radius && point.x <= enemyLocation.x + radius) && (point.y >= enemyLocation.y - radius && point.y <= enemyLocation.y + radius);
    }
}
