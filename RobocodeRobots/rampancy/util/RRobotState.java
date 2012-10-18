/**
 * RRobotState.java
 */
package rampancy.util;

import java.util.Comparator;

import rampancy.RampantRobot;
import rampancy.util.data.RSegmentFunction;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RRobotState {

    public RPoint location;
    public double absoluteBearing;
    public double velocity;
    public double lateralVelocity;
    public double advancingVelocity;
    public double deltaV;
    public double heading;
    public double deltaH;
    public double distance;
    public double distanceFromWall;
    public int    distanceFromWallCategory;
    public double timeSinceVelocityChange;
    public double timeSinceDirectionChange;
    public double timeSinceStop;
    public double energy;
    public double gunHeat;
    public boolean accelerating;
    public boolean breaking;
    public int directionTraveling;
    
    /**
     * Default constructor
     */
    public RRobotState() {}
    
    /**
     * Constructor for our states
     * @param robot
     */
    public RRobotState(RampantRobot robot, ScannedRobotEvent e) {
        RRobotState lastState = robot.getCurrentState();
        double enemyAbsoluteBearing = e == null ? 0 : Utils.normalAbsoluteAngle(robot.getHeadingRadians() + e.getBearingRadians());
        double enemyDistance = e == null ? 100 : e.getDistance();
        
        this.location                 = new RPoint(robot.getX(), robot.getY());
        RPoint enemyLocation = RUtil.project(location, enemyAbsoluteBearing, enemyDistance);
        
        this.absoluteBearing          = RUtil.computeAbsoluteBearing(enemyLocation, location);
        this.heading                  = robot.getHeadingRadians();
        this.velocity                 = robot.getVelocity();
        this.lateralVelocity          = velocity * Math.sin(heading - absoluteBearing);
        this.advancingVelocity        = 0;
        this.deltaV                   = lastState == null ? 0 : velocity - lastState.velocity;
        this.accelerating             = deltaV > 0;
        this.breaking                 = !accelerating;
        this.deltaH                   = lastState == null ? 0 : heading - lastState.heading;
        this.distance                 = 0;
        this.distanceFromWall         = RUtil.getDistanceFromWall(location);
        this.distanceFromWallCategory = RampantRobot.getGlobalBattlefield().distanceFromWallCategory(location);
        this.timeSinceVelocityChange  = deltaV != 0 || lastState == null ? 0 : lastState.timeSinceVelocityChange + 1;
        this.directionTraveling       = lateralVelocity >= 0 ? 1 : -1;
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
    public RRobotState(REnemyRobot robot, ScannedRobotEvent e) {
        RampantRobot reference = robot.getReference();
        if(reference == null) {
            return;
        } else {
            RRobotState lastState = robot.getCurrentState();
            
            this.heading                  = e.getHeadingRadians();
            this.absoluteBearing          = Utils.normalAbsoluteAngle(reference.getHeadingRadians() + e.getBearingRadians());
            this.location                 = RUtil.project(reference.getLocation(), absoluteBearing, e.getDistance());
            this.velocity                 = e.getVelocity();
            this.lateralVelocity          = velocity * Math.sin(heading - absoluteBearing);
            this.advancingVelocity        = velocity * -1 * Math.cos(heading - absoluteBearing);
            this.deltaV                   = lastState == null ? 0 : velocity - lastState.velocity;
            this.accelerating             = deltaV > 0;
            this.breaking                 = !accelerating;
            this.deltaH                   = lastState == null ? 0 : heading - lastState.heading;
            this.distance                 = e.getDistance();
            this.distanceFromWall         = RUtil.getDistanceFromWall(location);
            this.distanceFromWallCategory = RampantRobot.getGlobalBattlefield().distanceFromWallCategory(location);
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
    public RRobotState(RPoint location, double absoluteBearing, 
            double velocity, double lateralVelocity, double advancingVelocity,
            double deltaV, double heading, double deltaH, double distance, 
            double distanceFromWall, double timeSinceVelocityChange, 
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
        this.distanceFromWall         = distanceFromWall;
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
    public RRobotState(RRobotState state) {
        this(state.location,
             state.absoluteBearing,
             state.velocity,
             state.lateralVelocity,
             state.advancingVelocity,
             state.deltaV,
             state.heading,
             state.deltaH,
             state.distance,
             state.distanceFromWall,
             state.timeSinceVelocityChange,
             state.timeSinceDirectionChange,
             state.timeSinceStop,
             state.energy,
             state.directionTraveling);
    }
    
    /**
     * @return a copy of the current state
     */
    public RRobotState getCopy() {
        return new RRobotState(this);
    }
    
    
    
    // ------------- Distance Functions ------------- //
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(absoluteBearing);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (accelerating ? 1231 : 1237);
        temp = Double.doubleToLongBits(advancingVelocity);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (breaking ? 1231 : 1237);
        temp = Double.doubleToLongBits(deltaH);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(deltaV);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + directionTraveling;
        temp = Double.doubleToLongBits(distance);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(distanceFromWall);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + distanceFromWallCategory;
        temp = Double.doubleToLongBits(energy);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(gunHeat);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(heading);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lateralVelocity);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result
                + ((location == null) ? 0 : location.hashCode());
        temp = Double.doubleToLongBits(timeSinceDirectionChange);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(timeSinceStop);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(timeSinceVelocityChange);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(velocity);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RRobotState other = (RRobotState) obj;
        if (Double.doubleToLongBits(absoluteBearing) != Double
                .doubleToLongBits(other.absoluteBearing))
            return false;
        if (accelerating != other.accelerating)
            return false;
        if (Double.doubleToLongBits(advancingVelocity) != Double
                .doubleToLongBits(other.advancingVelocity))
            return false;
        if (breaking != other.breaking)
            return false;
        if (Double.doubleToLongBits(deltaH) != Double
                .doubleToLongBits(other.deltaH))
            return false;
        if (Double.doubleToLongBits(deltaV) != Double
                .doubleToLongBits(other.deltaV))
            return false;
        if (directionTraveling != other.directionTraveling)
            return false;
        if (Double.doubleToLongBits(distance) != Double
                .doubleToLongBits(other.distance))
            return false;
        if (Double.doubleToLongBits(distanceFromWall) != Double
                .doubleToLongBits(other.distanceFromWall))
            return false;
        if (distanceFromWallCategory != other.distanceFromWallCategory)
            return false;
        if (Double.doubleToLongBits(energy) != Double
                .doubleToLongBits(other.energy))
            return false;
        if (Double.doubleToLongBits(gunHeat) != Double
                .doubleToLongBits(other.gunHeat))
            return false;
        if (Double.doubleToLongBits(heading) != Double
                .doubleToLongBits(other.heading))
            return false;
        if (Double.doubleToLongBits(lateralVelocity) != Double
                .doubleToLongBits(other.lateralVelocity))
            return false;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (Double.doubleToLongBits(timeSinceDirectionChange) != Double
                .doubleToLongBits(other.timeSinceDirectionChange))
            return false;
        if (Double.doubleToLongBits(timeSinceStop) != Double
                .doubleToLongBits(other.timeSinceStop))
            return false;
        if (Double.doubleToLongBits(timeSinceVelocityChange) != Double
                .doubleToLongBits(other.timeSinceVelocityChange))
            return false;
        if (Double.doubleToLongBits(velocity) != Double
                .doubleToLongBits(other.velocity))
            return false;
        return true;
    }



    public static final RDistanceFunction<RRobotState> DELTA_H_DISTANCE_FUNCTION = new RDistanceFunction<RRobotState>() {
        public double distance(RRobotState o1, RRobotState o2) {
            return 0;
        }     
    };
    
    
    // ------------- Segment Functions -------------- //
    public static final RSegmentFunction DISTANCE_SEGMENT_FUNCTION = new RSegmentFunction() {
        public int getIndexForState(RRobotState state, int segmentSize) {
            return (int) RUtil.scaleToRange(0, segmentSize - 1, 0, 1200, state.distance);
        }
    };
    
    public static final RSegmentFunction DISTANCE_FROM_WALL_SEGMENT_FUNCTION = new RSegmentFunction() {
        public int getIndexForState(RRobotState state, int segmentSize) {
            return (int) RampantRobot.getGlobalBattlefield().distanceFromWallCategory(state.location);
        }
    };
    
    public static final RSegmentFunction VELOCITY_SEGMENT_FUNCTION = new RSegmentFunction() {
        public int getIndexForState(RRobotState state, int segmentSize) {
            return (int) RUtil.scaleToRange(0, segmentSize - 1, -8, 8, state.velocity, segmentSize / 2);
        }
    };
    
    public static final RSegmentFunction LATERAL_VELOCITY_SEGMENT_FUNCTION = new RSegmentFunction() {
        public int getIndexForState(RRobotState state, int segmentSize) {
            return (int) RUtil.scaleToRange(0, segmentSize - 1, -8, 8, state.lateralVelocity, segmentSize / 2);
        }
    };
    
    public static final RSegmentFunction ADVANCING_VELOCITY_SEGMENT_FUNCTION = new RSegmentFunction() {
        public int getIndexForState(RRobotState state, int segmentSize) {
            return (int) RUtil.scaleToRange(0, segmentSize - 1, -8, 8, state.advancingVelocity, segmentSize / 2);
        }
    };
    
    public static final RSegmentFunction DELTA_V_SEGMENT_FUNCTION = new RSegmentFunction() {
        public int getIndexForState(RRobotState state, int segmentSize) {
            return (int) RUtil.scaleToRange(0, segmentSize - 1, -8, 8, state.deltaV, segmentSize / 2);
        }
    };
    
    public static final RSegmentFunction ABSOLUTE_BEARING_SEGMENT_FUNCTION = new RSegmentFunction() {
        public int getIndexForState(RRobotState state, int segmentSize) {
            return (int) RUtil.scaleToRange(0, segmentSize - 1, 0, Math.PI * 2, state.absoluteBearing);
        }
    };
    
    public static final RSegmentFunction HEADNG_SEGMENT_FUNCTION = new RSegmentFunction() {
        public int getIndexForState(RRobotState state, int segmentSize) {
            return (int) RUtil.scaleToRange(0, segmentSize - 1, 0, Math.PI * 2, state.heading);
        }
    };
    
    public static final RSegmentFunction DELTA_H_SEGMENT_FUNCTION = new RSegmentFunction() {
        public int getIndexForState(RRobotState state, int segmentSize) {
            return (int) RUtil.scaleToRange(0, segmentSize - 1, 0, Math.PI, state.deltaH, segmentSize / 2);
        }
    };
    
    public static final RSegmentFunction TIME_SINCE_VELOCITY_CHANGE_SEGMENT_FUNCTION = new RSegmentFunction() {
        public int getIndexForState(RRobotState state, int segmentSize) {
            return (int) RUtil.scaleToRange(0, segmentSize - 1, 0, 1000, state.timeSinceVelocityChange);
        }
    };
    
    public static final RSegmentFunction TIME_SINCE_DIRECTION_CHANGE_SEGMENT_FUNCTION = new RSegmentFunction() {
        public int getIndexForState(RRobotState state, int segmentSize) {
            return (int) RUtil.scaleToRange(0, segmentSize - 1, 0, 1000, state.timeSinceDirectionChange);
        }
    };
    
    public static final RSegmentFunction TIME_SINCE_STOP_SEGMENT_FUCNTION = new RSegmentFunction() {
        public int getIndexForState(RRobotState state, int segmentSize) {
            return (int) RUtil.scaleToRange(0, segmentSize - 1, 0, 1000, state.timeSinceStop);
        }
    };
    
    public static final RSegmentFunction ACCELERATING_OR_BREAKING_SEGMENT_FUNCTION = new RSegmentFunction() {
        public int getIndexForState(RRobotState state, int segmentSize) {
            if(state.breaking) return 0;
            if(state.accelerating) return 1;
            return 2;
        }
    };
    
    public static final RSegmentFunction DIRECTION_TRAVELING_SEGMENT_FUNCTION = new RSegmentFunction() {
        public int getIndexForState(RRobotState state, int segmentSize) {
            return 1 + state.directionTraveling;
        }
    };

}
