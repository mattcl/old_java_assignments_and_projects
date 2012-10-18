package marathon.tycho;

import java.awt.geom.*;
import java.awt.geom.Point2D.Double;

import robocode.util.Utils;
import robocode.*;

public class Helper {
 
    public static final double MAX_RADAR_TRACKING_AMOUNT = Math.PI / 3;
    public static final double STARTING_ENERGY = 100.0;
    public static final int BINS = 47;
    public static final int GUESS_FACTORS = 31;
    public static final int DISTANCE = 21;
    public static final int VELOCITIES = 9;
    public static final int MOVE_TIMES = 1;
    public static final int BEARINGS = 16;
    public static final int HEADINGS = 16;
    public static final double STANDARD_WEIGHTING = 1;
    public static final double STANDARD_ROLL_DEPTH = 200;
    public static Rectangle2D.Double worldApproximation = new java.awt.geom.Rectangle2D.Double(18, 18, 764, 564);
    public static double WALL_STICK = 160;

    
    /**
     * Given the bullet power compute the velocity
     */
    public static double bulletVelocity(double bulletPower) {
        return (20.0 - (3.0 * bulletPower));
    }
    
    /**
     * Projects the robots location given distance and bearing
     */
    public static Point2D.Double project(Point2D.Double source, double angle, double length) {
        double x = source.x + Math.sin(angle) * length;
        double y = source.y + Math.cos(angle) * length;
        return new Point2D.Double(x, y);
    }
    
    /**
     * Computes the angle to target from source
     */
    public static double absoluteBearing(Point2D.Double source, Point2D.Double target) {
        return Math.atan2(target.x - source.x, target.y - source.y);
    }
    
    /**
     * Computes the maximum angle to escape
     */
    public static double maxEscapeAngle(double velocity) {
        return Math.asin(8.0/velocity);
    }

    /**
     * Ensures that the robot does not make contact with the wall
     */
    public static double wallSmoothing(Point2D.Double location, double angle, int orientation) {
        while (!worldApproximation.contains(project(location, angle, WALL_STICK))) {
            angle += orientation * 0.05;
        }
        return angle;
    }
    
    /**
     * returns the factor index for the statistics array
     */
    public static int getFactorIndex(Wave wave, Point2D.Double target) {
        double offsetAngle = (absoluteBearing(wave.getOrigin(), target) - wave.getAngle());
        double factor = Utils.normalRelativeAngle(offsetAngle) / maxEscapeAngle(wave.getVelocity()) * wave.getDirection();
        return computeBin(factor);
    }

    /**
     * Computes the proper guess factor for the statistics tracking array
     */
    public static int computeBin(double factor) {
        double value = (factor * ((BINS - 1) / 2)) + ((BINS - 1) / 2);
        return (int) limit(0, value, BINS -1);
    }
    
    /**
     * Computes a value within min and max
     */
    public static double limit(double min, double val, double max) {
        return Math.max(min, Math.min(val, max));
    }
    
    public static void setBackAsFront(AdvancedRobot robot, double goAngle) {
        double angle = Utils.normalRelativeAngle(goAngle - robot.getHeadingRadians());
        if (Math.abs(angle) > (Math.PI/2)) {
            if (angle < 0) {
                robot.setTurnRightRadians(Math.PI + angle);
            } else {
                robot.setTurnLeftRadians(Math.PI - angle);
            }
            robot.setBack(100);
        } else {
            if (angle < 0) {
                robot.setTurnLeftRadians(-1*angle);
           } else {
                robot.setTurnRightRadians(angle);
           }
            robot.setAhead(100);
        }
    }
    
    public static int nonZeroSign(double d) {
        if (d < 0) { return -1; }
        return 1;
    }
    
    /**
     * Computes the rolling average for our statistics array
     */
    public static double rollingAvg(double value, double newEntry, double n, double weighting ) {
        return (value * n + newEntry * weighting)/(n + weighting);
    } 
    
    /*
     * Code that follows is for determining the proper segment for the targeting
     * array
     */
    public static int getDistanceSegment(double distance) {
        return (int)(distance / 100);
    }
    
    public static int getBearingSegment(double bearing) {
        int index = (int) (bearing / (2 * Math.PI) * (BEARINGS));
        return Math.max(0, Math.min(BEARINGS - 1, index));
    }
    
    public static int getTimeSegment(long time) {
        int index = (int) Math.min(time / 10.0, MOVE_TIMES - 1);
        return index;
    }
    
    public static int getHeadingSegment(double heading) {
        return (int) (heading / (2 * Math.PI) * (HEADINGS - 1));
    }
    
    public static int getVelocitySegment(double velocity) {
        return (int) Math.max(0, velocity - 1);
    }

}
