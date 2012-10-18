/**
 * 
 */
package rampancy.util.data.segmentArray;

import rampancy.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RSegmentArray {
    private RNode[] segmentOrder;
    private RNode root;
    
    /**
     * Constructor
     * @param segmentOrder
     */
    public RSegmentArray(RNode[] segmentOrder) {
        this.segmentOrder = segmentOrder;
        root = segmentOrder[0].newInstance(this, 0);
    }
    
    /**
     * Creates a segment using the passed depth value
     * @param depth
     * @return the new segment created
     */
    public RNode createSegment(int depth) {
        return segmentOrder[depth].newInstance(this, depth);
    }
    
    /**
     * Recursively searches for the appropriate segment for the given state
     * @param state
     * @return the appropriate segment for the given state
     */
    public RLeafSegment getSegmentForState(RRobotState state) {
        return root.getSegmentForState(state);
    }
}
