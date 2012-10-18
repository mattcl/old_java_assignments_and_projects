/**
 * EnemyWave.java
 */
package rampancy_old.util;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import rampancy_old.RampantRobot;

/**
 * @author Matthew Chun-Lum
 *
 */
public class EnemyWave extends Wave {
    public static final Color DEFAULT_COLOR = Color.red;
    public static final int NO_GUESS_FACTOR_ANTICIPATED = -10;
    
    private EnemyRobot creator;
    public RampantRobotState targetStateAtFireTime;
    private double targetDeltaHeading;
    private double directionAngle;
    private long estimatedFlightTime;
    private int direction;
    public int anticipatedGuessFactor;
    
    /**
     * @param origin the origin of the wave
     * @param timeFired the time time bullet was fired
     * @param power the power the bullet was fired with
     */
    public EnemyWave(EnemyRobot creator, Point2D.Double origin, RampantRobot reference, long timeFired, double power) {
        super(origin, timeFired, power, DEFAULT_COLOR);
        this.creator = creator;
        targetStateAtFireTime = reference.getMovementManager().getLastUsableState();
        directionAngle = creator.getLastUsableBearing();
        direction = creator.getLastUsableSurfDirection();
        estimatedFlightTime = (long) (creator.getDistance() / velocity);
        anticipatedGuessFactor = NO_GUESS_FACTOR_ANTICIPATED;
    }

    /**
     * Checks if the wave has "broken" on the target
     * @param target the location of the target
     * @return <code>true</code> if the wave has broken on the target
     */
    public boolean didBreak(Point2D.Double target) {
        return (distanceTraveled > target.distance(origin) + 50);
    }
    
    /**
     * Checks if the target location at the specified time intercepts the wave
     * @param target
     * @param counter
     * @return
     */
    public boolean intercepted(Point2D.Double target, int time) {
        if (target.distance(getOrigin()) <
                getDistanceTraveled() + (time * getVelocity())
                + getVelocity()) {
            return true;
        }
        return false;
    }
    
    // ---------- Getters and Setters ---------- //
    
    /**
     * @return the targetDeltaHeading
     */
    public double getTargetDeltaHeading() {
        return targetDeltaHeading;
    }
    
    /**
     * @return the offset angle from the target
     */
    public double computeOffsetAngle(Point2D.Double target) {
        return (Util.computeAbsoluteBearing(getOrigin(), target) - getDirectionAngle());
    }
    
    /**
     * @return the directionAngle for this wave
     */
    public double getDirectionAngle() {
        return directionAngle;
    }
    
    /**
     * @return the direction for this wave
     */
    public int getDirection() {
        return direction;
    }
    
    /**
     * @return the estimated flight time
     */
    public double getEstimatedFlightTime() {
        return estimatedFlightTime;
    }
    
    /**
     * @return a reference to the enemy robot that created this wave
     */
    public EnemyRobot getCreator() {
        return creator;
    }
}
