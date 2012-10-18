/**
 * StatisticsReport.java
 */
package rampancy_old.statistics;

import rampancy_old.util.Constants;
import rampancy_old.util.Util;
import rampancy_old.util.tree.Segments;

/**
 * This class is a summary of the statistics analyzed by the StatisticsAnalyzer
 * @author Matthew Chun-Lum
 *
 */
public class StatisticsReport {
    public String enemyName;
    public long possibleBranches = Segments.NUM_POSSIBLE_BRANCHES;
    public int numGeneratedBranches;
    public int numTerminalBranches;
    public int shotsFired;
    public int shotsHit;
    public double hitRate;
    
    public double estimatedMemoryConsumption;
    
    /**
     * Generates an estimate of the memory consumed by the segment tree
     */
    public void estimateMemoryConsumption() {
        estimatedMemoryConsumption = ((double) (numGeneratedBranches * Segments.ESTIMATED_SEGMENT_SIZE)) / (1024.0);
        estimatedMemoryConsumption = Util.roundToPrecision(estimatedMemoryConsumption, 2);
    }
    
    public void computeSuccessRates(int shotsFired, int shotsHit) {
        this.shotsFired = shotsFired;
        this.shotsHit = shotsHit;
        hitRate = ((double) shotsHit / (double) shotsFired) * 100;
        hitRate = Util.roundToPrecision(hitRate, 2);
    }
    
    /**
     * returns a string representation of the report
     */
    public String toString() {
        double percent = (1.0 - (estimatedMemoryConsumption / 1024.0 / 1024.0 / Segments.WORST_CASE_MEMORY_CONSUMPTION)) * 100;
        percent = Util.roundToPrecision(percent, 2);
        
        String ret = "*************************\n";
        ret += "Enemy name:\n------------\n"; 
        ret += "    " + enemyName + "\n";
        
        ret += "\nWeapon Stats:\n------------\n";
        ret += "    " + shotsHit + " hits from " + shotsFired + " shots fired\n";
        ret += "    " + hitRate + "% hit rate\n";
        
        ret += "\nSegments:\n------------\n";
        ret += "    Generated " + numGeneratedBranches + " of " + possibleBranches + " possible branches\n";
        ret += "    " + numTerminalBranches + " terminal branches";
        
        ret += "\nMemory:\n------------\n";
        ret += "    Estimated consumption: " + estimatedMemoryConsumption + " KB\n" ;
        ret += "    Worst case: " + Util.roundToPrecision(Segments.WORST_CASE_MEMORY_CONSUMPTION, 2) + " GB\n";
        ret += "    Percent savings: " + percent + "%\n";
        
        return ret;
    }                           
}
