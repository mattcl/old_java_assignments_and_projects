/**
 * 
 */
package rampancy.util.weapon;

import rampancy.util.REnemyRobot;
import rampancy.util.wave.RBulletWave;

/**
 * @author Matthew Chun-Lum
 *
 */
public interface RTargetingStatistic {

    /**
     * Computes the best firing solution for the given enemy
     * @param enemy
     * @param currentGunHeading
     * @return the best firing solution
     */
    public RFiringSolution getBestFiringSolutionForEnemy(REnemyRobot enemy, double currentGunHeading);
    
    /**
     * Notes a hit on an enemy robot with a given wave
     * @param enemy
     * @param wave
     */
    public void noteHitOnEnemy(REnemyRobot enemy, RBulletWave wave);
}
