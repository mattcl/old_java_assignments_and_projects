/**
 * 
 */
package rampancy.standard;

import java.util.Arrays;

import rampancy.util.*;
import rampancy.util.data.segmentArray.RLeafSegment;
import rampancy.util.data.segmentTree.RSTNode;
import rampancy.util.movement.RMovementStatistic;
import rampancy.util.wave.REnemyWave;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RDefaultMovementStatistic implements RMovementStatistic {
    
    public static final int STANDARD_ROLL_DEPTH = 10;
    
    private RDefaultSegmentTree segmentTree;
    
    public RDefaultMovementStatistic() {
        segmentTree = new RDefaultSegmentTree();
    }

    /* (non-Javadoc)
     * @see rampancy_v2.util.RMovementStatistic#getDanger(rampancy_v2.util.REnemyWave, rampancy_v2.util.RPoint)
     */
    public double getDanger(REnemyWave wave, RPoint location) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    /* (non-Javadoc)
     * @see rampancy_v2.util.RMovementStatistic#getSafestGuessFactor(rampancy_v2.util.REnemyWave)
     */
    public double getSafestGuessFactor(REnemyWave wave) {
        RSTNode leaf = segmentTree.getSegmentForState(wave.getTargetState());
        double[] guessFactors = leaf.getGuessFactors();
        
        int lowestIndex = RUtil.indexOfSmallest(guessFactors);
        
        int offset = lowestIndex - guessFactors.length / 2;
        double factor = (double) offset / (double) ((guessFactors.length - 1) / 2);
        
        return factor;
    }

    /**
     * @param wave
     * @return a reference to the guess factor array specified by this wave
     */
    public double[] getGuessFactorArray(REnemyWave wave) {
        return segmentTree.getSegmentForState(wave.getTargetState()).getGuessFactors();
    }
    
    /* (non-Javadoc)
     * @see rampancy_v2.util.RMovementStatistic#noteHitByBullet(rampancy_v2.util.REnemyWave)
     */
    public void noteHitByBullet(REnemyWave wave, RPoint hitLocation) {
        double factorRaw = wave.computeRawFactor(hitLocation);
        int guessFactorIndex = (int) RUtil.limit(0, 
                (factorRaw * ((RDefaultSurfingSegmentArray.NUM_GUESS_FACTORS - 1) / 2)) + ((RDefaultSurfingSegmentArray.NUM_GUESS_FACTORS - 1) / 2), 
                RDefaultSurfingSegmentArray.NUM_GUESS_FACTORS - 1);
        
        RSTNode leaf = segmentTree.getSegmentForState(wave.getTargetState());
        RSTNode.updateGuessFactors(leaf, guessFactorIndex, 2.0, STANDARD_ROLL_DEPTH);
    }
}
