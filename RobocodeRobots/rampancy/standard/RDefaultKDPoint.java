/**
 * 
 */
package rampancy.standard;

import java.util.Comparator;
import java.util.*;

import rampancy.util.*;
import rampancy.util.data.kdTree.KDPoint;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RDefaultKDPoint extends KDPoint {
    
    public static final int WRONG_POINT_TYPE = 1000000;
    
    public RRobotState enemyState;
    public double guessFactor;
    
    public RDefaultKDPoint(RRobotState state, double guessFactor) {
        enemyState = state.getCopy();
        this.guessFactor = guessFactor;
    }
    
    /* (non-Javadoc)
     * @see rampancy.util.data.kdTree.KDPoint#distanceTo(rampancy.util.data.kdTree.KDPoint)
     */
    @Override
    public double distanceTo(KDPoint target) {
        if(target instanceof RDefaultKDPoint) {
            RDefaultKDPoint t = (RDefaultKDPoint) target;
            RRobotState state = t.enemyState;
            double dist = RUtil.square(enemyState.lateralVelocity - state.lateralVelocity);
            dist += RUtil.square(enemyState.advancingVelocity - state.advancingVelocity);
            dist += RUtil.square(enemyState.deltaH - state.deltaH);
            dist += RUtil.square(enemyState.velocity - state.velocity);
            dist += RUtil.square(enemyState.distanceFromWallCategory * 2 - state.distanceFromWallCategory * 2);
            dist += RUtil.square(enemyState.timeSinceDirectionChange - state.timeSinceDirectionChange);
            return dist;
        }
        return WRONG_POINT_TYPE;
    }
    
    // ------------ Comparison Functions -------------- //
    
    public static List<Comparator<RDefaultKDPoint> > getComparators() {
        ArrayList<Comparator<RDefaultKDPoint> > comparators = new ArrayList<Comparator<RDefaultKDPoint> >();
        comparators.add(VELOCITY_COMPARE);
        comparators.add(LATERAL_VEL_COMPARE);
        comparators.add(DELTA_H_COMPARE);
        comparators.add(DISTANCE_FROM_WALL_COMPARE);
        comparators.add(TIME_SINCE_DIRECTION_CHANGE_COMPARE);
        comparators.add(ADV_VEL_COMPARE);
        return comparators;
    }
    
    public static final Comparator<RDefaultKDPoint> VELOCITY_COMPARE = new Comparator<RDefaultKDPoint>() {
        public int compare(RDefaultKDPoint s1, RDefaultKDPoint s2) {
            double res = s1.enemyState.velocity - s2.enemyState.velocity;
            return RUtil.sign(res);
        }
    };
    
    public static final Comparator<RDefaultKDPoint> LATERAL_VEL_COMPARE = new Comparator<RDefaultKDPoint>() {
        public int compare(RDefaultKDPoint s1, RDefaultKDPoint s2) {
            double res = s1.enemyState.lateralVelocity - s2.enemyState.lateralVelocity;
            return RUtil.sign(res);
        }
    };
    
    public static final Comparator<RDefaultKDPoint> ADV_VEL_COMPARE = new Comparator<RDefaultKDPoint>() {
        public int compare(RDefaultKDPoint s1, RDefaultKDPoint s2) {
            double res = s1.enemyState.advancingVelocity - s2.enemyState.advancingVelocity;
            return RUtil.sign(res);
        }
    };
    
    public static final Comparator<RDefaultKDPoint> DELTA_V_COMPARE = new Comparator<RDefaultKDPoint>() {
        public int compare(RDefaultKDPoint s1, RDefaultKDPoint s2) {
            double res = s1.enemyState.deltaV - s2.enemyState.deltaV;
            return RUtil.sign(res);
        }
    };
    
    public static final Comparator<RDefaultKDPoint> HEADING_COMPARE = new Comparator<RDefaultKDPoint>() {
        public int compare(RDefaultKDPoint s1, RDefaultKDPoint s2) {
            double res = s1.enemyState.heading - s2.enemyState.heading;
            return RUtil.sign(res);
        }
    };
    
    public static final Comparator<RDefaultKDPoint> DELTA_H_COMPARE = new Comparator<RDefaultKDPoint>() {
        public int compare(RDefaultKDPoint s1, RDefaultKDPoint s2) {
            double res = s1.enemyState.deltaH - s2.enemyState.deltaH;
            return RUtil.sign(res);
        }
    };
    
    public static final Comparator<RDefaultKDPoint> TIME_SINCE_STOP_COMPARE = new Comparator<RDefaultKDPoint>() {
        public int compare(RDefaultKDPoint s1, RDefaultKDPoint s2) {
            double res = s1.enemyState.timeSinceStop - s2.enemyState.timeSinceStop;
            return RUtil.sign(res);
        }
        
    };
    
    public static final Comparator<RDefaultKDPoint> TIME_SINCE_DIRECTION_CHANGE_COMPARE = new Comparator<RDefaultKDPoint>() {
        public int compare(RDefaultKDPoint s1, RDefaultKDPoint s2) {
            double res = s1.enemyState.timeSinceDirectionChange - s2.enemyState.timeSinceDirectionChange;
            return RUtil.sign(res);
        }
        
    };
    
    public static final Comparator<RDefaultKDPoint> TIME_SINCE_VELOCITY_CHANGE = new Comparator<RDefaultKDPoint>() {
        public int compare(RDefaultKDPoint s1, RDefaultKDPoint s2) {
            double res = s1.enemyState.timeSinceVelocityChange - s2.enemyState.timeSinceVelocityChange;
            return RUtil.sign(res);
        }
        
    };
    
    public static final Comparator<RDefaultKDPoint> DISTANCE_COMPARE = new Comparator<RDefaultKDPoint>() {
        public int compare(RDefaultKDPoint s1, RDefaultKDPoint s2) {
            double res = s1.enemyState.distance - s2.enemyState.distance;
            return RUtil.sign(res);
        }
    };
    
    public static final Comparator<RDefaultKDPoint> DISTANCE_FROM_WALL_COMPARE = new Comparator<RDefaultKDPoint>() {
        public int compare(RDefaultKDPoint s1, RDefaultKDPoint s2) {
            double res = s1.enemyState.distanceFromWall - s2.enemyState.distanceFromWall;
            return RUtil.sign(res);
        }
    };
}
