/**
 * Segment.java
 */
package rampancy_old.util.tree;

import java.util.Arrays;

import rampancy_old.util.*;

/**
 * This is the class that represents a segment
 * @author Matthew Chun-Lum
 *
 */
public class Segment {

    public byte type;
    public short visits;
    public short previousVisits;
    public boolean hasBranched;
    public Segment[] branches;
    public byte[] availableBranches;
    public double[] guessFactors;

    public Segment(byte type, int numBranches) {
        this.type = type;
        this.previousVisits = 0;
        visits = 0;
        guessFactors = null;
        hasBranched = false;
        if(type == Segments.TERMINAL_SEGMENT) {
            branches = null;
        } else {
            branches = new Segment[numBranches];
        }
    }

    /**
     * Updates the guess factor array
     * @param guessFactor
     */
    public void updateGuessFactors(double guessFactor, boolean isVirtual) {
        int index = Segments.computeGuessFactorIndex(this, guessFactor);
        double weight = (isVirtual ? 0.1 : 1.0);
        guessFactors[index] = Util.rollingAvg(guessFactors[index], 1.0, Math.min(visits, Constants.STANDARD_ROLL_DEPTH), weight);
        for(int i = 0; i < guessFactors.length; i++)
            if(i != index)
                guessFactors[i] = Util.rollingAvg(guessFactors[i], 1.0 / (Math.pow(index - i, 2) + 1.0), Math.min(visits, Constants.STANDARD_ROLL_DEPTH), weight);
    }
    
    /**
     * @return a String description of the segment
     */
    public String getDescription() {
        return Segments.getSegmentDescription(type);
    }

    /**
     * @return number of branches this segment may have
     */
    public int getBranchCount() {
        return branches.length;
    }

    /**
     * create a new branch at the specified index
     * @param branchIndex
     */
    public void createBranch(int branchIndex, int[] profile) {
        byte branchType = getBestBranchType(profile);
        branches[branchIndex] = Segments.getNewSegmentOfType(branchType);
        branches[branchIndex].visits = visits;
        branches[branchIndex].previousVisits = visits;
        branches[branchIndex].guessFactors = new double[guessFactors.length];
        System.arraycopy(guessFactors, 0, branches[branchIndex].guessFactors, 0, guessFactors.length);
        setAvailableBranches(branches[branchIndex], branchType);
    }

    /**
     * Dives through the tree structure searching for the outermost leaf that satisfies
     * the current state of the enemy.
     * @param enemy
     * @return a reference to segment containing the appropriate array of guess factors
     */
    public Segment getSegment(EnemyState enemyState, int[] profile) {
        if(!hasBranched && guessFactors != null) {
            if(type != Segments.TERMINAL_SEGMENT && visits - previousVisits > Segments.DEFAULT_VISITS_BEFORE_BRANCH) {
                hasBranched = true;
            } else {
                visits++;
                return this;
            }
        }

        int branchIndex = Segments.getBranchIndex(enemyState, type);
        
        if(branches[branchIndex] == null)
            createBranch(branchIndex, profile);

        Segment branch = branches[branchIndex];
        return branch.getSegment(enemyState, profile);
    }

    /**
     * @return the number of times this segment has been visited
     */
    public short getVisitCount() {
        return visits;
    }

    /**
     * Recursively counts the total number of branches (nodes) in the segment tree
     * @return the total number of branches
     */
    public int getTotalNumberOfBranches() {
        int sum = 1;

        if(hasBranched) {
            for(int i = 0; i < branches.length; i++)
                if(branches[i] != null)
                    sum += branches[i].getTotalNumberOfBranches();
        }

        return sum;
    }
    
    /**
     * Recursively count the number of terminal branches
     * @return the number of terminal branches
     */
    public int getNumTerminalBranches() {
        int sum = (type == Segments.TERMINAL_SEGMENT ? 1 : 0);
        
        if(hasBranched) {
            for(int i = 0; i < branches.length; i++)
                if(branches[i] != null)
                    sum += branches[i].getNumTerminalBranches();
        }

        return sum;
    }
    
    // ------------------ Private -------------------- //
    
    /*
     * using the provided profile, determine which branch type to use
     */
    private byte getBestBranchType(int[] profile) {
        byte branchType = Segments.TERMINAL_SEGMENT;
        int greatestVariation = -1;
        if(availableBranches != null) {
            for(byte i = 0; i < profile.length; i++) {
                if(Arrays.binarySearch(availableBranches, i) >= 0) {
                    if(Math.abs(profile[i]) > greatestVariation) {
                        greatestVariation = Math.abs(profile[i]);
                        branchType = i;
                    }
                }
            }
        }
        return branchType;
    }
    
    /*
     * copy the array of available branches, leaving out the current choice
     */
    private void setAvailableBranches(Segment segment, byte ignoreType) {
        if(ignoreType != Segments.TERMINAL_SEGMENT) {
            segment.availableBranches = new byte[availableBranches.length - 1];
            int newPos = 0;
            for(int i = 0; i < availableBranches.length; i++) {
                if(availableBranches[i] != ignoreType) {
                    segment.availableBranches[newPos++] = availableBranches[i];
                }
            }
        }
    }
}
