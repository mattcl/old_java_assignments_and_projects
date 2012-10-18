/**
 * wave.java
 */
package rampancy_old.util;

import java.awt.geom.Point2D;
import java.awt.*;

/**
 * This is the abstract wave class. EnemyWave and BulletWave subclass off of this class
 * @author Matthew Chun-Lum
 *
 */
public abstract class Wave {
    protected Point2D.Double origin;
    protected long timeFired;
    protected double power;
    protected double velocity;
    protected double distanceTraveled;
    protected boolean isVirtual;
    
    // for drawing
    protected Color color;
    
    /**
     * @param origin the origin of the wave
     * @param timeFired the time time bullet was fired
     * @param power the power the bullet was fired with
     */
    public Wave(Point2D.Double origin, long timeFired, double power, Color color) {
        this.origin = origin;
        this.timeFired = timeFired - 1;
        this.power = power;
        this.velocity = Util.computeBulletVelocity(power);
        this.distanceTraveled = velocity;
        this.color = color;
        isVirtual = false;
    }
    
    /**
     * @return {@code true} the the wave is virtual
     */
    public boolean isVirtual() {
        return isVirtual;
    }
    
    /**
     * Sets whether the wave is real or virtual
     * @param val
     */
    public void setVirtual(boolean val) {
        isVirtual = val;
    }
    
    /**
     * Updates the wave
     * @param time
     */
    public void update(long time) {
        distanceTraveled = (time - timeFired) * velocity;
    }
    
    /**
     * Computes the distance the edge of the wave is from a given point
     * @param target
     * @return the distance to the target
     */
    public double distanceFrom(Point2D.Double target) {
        return target.distance(origin) - distanceTraveled;
    }
    
    /**
     * @param target the target location
     * @return the time to impact on the target
     */
    public long timeToImpact(Point2D.Double target) {
        return timeToImpact(target, 0);
    }
    
    /**
     * @param target
     * @param timeOffset
     * @return the time to impact on the target given the time offset
     */
    public long timeToImpact(Point2D.Double target, long timeOffset) {
        double remainingDistance = distanceFrom(target) - getDistanceTraveled() - getVelocity() * timeOffset;
        return (long) (remainingDistance / velocity);
    }
    
    /**
     * Draws the current wave on screen
     * @param g
     */
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.drawOval((int) (origin.x - distanceTraveled), 
                    (int) (origin.y - distanceTraveled), 
                    (int) (2 * distanceTraveled), 
                    (int) (2 * distanceTraveled));
    }
    
    // --------- Getters and Setters --------- //
    
    /**
     * Gets the power of a given wave
     * @return the power of the wave
     */
    public double getPower() {
        return power;
    }
    
    /**
     * @return the velocity of the wave
     */
    public double getVelocity() {
        return velocity;
    }
    
    /**
     * Gets the distance traveled for the given wave
     * @return the distance traveled
     */
    public double getDistanceTraveled() {
        return distanceTraveled;
    }
    
    /**
     * @return the origin of the wave
     */
    public Point2D.Double getOrigin() {
        return origin;
    }
    
    /**
     * @return the String representation of this Wave
     */
    public String toString() {
        return "Wave: Origin: " + origin.toString();
    }
}
