/**
 * RPoint.java
 */
package epgy.util;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Defines a data structure for storing an (x, y) coordinate.
 * The EPGYPoint has several helper methods, that simplify
 * certain calculations like distance and bot radius rectangles.
 * @author Matthew Chun-Lum
 *
 */
public class EPGYPoint extends Point2D.Double {
    
    /**
     * @param x
     * @param y
     */
    public EPGYPoint(double x, double y) {
        super(x, y);
    }
    
    /**
     * @return a copy of this RPoint
     */
    public EPGYPoint getCopy() {
        return (EPGYPoint) this.clone();
    }
    
    /**
     * @return a rectangle representing a robot centered at this point
     */
    public EPGYRectangle getBotRect() {
        return getBoundingRect(EPGYEnemyRobot.BOT_RADIUS);
    }
    
    /**
     * @param radius
     * @return a rectangle centered around 
     * this point with the specified radius
     */
    public EPGYRectangle getBoundingRect(double radius) {
        return new EPGYRectangle(this, radius);
    }

}
