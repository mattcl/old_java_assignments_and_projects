/**
 * KDPoint.java
 */
package rampancy.util.data.kdTree;

import java.util.Comparator;

/**
 * This interface specifies methods that kd data points need to implement
 * to be included in a kd tree
 * @author Matthew Chun-Lum
 *
 */
public abstract class KDPoint{

    /**
     * @param target
     * @return the complete distance from this KDPoint to the target point
     */
    public abstract double distanceTo(KDPoint target);
}
