package marathon.leela;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;



/**
 * The SwivelPoint is a point that keeps Durandal oriented properly
 * @author Matthew Chun-Lum
 *
 */
public class SwivelPoint extends ForcePoint {

    private Leela reference;
    
    private int direction;
    
    /**
     * Constructor
     * @param reference
     * @param magnitude
     */
    public SwivelPoint(Leela reference, double magnitude) {
        super(reference.getLocation(), magnitude);
        this.reference = reference;
        direction = (Math.random() > 0.5 ? 1 : -1);
    }
    
    /**
     * Returns the magnitude at the target location
     * The magnitude is determined by an inverse square of the distance
     * @param target
     * @return the magnitude at the target location
     */
    public double magnitudeTo(Point2D.Double target) {
        return magnitude;
    }
    
    /**
     * @param target
     * @return a ForceVector produced by the ForcePoint at a given target location
     */
    public ForceVector getVectorAt(Point2D.Double target) {
        double targetLocationMagnitude = magnitudeTo(target);
        
        double x = 0;
        double y = 0;
        
        // Switch directions with low probability
        if(Math.random() < 0.01) {
            direction = -direction;
        }
        
        if(!Utilities.isCloseToWall(target)) {
            double heading = reference.getHeadingRadians();
            double perpendicular = (heading + direction * (Math.PI / 2)) % (Math.PI * 2);
            
            x = targetLocationMagnitude * Math.cos(perpendicular);
            y = targetLocationMagnitude * Math.sin(perpendicular);
        } else {
            double angle = Utilities.getAngleToCenter(target);
            y = targetLocationMagnitude * Math.cos(angle);
            x = targetLocationMagnitude * Math.sin(angle);
        }
        return new ForceVector(x, y, targetLocationMagnitude);
    }
    
    /**
     * draws the point and magnitude
     */
    public void draw(Graphics2D g, Point2D.Double target) {
        ForceVector vect = getVectorAt(target);
        g.setColor(Color.red);
        g.fillOval((int) target.x - 2, (int) target.y - 2, 4, 4);
        g.drawLine((int) target.x, (int) target.y, (int) (target.x + vect.x), (int) (target.y + vect.y));
    }
}
