/**
 * 
 */
package rampancy.util.data.segmentArray;

import rampancy.util.RRobotState;
import rampancy.util.RUtil;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RLeafSegment implements RNode {
    
    public static void updateGuessFactors(RLeafSegment leaf, int guessFactorIndex, double weight, int rollDepth) {
        leaf.visits++;
        int visits = leaf.visits;
        
        double[] guessFactors = leaf.guessFactorArray;
        guessFactors[guessFactorIndex] = RUtil.rollingAvg(guessFactors[guessFactorIndex], 1.0, Math.min(visits - 1, rollDepth), weight + 1);
        for(int i = 0; i < guessFactors.length; i++)
            if(i != guessFactorIndex)
                guessFactors[i] = RUtil.rollingAvg(guessFactors[i], 1.0 / (Math.pow(guessFactorIndex - i, 2) + 1.0), Math.min(visits, rollDepth), weight);
    }
    
    public double[] guessFactorArray;
    public int visits;
    
    public RLeafSegment(int size) {
        guessFactorArray = new double[size];
        visits = 0;
    }

    /* (non-Javadoc)
     * @see rampancy_v2.util.data.segmentArray.RNode#getSegmentForState(rampancy_v2.util.RRobotState)
     */
    public RLeafSegment getSegmentForState(RRobotState state) {
        return this;
    }
    
    public RNode newInstance(RSegmentArray reference, int depth) {
        return new RLeafSegment(guessFactorArray.length);
    }
    
    
}
