/**
 * The superclass for all EPGY Robots
 */
package epgy.util;


import java.awt.Graphics2D;
import java.util.*;

import epgy.util.*;

import robocode.*;
import robocode.util.*;

/**
 * EPGYBot is the superclass for all of your robots.
 * @author Matthew Chun-Lum
 *
 */
public abstract class EPGYBot extends AdvancedRobot {

    public static final double MAX_RADAR_TRACKING_AMOUNT = Math.PI / 4.0;
    public static final int MAX_HISTORY = 100;

    protected static ArrayList<EPGYRobotState> states;
    protected static HashMap<String, EPGYEnemyRobot> enemies;
    protected String eLastScannedBot;

    private boolean disableGunCorrection;
    private boolean requestedShoot;
    private double requestedPower;
    private long fireTime;

    // abstract methods, must be implemented in subclass

    /**
     * The setup method is invoked at the beginning of every round.
     * This allows you to set certain variables at the beginning of
     * every round.
     */
    public abstract void setup();

    /**
     * This method is invoked every time an enemy robot is scanned. You probably
     * will not be able to fire every time a robot is scanned due to gun
     * cool-down, but you can still update the gun on every tick. It is important
     * to realize that a shot is fired on the NEXT tick, so you may have to 
     * make special adjustments to compensate
     * @param enemyName the name of the enemy robot from the current scan
     */
    public abstract void doGun(String enemyName);

    /**
     * This method is invoked every time an enemy robot is scanned. I've taken care
     * of spinning the radar for you, but if you want to use more complex
     * behavior (like a focusing algorithm), you can put that here. It will
     * override the default spinning behavior.
     * The philosophy in Robocode is that we react to scanning the enemy robot,
     * so the more frequently we can scan another robot, the better/more
     * accurate our data will be.
     * @param enemyName the name of the enemy robot from the current scan
     */
    public abstract void doRadar(String enemyName);

    /**
     * This method is invoked every time an enemy robot is scanned. Here is
     * where you should handle your robot's movement. Movement can be static
     * or dynamic: you can have a fixed moving style, or react dynamically
     * to another robot.
     * @param enemyName the name of the enemy robot from the current scan
     */
    public abstract void doMovement(String enemyName);

    // public
    public void run() {
        initialSetup();

        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);


        while(getRadarTurnRemainingRadians() == 0) {
            turnRadarRightRadians(MAX_RADAR_TRACKING_AMOUNT * 5);

        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        // update information about ourself
        recordState();

        // update information about the enemy robot
        eLastScannedBot = e.getName();
        if(!enemies.containsKey(e.getName()))
            enemies.put(e.getName(), new EPGYEnemyRobot(e.getName(), this));
        EPGYEnemyRobot enemy = enemies.get(e.getName());
        enemy.update(e);

        // check to fire guns
        if(!disableGunCorrection && requestedShoot && getTime() >= fireTime && getGunTurnRemaining() == 0) {
            setFire(requestedPower);
            requestedShoot = false;
        }

        // do user-specified actions
        doRadar(eLastScannedBot);
        doMovement(eLastScannedBot);
        doGun(eLastScannedBot);
    }

    /**
     * Return the current state of your robot. The data structure
     * returned is an {@link EPGYRobotState}. If there are no
     * states, it returns {@code null}
     * @return the current state of your EPGYBot
     */
    public EPGYRobotState getCurrentState() {
        if(states.isEmpty())
            return null;
        return states.get(0);
    }

    /**
     * Returns the current location of your EPGYBot in the form
     * of an {@link EPGYPoint}
     * @return the current location of your EPGYBot
     */
    public EPGYPoint getLocation() {
        EPGYRobotState state = getCurrentState();
        if(state == null)
            state = recordState();
        return state.location;
    }

    /**
     * Return the width of the battlefield in pixels
     * @return the width of the battlefield in pixels
     */
    public double getWidth() {
        return getBattleFieldWidth();
    }

    /**
     * Return the height of the battlefield in pixels
     * @return the height of the battlefield in pixels
     */
    public double getHeight() {
        return getBattleFieldHeight();
    }
    
    // bearing computation
    
    /**
     * Computes the bearing in degrees to the specified point
     * @param point the desired destination point
     * @return the bearing in degrees to the specified point
     */
    public double computeBearingToPoint(EPGYPoint point) {
        return Math.toDegrees(computeBearingToPointRadians(point));
    }
    
    /**
     * Computes the bearing in radians to the specified point
     * @param point the desired destination point
     * @return the bearing in radians to the specified point
     */
    public double computeBearingToPointRadians(EPGYPoint point) {
        return Utils.normalRelativeAngle(EPGYUtil.computeAbsoluteBearing(getLocation(), point) - getHeadingRadians());
    }

    // movement control

    /**
     * Sets the robot to move the specified distance.
     * A positive number will move the robot forward,
     * and a negative number will move the robot backward.
     * @param distance the distance you want to move.
     * A negative distance moves the robot backwards.
     */
    public void setMove(double distance) {
        setAhead(distance);
    }

    /**
     * Returns {@code true} if you are done with the current move
     * @return {@code true} if you are done with the current move
     */
    public boolean doneWithMove() {
        return (getDistanceRemaining() == 0);
    }

    /**
     * Sets the desired turn to the passed angle in degrees. 
     * A positive number will turn to the right, and a negative
     * number will turn to the left.
     * @param angle the angle to turn. A positive number
     * will turn the robot to the right. A negative
     * number will turn the robot to the left.
     */
    public void setTurn(double angle) {
        setTurnRight(angle);
    }

    /**
     * Sets the desired turn to the passed angle in radians. 
     * A positive number will turn to the right, and a negative
     * number will turn to the left.
     * @param angle the angle to turn. A positive number
     * will turn the robot to the right. A negative
     * number will turn the robot to the left.
     */
    public void setTurnRadians(double angle) {
        setTurnRightRadians(angle);
    }

    /**
     * Returns {@code true} if you are done with the current turn
     * @return {@code true} if you are done with the current turn
     */
    public boolean doneWithTurn() {
        return (getTurnRemaining() == 0);
    }

    // gun control

    /**
     * Returns {@code true} if the gun is cool enough to shoot
     * @return {@code true} if the gun is cool enough to shoot
     */
    public boolean canShoot() {
        return (getGunHeat() - getGunCoolingRate() <= 0);
    }

    /**
     * Will turn the gun and shoot on the next tick
     * The minimum power is 0.1 and the maximum is 3.0.
     * The angle is the angle to turn the gun in degrees
     * @param angle the angle to turn the gun in degrees
     * @param power the power of the bullet
     */
    public void shootWithAngleAndPower(double angle, double power) {
        shootWithAngleAndPowerRadians(Math.toRadians(angle), power);
    }

    /**
     * Will turn the gun and shoot on the next tick
     * The minimum power is 0.1 and the maximum is 3.0.
     * The angle is the angle to turn the gun in radians
     * @param angle the angle to turn the gun in radians
     * @param power the power of the bullet
     */
    public void shootWithAngleAndPowerRadians(double angle, double power) {
        angle = Utils.normalRelativeAngle(angle);
        setTurnGunRightRadians(angle);
        if(disableGunCorrection) {
            setTurnGunRightRadians(angle);
            setFire(power);
        } else {
            requestedShoot = true;
            requestedPower = power;
            fireTime = getTime() + 1;
        }

    }

    /**
     * Set whether the shootWithAngleAndPower methods will
     * compensate for the fact that shots happen before the
     * gun turns. {@code true} disables the gun correction,
     * {@code false} enables the gun correction.
     * @param disableGunCorrection
     */
    public void setDisableGunCorrection(boolean disableGunCorrection) {
        this.disableGunCorrection = disableGunCorrection;
    }

    // Enemy Information

    /**
     * Returns the x coordinate of the last scanned enemy
     * @return the x coordinate of the last scanned enemy
     */
    public double getScannedEnemyX() {
        return getEnemyX(eLastScannedBot);
    }

    /**
     * Returns the last known x coordinate of the specified enemy
     * @param name the name of the enemy robot
     * @return the last known x coordinate of the specified enemy
     */
    public double getEnemyX(String name) {
        return getEnemyState(name).location.x;
    }

    /**
     * Returns the y coordinate of the last scanned enemy
     * @return the y coordinate of the last scanned enemy
     */
    public double getScannedEnemyY() {
        return getEnemyY(eLastScannedBot);
    }

    /**
     * Returns the last known y coordinate of the specified enemy
     * @param name the name of the enemy robot
     * @return the last known y coordinate of the specified enemy
     */
    public double getEnemyY(String name) {
        return getEnemyState(name).location.y;
    }

    /**
     * Returns the location of the scanned enemy robot
     * @return the location of the scanned enemy robot
     */
    public EPGYPoint getScannedEnemyLocation() {
        return getEnemyLocation(eLastScannedBot);
    }

    /**
     * Returns the last known location of the specified enemy robot
     * @param name the name of the enemy robot
     * @return the last known location of the specified enemy robot
     */
    public EPGYPoint getEnemyLocation(String name) {
        return getEnemyState(name).location;
    }

    /**
     * Returns the distance to the last scanned enemy
     * @return the distance to the last scanned enemy
     */
    public double getScannedEnemyDistance() {
        return getEnemyDistance(eLastScannedBot);
    }

    /**
     * Returns the last known distance to the specified enemy
     * @param name the name of the enemy robot
     * @return the last known distance to the specified enemy
     */
    public double getEnemyDistance(String name) {
        return getEnemyState(name).distance;
    }

    /**
     * Returns the velocity of the scanned enemy robot
     * @return the velocity of the last scanned enemy robot
     */
    public double getScannedEnemyVelocity() {
        return getEnemyVelocity(eLastScannedBot);
    }

    /**
     * Returns the last known velocity of the specified enemy
     * @param name the name of the enemy robot
     * @return the last known velocity of the specified enemy
     */
    public double getEnemyVelocity(String name) {
        return getEnemyState(name).velocity;
    }

    /**
     * Returns the heading of the last scanned enemy in degrees
     * @return the heading of the last scanned enemy in degrees
     */
    public double getScannedEnemyHeading() {
        return Math.toDegrees(getScannedEnemyHeadingRadians());
    }

    /**
     * Returns the heading in radians of the last scanned enemy
     * @return the heading in radians of the last scanned enemy
     */
    public double getScannedEnemyHeadingRadians() {
        return getEnemyHeadingRadians(eLastScannedBot);
    }

    /**
     * Returns the last known heading of the specified enemy in degrees
     * @param name the name of the enemy robot
     * @return the last known heading of the specified enemy in degrees
     */
    public double getEnemyHeading(String name) {
        return Math.toDegrees(getEnemyHeadingRadians(name));
    }

    /**
     * Return the last known heading of the specified enemy in radians
     * @param name the name of the enemy robot
     * @return the last known heading of the specified enemy in radians
     */
    public double getEnemyHeadingRadians(String name) {
        return getEnemyState(name).heading;
    }

    /**
     * Return the bearing to the enemy robot in degrees  (from your robot's current heading)
     * @return the bearing to the enemy robot in degrees (from your robot's current heading).
     * You can think of the bearing as the amount you need to turn to be facing the enemy robot.
     */
    public double getScannedEnemyBearing() {
        return Math.toDegrees(getScannedEnemyBearingRadians());
    }

    /**
     * Return the bearing to the enemy robot in radians (from your robot's current heading)
     * @return the bearing to the enemy robot in radians (from your robot's current heading).
     * You can think of the bearing as the amount you need to turn to be facing the enemy robot.
     */
    public double getScannedEnemyBearingRadians() {
        return getEnemyBearingRadians(eLastScannedBot);
    }

    /**
     * Return the last known bearing to the specified enemy robot in degrees
     * @param name the name of the enemy robot
     * @return the last known bearing to the enemy robot in degrees (from your robot's current heading).
     * You can think of the bearing as the amount you need to turn to be facing the enemy robot.
     */
    public double getEnemyBearing(String name) {
        return Math.toDegrees(getEnemyBearingRadians(name));
    }

    /**
     * Return the last known bearing to the specified enemy robot in radians
     * @param name the name of the enemy robot
     * @return the last known bearing to the enemy robot in radians (from your robot's current heading)
     * You can think of the bearing as the amount you need to turn to be facing the enemy robot.
     */
    public double getEnemyBearingRadians(String name) {
        return Utils.normalRelativeAngle(getEnemyState(name).absoluteBearing - getHeadingRadians());
    }

    /**
     * Return the absolute bearing to the last scanned enemy robot in degrees
     * @return the absolute bearing to the last scanned enemy robot in degrees.
     * The absolute bearing is the amount your robot needs to rotate from 0 to
     * face the enemy robot
     */
    public double getScannedEnemyAbsoluteBearing() {
        return Math.toDegrees(getScannedEnemyAbsoluteBearingRadians());
    }

    /**
     * Return the absolute bearing to the last scanned enemy robot in radians
     * @return the absolute bearing to the last scanned enemy robot in radians.
     * The absolute bearing is the amount your robot needs to rotate from 0 to
     * face the enemy robot
     */
    public double getScannedEnemyAbsoluteBearingRadians() {
        return getEnemyAbsoluteBearingRadians(eLastScannedBot);
    }

    /**
     * Return the last known absolute bearing to the specified enemy robot in degrees
     * @param name the name of the enemy robot
     * @return the last known absolute bearing to the specified enemy robot in degrees.
     * The absolute bearing is the amount your robot needs to rotate from 0 to
     * face the enemy robot
     */
    public double getEnemyAbsoluteBearing(String name) {
        return Math.toDegrees(getEnemyAbsoluteBearingRadians(name));
    }

    /**
     * Return the last known absolute bearing to the specified enmy robot in radians
     * @param name the name of the enemy robot
     * @return the last known absolute bearing to the last scanned enemy robot in radians.
     * The absolute bearing is the amount your robot needs to rotate from 0 to
     * face the enemy robot
     */
    public double getEnemyAbsoluteBearingRadians(String name) {
        return getEnemyState(name).absoluteBearing;
    }

    /**
     * Return the gun bearing to the scanned enemy robot in degrees
     * @return the gun bearing to the scanned enemy robot in degrees
     * This is the amount you have to turn your gun to be facing directly at the
     * enemy robot.
     */
    public double getScannedGunBearingToEnemy() {
        return Math.toDegrees(getScannedGunBearingToEnemyRadians());
    }

    /**
     * Return the gun bearing to the scanned enemy robot in radians
     * @return the gun bearing to the scanned enemy robot in radians
     * This is the amount you have to turn your gun to be facing directly at the
     * enemy robot.
     */
    public double getScannedGunBearingToEnemyRadians() {
        return getGunBearingToEnemyRadians(eLastScannedBot);
    }

    /**
     * Return the last known gun bearing to the specified enemy robot in degrees
     * @param name the name of the enemy robot
     * @return the last known gun bearing to the specified enemy robot in degrees
     * This is the amount you have to turn your gun to be facing directly at the
     * enemy robot.
     */
    public double getGunBearingToEnemy(String name) {
        return Math.toDegrees(getGunBearingToEnemyRadians(name));
    }

    /**
     * Return the last known gun bearing to the specified enemy robot in radians
     * @param name the name of the enemy robot
     * @return the last known gun bearing to the specified enemy robot in radians.
     * This is the amount you have to turn your gun to be facing directly at the
     * enemy robot.
     */
    public double getGunBearingToEnemyRadians(String name) {
        return Utils.normalRelativeAngle(getEnemyAbsoluteBearingRadians(name) - getGunHeadingRadians());
    }

    /**
     * @return the energy of the last scanned enemy robot
     */
    public double getScannedEnemyEnergy() {
        return getEnemyEnergy(eLastScannedBot);
    }

    /**
     * @param name the name of the enemy robot
     * @return the last known energy of the specified enemy robot
     */
    public double getEnemyEnergy(String name) {
        return getEnemyState(name).energy;
    }


    // print wrappers

    /**
     * Prints the passed string in the console and starts a new line
     * Wrapper for System.out.println();
     * @param str
     */
    public void println(String str) {
        System.out.println(str);
    }

    /**
     * Prints the passed string to the console
     * Wrapper for System.out.print();
     * @param str
     */
    public void print(String str) {
        System.out.print(str);
    }

    public void onPaint(Graphics2D g) {
        // Do nothing, for now
    }

    // protected
    protected EPGYRobotState recordState() {
        states.add(0, new EPGYRobotState(this));
        if(states.size() > MAX_HISTORY)
            states.remove(states.size() - 1);

        return states.get(0);
    }

    protected EPGYEnemyRobot getEnemy(String name) {
        return enemies.get(name);
    }

    protected EPGYRobotState getEnemyState(String name) {
        return getEnemy(name).getCurrentState();
    }

    // private
    private void initialSetup() {
        if(states == null)
            states = new ArrayList<EPGYRobotState>();

        if(enemies == null)
            enemies = new HashMap<String, EPGYEnemyRobot>();

        setup();
    }
}
