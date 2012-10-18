package marathon.leela;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

/**
 * This class represents an enemy point. It ensures that the resulting
 * force is perpendicular to the bearing
 * @author Matthew Chun-Lum
 *
 */
public class EnemyPoint extends ForcePoint {

    private int direction;
    
    /**
     * Standard constructor
     * @param location
     * @param magnitude
     */
    public EnemyPoint(Double location, double magnitude) {
        super(location, magnitude);
        direction = Math.random() > 0.5 ? 1 : -1;
    }

    /**
     * @param target
     * @return a ForceVector produced by the ForcePoint at a given target location
     */
    public ForceVector getVectorAt(Point2D.Double target) {
        double distance = location.distance(target);
        double targetLocationMagnitude = magnitudeTo(target);
        
        // Switch directions with low probability
        if(Math.random() < 0.02) {
            direction = -direction;
        }
        
        double angle = Math.atan2(location.getX() - target.getX(), location.getY() - target.getY());
        
        angle += direction * Math.PI / 2;
        
        double y = targetLocationMagnitude * Math.cos(angle);
        double x = targetLocationMagnitude * Math.sin(angle);
       
        return new ForceVector(x, y, targetLocationMagnitude);
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
