/**
 * 
 */
package rampancy.util.vector;

import rampancy.util.*;
import robocode.util.Utils;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RVector {
    
    public static final Color  DEFAULT_COLOR    = new Color(0, 0, 200);
    public static final double ARROW_OFFSET     = Math.PI/6.0;
    public static final double ARROW_PROPORTION = 0.30;
    
    public RPoint origin;
    public double angle;
    public double magnitude;
    public double xComponent;
    public double yComponent;
    public Color  color;
    
    public RVector(RPoint origin) {
        this(origin, 0, 0);
    }
    
    /**
     * 
     * @param origin
     * @param angle
     * @param magnitude
     */
    public RVector(RPoint origin, double angle, double magnitude) {
        this(origin, angle, magnitude, DEFAULT_COLOR);
    }
    
    /**
     * 
     * @param origin
     * @param angle
     * @param magnitude
     * @param color
     */
    public RVector(RPoint origin, double angle, double magnitude, Color color) {
        this.origin    = origin.getCopy();
        this.angle     = angle;
        this.magnitude = magnitude;
        this.color     = color;
        xComponent = Math.sin(angle) * magnitude;
        yComponent = Math.cos(angle) * magnitude;
    }

    public void add(RVector vector) {
        xComponent = xComponent + vector.xComponent;
        yComponent = yComponent + vector.yComponent;
        magnitude = Math.sqrt(xComponent * xComponent + yComponent * yComponent);
        angle = Utils.normalAbsoluteAngle(Math.atan2(xComponent, yComponent));
    }
    
    public RPoint getEndPoint() {
        return RUtil.project(origin, angle, magnitude);
    }
    
    /**
     * @return the origin
     */
    public RPoint getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(RPoint origin) {
        this.origin = origin;
    }

    /**
     * @return the angle
     */
    public double getAngle() {
        return angle;
    }

    /**
     * @param angle the angle to set
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * @return the magnitude
     */
    public double getMagnitude() {
        return magnitude;
    }

    /**
     * @param magnitude the magnitude to set
     */
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
    
    /**
     * @return the xComponent
     */
    public double getXComponent() {
        return xComponent;
    }

    /**
     * @param component the xComponent to set
     */
    public void setXComponent(double component) {
        xComponent = component;
    }

    /**
     * @return the yComponent
     */
    public double getYComponent() {
        return yComponent;
    }

    /**
     * @param component the yComponent to set
     */
    public void setYComponent(double component) {
        yComponent = component;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    public void draw(Graphics2D g) {
        draw(g, magnitude);
    }

    public void draw(Graphics2D g, double magnitude) {
        draw(g, magnitude, color);
    }
    
    public void draw(Graphics2D g, double magnitude, Color color) {
        if(this.magnitude == 0) {
            magnitude = this.magnitude;
        }
        Color oldColor = g.getColor();
        
        RPoint endPoint = RUtil.project(origin, angle, magnitude);
        
        double arrowAngle = angle + Math.PI;
        double cwAngle    = Utils.normalAbsoluteAngle(arrowAngle - ARROW_OFFSET);
        double ccwAngle   = Utils.normalAbsoluteAngle(arrowAngle + ARROW_OFFSET);
        RPoint cwPoint    = RUtil.project(endPoint, cwAngle, magnitude * ARROW_PROPORTION);
        RPoint ccwPoint   = RUtil.project(endPoint, ccwAngle, magnitude * ARROW_PROPORTION);
        
        Line2D.Double body    = new Line2D.Double(origin, endPoint);
        Line2D.Double cwLine  = new Line2D.Double(cwPoint, endPoint);
        Line2D.Double ccwLine = new Line2D.Double(endPoint, ccwPoint);
        
        g.setColor(color);
        g.draw(body);
        g.draw(cwLine);
        g.draw(ccwLine);
        g.setColor(oldColor);
    }

    public String toString() {
        return "RVector: Origin: " + origin.toString() + " a: " + angle + " m: " + magnitude;
    }
}
