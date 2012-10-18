/**
 * GuessFactorGunStatistic.java
 */
package rampancy_old.statistics;
import rampancy_old.util.*;
import rampancy_old.util.tree.*;
import rampancy_old.weapons.*;

/**
 * This class tracks weapon data for use by the TargetingManager
 * @author Matthew Chun-Lum
 *
 */
public class GuessFactorGunStatistic {

    private SegmentTree segmentTree;
    private int shotsFired;
    private int shotsHit;

    public GuessFactorGunStatistic() {
        segmentTree = new SegmentTree();
        shotsFired = 0;
    }

    /**
     * @param enemy
     * @return a firing solution based on the enemy's current state
     */
    public FiringSolution getFiringSolution(EnemyRobot enemy) {
        EnemyState enemyState = new EnemyState(enemy);
        int[] profile = enemy.getVariationProfile().getProfile();
        Segment segment = segmentTree.getSegment(enemyState, profile);
        if(segment == null) {
            System.out.println("SEGMENT IS NULL!");
        }
        double guessFactor = GuessFactorGun.computeGuessFactor(segment.guessFactors);
        double power = GuessFactorGun.computeShotPower(enemy);
        double offsetAngle = enemy.getDirectionTraveling() * guessFactor * Util.computeMaxEscapeAngle(Util.computeBulletVelocity(power));
        return new FiringSolution(enemyState, offsetAngle, power, segment, GuessFactorGun.DEFAULT_COLOR);
    }
    
    /**
     * @return the number of generated branches on the tree
     */
    public int getNumGeneratedBranches() {
        return segmentTree.getNumBranches();
    }
    
    /**
     * @return the number of terminal branches on the tree
     */
    public int getNumTerminalBranches() {
        return segmentTree.getNumTerminalBranches();
    }
    
    /**
     * Increases the shotsFired count
     */
    public void noteShotFired() {
        shotsFired++;
    }
    
    /**
     * @return the number of shots fired
     */
    public int getShotsFired() {
        return shotsFired;
    }
    
    /**
     * Increases the shotsHit count
     */
    public void noteShotHit() {
        shotsHit++;
    }
    
    /**
     * @return the number of shots that hit the enemy
     */
    public int getShotsHit() {
        return shotsHit;
    }
}
