package marathon.leela;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.util.*;


/**
 * The Wall class is a subclass of ForcePoint. It provides a repulsive force that
 * keeps Durandal away from walls.
 * @author Matthew Chun-Lum
 *
 */
public class Wall extends ForcePoint {
    
    // Wall direction constants
    public static final int NORTH = 0;
    public static final int SOUTH = 1;
    public static final int EAST = 2;
    public static final int WEST = 3;
    
    public int wallDirection;
    public String direction;
    public double sin;
    public double cos;
    
    /**
     * Constructor
     * @param location
     * @param magnitude
     * @param direction
     */
    public Wall(Point2D.Double location, double magnitude, int wallDirection) {
        super(location, magnitude);
        this.wallDirection = wallDirection;
        switch(wallDirection) {
            case NORTH:
                cos = 0;
                sin = -1;
                direction = "NORTH";
                break;
            case SOUTH:
                cos = 0;
                sin = 1;
                direction = "SOUTH";
                break;
            case EAST:
                cos = 1;
                sin = 0;
                direction = "EAST";
                break;
            case WEST:
                cos = -1;
                sin = 0;
                direction = "WEST";
                break;
            default:
                System.out.println("INVALID WALL DIRECTION: " + wallDirection);
        }
    }
    
    /**
     * Returns the magnitude at the target location
     * The magnitude is determined by an inverse square of the distance
     * @param target
     * @return the magnitude at the target location
     */
    public double magnitudeTo(Point2D.Double target) {
        switch(wallDirection) {
        case NORTH:
        case SOUTH:
            double dist = location.y - target.y;
            return magnitude / Math.max(1, dist * dist);
        case EAST:
        case WEST:
            double dist2 = location.x - target.x;
            return magnitude / Math.max(1, dist2 * dist2);
        default:
            System.out.println("INVALID WALL DIRECTION: " + wallDirection);
            return 0;
        }   
    }
    
    /**
     * @param target
     * @return a ForceVector produced by the ForcePoint at a given target location
     */
    public ForceVector getVectorAt(Point2D.Double target) {
        double targetLocationMagnitude = magnitudeTo(target);
        return new ForceVector(targetLocationMagnitude * cos, targetLocationMagnitude * sin, targetLocationMagnitude);
    }

    /**
     * Returns a string representation of this wall
     */
    public String toString() {
        return "Wall: Direction: " + direction + " Force magnitude" + magnitude + " sin: " + sin + " cos: " + cos;
    }
}
