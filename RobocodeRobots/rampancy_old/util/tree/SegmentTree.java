/**
 * SegmentTree.java
 */
package rampancy_old.util.tree;

import rampancy_old.util.*;

/**
 * 
 * @author Matthew Chun-Lum
 *
 */
public class SegmentTree {
    
    private Segment rootNode;
    
    /**
     * Constructor
     */
    public SegmentTree() {
        byte initialType = Segments.DISTANCE;
        rootNode = Segments.getNewSegmentOfType(initialType);
        rootNode.guessFactors = new double[Segments.NUM_BINS];
        rootNode.hasBranched = false;
        rootNode.availableBranches = new byte[Segments.BRANCH_COUNTS_BY_TYPE.length - 1];
        int pos = 0;
        for(byte i = 0; i < rootNode.availableBranches.length; i++) {
            if(i != initialType)
                rootNode.availableBranches[pos++] = i;  
        }
    }
    
    /**
     * @param enemy
     * @return the appropriate segment corresponding to the state
     * of the enemy robot
     */
    public Segment getSegment(EnemyState enemyState, int[] profile) {
        return rootNode.getSegment(enemyState, profile);
    }
    
    /**
     * @return the total number of branches in the tree
     */
    public int getNumBranches() {
        return rootNode.getTotalNumberOfBranches();
    }
    
    /**
     * @return the number of terminal branches
     */
    public int getNumTerminalBranches() {
        return rootNode.getNumTerminalBranches();
    }
}
