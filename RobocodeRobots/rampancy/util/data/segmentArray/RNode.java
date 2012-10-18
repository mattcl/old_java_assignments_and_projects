/**
 * 
 */
package rampancy.util.data.segmentArray;

import rampancy.util.*;
/**
 * @author Matthew Chun-Lum
 *
 */
public interface RNode {

    /**
     * Recursively finds the appropriate guess factor array
     * for the given state
     * @param state
     * @return the guess factor leaf for the given state
     */
    public RLeafSegment getSegmentForState(RRobotState state);
    
    /**
     * @return a new instance of self
     */
    public RNode newInstance(RSegmentArray ref, int depth);
}
