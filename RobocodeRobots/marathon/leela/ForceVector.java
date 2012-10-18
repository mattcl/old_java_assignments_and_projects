package marathon.leela;

import java.awt.geom.*;
import java.awt.*;

/**
 * A simple vector "struct"
 * @author Matthew Chun-Lum
 *
 */
public class ForceVector {
    // ----------- STATIC HELPERS ------------ //
    
    /**
     * Returns a ForceVector representing the sum of two vectors
     */
    public static ForceVector sumVectors(ForceVector v1, ForceVector v2) {
        double x = v1.x + v2.x;
        double y = v1.y + v2.y;
        return new ForceVector(x, y, Math.sqrt(x * x + y * y));
    }
    
    public double x;
    public double y;
    public double magnitude;
    
    /**
     * Constructor
     * @param x
     * @param y
     * @param magnitude
     */
    public ForceVector(double x, double y, double magnitude) {
        this.x = x;
        this.y = y;
        this.magnitude = magnitude;
    }
    
    /**
     * Returns a coterminal vector with the given magnitude
     * @param magnitude
     * @return
     */
    public ForceVector getCoterminal(double magnitude) {
        double angle = getAngle();
        return new ForceVector(Math.cos(angle) * magnitude, Math.sin(angle) * magnitude, magnitude);
    }
    
    /**
     * Returns the angle of this vector
     * @return
     */
    public double getAngle() {
        return Math.atan2(x, y);
    }
    
    /**
     * Draws the force vector
     * @param g
     * @param startingLocation
     */
    public void draw(Graphics2D g, Point2D.Double startingLocation) {
        g.setColor(Color.green);
        g.drawLine((int) startingLocation.x, (int) startingLocation.y, (int) (startingLocation.x + x), (int) (startingLocation.y + y));
    }
    
    /**
     * Returns a string representation of the vector
     */
    public String toString() {
        return "(" + x + ", " + y + ")" + "magnitude: " + magnitude;
    }
    
    
}