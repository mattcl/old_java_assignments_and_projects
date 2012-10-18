/**
 * 
 */
package rampancy.util;

import java.awt.geom.Rectangle2D;

import rampancy.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RRectangle extends Rectangle2D.Double {
    
    /**
     * @param p1
     * @param p2
     * @return the intersection of the two points
     */
    public static RRectangle getIntersectionRectangle(RPoint p1, RPoint p2) {
        return getIntersectionRectangle(p1, p2, REnemyRobot.BOT_RADIUS, REnemyRobot.BOT_RADIUS);
    }
    
    /**
     * @param p1
     * @param p2
     * @param r1 the radius of the first point
     * @param r2 the radius of the second point
     * @return the intersection of the two points
     */
    public static RRectangle getIntersectionRectangle(RPoint p1, RPoint p2, double r1, double r2) {
        RRectangle rect1 = new RRectangle(p1, r1);
        RRectangle rect2 = new RRectangle(p2, r2);
        RRectangle intersection = (RRectangle) rect1.createIntersection(rect2);
        if(intersection.width < 0 || intersection.height < 0)
            return null;
        
        return intersection;
    }

    /**
     * @param point
     */
    public RRectangle(RPoint point) {
        this(point, REnemyRobot.BOT_RADIUS);
    }
    
    /**
     * @param point
     * @param radius
     */
    public RRectangle(RPoint point, double radius) {
        this(point.x - radius, point.y - radius, radius * 2, radius * 2);
    }
    
    /**
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public RRectangle(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
    
    /**
     * @return the center of the rectangle
     */
    public RPoint getCenter() {
        return new RPoint(x + width / 2.0, y + height / 2.0);
    }
    
}
