/**
 * 
 */
package rampancy.util.data.staticSegmentArray;

import rampancy.util.RRobotState;
import rampancy.util.RUtil;
import rampancy.util.data.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RStaticSegmentArray {
    
//    public static void updateGuessFactors(double[] segment, int guessFactorIndex, double weight, int rollDepth) {
//        double[] guessFactors = leaf.guessFactorArray;
//        guessFactors[guessFactorIndex] = RUtil.rollingAvg(guessFactors[guessFactorIndex], 1.0, Math.min(visits - 1, rollDepth), weight + 1);
//        for(int i = 0; i < guessFactors.length; i++)
//            if(i != guessFactorIndex)
//                guessFactors[i] = RUtil.rollingAvg(guessFactors[i], 1.0 / (Math.pow(guessFactorIndex - i, 2) + 1.0), Math.min(visits, rollDepth), weight);
//    }
    
    private RSegmentFunction[] segmentFunctions;
    private double[][][][][][] segmentArray;
    private int numGuessFactors;
    
    public RStaticSegmentArray(RSegmentFunction[] segmentFunctions, int[] segmentsPerLevel, int numGuessFactors) {
        this.segmentFunctions = segmentFunctions;
        this.numGuessFactors = numGuessFactors;
        segmentArray = new double[segmentsPerLevel[0]]
                                  [segmentsPerLevel[1]]
                                   [segmentsPerLevel[2]]
                                    [segmentsPerLevel[3]]
                                     [segmentsPerLevel[4]]
                                      [numGuessFactors];
    }
    
    public double[] getSegmentsForState(RRobotState state) {
        int[] segmentKey = new int[segmentFunctions.length];
        for(int i = 0; i < segmentKey.length; i++)
            segmentKey[i] = segmentFunctions[0].getIndexForState(state, segmentArray[i].length);
        
        return segmentArray[segmentKey[0]]
                            [segmentKey[1]]
                             [segmentKey[2]]
                              [segmentKey[3]]
                               [segmentKey[4]];
    }

}
