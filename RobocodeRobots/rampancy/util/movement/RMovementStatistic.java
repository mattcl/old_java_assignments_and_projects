/**
 * 
 */
package rampancy.util.movement;

import rampancy.util.RPoint;
import rampancy.util.wave.REnemyWave;

/**
 * @author Matthew Chun-Lum
 *
 */
public interface RMovementStatistic {

    /**
     * @param wave
     * @param location
     * @return the danger associated with the specified wave and location
     */
    public double getDanger(REnemyWave wave, RPoint location);
    
    /**
     * @param wave
     * @return the safest guess factor
     */
    public double getSafestGuessFactor(REnemyWave wave);
    
    /**
     * Logs a hit by a bullet in the movement statistic
     * @param wave
     * @param hitLocation
     */
    public void noteHitByBullet(REnemyWave wave, RPoint hitLocation);
}
