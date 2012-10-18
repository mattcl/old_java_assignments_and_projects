/**
 * 
 */
package rampancy.util.vector;

import rampancy.util.REnemyListener;
import rampancy.util.REnemyRobot;
import rampancy.util.RPoint;
import rampancy.util.RUtil;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RRepulsivePoint extends RPoint implements RRepulsiveObject {
    
    public static final double STANDARD_MAX_RADIUS = 100;
    public static final double STANDARD_DANGER     = 100;
    public static final double STANDARD_MULTIPLIER = 1;

    public double danger;
    public double maxRadius;
    public double multiplier;
    
    public RRepulsivePoint(double x, double y) {
        this(x, y, STANDARD_MAX_RADIUS);
    }
    
    public RRepulsivePoint(double x, double y, double maxRadius) {
        this(x, y, maxRadius, STANDARD_DANGER);
    }
    
    public RRepulsivePoint(double x, double y, double maxRadius, double danger) {
        this(x, y, maxRadius, danger, STANDARD_MULTIPLIER);
    }
    
    public RRepulsivePoint(RPoint point, double maxRadius, double danger, double multiplier) {
        this(point.x, point.y, maxRadius, danger, multiplier);
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param maxRadius
     * @param danger
     * @param multiplier
     */
    public RRepulsivePoint(double x, double y, double maxRadius, double danger, double multiplier) {
        super(x, y);
        this.maxRadius = maxRadius;
        this.danger = danger;
        this.multiplier = multiplier;
    }
    
    public RVector getForceAtPoint(RPoint point) {
        double dist = this.distance(point);
        if(dist > maxRadius)
            return null;
        if(dist == 0) {
            dist += 0.00001;
        }
        double computedDanger = danger / (dist * dist * (1.0 /multiplier));
        double angle = RUtil.computeAbsoluteBearing(this, point);
        return new RVector(point, angle, computedDanger);
    }

    /**
     * @return the danger
     */
    public double getDanger() {
        return danger;
    }

    /**
     * @param danger the danger to set
     */
    public void setDanger(double danger) {
        this.danger = danger;
    }

    /**
     * @return the maxRadius
     */
    public double getMaxRadius() {
        return maxRadius;
    }

    /**
     * @param maxRadius the maxRadius to set
     */
    public void setMaxRadius(double maxRadius) {
        this.maxRadius = maxRadius;
    }

    /**
     * @return the multiplier
     */
    public double getMultiplier() {
        return multiplier;
    }

    /**
     * @param multiplier the multiplier to set
     */
    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    
    

}
