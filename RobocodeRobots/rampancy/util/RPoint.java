/**
 * RPoint.java
 */
package rampancy.util;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RPoint extends Point2D.Double {
    
    /**
     * @param x
     * @param y
     */
    public RPoint(double x, double y) {
        super(x, y);
    }
    
    /**
     * @return a copy of this RPoint
     */
    public RPoint getCopy() {
        return (RPoint) this.clone();
    }
    
    /**
     * Projects self at the specified angle and distance
     * @param angle the angle from 0 in radians
     * @param distance the distance to project
     * @return the projected point
     */
    public RPoint projectTo(double angle, double distance) {
        double px = x + Math.sin(angle) * distance;
        double py = y + Math.cos(angle) * distance;
        return new RPoint(px, py);
    }
    
    /**
     * Computes the absolute bearing from self to the destination point
     * @param destination
     * @return the absolute bearing in radians
     */
    public double computeAbsoluteBearingTo(RPoint destination) {
        return Utils.normalAbsoluteAngle(Math.atan2(destination.x - x, destination.y - y));
    }
    
    /**
     * Computes the absolute bearing from the source to self
     * @param source
     * @return the absolute bearing in radians
     */
    public double computeAbsoluteBearingFrom(RPoint source) {
        return Utils.normalAbsoluteAngle(Math.atan2(x - source.x, y - source.y));
    }
    
    /**
     * @return a rectangle representing a robot centered at this point
     */
    public RRectangle getBotRect() {
        return getBoundingRect(REnemyRobot.BOT_RADIUS);
    }
    
    /**
     * @param radius
     * @return a rectangle centered around 
     * this point with the specified radius
     */
    public RRectangle getBoundingRect(double radius) {
        return new RRectangle(this, radius);
    }

}
