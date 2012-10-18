/**
 * KDNearestSearch.java
 */
package rampancy.util.data.kdTree.copy;

import java.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class KDNearestSearch<T> {

    public T target;
    public int k;
    public List<T> nearestNeighbors;
    public double  furthestDistance;
    public KDDistanceFunction<T> distanceFunction;
    
    public KDNearestSearch(T target, int k, KDDistanceFunction<T> distanceFunction) {
        this.k = k;
        this.target = target;
        nearestNeighbors = new ArrayList<T>();
        furthestDistance = -1;
        this.distanceFunction = distanceFunction;
    }
    
    /**
     * Attempts to add the candidate point to the nearest neighbor list
     * @param candidate
     * @return {@code true} if the candidate was added
     */
    public boolean attemptToAdd(T candidate) {
        if(nearestNeighbors.size() < k) {
            nearestNeighbors.add(candidate);
            furthestDistance = Math.max(distanceFunction.distance(candidate, target), furthestDistance);
            return true;
        } else if(distanceFunction.distance(candidate, target) <= furthestDistance) {
            nearestNeighbors.add(candidate);
            return true;
        }
        return false;
    }
    
    /**
     * @param candidate
     * @return {@code true} if the candidate could be added to the neighbors list
     */
    public boolean canTraverse(T candidate) {
        return (nearestNeighbors.size() < k || distance(candidate) <= furthestDistance);
    }
    
    /**
     * @param point
     * @return using the distance function, compute the distance to the point
     */
    public double distance(T point) {
        return distanceFunction.distance(target, point);
    }
    
    /**
     * @return Return only the K nearest neighbors
     */
    public List<T> getNearestNeighbors() {
        sortNeighbors();
        return nearestNeighbors.subList(0, Math.min(nearestNeighbors.size(), k));
    }
    
    // ------------ Private ------------- //
    
    /**
     * Sort the nearest neighbors
     */
    private void sortNeighbors() {
        Collections.sort(nearestNeighbors, new Comparator<T>() {
            public int compare(T o1, T o2) {
                if(distanceFunction.distance(o1, target) == distanceFunction.distance(o2, target))
                    return 0;
                else if(distanceFunction.distance(o1, target) < distanceFunction.distance(o2, target))
                    return -1;
                else
                    return 1;
            }
            
        });
    }
}
