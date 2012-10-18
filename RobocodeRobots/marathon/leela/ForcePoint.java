package marathon.leela;

import java.awt.geom.*;
import java.awt.*;
import java.util.*;


import rampancy_old.*;


/**
 * The ForcePoint class is used in Durandal's movement algorithms. It produces an 
 * anti-gravity force whose magnitude decreases inversely with distance.
 * @author Matthew Chun-Lum
 *
 */
public class ForcePoint {

    public Point2D.Double location;
    public double magnitude;
    public double distanceFactor;
    
    /**
     * Standard constructor
     * @param location
     * @param magnitude
     */
    public ForcePoint(Point2D.Double location, double magnitude) {
        this(location, magnitude, Constants.DEFAULT_DISTANCE_FACTOR);
    }
    
    /**
     * Standard constructor
     * @param location
     * @param magnitude
     */
    public ForcePoint(Point2D.Double location, double magnitude, double distanceFactor) {
        this.location = location;
        this.magnitude = magnitude;
        this.distanceFactor = distanceFactor;
    }
    
    /**
     * Returns the magnitude at the target location
     * The magnitude is determined by an inverse square of the distance
     * @param target
     * @return the magnitude at the target location
     */
    public double magnitudeTo(Point2D.Double target) {
        return magnitude / Math.max(1, Math.pow(location.distance(target), distanceFactor));
    }
    
    /**
     * @param target
     * @return a ForceVector produced by the ForcePoint at a given target location
     */
    public ForceVector getVectorAt(Point2D.Double target) {
        double distance = location.distance(target);
        double targetLocationMagnitude = magnitudeTo(target);
        
        double sin = (target.getY() - location.getY()) / distance;
        double cos = (target.getX() - location.getX()) / distance;
        
        return new ForceVector(targetLocationMagnitude * cos, targetLocationMagnitude * sin, targetLocationMagnitude);
    }
    
    /**
     * Getter for the absolute magnitude
     * @return the absolute magnitude
     */
    public double getMagnitude() {
        return magnitude;
    }
    
    /**
     * Sets the magnitude to the given value
     * @param value
     */
    public void setMagnitude(double value) {
        magnitude = value;
    }
    
    /**
     * @return a reference to the location variable
     */
    public Point2D.Double getLocation() {
        return location;
    }
    
    /**
     * Assigns the passed location to the local location. DOES NOT MAKE A COPY!
     * @param location
     */
    public void setLocation(Point2D.Double location) {
        this.location = location;
    }
    
    /**
     * Returns a string representation of this ForcePoint
     */
    public String toString() {
        return "ForcePoint: Location: " + location.toString() + " Force magnitude" + magnitude;
    }
    
    /**
     * draws the point and magnitude
     */
    public void draw(Graphics2D g, Point2D.Double target) {
        ForceVector vect = getVectorAt(target);
        g.setColor(Color.red);
        g.fillOval((int) location.x - 2, (int) location.y - 2, 4, 4);
        g.drawLine((int) location.x, (int) location.y, (int) (location.x + vect.x), (int) (location.y + vect.y));
    }
}
