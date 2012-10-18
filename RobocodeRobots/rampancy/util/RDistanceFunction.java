/**
 * KDDistanceFunction.java
 */
package rampancy.util;

/**
 * @author Matthew Chun-Lum
 *
 */
public interface RDistanceFunction<T> {
    
    /**
     * @param o1
     * @param o2
     * @return the distance between the two passed objects
     */
    public double distance(T o1, T o2);
}
