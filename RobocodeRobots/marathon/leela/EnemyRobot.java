package marathon.leela;

import java.awt.geom.*;
import java.awt.*;

import marathon.tycho.Helper;
import robocode.*;

/**
 * This class stores data about enemy robots
 * @author Matthew Chun-Lum
 *
 */
public class EnemyRobot {
    
    private Leela reference;
    
    private Point2D.Double location;
    private Point2D.Double lastLocation;
    
    private String name;
    
    private double priority;
    
    private double energy;
    private double lastEnergy;
    
    private double heading;
    private double lastHeading;
    
    private double absoluteBearing;
    private double bearing;
    
    private double lastAbsoluteBearing;
    private double lastBearing;
    
    private double velocity;
    private double lastVelocity;
    
    private double distance;
    
    /**
     * Constructor
     * @param e
     */
    public EnemyRobot(ScannedRobotEvent e, Leela reference) {
        this.reference = reference;
        name = e.getName();
        heading = e.getHeadingRadians();
        lastHeading = heading;
        bearing = e.getBearingRadians();
        lastBearing = bearing;
        absoluteBearing = bearing + reference.getHeadingRadians();
        lastAbsoluteBearing = absoluteBearing;
        distance = e.getDistance();
        location = Utilities.project(reference.getLocation(), absoluteBearing, distance);
        lastLocation = location;
        velocity = e.getVelocity();
        lastVelocity = velocity;
        energy = Constants.INITIAL_ENERGY;
        lastEnergy = energy;
        
    }
    
    /**
     * Updates the robot using the passed ScannedRobotEvent
     * @param e
     */
    public void update(ScannedRobotEvent e) {
        setEnergy(e.getEnergy());
        setVelocity(e.getVelocity());
        setDistance(e.getDistance());
        setHeading(e.getHeadingRadians());
        setBearing(e.getBearingRadians());
        setAbsoluteBearing(e.getBearingRadians() + reference.getHeadingRadians());
        setLocation(Helper.project(reference.getLocation(), absoluteBearing, distance)); 
    }
    
    /**
     * @return the name of this EnemyRobot
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the absolute bearing to the given value and updates the lastAbsoluteBearing
     * variable
     * @param value
     */
    public void setAbsoluteBearing(double value) {
        lastAbsoluteBearing = absoluteBearing;
        absoluteBearing = value;
    }
    
    /**
     * @return the absolute bearing variable
     */
    public double getAbsoluteBearing() {
        return absoluteBearing;
    }
    
    /**
     * Sets the last absolute bearing to the given value
     * @param value
     */
    public void setLastAbsoluteBearing(double value) {
        lastAbsoluteBearing = value;
    }
    
    /**
     * @return the lastAbsoluteBearing
     */
    public double getLastAbsoluteBearing() {
        return lastAbsoluteBearing;
    }
    
    /**
     * Sets the bearing to the given value and updates the lastBearing variable
     * @param value
     */
    public void setBearing(double value) {
        lastBearing = bearing;
        bearing = value;
    }
    
    /**
     * @return the bearing value
     */
    public double getBearing() {
        return bearing;
    }
    
    /**
     * Sets the lastBearing to the given value
     * @param value
     */
    public void setLastBearing(double value) {
        lastBearing = value;
    }
    
    /**
     * @return the lastBearing value
     */
    public double getLastBearing() {
        return lastBearing;
    }
    
    /**
     * Sets the heading to the passed value and updates the old heading
     * @param value
     */
    public void setHeading(double value) {
        lastHeading = heading;
        heading = value;
    }
    
    /**
     * @return the current heading
     */
    public double getHeading() {
        return heading;
    }
    
    /**
     * Sets the lastHeading to the given value
     * @param value
     */
    public void setLastHeading(double value) {
        lastHeading = value;
    }
    
    /**
     * @return the last heading
     */
    public double getLastHeading() {
        return lastHeading;
    }
    
    /**
     * Sets the distance to the given value
     * @param value
     */
    public void setDistance(double value) {
        distance = value;
    }
    
    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }
    
    /**
     * Sets the velocity to the given value and updates the lastVelocity value
     * @param value
     */
    public void setVelocity(double value) {
        lastVelocity = velocity;
        velocity = value;
    }
    
    /**
     * @return the velocity
     */
    public double getVelocity() {
        return velocity;
    }
    
    /**
     * Sets the lastVelocity to the given value
     * @param value
     */
    public void setLastVelocity(double value) {
        lastVelocity = value;
    }
    
    /**
     * @return the lastVelocity
     */
    public double getLastVelocity() {
        return lastVelocity;
    }
    
    /**
     * Sets the energy to the given value and updates the lastEnergy variable
     * @param value
     */
    public void setEnergy(double value) {
        lastEnergy = energy;
        energy = value;
    }
    
    /**
     * @return the current energy value
     */
    public double getEnergy() {
        return energy;
    }
    
    /**
     * Sets the lastEnergy to the given value
     * @param value
     */
    public void setLastEnergy(double value) {
        lastEnergy = value;
    }
    
    /**
     * @return the lastEnergy value
     */
    public double getLastEnergy() {
        return lastEnergy;
    }
    
    /**
     * Sets the current location and updates the last location
     * @param newLoc
     */
    public void setLocation(Point2D.Double newLoc) {
        lastLocation = location;
        location = newLoc;
    }
    
    /**
     * @return the current location of the robot
     */
    public Point2D.Double getLocation() {
        return location;
    }
    
    /**
     * Sets the last location to the specified value
     * @param location
     */
    public void setLastLocation(Point2D.Double location) {
        lastLocation = location;
    }
    
    /**
     * @return the last location
     */
    public Point2D.Double getLastLocation() {
        return lastLocation;
    }
    
    /**
     * Sets the reference to Leela
     * @param reference
     */
    public void setReference(Leela reference) {
        this.reference = reference;
    }
    
    /**
     * @return the ForcePoint for this EnemyRobot
     */
    public ForcePoint getForcePoint() {
        return new EnemyPoint((Point2D.Double) getLocation().clone(), Constants.DEFAULT_ENEMY_MAGNITUDE);
    }
    
    /**
     * Returns a string representation of the enemy robot
     */
    public String toString() {
        return "EnemyRobot: " + name + " location: " + getLocation().toString() + " Energy: " + energy;
    }
    
}
