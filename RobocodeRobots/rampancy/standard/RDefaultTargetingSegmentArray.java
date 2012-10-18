/**
 * 
 */
package rampancy.standard;

import rampancy.util.RRobotState;
import rampancy.util.data.segmentArray.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RDefaultTargetingSegmentArray extends RSegmentArray {

    public static final int LATERAL_VELOCITY_SEGMENTS   = 5;
    public static final int DELTA_HEADING_SEGMENTS      = 5;
    public static final int ADVANCING_VELOCITY_SEGMENTS = 5;
    public static final int DIST_FROM_WALL_SEGMENTS     = 3;
    public static final int DISTANCE_SEGMENTS           = 5;
    public static final int TIME_SINCE_DV_SEGMENTS      = 5;
    public static final int NUM_GUESS_FACTORS           = 61;
    
    public static final RNode[] DEFAULT_SEGMENT_ORDER = {
        new RBranchSegment(DELTA_HEADING_SEGMENTS, RRobotState.DELTA_H_SEGMENT_FUNCTION),
        new RBranchSegment(LATERAL_VELOCITY_SEGMENTS, RRobotState.LATERAL_VELOCITY_SEGMENT_FUNCTION),
        new RBranchSegment(DIST_FROM_WALL_SEGMENTS, RRobotState.DISTANCE_FROM_WALL_SEGMENT_FUNCTION),
        new RBranchSegment(DISTANCE_SEGMENTS, RRobotState.DISTANCE_SEGMENT_FUNCTION),
        new RBranchSegment(TIME_SINCE_DV_SEGMENTS, RRobotState.TIME_SINCE_VELOCITY_CHANGE_SEGMENT_FUNCTION),
        new RBranchSegment(ADVANCING_VELOCITY_SEGMENTS, RRobotState.ADVANCING_VELOCITY_SEGMENT_FUNCTION),
        new RLeafSegment(NUM_GUESS_FACTORS)
    };
    
    public RDefaultTargetingSegmentArray() {
        super(DEFAULT_SEGMENT_ORDER);  
    }
}
