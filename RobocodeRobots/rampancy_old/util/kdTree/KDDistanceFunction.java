/**
 * KDDistanceFunction.java
 */
package rampancy_old.util.kdTree;

/**
 * @author Matthew Chun-Lum
 *
 */
public interface KDDistanceFunction<T> {
    
    /**
     * @param o1
     * @param o2
     * @return the distance between the two passed objects
     */
    public double distance(T o1, T o2);
}
