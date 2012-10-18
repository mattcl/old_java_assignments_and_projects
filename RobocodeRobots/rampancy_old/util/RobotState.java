/**
 * RobotState.java
 */
package rampancy_old.util;

import java.awt.geom.Point2D;

/**
 * @author Matthew Chun-Lum
 *
 */
public abstract class RobotState {
    public Point2D.Double location;
    public double absoluteBearing;
    public double heading;
    public double lastHeading;
    public double distance;
    public double lastDistance;
    public double velocity;
    public double lastVelocity;
    
    /**
     * Constructor
     * @param location
     * @param heading
     * @param lastHeading
     * @param distance
     * @param lastDistance
     * @param velocity
     * @param lastVelocity
     */
    public RobotState(Point2D.Double location, double absoluteBearing,
            double heading, double lastHeading, double distance, double lastDistance, double velocity,
            double lastVelocity) {
        this.location = (Point2D.Double) location.clone();
        this.absoluteBearing = absoluteBearing;
        this.heading = heading;
        this.lastHeading = lastHeading;
        this.distance = distance;
        this.lastDistance = lastDistance;
        this.velocity = velocity;
        this.lastVelocity = lastVelocity;
    }
}
