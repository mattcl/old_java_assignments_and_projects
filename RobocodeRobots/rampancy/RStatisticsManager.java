/**
 * 
 */
package rampancy;

import rampancy.util.*;
import rampancy.util.movement.RMovementStatistic;
import rampancy.util.weapon.RTargetingStatistic;

/**
 * @author Matthew Chun-Lum
 *
 */
public interface RStatisticsManager {

    /**
     * @return the movement statistic contained within the manager
     */
    public RMovementStatistic getMovementStatistics();
    
    public RTargetingStatistic getTargetingStatistics();
}
