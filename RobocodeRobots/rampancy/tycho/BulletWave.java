package rampancy.tycho;

import java.awt.geom.Point2D;
import robocode.*;
import robocode.util.*;

public class BulletWave extends Wave {

    private double[] returnSegment;
    private int[] readings;
    private int readingsIndex;
    
    public BulletWave(Point2D.Double origin, long lifetime, double bulletPower, double angle, int direction, double[] returnSegment, int[] readings, int readingsIndex) {
        super(origin, lifetime + 1, bulletPower, angle, direction);
        this.returnSegment = returnSegment;
        this.readings = readings;
        this.readingsIndex = readingsIndex;
    }
    
    public boolean checkHit(Point2D.Double enemyLocation, long currentTime) {
        distanceTraveled = (currentTime - lifetime) * velocity;
        if (origin.distance(enemyLocation) <= distanceTraveled) {
            double desiredDirection = Math.atan2(enemyLocation.x - origin.x, enemyLocation.y - origin.y);
            double angleOffset = Utils.normalRelativeAngle(desiredDirection - angle);
            
            double guessFactor = Math.max(-1, Math.min(1.0, angleOffset / Helper.maxEscapeAngle(velocity))) * (double) direction;
            int index = computeIndex(guessFactor, direction);
            returnSegment[index] = Helper.rollingAvg(returnSegment[index], 1.0, Math.min(readings[readingsIndex]++, Helper.STANDARD_ROLL_DEPTH), Helper.STANDARD_WEIGHTING);
            for(int i = 0; i < returnSegment.length; i++)
                if(i != index)
                    returnSegment[i] = Helper.rollingAvg(returnSegment[i], 1.0 / (Math.pow(index - i, 2) + 1), Math.min(readings[readingsIndex], Helper.STANDARD_ROLL_DEPTH), Helper.STANDARD_WEIGHTING);
            return true;
        }
        return false;
    }
    
    public int computeIndex(double guessFactor, int direction) {
        return (int) Math.round((returnSegment.length - 1) /2 * (guessFactor + 1));
    }

    
    
}
