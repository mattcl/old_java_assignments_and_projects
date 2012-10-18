/**
 * RMoveChoice.java
 */
package rampancy.util.movement;

import java.awt.*;

import rampancy.util.RPoint;
import rampancy.util.RRectangle;
import rampancy.util.RUtil;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RMoveChoice {

    public static final Color DEFAULT_COLOR = Color.blue;
    
    public RPoint destination;
    public Color color;
    public double angle;
    public double distance;
    public double maxVelocity;
    public double danger;
    public double guessFactor;
    public long timeToDestination;
    public int direction;
    
    /**
     * @param destination
     * @param angle
     * @param distance
     * @param maxVelocity
     * @param danger
     * @param timeToDestination
     */
    public RMoveChoice(RPoint destination, double angle, double distance, double maxVelocity, double danger, double guessFactor, long timeToDestination, int direction) {
        this(destination, angle, distance, maxVelocity, danger, guessFactor, timeToDestination, direction, DEFAULT_COLOR);
    }
    
    /**
     * @param destination
     * @param angle
     * @param distance
     * @param maxVelocity
     * @param danger
     * @param timeToDestination
     * @param color
     */
    public RMoveChoice(RPoint destination, double angle, double distance, double maxVelocity, double danger, double guessFactor, long timeToDestination, int direction, Color color) {
        this.destination        = destination.getCopy();
        this.angle              = angle;
        this.distance           = distance;
        this.maxVelocity        = maxVelocity;
        this.danger             = danger;
        this.timeToDestination  = timeToDestination;
        this.color              = color;
        this.guessFactor        = guessFactor;
        this.direction          = direction;
    }
    
    /**
     * Copy constructor
     * @param choice
     */
    public RMoveChoice(RMoveChoice choice) {
        this(choice.destination,
             choice.angle,
             choice.distance,
             choice.maxVelocity,
             choice.danger,
             choice.guessFactor,
             choice.timeToDestination,
             choice.direction,
             choice.color);
    }
    
    /**
     * Draws the location
     * @param g
     */
    public void draw(Graphics2D g) {
        g.setColor(color);
        RUtil.fillOval(destination, 4, g);
        if(color.equals(Color.pink)) {
        g.draw(new RRectangle(destination));
        }
    }
}
