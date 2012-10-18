/**
 * MovementStatistic.java
 */
package rampancy_old.statistics;

import rampancy_old.management.EnemyListener;
import rampancy_old.management.MovementManager;
import rampancy_old.util.*;
import rampancy_old.util.kdTree.*;
import robocode.util.Utils;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * This class tracks our movment statistics per enemy
 * @author Matthew Chun-Lum
 * 
 */
public class MovementStatistic implements EnemyListener {
    public static final int NUM_BINS = 47;
    public static final int PREFERRED_TREE_SIZE = 80;
    public static final int NUM_NEIGHBORS = 30;
    public static final double BANDWIDTH = 1;
    
    private String enemyName;
    private KDTree<SurfPoint> clusterTree;
    private int shotsFired;
    private int shotsHit;
    private double hitPercentage;
    
    /**
     * Default constructor
     * @param enemyName
     */
    public MovementStatistic(String enemyName) {
        this.enemyName = enemyName;
        clusterTree = new KDTree<SurfPoint>(new ArrayList<SurfPoint>(), SurfPoint.getComparators());
    }
    
    public void rebuildTree() {
        List<SurfPoint> points = clusterTree.getAllPoints();
        Collections.sort(points, SurfPoint.FIRE_TIME_COMPARATOR);
        System.out.println("Rebuilding kd tree, using " + Math.min(PREFERRED_TREE_SIZE, points.size()) + " of the newest points");
        points = points.subList(0, Math.min(PREFERRED_TREE_SIZE, points.size()));
        clusterTree = new KDTree<SurfPoint>(points, SurfPoint.getComparators());
//        clusterTree = new KDTree<SurfPoint>(points, Math.min(PREFERRED_TREE_SIZE, points.size()), SurfPoint.getComparators());
    }
    
    public double getHitPercentage() {
        return hitPercentage;
    }
    
    /**
     * @param wave
     * @param target
     * @return the guess factor index for a given wave and target
     */
    public double getFactorIndex(EnemyWave wave, Point2D.Double target) {
        double offset = wave.computeOffsetAngle(target);
        double factor = Utils.normalRelativeAngle(offset) / Util.computeMaxEscapeAngle(wave.getVelocity()) * wave.getDirection();
        return (int) Util.limit(0, (factor * ((NUM_BINS - 1) / 2)) + ((NUM_BINS - 1) / 2), NUM_BINS - 1);
    }
    
    /**
     * Compute the danger for a given wave and location
     * @param wave
     * @param location
     * @return the computed danger
     */
    public double getDanger(EnemyWave wave, Point2D.Double location) {
        SurfPoint searchPoint = new SurfPoint(wave);
        searchPoint.guessFactorIndex = getFactorIndex(wave, location);
        
        List<SurfPoint> nearestNeighbors = clusterTree.getKNearestNeighbors(searchPoint, NUM_NEIGHBORS, SurfPoint.WEIGHTED_DISTANCE_FUNCTION);
        
        if(nearestNeighbors == null) {
            return 0;
        }
        
        List<Double> distances = new ArrayList<Double>();
        
        for(SurfPoint neighbor : nearestNeighbors) {
            distances.add(searchPoint.guessFactorIndex - neighbor.guessFactorIndex);
        }
        
        double density = Util.computeDensity(distances, BANDWIDTH);
        return density;
    }
    
    /**
     * Notes a hit by an enemy bullet
     * @param wave
     * @param location
     */
    public void logHit(EnemyWave wave, Point2D.Double location) {
        shotsHit++;
        hitPercentage = (double) shotsHit / (double) shotsFired;
        logVirtualHit(wave, location);
    }
    
    /**
     * Notes a virtual hit, does not update the hit percentage
     * @param wave
     * @param location
     */
    public void logVirtualHit(EnemyWave wave, Point2D.Double location) {
        SurfPoint pt = new SurfPoint(wave);
        pt.guessFactorIndex = getFactorIndex(wave, location);
        clusterTree.add(pt);
    }
    
    /* (non-Javadoc)
     * @see rampancy.management.EnemyListener#enemyUpdated(rampancy.util.EnemyRobot)
     */
    public void enemyUpdated(EnemyRobot enemy) {
        if(enemy.getShotsFired() != shotsFired) {
            shotsFired = enemy.getShotsFired();
            hitPercentage = (double) shotsHit / (double) shotsFired;
        }
        
    }
}
