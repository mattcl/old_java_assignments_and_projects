/**
 * 
 */
package rampancy.util.data.segmentArray;

import rampancy.util.RRobotState;
import rampancy.util.data.RSegmentFunction;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RBranchSegment implements RNode {
    private RNode[] branches;
    private RSegmentFunction segmentFunction;
    private RSegmentArray reference;
    private int depth;
    
    public RBranchSegment(int size, RSegmentFunction segmentFunction) {
        this(size, segmentFunction, 0, null);
    }
    
    public RBranchSegment(int size, RSegmentFunction segmentFunction, int depth, RSegmentArray reference) {
        branches = new RNode[size];
        this.segmentFunction = segmentFunction;
        this.reference = reference;
        this.depth = depth;
    }

    /* (non-Javadoc)
     * @see rampancy_v2.util.data.segmentArray.RNode#getSegmentForState(rampancy_v2.util.RRobotState)
     */
    public RLeafSegment getSegmentForState(RRobotState state) {
        int index = segmentFunction.getIndexForState(state, branches.length);
        if(branches[index] == null)
            branches[index] = reference.createSegment(depth + 1);
        return branches[index].getSegmentForState(state);
    }

    /* (non-Javadoc)
     * @see rampancy_v2.util.data.segmentArray.RNode#newInstance()
     */
    public RNode newInstance(RSegmentArray reference, int depth) {
        return new RBranchSegment(branches.length, segmentFunction, depth, reference);
    }
    
}
