/**
 * 
 */
package rampancy.util.data.segmentTree;

import java.util.Arrays;


import rampancy.util.*;
import rampancy.util.data.RSegmentFunction;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RSTNode {
    
    public static void updateGuessFactors(RSTNode node, int guessFactorIndex, double weight, int rollDepth) {
        node.noteVisit();
        int visits = node.getVisitCount();
        
        double[] guessFactors = node.getGuessFactors();
        guessFactors[guessFactorIndex] = RUtil.rollingAvg(guessFactors[guessFactorIndex], 1.0, Math.min(visits, rollDepth), weight);
        for(int i = 0; i < guessFactors.length; i++)
            if(i != guessFactorIndex)
                guessFactors[i] = RUtil.rollingAvg(guessFactors[i], 1.0 / (Math.pow(guessFactorIndex - i, 2) + 1.0), Math.min(visits, rollDepth), weight);
    }
    
    private RSegmentTree rootReference;
    private RSegmentFunction segmentFunction;
    private RSTNode[] branches;
    private double[] guessFactors;
    private boolean hasBranched;
    private int segmentSize;
    private int visits;
    private int depth;
    
    /**
     * Use this constructor
     * @param segmentFunction
     * @param segmentSize
     */
    public RSTNode(RSegmentFunction segmentFunction, int segmentSize) {
        this.segmentFunction = segmentFunction;
        this.segmentSize = segmentSize;
    }
    
    /**
     * This constructor is only used internally
     * @param segmentFunction
     * @param segmentSize
     * @param seedFactors
     * @param rootReference
     */
    public RSTNode(RSegmentFunction segmentFunction, int segmentSize, double[] seedFactors, int depth, int visits, RSegmentTree rootReference) {
        this.rootReference = rootReference;
        this.segmentFunction = segmentFunction;
        branches = new RSTNode[segmentSize];
        this.segmentSize = segmentSize;
        guessFactors = Arrays.copyOf(seedFactors, seedFactors.length);
        this.visits = visits;
        this.depth = depth;
    }
    
    public RSTNode newInstance(double[] seedFactors, int depth, RSegmentTree reference) {
        return new RSTNode(segmentFunction, segmentSize, seedFactors, depth, visits, reference);
    }
    
    public RSTNode getSegmentForState(RRobotState state) {
        if(hasBranched()) {
            int index = segmentFunction.getIndexForState(state, segmentSize);
            if(branches[index] == null) {
                RSTNode template = rootReference.getTemplateNodeForDepth(depth + 1);
                branches[index] = template.newInstance(guessFactors, depth + 1, rootReference);
            }
            return branches[index].getSegmentForState(state);
        }
        return this;
    }
    
    public int getSegmentSize() {
        return segmentSize;
    }
    
    public void setBranched() {
        hasBranched = true;
    }
    
    public boolean hasBranched() {
        if(!hasBranched)
            hasBranched = (segmentFunction != null && visits >= rootReference.getVisitsBeforeBranch());
        return hasBranched;
    }
    
    public void noteVisit() {
        visits++;
    }
    
    public int getVisitCount() {
        return visits;
    }
    
    public double[] getGuessFactors() {
        return guessFactors;
    }
    
    public int getBranchCount() {
        int count = 1;
        for(int i = 0; i < branches.length; i++) {
            if(branches[i] != null)
                count += branches[i].getBranchCount();
        }
        return count;
    }
    
    public int getTerminalBranchCount(int maxDepth) {
        if(depth == maxDepth) {
            return 1;
        }
        int count = 0;
        for(int i = 0; i < branches.length; i++) {
            if(branches[i] != null)
                count += branches[i].getTerminalBranchCount(maxDepth);
        }
        return count;
    }
}
