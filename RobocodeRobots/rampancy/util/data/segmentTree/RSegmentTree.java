/**
 * 
 */
package rampancy.util.data.segmentTree;

import java.util.*;

import rampancy.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RSegmentTree {
    
    private RSTNode rootNode;
    private RSTNode[] nodeOrder;
    private int visitsBeforeBranch;
    
    /**
     * Standard constructor
     * @param order and array of RSTNodes representing the default order of branches
     * @param visitsBeforeBranch
     * @param numGuessFactorBins
     */
    public RSegmentTree(RSTNode[] order, int visitsBeforeBranch, int numGuessFactorBins) {
        this.visitsBeforeBranch = visitsBeforeBranch;
        nodeOrder = order;
        double[] seed = new double[numGuessFactorBins];
        rootNode = nodeOrder[0].newInstance(seed, 0, this);
        //rootNode.setBranched();
    }
    
    /**
     * Returns the appropriate segment for the given state
     * @param state
     * @return
     */
    public RSTNode getSegmentForState(RRobotState state) {
        return rootNode.getSegmentForState(state);
    }
    
    /**
     * Returns a template node given a specified depth
     * @param depth
     * @return
     */
    public RSTNode getTemplateNodeForDepth(int depth) {
        if(depth >= nodeOrder.length)
            return new RSTNode(null, 0);
        return nodeOrder[depth];
    }
    
    /**
     * Return the number of visits before a branch
     * @return the number of visits before a branch
     */
    public int getVisitsBeforeBranch() {
        return visitsBeforeBranch;
    }
    
    public int getMaxBranchCount() {
        int count = nodeOrder[0].getSegmentSize();
        for(int i = 1; i < nodeOrder.length; i++) {
            count *= nodeOrder[i].getSegmentSize();
        }
        return count;
    }
    
    /**
     * Return the number of branches
     * @return the number of branches
     */
    public int getBranchCount() {
        return rootNode.getBranchCount();
    }
    
    /**
     * Return the number of terminal branches
     * @return the number of terminal branches
     */
    public int getTerminalBranchCount() {
        return rootNode.getTerminalBranchCount(nodeOrder.length);
    }
    
    /**
     * Returns a string representation of the tree
     */
    public String toString() {
        String str = "RSegmentTree:\n";
        str += nodeOrder.length + " unique branch types\n";
        str += "Generated " + getBranchCount() + " of " + getMaxBranchCount() + " possible branches\n";
        str += getTerminalBranchCount() + " terminal branches\n\n";
        return str;
    }
}
