/**
 * Structure for storing battlefield information
 */
package epgy.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Structure for storing battlefield information.
 * @author Matthew Chun-Lum
 *
 */
public class EPGYBattlefield {
    public static final int AGAINST_WALL   = 0;
    public static final int NEAR_WALL      = 1;
    public static final int AWAY_FROM_WALL = 2;
    
    public static final double AGAINST_WALL_TOLERANCE = 150;
    public static final double NEAR_WALL_TOLERANCE    = AGAINST_WALL_TOLERANCE + 150;
    
    public static final int INNER_DISTANCE = 18;
    
    public Rectangle bfRect;
    public Rectangle innerRect;
    public int width;
    public int height;
    
    /**
     * Constructor
     * @param width
     * @param height
     */
    public EPGYBattlefield(int width, int height) {
        this.width = width;
        this.height = height;
        bfRect = new Rectangle(width, height);
        innerRect = new Rectangle(INNER_DISTANCE, 
                                  INNER_DISTANCE, 
                                  width - INNER_DISTANCE * 2, 
                                  height - INNER_DISTANCE * 2);
    }
    
    /**
     * @param point
     * @return true if the point is contained in the battlefield
     */
    public boolean contains(EPGYPoint point) {
        return bfRect.contains(point);
    }
    
    public double distanceFromTop(EPGYPoint point) {
        return Math.abs(bfRect.height - point.y);
    }
    
    public double distanceFromBot(EPGYPoint point) {
        return point.y;
    }
    
    public double distanceFromLeft(EPGYPoint point) {
        return point.x;
    }
    
    public double distanceFromRight(EPGYPoint point) {
        return Math.abs(bfRect.width - point.x);
    }
    
    public double innerDistanceFromTop(EPGYPoint point) {
        return Math.max(1, distanceFromTop(point) - INNER_DISTANCE);
    }
    
    public double innerDistanceFromBot(EPGYPoint point) {
        return Math.max(1, distanceFromBot(point) - INNER_DISTANCE);
    }
    
    public double innerDistanceFromRight(EPGYPoint point) {
        return Math.max(1, distanceFromRight(point) - INNER_DISTANCE);
    }
    
    public double innerDistanceFromLeft(EPGYPoint point) {
        return Math.max(1, distanceFromLeft(point) - INNER_DISTANCE);
    }
    
    /**
     * @param point
     * @return the closest distance to any wall
     */
    public double distanceFromWall(EPGYPoint point) {
        if(!contains(point))
            return -1;
        
        double distLeft = point.x;
        double distRight = bfRect.width - point.x;
        double distTop = bfRect.height - point.y;
        double distBot = point.y;
        
        return Math.min(Math.min(distRight, distLeft), Math.min(distTop, distBot));
    }
    
    /**
     * @param point
     * @return the category corresponding to the point's
     *         distance from a wall
     */
    public int distanceFromWallCategory(EPGYPoint point) {
        return distanceFromWallCategory(distanceFromWall(point));
    }
    
    /**
     * @param distance
     * @return the category corresponding to a distance from a wall
     */
    public int distanceFromWallCategory(double distance) {
        if(distance < AGAINST_WALL_TOLERANCE)
            return AGAINST_WALL;
        
        if(distance < NEAR_WALL_TOLERANCE)
            return NEAR_WALL;
        
        return AWAY_FROM_WALL;
    }
    
    /**
     * @param point
     * @return
     */
    public boolean validMovePosition(EPGYPoint point) {
        return innerRect.contains(point);
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.gray);
        g.drawRect(innerRect.x, innerRect.y, innerRect.width, innerRect.height);
    }
}
