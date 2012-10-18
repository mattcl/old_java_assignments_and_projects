/**
 * Segments.java
 */
package rampancy_old.util.tree;

import rampancy_old.management.MovementManager;
import rampancy_old.util.Constants;
import rampancy_old.util.EnemyRobot;
import rampancy_old.util.EnemyState;
import rampancy_old.util.Util;

/**
 * This class contains the Segment constants and helper functions
 * @author Matthew Chun-Lum
 *
 */
public abstract class Segments {
    
    public static final int DEFAULT_VISITS_BEFORE_BRANCH = 20;
    
    // -------------- Segment Lengths ------------- //
    
    public static final byte NUM_VELOCITY_SEGMENTS           = 7;
    public static final byte NUM_DELTA_VELOCITY_SEGMENTS     = 5;
    public static final byte NUM_DISTANCE_SEGMENTS           = 7;
    public static final byte NUM_LATERAL_VELOCITY_SEGMENTS   = 7;
    public static final byte NUM_ADVANCING_VELOCITY_SEGMENTS = 7;
    public static final byte NUM_MOVE_TIMES_SEGMENTS         = 7;
    public static final byte NUM_HEADING_SEGMENTS            = 7;
    public static final byte NUM_DELTA_HEADING_SEGMENTS      = 5;
    public static final byte DISTANCE_FROM_WALL_SEGMENTS     = 6;
    public static final byte NUM_TSLVC_SEGMENTS              = 11;
    
    public static final byte NUM_ACCELERATION_SEGMENTS       = 3;
    public static final byte NEAR_WALL_SEGMENTS              = 3;

    public static final byte[] BRANCH_COUNTS_BY_TYPE = {
                                NUM_DISTANCE_SEGMENTS,
                                NUM_VELOCITY_SEGMENTS,
                                NUM_LATERAL_VELOCITY_SEGMENTS,
                                NUM_ADVANCING_VELOCITY_SEGMENTS,
                                NUM_DELTA_VELOCITY_SEGMENTS,
                                NUM_HEADING_SEGMENTS,
                                NUM_DELTA_HEADING_SEGMENTS,
                                DISTANCE_FROM_WALL_SEGMENTS,
                                NUM_MOVE_TIMES_SEGMENTS,
                                NUM_TSLVC_SEGMENTS
                            };
    
    public static final byte NUM_BINS = 47;
    
    public static final long NUM_POSSIBLE_BRANCHES = NUM_DISTANCE_SEGMENTS *
                                                   NUM_LATERAL_VELOCITY_SEGMENTS *
                                                   NUM_HEADING_SEGMENTS *
                                                   NUM_DELTA_HEADING_SEGMENTS *
                                                   DISTANCE_FROM_WALL_SEGMENTS *
                                                   NUM_MOVE_TIMES_SEGMENTS *
                                                   NUM_VELOCITY_SEGMENTS *
                                                   NUM_DELTA_VELOCITY_SEGMENTS *
                                                   NUM_TSLVC_SEGMENTS * 
                                                   NUM_ADVANCING_VELOCITY_SEGMENTS;
    
    // -------------- Segment types -------------- //
    
    public static final byte NO_BRANCH_TYPE_SPECIFIED = -5;

    public static final byte DISTANCE                        = 0;
    public static final byte VELOCITY                        = 1;
    public static final byte LATERAL_VELOCITY                = 2;
    public static final byte ADVANCING_VELOCITY              = 3;
    public static final byte DELTA_VELOCITY                  = 4;
    public static final byte HEADING                         = 5;
    public static final byte DELTA_HEADING                   = 6;
    public static final byte DISTANCE_FROM_WALL              = 7;
    public static final byte MOVE_TIMES                      = 8;
    public static final byte TIME_SINCE_LAST_VELOCITY_CHANGE = 9;
    
    public static final byte TERMINAL_SEGMENT                = 100;
    
    public static final String[] DESCRIPTIONS = {
                                "Distance",
                                "Velocity",
                                "Lateral velocity",
                                "Advancing Velocity",
                                "Delta velocity",
                                "Heading",
                                "Delta heading",
                                "Distance from wall",
                                "Time since last stop",
                                "Time since last velocity change"
                            };
    
    
    // ----------- Memory consumption ---------- //
    public static final int ESTIMATED_SEGMENT_SIZE = 40 + 8 * (int) NUM_BINS; 
    public static final double WORST_CASE_MEMORY_CONSUMPTION = (NUM_POSSIBLE_BRANCHES * ESTIMATED_SEGMENT_SIZE) / (1024.0 * 1024.0 * 1024.0);
    
    
    // ------------ Static Methods ------------ //
    
    /**
     * @param type the type of the segment to create
     * @return a new segment of the specified type
     */
    public static Segment getNewSegmentOfType(byte type) {
        if(type == TERMINAL_SEGMENT)
            return new Segment(type, -1);
        return new Segment(type, BRANCH_COUNTS_BY_TYPE[type]);
    }
    
    public static int getBranchIndex(EnemyState enemyState, byte type) {
        switch(type) {
            case DISTANCE: return getDistanceBranchIndex(enemyState);
            case DISTANCE_FROM_WALL: return getDistanceFromWallBranchIndex(enemyState);
            case VELOCITY: return getVelocityBranchIndex(enemyState);
            case DELTA_VELOCITY: return getDeltaVelocityBranchIndex(enemyState);
            case LATERAL_VELOCITY: return getLateralVelocityBranchIndex(enemyState);
            case ADVANCING_VELOCITY: return getAdvancingVelocityBranchIndex(enemyState);
            case HEADING: return getHeadingBranchIndex(enemyState);
            case DELTA_HEADING: return getDeltaHeadingBranchIndex(enemyState);
            case MOVE_TIMES: return getTimeSinceStopBranchIndex(enemyState);
            case TIME_SINCE_LAST_VELOCITY_CHANGE: return getTimeSinceVelocityChangeBranchIndex(enemyState);
            default: return -1;
        }
    }
    
    public static byte getBestBranchType(byte[] availableSegments) {
        return availableSegments[0];
    }
    
    /**
     * Computes the appropriate index for the given segment and guess factor
     * @param segment
     * @param guessFactor
     * @return the index corresponding to the passed guess factor and segment
     */
    public static int computeGuessFactorIndex(Segment segment, double guessFactor) {
        return (int) Math.round((segment.guessFactors.length - 1) / 2.0 * (guessFactor + 1));
    }
    
    /**
     * @param type the type of the segment we want the description for
     * @return a description for the passed type
     */
    public static String getSegmentDescription(int type) {
        if(type < DESCRIPTIONS.length)
            return DESCRIPTIONS[type];
        return "ERROR, NO TYPE: " + type;
    }
    
    // ---------------- Segment specific helpers ---------------- //
    
    // ----------------- Distance related --------------- //
    
    /**
     * @param enemyState
     * @return the branch index using the distance method
     */
    public static int getDistanceBranchIndex(EnemyState enemyState) {
        return (int) Util.scaleToRange(0, NUM_DISTANCE_SEGMENTS - 1, 0, 12, enemyState.distance / 100);
    }
    
    /**
     * @param enemyState
     * @return the branch index using the distance from wall method
     */
    public static int getDistanceFromWallBranchIndex(EnemyState enemyState) {
        if(MovementManager.battlefield.isInCorner(enemyState.location))
            return 0;
        
        double distance = MovementManager.battlefield.distanceFromWall(enemyState.location);
        
        if(distance < 40)
            return 1;
        else if(distance < 60)
            return 2;
        else if(distance < 100)
            return 3;
        else if(distance < 150)
            return 4;
        else
            return 5;       
    }
    
    
    // ----------------- Velocity related --------------- //
    
    /**
     * @param enemyState
     * @return the branch index using the advancing velocity method
     */
    public static int getAdvancingVelocityBranchIndex(EnemyState enemyState) {
        double advancingVelocity = enemyState.velocity * -1 * Math.cos(enemyState.heading - enemyState.absoluteBearing);
        int half = (NUM_ADVANCING_VELOCITY_SEGMENTS - 1) / 2;
        return (int) Util.scaleToRange(-half, half, -8, 8, advancingVelocity) + half;
    }
    
    /**
     * @param enemyState
     * @return the branch index using the lateral velocity method
     */
    public static int getLateralVelocityBranchIndex(EnemyState enemyState) {
        double lateralVelocity = enemyState.velocity * Math.sin(enemyState.heading - enemyState.absoluteBearing);
        int half = (NUM_LATERAL_VELOCITY_SEGMENTS - 1) / 2;
        return (int) Util.scaleToRange(-half, half, -8, 8, lateralVelocity) + half;
    }
    
    /**
     * @param enemyState
     * @return the branch index using the delta velocity method
     */
    public static int getDeltaVelocityBranchIndex(EnemyState enemyState) {
        int half = (NUM_DELTA_VELOCITY_SEGMENTS - 1) / 2;
        return (int) Util.scaleToRange(-half, half, -8, 8, enemyState.velocity - enemyState.lastVelocity) + half;
    }
    
    /**
     * @param enemyState
     * @return the branch index using the time since last stop method
     */
    public static int getTimeSinceStopBranchIndex(EnemyState enemyState) {
        return (int) Util.scaleToRange(0, NUM_MOVE_TIMES_SEGMENTS - 1, 0, 50, enemyState.moveTimes);
    }
    
    /**
     * @param enemy
     * @return the branch index using the time since last velocity change method
     */
    public static int getTimeSinceVelocityChangeBranchIndex(EnemyState enemy) {
        return (int) Util.scaleToRange(0, NUM_TSLVC_SEGMENTS - 1, 0, 100, enemy.timeSinceVelocityChange);
    }
    
    /**
     * @param enemyState
     * @return the branch index using the velocity method
     */
    public static int getVelocityBranchIndex(EnemyState enemyState) {
        int half = (NUM_VELOCITY_SEGMENTS - 1) / 2;
        return (int) Util.scaleToRange(-half, half, -8, 8, enemyState.velocity) + half;
    }
    
    // --------------- Orientation related --------------- //
    
    /**
     * @param enemyState
     * @return the branch index using the heading method
     */
    public static int getHeadingBranchIndex(EnemyState enemyState) {
        return (int) (enemyState.heading / (Math.PI * 2) * (NUM_HEADING_SEGMENTS - 1));
    }
    
    /**
     * @param enemyState
     * @return the branch index using the delta heading method
     */
    public static int getDeltaHeadingBranchIndex(EnemyState enemyState) {
        double delta = (enemyState.heading - enemyState.lastHeading) * 100;
        int half = (NUM_DELTA_HEADING_SEGMENTS - 1) / 2;
        return (int) Util.scaleToRange(-half, half, -17, 17, delta) + half;
    }
}
