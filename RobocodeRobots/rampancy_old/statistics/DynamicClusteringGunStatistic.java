/**
 * DynamicClusteringGunStatistic
 */
package rampancy_old.statistics;

import rampancy_old.util.*;
import rampancy_old.util.kdTree.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class DynamicClusteringGunStatistic {
    
    KDTree<GFPoint> clusterTree;
    
    public DynamicClusteringGunStatistic() {
        clusterTree = new KDTree<GFPoint>(null);
    }
    
}
