/**
 * 
 */
package rampancy.standard;

import java.util.ArrayList;
import java.util.List;

import rampancy.util.RRobotState;
import rampancy.util.data.kdTree.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RDefaultKDTree {
    public static final int NUM_NEIGHBORS = 25;
    public static final int MAX_POINTS = 2500;
    KDTree<RDefaultKDPoint> kdTree;
    
    public RDefaultKDTree() {
        kdTree = new KDTree<RDefaultKDPoint>(RDefaultKDPoint.getComparators());
    }
    
    public void rebalance() {
        List<RDefaultKDPoint> points = kdTree.getAllPoints();
        if(points.size() > MAX_POINTS) {
            points = points.subList(points.size() - MAX_POINTS, points.size() - 1);
        }
        System.out.println("Rebalancing kd-tree with " + points.size() + " points");
        kdTree = new KDTree<RDefaultKDPoint>(points, RDefaultKDPoint.getComparators());
    }
    
    public void addPoint(RRobotState state, double guessFactor) {
        kdTree.add(new RDefaultKDPoint(state, guessFactor));
    }
    
    public ArrayList<RDefaultKDPoint> getNearestPoints(RRobotState state) {
        return new ArrayList<RDefaultKDPoint>(kdTree.getKNearestNeighbors(new RDefaultKDPoint(state, 0), NUM_NEIGHBORS));
    }
}