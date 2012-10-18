/**
 * 
 */
package rampancy.util.wave;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import rampancy.RampantRobot;
import rampancy.util.REnemyRobot;
import rampancy.util.RPoint;
import rampancy.util.RRobotState;
import rampancy.util.RUtil;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class REnemyWave extends RWave {

    public static final Color DEFAULT_COLOR = Color.red;
    
    protected REnemyRobot creator;
    protected RRobotState creatorState;
    protected RRobotState targetState;
    protected double directionAngle;
    protected int direction;
    protected long estimatedFlightTime;
    
    /**
     * @param creator
     */
    public REnemyWave(REnemyRobot creator) {
        this(creator, 
             creator.getCurrentState().location.getCopy(), 
             creator.getReference().getTime(), 
             (creator.getShotPower() > 0 ? creator.getShotPower() : 3.0));
    }
    
    /**
     * @param creator
     * @param origin
     * @param timeFired
     * @param power
     */
    public REnemyWave(REnemyRobot creator, RPoint origin, long timeFired, double power) {
        super(origin, timeFired - 1, power, DEFAULT_COLOR);
        this.creator = creator;
        this.creatorState = creator.getCurrentState().getCopy();
        this.targetState = creator.getReference().getLastUsableState();
        this.directionAngle = creator.getLastUsableBearing();
        this.direction = creator.getLastUsableSurfDirection();
        this.estimatedFlightTime = (long) (creatorState.distance / velocity);
    }
    
    /**
     * @param point
     * @return <code>true</code> if the wave has broken through the specified point
     */
    public boolean didBreak(RPoint point) {
        return (distanceTraveled > point.distance(origin) + 50);
    }
    
    /**
     * Checks if the target location at the specified time intercepts the wave
     * @param point
     * @param time
     * @return
     */
    public boolean intercepted(RPoint point, int time) {
        return (point.distance(getOrigin()) < 
                    getDistanceTraveled() + (time * getVelocity()) 
                    + getVelocity());
    }
    
    // ---------- Getters and Setters ---------- //
    
    /**
     * @return the offset angle from the target
     */
    public double computeOffsetAngle(RPoint target) {
        return (RUtil.computeAbsoluteBearing(getOrigin(), target) - getDirectionAngle());
    }
    
    /**
     * @param hitLocation
     * @return the raw guess factor
     */
    public double computeRawFactor(RPoint hitLocation) {
        double offset = computeOffsetAngle(hitLocation);
        return Utils.normalRelativeAngle(offset) / RUtil.computeMaxEscapeAngle(getVelocity()) * getDirection();
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
    public REnemyRobot getCreator() {
        return creator;
    }

    /**
     * @return the creatorState
     */
    public RRobotState getCreatorState() {
        return creatorState;
    }

    /**
     * @return the targetState
     */
    public RRobotState getTargetState() {
        return targetState;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((creatorState == null) ? 0 : creatorState.hashCode());
        result = prime * result + direction;
        long temp;
        temp = Double.doubleToLongBits(directionAngle);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result
                + (int) (estimatedFlightTime ^ (estimatedFlightTime >>> 32));
        result = prime * result
                + ((targetState == null) ? 0 : targetState.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        REnemyWave other = (REnemyWave) obj;
        if (creatorState == null) {
            if (other.creatorState != null)
                return false;
        } else if (!creatorState.equals(other.creatorState))
            return false;
        if (direction != other.direction)
            return false;
        if (Double.doubleToLongBits(directionAngle) != Double
                .doubleToLongBits(other.directionAngle))
            return false;
        if (estimatedFlightTime != other.estimatedFlightTime)
            return false;
        if (targetState == null) {
            if (other.targetState != null)
                return false;
        } else if (!targetState.equals(other.targetState))
            return false;
        return true;
    }
}
