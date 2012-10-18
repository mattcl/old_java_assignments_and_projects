/**
 * RRobotState.java
 */
package epgy.util;

import java.util.Comparator;


import robocode.ScannedRobotEvent;
import robocode.util.Utils;

/**
 * This class represents the state of a robot at a given point in time. The EPGYRobotState is used mainly
 * to store past states, and can be useful in referencing a past state of the enemy.
 * @author Matthew Chun-Lum
 */
public class EPGYRobotState {

    /**
     * The location of the robot for this state
     */
    public EPGYPoint location;
    
    /**
     * The absolute bearing in radians to the robot for this state
     */
    public double absoluteBearing;
    
    /**
     * The velocity for the robot
     */
    public double velocity;
    
    /**
     * The lateral velocity is the the enemy velocity
     * that is perpendicular to the absolute bearing
     */
    public double lateralVelocity;
    
    /**
     * The advancing velocity is the enemy velocity
     * towards your direction
     */
    public double advancingVelocity;
    
    /**
     * The change in velocity from the last state
     */
    public double deltaV;
    
    /**
     * The heading in radians
     */
    public double heading;
    
    /**
     * The change in heading in radians
     */
    public double deltaH;
    
    /**
     * The distance from your robot for this state
     */
    public double distance;
    
    /**
     * The time in ticks since the robot changed velocity
     */
    public double timeSinceVelocityChange;
    
    /**
     * The time in ticks since the root changed directions
     */
    public double timeSinceDirectionChange;
    
    /**
     * The time in ticks since the robot stopped
     */
    public double timeSinceStop;
    
    /**
     * The energy of the robot
     */
    public double energy;
    
    /**
     * {@code true} if the robot is accelerating
     */
    public boolean accelerating;
    
    /**
     * {@code true} if the robot is breaking
     */
    public boolean breaking;
    
    /**
     * {@code 1} for clockwise and {@code -1} for counter clockwise relative to your position
     */
    public int directionTraveling;
    
    /**
     * Default constructor
     */
    public EPGYRobotState() {}
    
    /**
     * Constructor for our states
     * @param robot
     */
    public EPGYRobotState(EPGYBot robot) {
        EPGYRobotState lastState = robot.getCurrentState();
        
        this.location                 = new EPGYPoint(robot.getX(), robot.getY());
        this.absoluteBearing          = 0;
        this.velocity                 = robot.getVelocity();
        this.lateralVelocity          = 0;
        this.advancingVelocity        = 0;
        this.deltaV                   = lastState == null ? 0 : velocity - lastState.velocity;
        this.accelerating             = deltaV > 0;
        this.breaking                 = !accelerating;
        this.heading                  = robot.getHeadingRadians();
        this.deltaH                   = lastState == null ? 0 : heading - lastState.heading;
        this.distance                 = 0;
        this.timeSinceVelocityChange  = deltaV != 0 || lastState == null ? 0 : lastState.timeSinceVelocityChange + 1;
        this.directionTraveling       = velocity == 0 ? 0 : 
                                        Math.sin(heading - absoluteBearing) * velocity < 0 ? -1 :
                                        1;
        this.timeSinceDirectionChange = lastState == null || lastState.directionTraveling != directionTraveling ? 0 : 
                                        lastState.timeSinceDirectionChange + 1;
        this.timeSinceStop            = velocity == 0 || lastState == null ? 0 : lastState.timeSinceStop + 1;
        this.energy                   = robot.getEnergy();
    }
    
    /**
     * Constructor for enemy states
     * @param enemy
     * @param e
     */
    public EPGYRobotState(EPGYBot bot, EPGYEnemyRobot robot, ScannedRobotEvent e) {
        EPGYBot reference = bot;
        if(reference == null) {
            return;
        } else {
            EPGYRobotState lastState = robot.getCurrentState();
            
            this.absoluteBearing          = Utils.normalAbsoluteAngle(reference.getHeadingRadians() + e.getBearingRadians());
            this.location                 = EPGYUtil.project(reference.getLocation(), absoluteBearing, e.getDistance());
            this.velocity                 = e.getVelocity();
            this.heading                  = e.getHeadingRadians();
            this.lateralVelocity          = velocity * Math.sin(heading - absoluteBearing);
            this.advancingVelocity        = velocity * -1 * Math.cos(heading - absoluteBearing);
            this.deltaV                   = lastState == null ? 0 : velocity - lastState.velocity;
            this.accelerating             = deltaV > 0;
            this.breaking                 = !accelerating;
            this.deltaH                   = lastState == null ? 0 : heading - lastState.heading;
            this.distance                 = e.getDistance();
            this.timeSinceVelocityChange  = deltaV != 0 || lastState == null ? 0 : lastState.timeSinceVelocityChange + 1;
            this.directionTraveling       = lateralVelocity >= 0 ? 1 : -1;
            this.timeSinceDirectionChange = lastState == null || lastState.directionTraveling != directionTraveling ? 0 : 
                                            lastState.timeSinceDirectionChange + 1;
            this.timeSinceStop            = velocity == 0 || lastState == null ? 0 : lastState.timeSinceStop + 1;
            this.energy                   = e.getEnergy();
        }
    }
    
    /**
     * Standard constructor
     * @param location
     * @param absoluteBearing
     * @param velocity
     * @param lateralVelocity
     * @param advancingVelocity
     * @param deltaV
     * @param heading
     * @param deltaH
     * @param distance
     * @param distanceFromWall
     * @param timeSinceVelocityChange
     * @param timeSinceDirectionChange
     * @param timeSinceStop
     * @param energy
     * @param directionTraveling
     */
    public EPGYRobotState(EPGYPoint location, double absoluteBearing, 
            double velocity, double lateralVelocity, double advancingVelocity,
            double deltaV, double heading, double deltaH, double distance, 
            double timeSinceVelocityChange, 
            double timeSinceDirectionChange, double timeSinceStop, 
            double energy, int directionTraveling) {
        this.location                 = location.getCopy();
        this.absoluteBearing          = absoluteBearing;
        this.velocity                 = velocity;
        this.lateralVelocity          = lateralVelocity;
        this.advancingVelocity        = advancingVelocity;
        this.deltaV                   = deltaV;
        this.heading                  = heading;
        this.deltaH                   = deltaH;
        this.distance                 = distance;
        this.timeSinceVelocityChange  = timeSinceVelocityChange;
        this.timeSinceDirectionChange = timeSinceDirectionChange;
        this.timeSinceStop            = timeSinceStop;
        this.energy                   = energy;
        this.directionTraveling       = directionTraveling;
        this.accelerating             = deltaV > 0;
        this.breaking                 = deltaV < 0;
    }
    
    /**
     * Copy constructor
     * @param state
     */
    public EPGYRobotState(EPGYRobotState state) {
        this(state.location,
             state.absoluteBearing,
             state.velocity,
             state.lateralVelocity,
             state.advancingVelocity,
             state.deltaV,
             state.heading,
             state.deltaH,
             state.distance,
             state.timeSinceVelocityChange,
             state.timeSinceDirectionChange,
             state.timeSinceStop,
             state.energy,
             state.directionTraveling);
    }
    
    /**
     * Will return a copy of the passed state
     * @return a copy of the current state
     */
    public EPGYRobotState getCopy() {
        return new EPGYRobotState(this);
    }

}
