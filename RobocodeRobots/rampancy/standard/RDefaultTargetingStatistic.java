/**
 * 
 */
package rampancy.standard;

import rampancy.RampantRobot;
import rampancy.util.*;
import rampancy.util.data.segmentArray.*;
import rampancy.util.wave.*;
import rampancy.util.weapon.*;
import rampancy_old.util.Util;
import robocode.util.Utils;

import java.awt.Color;
import java.util.*;

/**
 * The default targeting statistic accepts a variety of
 * weapon statistics as input, and more can be added
 * later.
 * 
 * @author Matthew Chun-Lum
 *
 */
public class RDefaultTargetingStatistic implements RTargetingStatistic {
    
    private RDefaultTargetingSegmentArray segmentArray;
    private RDefaultKDTree kdTree;
    private int currentWeapon;
    
    public RDefaultTargetingStatistic() {
        segmentArray = new RDefaultTargetingSegmentArray();
        kdTree = new RDefaultKDTree();
        currentWeapon = 0;
    }
    
    /* (non-Javadoc)
     * @see rampancy_v2.util.weapon.RTargetingStatistic#getBestFiringSolutionForEnemy(rampancy_v2.util.REnemyRobot, double)
     */
    public RFiringSolution getBestFiringSolutionForEnemy(REnemyRobot enemy,
            double currentBearingToTarget) {
//        RRobotState enemyState = enemy.getCurrentState();
//        
//        double guessFactor = getBestGuessFactorForEnemy(enemy);
//        
//        double power;
//        if(enemy.getCurrentState().distance < 100) {
//            power = 3.0;
//        } else {
//            power = (1 - enemy.getCurrentState().distance / 1500.0) * 3.0;
//        }
//        
//        power =  Util.limit(0.1, power, 3.0);
//
//        double offsetAngle = enemyState.directionTraveling * guessFactor * RUtil.computeMaxEscapeAngle(RUtil.computeBulletVelocity(power));
//        offsetAngle = Utils.normalRelativeAngle(offsetAngle);
//        RFiringSolution guessFactorSolution = new RFiringSolution(power, currentBearingToTarget + offsetAngle, 0, guessFactor, Color.green, "guess factor gun");
//        RFiringSolution pmFiringSolution = OldRPatternMatchingGun.getFiringSolution(enemy, RampantRobot.getGlobalReference().getGunHeadingRadians());
//        if(pmFiringSolution == null || pmFiringSolution.reliabilityFactor > 0.02) {
//            return guessFactorSolution;
//        } else {
//            return pmFiringSolution;
//        }
            return null;
    }

    /* (non-Javadoc)
     * @see rampancy_v2.util.weapon.RTargetingStatistic#noteHitOnEnemy(rampancy_v2.util.REnemyRobot, rampancy_v2.util.wave.RBulletWave)
     */
    public void noteHitOnEnemy(REnemyRobot enemy, RBulletWave wave) {
        updateSegmentArray(enemy, wave);
        updateKDTree(enemy, wave);
    }

    // -------------- Private Helpers ---------------- //
    
    private void updateSegmentArray(REnemyRobot enemy, RBulletWave wave) {
        RLeafSegment leaf = segmentArray.getSegmentForState(wave.getTargetState());
        double desiredDirection = RUtil.computeAbsoluteBearing(wave.getOrigin(), wave.getTarget().getLastState().location);
        double angleOffset = Utils.normalRelativeAngle(desiredDirection - wave.getTargetState().absoluteBearing);
        double guessFactor = Math.max(-1, Math.min(1, angleOffset / RUtil.computeMaxEscapeAngle(wave.getVelocity()))) * wave.getTargetState().directionTraveling;
        int index = RUtil.computeBin(guessFactor, leaf.guessFactorArray.length);
        RLeafSegment.updateGuessFactors(leaf, index, 1.0, 2);
    }
    
    private void updateKDTree(REnemyRobot enemy, RBulletWave wave) {
        double desiredDirection = RUtil.computeAbsoluteBearing(wave.getOrigin(), wave.getTarget().getLastState().location);
        double angleOffset = Utils.normalRelativeAngle(desiredDirection - wave.getTargetState().absoluteBearing);
        double guessFactor = Math.max(-1, Math.min(1, angleOffset / RUtil.computeMaxEscapeAngle(wave.getVelocity()))) * wave.getTargetState().directionTraveling;
        kdTree.addPoint(wave.getTargetState(), guessFactor);
    }
    
    /*
     * compute the best guess factor for the given enemy
     */
    private double getBestGuessFactorForEnemy(REnemyRobot enemy) {
        RLeafSegment leaf = segmentArray.getSegmentForState(enemy.getCurrentState());
        
        // for now, just do this, modify for more guns later
        int bestIndex = RUtil.indexOfLargest(leaf.guessFactorArray);
        double factor = RUtil.getGuessFactorForIndex(bestIndex, leaf.guessFactorArray.length);
        
        return factor;
    }

}
