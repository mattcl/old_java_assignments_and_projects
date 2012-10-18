/**
 * SurfPoint.java
 */
package rampancy_old.util;

import java.util.*;

import rampancy_old.util.kdTree.KDDistanceFunction;


/**
 * This class is used to store surf stats in a KD tree
 * @author Matthew Chun-Lum
 *
 */
public class SurfPoint {
    
    // overall distance function
    public static final KDDistanceFunction<SurfPoint> DEFAULT_DISTANCE_FUNCTION = new KDDistanceFunction<SurfPoint>() {
        public double distance(SurfPoint o1, SurfPoint o2) {
            return Math.pow(o1.distance - o2.distance, 2) + 
                    Math.pow(o1.deltaHeading - o2.deltaHeading, 2) + 
                    Math.pow(o1.velocity - o2.velocity, 2) + 
                    Math.pow(o1.lateralVelocity - o2.lateralVelocity, 2);
        }
    };
    
    // weighted distance function
    public static final KDDistanceFunction<SurfPoint> WEIGHTED_DISTANCE_FUNCTION = new KDDistanceFunction<SurfPoint>() {
        public double distance(SurfPoint o1, SurfPoint o2) {
            return (Math.pow(o1.distance - o2.distance, 2) * 0.5 + 
                    Math.pow(o1.deltaHeading - o2.deltaHeading, 2) + 
                    Math.pow(o1.lateralVelocity - o2.lateralVelocity, 2) * 3 +
                    Math.pow(o1.velocity - o2.velocity, 2) + 
                    Math.pow(o1.guessFactorIndex - o2.guessFactorIndex, 2) * 2);
        }
    };
    
    // fire time comparator
    public static final Comparator<SurfPoint> FIRE_TIME_COMPARATOR = new Comparator<SurfPoint>() {
        public int compare(SurfPoint o1, SurfPoint o2) {
            return (int) (o2.fireTime - o1.fireTime);
        }
    };
    
    // distance comparator
    public static final Comparator<SurfPoint> DISTANCE_COMPARATOR = new Comparator<SurfPoint>() {
        public int compare(SurfPoint o1, SurfPoint o2) {
            if(o1.distance == o2.distance)
                return 0;
            else if(o1.distance < o2.distance)
                return -1;
            else
                return 1;
        } 
    };
    
    // delta heading comparator
    public static final Comparator<SurfPoint> DELTA_HEADING_COMPARATOR = new Comparator<SurfPoint>() {
        public int compare(SurfPoint o1, SurfPoint o2) {
            if(o1.deltaHeading == o2.deltaHeading)
                return 0;
            else if(o1.deltaHeading < o2.deltaHeading)
                return -1;
            else
                return 1;
        }  
    };
    
    // velocity comparator
    public static final Comparator<SurfPoint> VELOCITY_COMPARATOR = new Comparator<SurfPoint>() {
        public int compare(SurfPoint o1, SurfPoint o2) {
            if(o1.velocity == o2.velocity)
                return 0;
            else if(o1.velocity < o2.velocity)
                return -1;
            else
                return 1;
        } 
    };
    
    // velocity comparator
    public static final Comparator<SurfPoint> LATERAL_VELOCITY_COMPARATOR = new Comparator<SurfPoint>() {
        public int compare(SurfPoint o1, SurfPoint o2) {
            if(o1.lateralVelocity == o2.lateralVelocity)
                return 0;
            else if(o1.lateralVelocity < o2.lateralVelocity)
                return -1;
            else
                return 1;
        } 
    };
    
    
    // velocity comparator
    public static final Comparator<SurfPoint> GF_INDEX_COMPARATOR = new Comparator<SurfPoint>() {
        public int compare(SurfPoint o1, SurfPoint o2) {
            if(o1.guessFactorIndex == o2.guessFactorIndex)
                return 0;
            else if(o1.guessFactorIndex < o2.guessFactorIndex)
                return -1;
            else
                return 1;
        }   
    };
    
    /**
     * @return a list of all the comparators
     */
    public static List<Comparator<SurfPoint>> getComparators() {
        List<Comparator<SurfPoint>> comparators = new ArrayList<Comparator<SurfPoint>>();
        //comparators.add(GF_INDEX_COMPARATOR);
        comparators.add(DELTA_HEADING_COMPARATOR);
        comparators.add(VELOCITY_COMPARATOR);
        comparators.add(LATERAL_VELOCITY_COMPARATOR);
        comparators.add(DISTANCE_COMPARATOR);
        return comparators;
    }
    
    public static long FIRE_TIME = 1;
    
    public static long getFireTime() {
        return FIRE_TIME++;
    }
    
    public double distance;
    public double deltaHeading;
    public double velocity;
    public double lateralVelocity;
    public double guessFactorIndex;
    public double weight;
    public long fireTime;
    
    public SurfPoint(EnemyWave wave) {
        RampantRobotState state = wave.targetStateAtFireTime;
        if(state != null) {
            distance = state.distance;
            deltaHeading = state.heading - state.lastHeading;
            velocity = state.velocity;
            lateralVelocity = velocity * Math.sin(state.heading - state.absoluteBearing); 
        }
        fireTime = getFireTime();
    }
    
    
}
