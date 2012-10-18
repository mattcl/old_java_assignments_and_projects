/**
 * 
 */
package rampancy.standard;

import rampancy.RStatisticsManager;
import rampancy.util.*;
import rampancy.util.movement.RMovementStatistic;
import rampancy.util.weapon.RTargetingStatistic;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RDefaultStatisticsManager implements RStatisticsManager {

    private RMovementStatistic movementStatistic;
    private RTargetingStatistic targetingStatistic;
    
    public RDefaultStatisticsManager() {
        movementStatistic = new RDefaultMovementStatistic();
        targetingStatistic = new RDefaultTargetingStatistic();
    }
    
    /* (non-Javadoc)
     * @see rampancy_v2.RStatisticsManager#getMovementStatistics()
     */
    public RMovementStatistic getMovementStatistics() {
        return movementStatistic;
    }

    /* (non-Javadoc)
     * @see rampancy_v2.RStatisticsManager#getTargetingStatistics()
     */
    public RTargetingStatistic getTargetingStatistics() {
        return targetingStatistic;
    }
    
    

}
