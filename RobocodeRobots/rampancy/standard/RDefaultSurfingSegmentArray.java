/**
 * 
 */
package rampancy.standard;

import rampancy.RampantRobot;
import rampancy.util.RRobotState;
import rampancy.util.RUtil;
import rampancy.util.data.segmentArray.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RDefaultSurfingSegmentArray extends RSegmentArray {
    
    public static final int DIST_FROM_WALL_SEGMENTS   = 3;
    public static final int HEAD_SEGMENTS             = 5;
    public static final int LATERAL_VELOCITY_SEGMENTS = 5;
    public static final int NUM_GUESS_FACTORS         = 61;
    
    public static final RNode[] DEFAULT_SEGMENT_ORDER = {
        new RBranchSegment(DIST_FROM_WALL_SEGMENTS, RRobotState.DISTANCE_FROM_WALL_SEGMENT_FUNCTION),
        new RBranchSegment(HEAD_SEGMENTS, RRobotState.DELTA_H_SEGMENT_FUNCTION),
        new RBranchSegment(LATERAL_VELOCITY_SEGMENTS, RRobotState.LATERAL_VELOCITY_SEGMENT_FUNCTION),
        new RLeafSegment(NUM_GUESS_FACTORS)
    };
    
    public RDefaultSurfingSegmentArray() {
        super(DEFAULT_SEGMENT_ORDER);  
    }
}
