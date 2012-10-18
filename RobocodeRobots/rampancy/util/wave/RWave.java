/**
 * RWave.java
 */
package rampancy.util.wave;

import java.awt.*;

import rampancy.util.RDrawableObject;
import rampancy.util.RPoint;
import rampancy.util.RUtil;

/**
 * This class represents a wave
 * @author Matthew Chun-Lum
 *
 */
abstract public class RWave {
    

    protected RPoint origin;
    protected Color color;
    protected long timeFired;
    protected double power;
    protected double velocity;
    protected double distanceTraveled;
    protected boolean isVirtual;
    protected RDrawableObject drawableObject;
    
    /**
     * Constructor
     * @param origin
     * @param timeFired
     * @param power
     * @param color
     */
    public RWave(RPoint origin, long timeFired, double power, Color color) {
        this(origin, timeFired, power, color, false);
    }
    
    public RWave(RPoint origin, long timeFired, double power, Color color, boolean virtual) {
        this(origin, timeFired, power, color, virtual, null);
    }
    
    /**
     * Constructor
     * @param origin
     * @param timeFired
     * @param power
     * @param color
     * @param virtual
     */
    public RWave(RPoint origin, long timeFired, double power, Color color, boolean virtual, RDrawableObject drawableObject) {
        this.origin = origin.getCopy();
        this.timeFired = timeFired;
        this.power = power;
        this.velocity = RUtil.computeBulletVelocity(power);
        this.distanceTraveled = velocity;
        this.color = color;
        this.isVirtual = virtual;
        this.drawableObject = drawableObject;
    }

    /**
     * Updates the distance traveled of the wave
     * @param time
     */
    public void update(long time) {
        distanceTraveled = (time - timeFired) * getVelocity();
    }
    
    /**
     * @param point
     * @return the distance of the wave edge from the specified point
     */
    public double distanceFrom(RPoint point) {
        return point.distance(origin) - distanceTraveled;
    }
    
    public double distanceFrom(RPoint point, long timeOffset) {
        return point.distance(origin) - getDistanceTraveled() + getVelocity() * timeOffset;
    }
    
    /**
     * @param point the target location
     * @return the time to impact on the target
     */
    public long timeToImpact(RPoint point) {
        return timeToImpact(point, 0);
    }
    
    /**
     * @param point the target location
     * @param timeOffset number of turns in the future (or negative for past) to simulate from
     * @return the time to impact on the target
     */
    public long timeToImpact(RPoint point, long timeOffset) {
        double remainingDistance = distanceFrom(point) - getDistanceTraveled();
        return (long) (remainingDistance / getVelocity());
    }
    
    /**
     * Draws the current wave on screen
     * @param g
     */
    public void draw(Graphics2D g) {
        Color lastColor = g.getColor();
        g.setColor(color);
        g.drawOval((int) (origin.x - distanceTraveled),
                   (int) (origin.y - distanceTraveled),
                   (int) (2 * distanceTraveled),
                   (int) (2 * distanceTraveled));
        if(drawableObject != null)
            drawableObject.draw(g);
        g.setColor(lastColor);
        
    }
    
    // -------- Getters and Setters ---------- //
    
    /**
     * @return the string representation of this RWave
     */
    public String toString() {
        return "RWave: Origin: " + origin.toString() + " velocity: " + getVelocity();
    }
    
    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the isVirtual
     */
    public boolean isVirtual() {
        return isVirtual;
    }

    /**
     * @param isVirtual the isVirtual to set
     */
    public void setVirtual(boolean isVirtual) {
        this.isVirtual = isVirtual;
    }

    /**
     * @return the origin
     */
    public RPoint getOrigin() {
        return origin;
    }

    /**
     * @return the timeFired
     */
    public long getTimeFired() {
        return timeFired;
    }

    /**
     * @return the power
     */
    public double getPower() {
        return power;
    }

    /**
     * @return the velocity
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * @return the distanceTraveled
     */
    public double getDistanceTraveled() {
        return distanceTraveled;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (isVirtual ? 1231 : 1237);
        result = prime * result + ((origin == null) ? 0 : origin.hashCode());
        long temp;
        temp = Double.doubleToLongBits(power);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (int) (timeFired ^ (timeFired >>> 32));
        temp = Double.doubleToLongBits(velocity);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RWave other = (RWave) obj;
        if (isVirtual != other.isVirtual)
            return false;
        if (origin == null) {
            if (other.origin != null)
                return false;
        } else if (!origin.equals(other.origin))
            return false;
        if (Double.doubleToLongBits(power) != Double
                .doubleToLongBits(other.power))
            return false;
        if (timeFired != other.timeFired)
            return false;
        if (Double.doubleToLongBits(velocity) != Double
                .doubleToLongBits(other.velocity))
            return false;
        return true;
    }
    

}
