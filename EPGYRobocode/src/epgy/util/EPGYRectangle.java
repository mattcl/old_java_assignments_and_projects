/**
 * 
 */
package epgy.util;

import java.awt.geom.Rectangle2D;

/**
 * This class is a data structure for storing rectangles.
 * There are a number of useful operations like finding
 * intersections and checking for the inclusion of points.
 * @author Matthew Chun-Lum
 *
 */
public class EPGYRectangle extends Rectangle2D.Double {
    
    /**
     * @param p1
     * @param p2
     * @return the intersection of the two points
     */
    public static EPGYRectangle getIntersectionRectangle(EPGYPoint p1, EPGYPoint p2) {
        return getIntersectionRectangle(p1, p2, EPGYEnemyRobot.BOT_RADIUS, EPGYEnemyRobot.BOT_RADIUS);
    }
    
    /**
     * @param p1
     * @param p2
     * @param r1 the radius of the first point
     * @param r2 the radius of the second point
     * @return the intersection of the two points
     */
    public static EPGYRectangle getIntersectionRectangle(EPGYPoint p1, EPGYPoint p2, double r1, double r2) {
        EPGYRectangle rect1 = new EPGYRectangle(p1, r1);
        EPGYRectangle rect2 = new EPGYRectangle(p2, r2);
        EPGYRectangle intersection = (EPGYRectangle) rect1.createIntersection(rect2);
        if(intersection.width < 0 || intersection.height < 0)
            return null;
        
        return intersection;
    }

    /**
     * @param point
     */
    public EPGYRectangle(EPGYPoint point) {
        this(point, EPGYEnemyRobot.BOT_RADIUS);
    }
    
    /**
     * @param point
     * @param radius
     */
    public EPGYRectangle(EPGYPoint point, double radius) {
        this(point.x - radius, point.y - radius, radius * 2, radius * 2);
    }
    
    /**
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public EPGYRectangle(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
    
    /**
     * @return the center of the rectangle
     */
    public EPGYPoint getCenter() {
        return new EPGYPoint(x + width / 2.0, y + height / 2.0);
    }
    
}
