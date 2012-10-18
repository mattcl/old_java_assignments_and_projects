/**
 * Battlefield.java
 */
package rampancy_old.util;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import rampancy_old.Durandal;

/**
 * This class represents a battlefield
 * @author Matthew Chun-Lum
 *
 */
public class Battlefield {
   
    public static final double MARGIN = 18;
    public static final double NEAR_WALL_MARGIN = 50;
    public static final double CORNER_WIDTH = 50;
    public static final double TOLERANCE = 25;
   
    private static final double EAST = Math.PI / 4;
    private static final double SOUTH = EAST * 3;
    private static final double WEST = EAST * 5;
    private static final double NORTH = EAST * 7;
    
    
    public double width;
    public double height;
    public Rectangle2D.Double battlefieldRect;
    public Rectangle2D.Double centerRect;
    public Rectangle2D.Double upperRight;
    public Rectangle2D.Double upperLeft;
    public Rectangle2D.Double lowerRight;
    public Rectangle2D.Double lowerLeft;
    
    /**
     * Default constructor
     * @param width
     * @param height
     */
    public Battlefield(double width, double height) {
        this.width = width;
        this.height = height;
        battlefieldRect = new Rectangle2D.Double(MARGIN, MARGIN, width - MARGIN * 2, height - MARGIN * 2);
        centerRect     = new Rectangle2D.Double(NEAR_WALL_MARGIN, NEAR_WALL_MARGIN, width - NEAR_WALL_MARGIN * 2, height - NEAR_WALL_MARGIN * 2);
        upperLeft      = new Rectangle2D.Double(0, 0, CORNER_WIDTH, CORNER_WIDTH);
        upperRight     = new Rectangle2D.Double(width - CORNER_WIDTH, 0, CORNER_WIDTH, CORNER_WIDTH);
        lowerRight     = new Rectangle2D.Double(width - CORNER_WIDTH, height - CORNER_WIDTH, CORNER_WIDTH, CORNER_WIDTH);
        lowerLeft      = new Rectangle2D.Double(0, height - CORNER_WIDTH, CORNER_WIDTH, CORNER_WIDTH);
    }
    
    public String toString() {
        return "width: " + width + " height: " + height + " bf rect: " + battlefieldRect.toString();
    }
    
    /**
     * @param location
     * @return the distance of the point to the closest wall
     */
    public double distanceFromWall(Point2D.Double location) {
        double[] distances = new double[4];
        distances[0] = location.x;
        distances[1] = battlefieldRect.width - location.x;
        distances[2] = battlefieldRect.height - location.y;
        distances[3] = location.y;
        return Util.lowest(distances);     
    }
    
    
    /**
     * @param location
     * @return {@code true} if the location is near the south wall
     */
    public boolean isNearSouthWall(Point2D.Double location) {
        return Math.abs(location.y - height) < TOLERANCE;
    }
    
    /**
     * @param location
     * @return {@code true} if the location is near the north wall
     */
    public boolean isNearNorthWall(Point2D.Double location) {
        return location.y < TOLERANCE + 2;
    }
    
    /**
     * @param location
     * @return {@code true} if the location is near the east wall
     */
    public boolean isNearEastWall(Point2D.Double location) {
        return Math.abs(location.x - width) < TOLERANCE;
    }
    
    /**
     * @param location
     * @return {@code true} if the location is near the west wall
     */
    public boolean isNearWestWall(Point2D.Double location) {
        return location.x < TOLERANCE + 2;
    }
    
    /**
     * @param location
     * @return {@code true} if the battlefield contains the passed location
     */
    public boolean contains(Point2D.Double location) {
        return battlefieldRect.contains(location);
    }
    
    /**
     * @param location
     * @return {@code true} if the location is in a corner
     */
    public boolean isInCorner(Point2D.Double location) {
        return (upperLeft.contains(location) || upperRight.contains(location) || lowerLeft.contains(location) || lowerRight.contains(location));
    }
    
    /**
     * @param location
     * @return {@code true} if the location is near a wall
     */
    public boolean isNearWall(Point2D.Double location) {
        return !centerRect.contains(location);
    }
}
