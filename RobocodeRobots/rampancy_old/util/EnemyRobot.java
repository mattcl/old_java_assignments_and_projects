/**
 * EnemyRobot.java
 */
package rampancy_old.util;

import robocode.*;
import robocode.util.*;

import java.util.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.*;

import rampancy_old.*;
import rampancy_old.management.EnemyListener;
import rampancy_old.statistics.PatternMatchingGunStatistic;
import rampancy_old.statistics.pattern.PMState;
import rampancy_old.util.tree.*;

/**
 * This is the utility class that represents an enemy robot
 * @author Matthew Chun-Lum
 *
 */
public class EnemyRobot {
    
    private String name;
    
    private RampantRobot reference;
    private Point2D.Double location;
    private Point2D.Double lastLocation;
    
    private double absoluteBearing;
    private double lastAbsoluteBearing;
    private double heading;
    private double lastHeading;
    private double energy;
    private double lastEnergy;
    private double bulletPower;
    private double distance;
    private double lastDistance;
    private double velocity;
    private double lastVelocity;
    private int moveTimes;
    private long timeSinceVelocityChange;
    private int directionTraveling;
    private int shotsFired;
    
    private EnemyWave lastShot;
    
    private boolean hasFired;
    private boolean advancing;
    private boolean recordOnFile;
    
    private ArrayList<Double> trackedBearings;
    private ArrayList<Integer> trackedDirections;
    
    private ArrayList<EnemyListener> listeners;
    
    private VariationProfile variationProfile;
    private ArrayList<PMState> movementLog;
    
    /**
     * Just for testing
     */
    public EnemyRobot() {}
    
    /**
     * Default constructor
     * @param e
     * @param reference
     */
    public EnemyRobot(ScannedRobotEvent e, RampantRobot reference) {
        setInitialState(e, reference);
        listeners = new ArrayList<EnemyListener>();
        recordOnFile = false;
        variationProfile = new VariationProfile();
        movementLog = new ArrayList<PMState>();
    }
    
    /**
     * Sets the initial state of an EnemyRobot. May be called at the beginning of each round
     * @param e
     * @param reference
     */
    public void setInitialState(ScannedRobotEvent e, RampantRobot reference) {
        this.reference = reference;
        name = e.getName();
        hasFired = false;
        advancing = false;
        lastShot = null;
        bulletPower = 0;
        moveTimes = 0;
        directionTraveling = 1;
        setAbsoluteBearing(e.getBearingRadians() + reference.getHeadingRadians());
        setHeading(e.getHeadingRadians());
        setLocation(Util.project(reference.getLocation(), absoluteBearing, e.getDistance()));
        setDistance(location.distance(reference.getLocation()));
        setVelocity(e.getVelocity());
        setEnergy(e.getEnergy());
        resetTrackingLists();
    }
    
    /**
     * Updates the robot
     * @param e
     */
    public void update(ScannedRobotEvent e) {
       double lateralVelocity = reference.getVelocity() * Math.sin(e.getBearingRadians());
       setAbsoluteBearing(e.getBearingRadians() + reference.getHeadingRadians());
       setHeading(e.getHeadingRadians());
       setLocation(Util.project(reference.getLocation(), absoluteBearing, e.getDistance()));
       setDistance(getLocation().distance(reference.getLocation()));
       setVelocity(e.getVelocity());
       updateDirectionTraveling();
       // Add the direction of Durandal CW or CCW relative to the robot
       int direction = (lateralVelocity >= 0 ? 1 : -1);
       trackedDirections.add(0, direction);
       
       // Add the absolute bearing
       trackedBearings.add(0, absoluteBearing + Math.PI);
       
       setEnergy(e.getEnergy());
       checkFire();
       movementLog.add(new PMState(this));
       if(movementLog.size() > PatternMatchingGunStatistic.LOG_DEPTH)
           movementLog.remove(0);
       notifyListeners();
    }
    
    public ArrayList<PMState> getMovementLog() {
        return movementLog;
    }
    
    /**
     * @return {@code true} if the robot has fired
     */
    public boolean hasFired() {
        return hasFired;
    }
    
    /**
     * @return {@code true} if this robot's record was loaded from a file
     */
    public boolean recordOnFile() {
        return recordOnFile;
    }
    
    public void setRecordOnFile(boolean val) {
        recordOnFile = val;
    }
    
    // ----------- Getters and Setters ------------- //
    
    public int getShotsFired() {
        return shotsFired;
    }
    
    public void updateVariationProfile() {
        variationProfile.update(this);
    }
    
    public VariationProfile getVariationProfile() {
        return variationProfile;
    }
    
    /**
     * @return the last direction that Durandal was to the enemy when the enemy could have shot
     */
    public int getLastUsableSurfDirection() {
        if(trackedDirections.size() < 3)
            return 0;
        return trackedDirections.get(2);
    }
    
    /**
     * @return the last bearing that the enemy could have used to shoot
     */
    public double getLastUsableBearing() {
        if(trackedBearings.size() < 3)
            return 0;
        return trackedBearings.get(2);
    }

    /**
     * @return the direction traveling, which is either {@code 1} or {@code -1}
     */
    public int getDirectionTraveling() {
        return directionTraveling;
    }
    
    /**
     * Updates the direction traveling based on velocity and bearing
     */
    public void updateDirectionTraveling() {
        if (getVelocity() != 0) {
            if (Math.sin(getHeading()-getAbsoluteBearing()) * getVelocity() < 0)
                directionTraveling = -1;
            else
                directionTraveling = 1;
        }
    }
    
    /**
     * @return the name of the EnemyRobot
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the reference to Durandal
     * @param reference
     */
    public void setReference(RampantRobot reference) {
        this.reference = reference;
        resetTrackingLists();
    }
    
    /**
     * @return the current absolute bearing
     */
    public double getAbsoluteBearing() {
        return absoluteBearing;
    }

    /**
     * Sets the current absolute bearing and updates the last absolute bearing
     * @param absoluteBearing
     */
    public void setAbsoluteBearing(double absoluteBearing) {
        setLastAbsoluteBearing(this.absoluteBearing);
        this.absoluteBearing = absoluteBearing;
    }

    /**
     * @return the last absolute bearing
     */
    public double getLastAbsoluteBearing() {
        return lastAbsoluteBearing;
    }

    /**
     * Sets the last absolute bearing
     * @param lastAbsoluteBearing
     */
    public void setLastAbsoluteBearing(double lastAbsoluteBearing) {
        this.lastAbsoluteBearing = lastAbsoluteBearing;
    }

    /**
     * @return the heading
     */
    public double getHeading() {
        return heading;
    }

    /**
     * @param heading the heading to set
     */
    public void setHeading(double heading) {
        setLastHeading(this.heading);
        this.heading = heading;
    }

    /**
     * @return the lastHeading
     */
    public double getLastHeading() {
        return lastHeading;
    }

    /**
     * @param lastHeading the lastHeading to set
     */
    public void setLastHeading(double lastHeading) {
        this.lastHeading = lastHeading;
    }

    /**
     * @return the distance to this robot
     */
    public double getDistance() {
        return distance;
    }
    
    /**
     * Sets the distance
     * @param distance
     */
    public void setDistance(double distance) {
        setLastDistance(this.distance);
        this.distance = distance;
    }
    
    /**
     * @return the last recorded distance to the enemy
     */
    public double getLastDistance() {
        return lastDistance;
    }

    /**
     * Sets the last distance
     * @param lastDistance
     */
    public void setLastDistance(double lastDistance) {
        this.lastDistance = lastDistance;
    }

    /**
     * @return the number of scans since the velocity was last 0
     */
    public int getMoveTimes() {
        return moveTimes;
    }
    
    /**
     * @return the current velocity of the robot
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * Sets the velocity of the robot
     * @param velocity
     */
    public void setVelocity(double velocity) {
        setLastVelocity(this.velocity);
        this.velocity = velocity;
        
        if(this.velocity == 0)
            moveTimes = 0;
        else
            moveTimes++;
        
        if(this.velocity == lastVelocity)
            timeSinceVelocityChange++;
        else 
            timeSinceVelocityChange = 0;
        
        checkForRam();
    }

    /**
     * @return the last velocity of the robot
     */
    public double getLastVelocity() {
        return lastVelocity;
    }

    /**
     * Sets the last velocity of the robot
     * @param lastVelocity
     */
    public void setLastVelocity(double lastVelocity) {
        this.lastVelocity = lastVelocity;
    }
    
    public long getTimeSinceVelocityChange() {
        return timeSinceVelocityChange;
    }

    /**
     * @return the location of the EnemyRobot
     */
    public Point2D.Double getLocation() {
        return location;
    }

    /**
     * Sets the location of the EnemyRobot and updates the lastLocation
     * @param location
     */
    public void setLocation(Point2D.Double location) {
        setLastLocation(this.location);
        this.location = location;
//        this.location.x -= 2;
//        this.location.y += 2;
    }
    
    /**
     * @return the last location of the EnemyRobot
     */
    public Point2D.Double getLastLocation() {
        return lastLocation;
    }

    /**
     * Sets the lastLocation of the EnemyRobot
     * @param lastLocation
     */
    public void setLastLocation(Point2D.Double lastLocation) {
        this.lastLocation = lastLocation;
    }

    /**
     * @return the current recorded energy of the EnemyRobot
     */
    public double getEnergy() {
        return energy;
    }

    /**
     * Sets the current energy of the EnemyRobot and updates the lastEnergy
     * @param energy
     */
    public void setEnergy(double energy) {
        setLastEnergy(this.energy);
        this.energy = energy;
    }

    /**
     * @return the lastEnergy of the EnemyRobot
     */
    public double getLastEnergy() {
        return lastEnergy;
    }

    /**
     * Sets the lastEnergy of the EnemyRobot
     * @param lastEnergy
     */
    public void setLastEnergy(double lastEnergy) {
        this.lastEnergy = lastEnergy;
    }
    
    /**
     * @return the power the last shot was fired with
     */
    public double getBulletPower() {
        return bulletPower;
    }
    
    /**
     * @return an EnemyWave of the last shot fired
     */
    public EnemyWave getLastShot() {
        EnemyWave temp = lastShot;
        lastShot = null;
        hasFired = false;
        return temp;
    }
    
    /**
     * Simulates a bullet shot
     * @return an enemy wave representing a virtual bullet
     */
    public EnemyWave getVirtualBullet() {
        EnemyWave wave = new EnemyWave(this, (Point2D.Double) getLocation().clone(), reference, reference.getTime(), 2.5);
        wave.setVirtual(true);
        return wave;
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.red);
        g.fillOval((int) location.x - 2, (int) location.y - 1, 4, 4);
    }
    
    // ----------- Listener Code ------------- //
    
    /**
     * Notifies the objects listening to the EnemyManager
     */
    public void notifyListeners() {
        for(EnemyListener listener : listeners)
            listener.enemyUpdated(this);
    }
    
    /**
     * Adds a listener to the list of listeners
     * @param listener
     */
    public void addListener(EnemyListener listener) {
        if(!listeners.contains(listener))
            listeners.add(listener);
    }
    
    /**
     * Removes a listener from the list of listeners
     * @param listener
     */
    public void removeListener(EnemyListener listener) {
        if(listeners.contains(listener))
            listeners.remove(listener);
    }
    
    /**
     * Removes all the listeners from the list of listeners
     */
    public void removeAllListeners() {
        listeners.clear();
    }
   
    
    
    // ------------ Private Helpers ------------ //
    
    /**
     * Clears the trackedBearings and trackedDirections arrays
     */
    private void resetTrackingLists() {
        trackedBearings = new ArrayList<Double>();
        trackedDirections = new ArrayList<Integer>();
    }
    
    /**
     * Determines if the robot has fired a bullet
     */
    private void checkFire() {
        bulletPower = getLastEnergy() - getEnergy();
        if(bulletPower < 0 || bulletPower > 3.0 || bulletPower < 0.1) return;
        if(trackedDirections.size() < 3) return;
        
        lastShot = new EnemyWave(this, (Point2D.Double) getLocation().clone(), reference, reference.getTime(), bulletPower);
        hasFired = true;
        shotsFired++;
    }
    
    private void checkForRam() {
        if(!advancing && Util.isAdvancing(getAbsoluteBearing(), getHeading(), getLastHeading() - getHeading(), getVelocity())) {
            lastShot = new EnemyWave(this, (Point2D.Double) getLocation().clone(), reference, reference.getTime(), 0.5);
            advancing = true;
            hasFired = true;
        } else {
            advancing = false;
        }
    }
}
