package marathon.tycho;

import robocode.*;

public class GunStatisticManager {

    private double[][][][][][] guessFactors;
    private int[][][][][] readings;
    
    private EnemyRobot reference;
    
    public GunStatisticManager(EnemyRobot referenceRobot) {
        guessFactors = new double[Helper.DISTANCE]
                                  [Helper.VELOCITIES]
                                   [Helper.MOVE_TIMES]
                                    [Helper.HEADINGS]
                                     [Helper.BEARINGS]
                                      [Helper.GUESS_FACTORS];
        
        readings = new int[Helper.DISTANCE]
                           [Helper.VELOCITIES]
                            [Helper.MOVE_TIMES]
                             [Helper.HEADINGS]
                              [Helper.BEARINGS];
        
        reference = referenceRobot;
    }
    
    /**
     * Returns the appropriate segment of the guess factor array
     */
    public double[] getReturnSegment() {
//        return guessFactors[Helper.getDistanceSegment(reference.getDistance())]
//                            [Helper.getVelocitySegment(reference.getVelocity())]
//                             [Helper.getTimeSegment(reference.getTimeSinceLastMove())]
//                              [Helper.getHeadingSegment(reference.getHeading())]
//                               [Helper.getBearingSegment(reference.getAbsoluteBearing())];
        return guessFactors[0]
                            [0]
                             [0]
                              [0]
                               [0];
    }
    
    /**
     * Returns the appropriate segment of the readings array
     */
    public int[] getReadingsSegment() {
//        return readings[Helper.getDistanceSegment(reference.getDistance())]
//                        [Helper.getVelocitySegment(reference.getVelocity())]
//                         [Helper.getTimeSegment(reference.getTimeSinceLastMove())]
//                          [Helper.getHeadingSegment(reference.getHeading())];
        return readings[0]
                        [0]
                         [0]
                          [0];
    }
    
    /**
     * Returns the index of the readings segment
     */
    public int getReadingsSegmentIndex() {
        return Helper.getBearingSegment(reference.getAbsoluteBearing());
    }
}
