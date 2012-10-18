/**
 * KDTree.java
 */
package rampancy_old.util.kdTree;

import java.awt.Point;
import java.util.*;
/**
 * @author Matthew Chun-Lum
 *
 */
public class KDTree<T> {

    private KDNode<T> rootNode;
    private List<T> points; // Store these so we can fetch all of the points
                                  // in constant time
    private List<Comparator<T>> comparators;
    private int numDimensions;
    
    /**
     * Constructor
     * @param comparators
     */
    public KDTree(List<Comparator<T>> comparators) {
        this(new ArrayList<T>(), comparators);
    }
    
    /**
     * Constructor
     * @param points
     * @param comparators
     */
    public KDTree(List<T> points, List<Comparator<T>> comparators) {
        this(points, points.size(), comparators);
    }
    
    /**
     * Constructor
     * @param points
     * @param size
     * @param comparators
     */
    public KDTree(List<T> points, int size, List<Comparator<T>> comparators) {
        this.points = points;
        this.comparators = comparators;
        this.numDimensions = comparators.size();
        rootNode = generateInitialNodes(this.points.subList(0, size), 0);
    }
    
    /**
     * Finds the nearest neighbor to the passed point
     * @param point
     * @param distanceFunction
     * @return
     */
    public T getNearestNeighbor(T point, KDDistanceFunction<T> distanceFunction) {
        List<T> nearest = getKNearestNeighbors(point, 1, distanceFunction);
        if(nearest == null || nearest.isEmpty())
            return null;
        return nearest.get(0);
    }
    
    /**
     * Finds the k nearest neighbors to the passed point
     * @param point
     * @param k
     * @param distanceFunction
     * @return
     */
    public List<T> getKNearestNeighbors(T point, int k, KDDistanceFunction<T> distanceFunction) {
        if(rootNode == null)
            return null;

        KDNearestSearch<T> nearestSearch = new KDNearestSearch<T>(point, k, distanceFunction);
        recursiveFindKNearestNeighbors(nearestSearch, rootNode);
        return nearestSearch.getNearestNeighbors();
    }

    /**
     * Adds the point to the tree, splits nodes as necessary
     * @param point
     */
    public void add(T point) {
        points.add(point);
        if(rootNode == null) {
            rootNode = generateNewNode(point, 0);
        } else {
            recursiveAdd(point, rootNode);
        }
    }
    
    /**
     * @return a pointer to the root node (mainly used in testing)
     */
    public KDNode<T> getRootNode() {
        return rootNode;
    }
    
    /**
     * @return the number of dimensions in this tree
     */
    public int getNumberOfDimensions() {
        return numDimensions;
    }
    
    /**
     * @return all of the points in the tree. This operation is O(1)
     */
    public List<T> getAllPoints() {
        return points;
    }
    
    // ------------------ Private ------------------ //
    
    /**
     * Recursively finds the K nearest neighbors
     */
    private void recursiveFindKNearestNeighbors(KDNearestSearch<T> search, KDNode<T> node) {
        if(node.isLeaf()) {
            search.attemptToAdd(node.median);
        } else {
            boolean canMoveLeft = (node.left != null);
            boolean canMoveRight = (node.right != null);
            boolean targetToTheLeft = isToTheLeftOf(search.target, node.median, node.axis);
            boolean wentLeft = false;
            boolean wentRight = false;
            
            if(canMoveLeft && (!canMoveRight || targetToTheLeft)) {
                recursiveFindKNearestNeighbors(search, node.left);
                wentLeft = true;
            } else if(canMoveRight && (!canMoveLeft || !targetToTheLeft)) {
                recursiveFindKNearestNeighbors(search, node.right);
                wentRight = true;
            }
            
            if(search.attemptToAdd(node.median)) {
                KDNearestSearch<T> newSearch = new KDNearestSearch<T>(search.target, search.k, search.distanceFunction);
                if(wentLeft && canMoveRight) {
                    recursiveFindKNearestNeighbors(newSearch, node.right);
                } else if(wentRight && canMoveLeft) {
                    recursiveFindKNearestNeighbors(newSearch, node.left);
                }
                
                if(!newSearch.nearestNeighbors.isEmpty()) {
                    search.nearestNeighbors.addAll(newSearch.nearestNeighbors);
                }
            }          
        }
    }
    
    /**
     * Recursively add the point to the tree, generating new nodes as necessary
     * @param point
     * @param node
     */
    private void recursiveAdd(T point, KDNode<T> node) {
        if(isToTheLeftOf(point, node.median, node.axis)) {
            if(node.left == null) {
                node.left = generateNewNode(point, node.depth + 1);
            } else {
                recursiveAdd(point, node.left);
            }
        } else {
            if(node.right == null) {
                node.right = generateNewNode(point, node.depth + 1);
            } else {
                recursiveAdd(point, node.right);
            }
        }
    }
    
    /**
     * @param point
     * @param median
     * @param axis
     * @return {@code true} if the passed point is less than or equal to the median
     */
    private boolean isToTheLeftOf(T point, T median, int axis) {
        return comparators.get(axis).compare(point, median) < 0 ;
    }
    
    /**
     * Sets the root node to contain the passed point
     * @param point
     */
    private KDNode<T> generateNewNode(T point, int depth) {
        KDNode<T> node = new KDNode<T>();
        node.axis = depth % numDimensions;
        node.depth = depth;
        node.median = point;
        return node;
    }
    
    /**
     * Recursively build the tree from a passed list of KDPoints
     * @param points
     * @param depth
     * @return the pointer to the root node
     */
    private KDNode<T> generateInitialNodes(List<T> points, int depth) {
        if(points.isEmpty())
            return null;
        
        int axis = depth % numDimensions;
        Collections.sort(points, comparators.get(axis));
        int medianIndex = (points.size()) / 2;
        T median = points.get(medianIndex);
        
        KDNode<T> node = new KDNode<T>();
        node.axis = axis;
        node.depth = depth;
        node.median = median;
        
        List<T> left = points.subList(0, medianIndex);
        List<T> right = points.subList(medianIndex + 1, points.size());
        
        node.left = generateInitialNodes(left, depth + 1);
        node.right = generateInitialNodes(right, depth + 1);
        
        return node;
    }
}
