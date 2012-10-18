package marathon.tycho;

import java.util.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class Wave {
    
    protected Point2D.Double origin;
    protected long lifetime;
    protected double velocity;
    protected double angle;
    protected double distanceTraveled;
    protected int direction;
    
    protected boolean markedForDeletion;
    protected boolean markedIgnore;
    
    protected EnemyRobot creator;
    
    public Wave(Point2D.Double origin, long lifetime, double bulletPower, double angle, int direction) {
        this.origin = origin;
        this.lifetime = lifetime - 1;
        velocity = Helper.bulletVelocity(bulletPower);
        distanceTraveled = velocity;
        this.direction = direction;
        this.angle = angle;
        markedForDeletion = false;
        markedIgnore = false;
    }
    
    public Wave(EnemyRobot creator, Point2D.Double origin, long lifetime, double bulletPower, double angle, int direction) {
        this(origin, lifetime, bulletPower, angle, direction);
        this.creator = creator;
    }
    
    public EnemyRobot getEnemy() {
        return creator;
    }
    
    public void update(long time) {
        if(!markedIgnore)
            distanceTraveled = (time - lifetime) * velocity;
    }
    
    public boolean didBreak(Point2D.Double target) {
        return (distanceTraveled > target.distance(origin) + 50);
    }
    
    public boolean didHit(Point2D.Double target, double bulletPower) {
        return (Math.abs(distanceTraveled - target.distance(origin)) < 50) &&
               (Math.round(Helper.bulletVelocity(bulletPower)) == Math.round(velocity));
    }
    
    public boolean intercepted(Point2D.Double source, int tickCount) {
        return (source.distance(origin) < distanceTraveled + (tickCount * velocity) + velocity);
    }
    
    public double distanceFrom(Point2D.Double target) {
        return target.distance(origin) - distanceTraveled;
    }
    
    public double getVelocity() {
        return velocity;
    }
    
    public double getDistanceTraveled() {
        return distanceTraveled;
    }
    
    public Point2D.Double getOrigin() {
        return origin;
    }
    
    public double getAngle() {
        return angle;
    }
    
    public int getDirection() {
        return direction;
    }
    
    public void markForDeletion() {
        markedForDeletion = true;
    }
    
    public boolean isMarkedForDeletion() {
        return markedForDeletion;
    }
    
    public void markIgnore() {
        markedIgnore = true;
    }
    
    public boolean isMarkedIgnore() {
        return markedIgnore;
    }
    
}