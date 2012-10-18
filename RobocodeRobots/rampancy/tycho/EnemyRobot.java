package rampancy.tycho;

import robocode.*;
import robocode.util.*;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class EnemyRobot {
 
    private ArrayList<Wave> waves;
    private ArrayList<BulletWave> incomingBullets;
    private ArrayList<Integer> surfDirections;
    private ArrayList<Double> surfBearings;
 
    private String name;
    private Point2D.Double location;
    private Point2D.Double lastLocation;
    
    private double distance;
    
    private double energy;
    private boolean hasFired;
    
    private double velocity;
    private double lastVelocity;
    
    private double heading;
    private double lastHeading;
    
    private double bearing;
    private double lastBearing;
    
    private double absoluteBearing;
    private double lastAbsoluteBearing;
    
    private long lastMoveTime;
    
    private double bulletPower;
    private double bulletVelocity;
    
    private double[][] statistics;
    private double[][][][][][] guessFactors;
    private int[][][][][] readings;
    
    private Tycho reference;
    
    public EnemyRobot(ScannedRobotEvent e, Tycho reference) {
        this.reference = reference;
        statistics = new double[Helper.DISTANCE][Helper.BINS];
        guessFactors = new double[Helper.DISTANCE][Helper.VELOCITIES][Helper.MOVE_TIMES][Helper.HEADINGS][Helper.BEARINGS][Helper.GUESS_FACTORS];
        readings = new int[Helper.DISTANCE][Helper.VELOCITIES][Helper.MOVE_TIMES][Helper.HEADINGS][Helper.BEARINGS];
        
        name = e.getName();
        heading = e.getHeadingRadians();
        lastHeading = heading;
        bearing = e.getBearingRadians();
        lastBearing = bearing;
        absoluteBearing = bearing + reference.getHeadingRadians();
        lastAbsoluteBearing = absoluteBearing;
        
        distance = e.getDistance();
        
        location = Helper.project(reference.getLocation(), absoluteBearing, distance);
        lastLocation = location;
        
        energy = e.getEnergy();
        hasFired = false;
        
        velocity = e.getVelocity();
        lastVelocity = velocity; 
        
        lastMoveTime = 0;
        
        waves = new ArrayList<Wave>();
        incomingBullets = new ArrayList<BulletWave>();
        surfDirections = new ArrayList<Integer>();
        surfBearings = new ArrayList<Double>();
    }
    
    /**
     * Updates the local energy variable with the energy reading
     * and computes the power of the "bullet." If the energy drop
     * is within the tolerance levels for a shot, the bullet power
     * is returned. Otherwise, -1 is returned.
     */
    public double getBulletPower(double energyReading) {
        double power = energy - energyReading;
        energy = energyReading;
        if(power < 3.01 && power > 0.09) {
            return power;
        }
        return -1.0;
    }
    
    /**
     * Returns true if the robot has fired (actually two ticks ago)
     * 
     * NOT VALID AFTER A CALL TO bulletPower OR bulletVelocity
     */
    public boolean hasFired() {
        return hasFired;
    }
    
    /**
     * Determines if the shot should be tracked
     */
    public boolean shouldTrackShot() {
        return surfDirections.size() > 2;
    }
    
    /**
     * Returns the closest wave in the array
     */
    public Wave getClosestWave() {
        double closest = 5000000;
        Wave closestWave = null;
        
        for(Wave wave : waves) {
            double distance = wave.distanceFrom(reference.getLocation());
            if(distance < closest) {
                closestWave = wave;
                closest = distance;
            }
        }
        return closestWave;
    }
    
    public double getStatistic(int distance, int index) {
        return statistics[distance][index];
    }
    
    /**
     * Analyzes a bullet fired from this robot that struck Tycho
     */
    public void processBulletHit(HitByBulletEvent e) {
        if(!waves.isEmpty()) {
            Point2D.Double hitLocation = new Point2D.Double(e.getBullet().getX(), e.getBullet().getY());
            
            Wave hitWave = null;
            for(Wave wave : waves)
                if(wave.didHit(reference.getLocation(), e.getBullet().getPower())) {
                    hitWave = wave;
                    break;
                }
            if(hitWave != null) {
                noteHit(hitWave, hitLocation);
                waves.remove(hitWave);
            }
        }
    }
    
    /**
     * Draws aspects of the robot
     */
    public void draw(Graphics2D g) {
        g.setColor(Color.red);
        for(Wave wave : waves) {
            int x = (int) (wave.getOrigin().x - wave.getDistanceTraveled());
            int y = (int) (wave.getOrigin().y- wave.getDistanceTraveled());
            int diameter = (int) (wave.getDistanceTraveled() * 2);
            g.drawOval(x, y, diameter, diameter);
        }
        g.setColor(Color.green);
        for(Wave wave : incomingBullets) {
            int x = (int) (wave.getOrigin().x - wave.getDistanceTraveled());
            int y = (int) (wave.getOrigin().y- wave.getDistanceTraveled());
            int diameter = (int) (wave.getDistanceTraveled() * 2);
            g.drawOval(x, y, diameter, diameter);
        }
    }
    
    /**
     * Updates the statistics tracking array
     */
    private void noteHit(Wave wave, Point2D.Double target) {
        int distance = Helper.getDistanceSegment(target.distance(wave.getOrigin()));
        int index = Helper.getFactorIndex(wave, target);
        for(int i = 0; i < Helper.BINS; i++)
            statistics[distance][i] += 1.0 / (Math.pow(index - i, 2) + 1);
    }
    
    
    /**
     * Updates the waves we're currently tracking
     */
    private void updateWaves() {
        for(int i = waves.size() - 1; i >= 0; i--) {
            Wave wave = waves.get(i);
            wave.update(reference.getTime());
            if(wave.didBreak(reference.getLocation()))
                waves.remove(i);
        }
    }
   
    /**
     * Determines if new waves should be added to the arrayList
     */
    private void addNewWavesIfNeeded() {
        if(hasFired && shouldTrackShot()) {
            waves.add(new Wave(this, 
                               (Point2D.Double) lastLocation.clone(), 
                               reference.getTime(), 
                               getBulletPower(), 
                               surfBearings.get(2), 
                               surfDirections.get(2)));
        }
    }
    
    // --------------- BulletWave code ---------------- //
    
    public void addIncomingBullet(BulletWave bullet) {
        incomingBullets.add(bullet);
    }
    
    /**
     * Updates the incoming bullets array
     */
    public void updateIncomingBullets() {
        for (int i = 0; i < incomingBullets.size(); i++) {
            BulletWave currentWave = incomingBullets.get(i);
            if (currentWave.checkHit(location, reference.getTime()))
                incomingBullets.remove(currentWave);
        }
    }
    
    // ------------- Getters and Setters -------------- //
    
    /**
     * Updates the robots current stats
     */
    public void update(ScannedRobotEvent e) {
        setEnergy(e.getEnergy());
        setVelocity(e.getVelocity());
        setDistance(e.getDistance());
        setHeading(e.getHeadingRadians());
        setBearing(e.getBearingRadians());
        setAbsoluteBearing(e.getBearingRadians() + reference.getHeadingRadians());
        setLocation(Helper.project(reference.getLocation(), absoluteBearing, distance)); 
        
        double lateralVelocity = reference.getVelocity() * Math.sin(e.getBearingRadians());
        surfDirections.add(0, lateralVelocity > 0 ? 1 : -10);
        surfBearings.add(0, absoluteBearing + Math.PI);
        
        addNewWavesIfNeeded();
        updateWaves();
        updateIncomingBullets();
    }
    
    /**
     * Returns the statistics array
     */
//    public double[] getStatistics() {
//        return statistics;
//    }
    
    /**
     * Returns the power of the last recorded shot
     */
    public double getBulletPower() {
        if(hasFired)
            hasFired = !hasFired;
        return bulletPower;
    }
    
    /**
     * Returns the velocity of the last recorded shot
     */
    public double getBulletVelocity() {
        if(hasFired)
            hasFired = !hasFired;
        return bulletVelocity;
    }
    
    /**
     * Sets the current distance to enemy
     */
    public void setDistance(double value) {
        distance = value;
    }
    
    /**
     * Returns the last measured distance to the enemy
     */
    public double getDistance() {
        return distance;
    }
    
    /**
     * Sets the enemy heading to the given value
     */
    public void setHeading(double value) {
        lastHeading = heading;
        heading = value;
    }
    
    /**
     * Alters the enemy heading by delta
     * @param delta
     */
    public void changeHeading(double delta) {
        lastHeading = heading;
        heading += delta;
    }
    
    /**
     * Returns the heading of this robot
     */
    public double getHeading() {
        return heading;
    }
    
    /**
     * Returns the last heading
     */
    public double getLastHeading() {
        return lastHeading;
    }
    
    /**
     * Sets the enemy bearing to the given value
     */
    public void setBearing(double value) {
        lastBearing = bearing;
        bearing = value;
    }
    
    /**
     * Alters the enemy bearing by delta
     * @param delta
     */
    public void changeBearing(double delta) {
        lastBearing = bearing;
        bearing += delta;
    }
    
    /**
     * Returns the bearing of this robot
     */
    public double getBearing() {
        return bearing;
    }
    
    /**
     * Returns the last bearing
     */
    public double getLastBearing() {
        return lastBearing;
    }
    
    /**
     * Sets the enemy velocity to the given value
     */
    public void setVelocity(double value) {
        lastVelocity = velocity;
        velocity = value;
    }
    
    /**
     * Alters the velocity by delta
     * @param delta
     */
    public void changeVelocity(double delta) {
        lastVelocity = velocity;
        velocity += delta;
    }
    
    /**
     * Returns the current velocity
     */
    public double getVelocity() {
        return velocity;
    }
    
    /**
     * Returns the last velocity
     */
    public double getLastVelocity() {
        return lastVelocity;
    }
    
    /**
     * Sets the enemy bearing to the given value
     */
    public void setAbsoluteBearing(double value) {
        lastAbsoluteBearing = absoluteBearing;
        absoluteBearing = value;
    }
    
    /**
     * Alters the enemy bearing by delta
     * @param delta
     */
    public void changeAbsoluteBearing(double delta) {
        lastAbsoluteBearing = absoluteBearing;
        absoluteBearing += delta;
    }
    
    /**
     * Returns the bearing of this robot
     */
    public double getAbsoluteBearing() {
        return absoluteBearing;
    }
    
    /**
     * Returns the last bearing of this robot
     */
    public double getLastAbsoluteBearing() {
        return lastAbsoluteBearing;
    }
    
    /**
     * sets the energy to the specified value and determines if a shot
     * has been fired
     */
    public void setEnergy(double value) {
        double power = energy - value;
        energy = value;
        if(power < 3.01 && power > 0.09) {
            hasFired = true;
            bulletPower = power;
            bulletVelocity = Helper.bulletVelocity(bulletPower);
        }
    }
    
    /**
     * Adjusts the energy by delta
     */
    public void changeEnergy(double delta) {
        energy += delta;
    }
    
    /**
     * returns the current energy
     */
    public double getEnergy() {
        return energy;
    }
    
    /**
     * Sets the location of the enemy robot
     */
    public void setLocation(Point2D.Double point) {
        lastLocation = location;
        location = (Point2D.Double) point.clone();
        if(lastLocation.distance(location) > 1)
            lastMoveTime = reference.getTime();
    }
    
    /**
     * Returns a copy of the current location
     */
    public Point2D.Double getLocation() {
        return (Point2D.Double) location.clone();
    }
    
    /**
     * Returns a copy of the last location
     */
    public Point2D.Double getLastLocation() {
        return (Point2D.Double) location.clone();
    }
    
    /**
     * Returns the appropriate segment in the guessFactors array
     */
    public double[] getGuessFactorArray() {
        return guessFactors[Helper.getDistanceSegment(distance)]
                           [Helper.getVelocitySegment(velocity)]
                           [Helper.getTimeSegment(reference.getTime() - lastMoveTime)]
                           [Helper.getHeadingSegment(heading)]
                           [Helper.getBearingSegment(absoluteBearing)];
    }
    
    /**
     * Returns the appropriate reading value
     */
    public int[] getCurrentReadingArray() {
        return readings[Helper.getDistanceSegment(distance)]
                       [Helper.getVelocitySegment(velocity)]
                       [Helper.getTimeSegment(reference.getTime() - lastMoveTime)]
                       [Helper.getHeadingSegment(heading)];
    }
    
    public int getCurrentReadingIndex() {
        return Helper.getBearingSegment(absoluteBearing);
    }
    
    /**
     * Returns the time since the last location change
     */
    public long getTimeSinceLastMove() {
        return reference.getTime() - lastMoveTime;
    }
    
    /**
     * Resets the robot to starting conditions minus the stats and guessFactor arrays
     */
    public void reset(Tycho reference) {
        this.reference = reference;
        heading =0;
        lastHeading = heading;
        bearing = 0;
        lastBearing = bearing;
        absoluteBearing = 0;
        lastAbsoluteBearing = absoluteBearing;
        
        distance = 0;
        
        location = Helper.project(reference.getLocation(), absoluteBearing, distance);
        lastLocation = location;
        
        energy = 0;
        hasFired = false;
        
        velocity = 0;
        lastVelocity = velocity; 
        
        lastMoveTime = 0;
        
        waves.clear();
        incomingBullets.clear();
        surfDirections.clear();
        surfBearings.clear();
    }
    
    /**
     * Prints out information about the robot
     */
    public String toString() {
        return ("------------------------------" + 
                "\nName: " + name + 
                "\nLocation: " + location.toString() + 
                "\nEnergy: " + energy + 
                "\nLast known velocity: " + lastVelocity);
    }
}

