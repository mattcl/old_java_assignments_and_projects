/**
 * 
 */
package rampancy.util.data;

import rampancy.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public interface RSegmentFunction {
    
    /**
     * @param state
     * @param segmentSize
     * @return the appropriate index given a robot's state and the size of the segment
     */
    public int getIndexForState(RRobotState state, int segmentSize);
}
