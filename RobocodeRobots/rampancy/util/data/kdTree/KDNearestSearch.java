/**
 * KDNearestSearch.java
 */
package rampancy.util.data.kdTree;

import java.util.*;

import rampancy.util.RDistanceFunction;

/**
 * @author Matthew Chun-Lum
 *
 */
public class KDNearestSearch<T extends KDPoint> {

    public T        target;
    public int      k;
    public List<T>  nearestNeighbors;
    public ArrayList<Double> distances;
    public double   furthestDistance;
    
    public KDNearestSearch(T target, int k) {
        this.k = k;
        this.target = target;
        nearestNeighbors = new ArrayList<T>();
        distances = new ArrayList<Double>();
        furthestDistance = -1;
    }
    
    /**
     * Attempts to add the candidate point to the nearest neighbor list
     * @param candidate
     * @return {@code true} if the candidate was added
     */
    public boolean attemptToAdd(T candidate) {
        double dist = candidate.distanceTo(target);
        if(nearestNeighbors.size() < k) {
            nearestNeighbors.add(candidate);
            furthestDistance = Math.max(dist, furthestDistance);
            return true;
        } else if(dist < furthestDistance) {
            furthestDistance = (furthestDistance - dist) / 2.0;
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
        return (nearestNeighbors.size() < k || candidate.distanceTo(target) <= furthestDistance);
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
                double dist1 = o1.distanceTo(target);
                double dist2 = o2.distanceTo(target);
                if(dist1 == dist2)
                    return 0;
                else if(dist1 < dist2)
                    return -1;
                else
                    return 1;
            }
            
        });
    }
}
