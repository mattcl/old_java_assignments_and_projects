/**
 * 
 */
package rampancy.standard;

import java.util.List;

import rampancy.util.RRobotState;
import rampancy.util.data.segmentTree.RSTNode;
import rampancy.util.data.segmentTree.RSegmentTree;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RDefaultSegmentTree extends RSegmentTree {

    public static final int NUM_BINS = 61;
    public static final int VISITS_BEFORE_BRANCH = 10;
    
    public static final int VELOCITY_SEGMENTS           = 7;
    public static final int LATERAL_VELOCITY_SEGMENTS   = 7;
    public static final int DELTA_HEADING_SEGMENTS      = 7;
    public static final int ADVANCING_VELOCITY_SEGMENTS = 7;
    public static final int DIST_FROM_WALL_SEGMENTS     = 3;
    public static final int DISTANCE_SEGMENTS           = 7;
    public static final int TIME_SINCE_DV_SEGMENTS      = 5;
    public static final int DELTA_V_SEGMENTS            = 5;
    public static final int TIME_SINCE_DIR_SEGMENTS     = 5;
    
    public static final RSTNode[] NODE_ORDER = {
        new RSTNode(RRobotState.LATERAL_VELOCITY_SEGMENT_FUNCTION, LATERAL_VELOCITY_SEGMENTS),
        new RSTNode(RRobotState.DELTA_H_SEGMENT_FUNCTION, DELTA_HEADING_SEGMENTS),
        new RSTNode(RRobotState.DISTANCE_FROM_WALL_SEGMENT_FUNCTION, DIST_FROM_WALL_SEGMENTS),
        new RSTNode(RRobotState.ADVANCING_VELOCITY_SEGMENT_FUNCTION, ADVANCING_VELOCITY_SEGMENTS),
        new RSTNode(RRobotState.DELTA_V_SEGMENT_FUNCTION, DELTA_V_SEGMENTS),
        new RSTNode(RRobotState.VELOCITY_SEGMENT_FUNCTION, VELOCITY_SEGMENTS),
        new RSTNode(RRobotState.TIME_SINCE_DIRECTION_CHANGE_SEGMENT_FUNCTION, TIME_SINCE_DIR_SEGMENTS),
        new RSTNode(RRobotState.DISTANCE_SEGMENT_FUNCTION, DISTANCE_SEGMENTS)
    };
    
    /**
     * @param order
     * @param visitsBeforeBranch
     * @param numGuessFactorBins
     */
    public RDefaultSegmentTree() {
        super(NODE_ORDER, VISITS_BEFORE_BRANCH, NUM_BINS);
    }

}
