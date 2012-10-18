package marathon.leela;

import java.awt.geom.*;


/**
 * Dummy class for holding common utility methods
 * @author Matthew Chun-Lum
 *
 */
public abstract class Utilities {

    // --------------- Movement Helpers -------------- //
    
    /**
     * Projects a point location given distance and angle
     */
    public static Point2D.Double project(Point2D.Double source, double angle, double length) {
        double x = source.x + Math.sin(angle) * length;
        double y = source.y + Math.cos(angle) * length;
        return new Point2D.Double(x, y);
    }
    
    /**
     * 
     * @param location
     * @return the angle to the center
     */
    public static double getAngleToCenter(Point2D.Double location) {
        Point2D.Double center = new Point2D.Double(MovementManager.width / 2, MovementManager.height / 2);
        return Math.atan2((int) (center.x - location.x), (int) (center.y - location.y));
    }
    
    /**
     * Tests if Durandal is too close to a wall
     * @param location
     * @return true if too close to a wall
     */
    public static boolean isCloseToWall(Point2D.Double location) {
        return (location.x < Constants.WALL_TOLERANCE || location.x > MovementManager.width - Constants.WALL_TOLERANCE) ||
                (location.y < Constants.WALL_TOLERANCE || location.y > MovementManager.height - Constants.WALL_TOLERANCE);
    }
    
    // ------------- Projectile Helpers -------------- //
    
    /**
     * Given the bullet power compute the velocity
     */
    public static double bulletVelocity(double bulletPower) {
        return (20.0 - (3.0 * bulletPower));
    }
    
}
