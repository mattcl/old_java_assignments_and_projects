package marathon.leela;

import java.util.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * This abstract class defines the basic behavior for waves
 * @author Matthew Chun-Lum
 *
 */
public abstract class Wave {
    
    protected Color color;
    
    protected Point2D.Double origin;
    protected long fireTime;
    protected double velocity;
    protected double power;
    protected double distanceTraveled;
    protected int direction;
    
    public Wave(Color color, Point2D.Double origin, long fireTime, double power) {
        this.color = color;
        this.origin = origin;
        this.power = power;
        velocity = Utilities.bulletVelocity(power);
        distanceTraveled = velocity;
    }
    
    /**
     * Draws the wave
     * @param g
     */
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.drawOval((int) (origin.x - distanceTraveled), 
                    (int) (origin.y - distanceTraveled), 
                     (int) (distanceTraveled * 2), 
                      (int) (distanceTraveled * 2));
    }
}
