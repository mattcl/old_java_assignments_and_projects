/**
 * 
 */
package rampancy.util.weapon;

import rampancy.util.*;
import rampancy.util.wave.*;

/**
 * @author Matthew Chun-Lum
 *
 */
abstract public class RGun {
    
    protected String name;
    protected RGunStatistic stat = new RGunStatistic();
    
    /**
     * @param pmType
     */
    public RGun(String name) {
        this.name = name;
    }

    /**
     * Given an enemy robot, return a firing solution to aim at that enemy
     * @param enemy
     * @return firing solution
     */
    abstract public RFiringSolution getFiringSolution(REnemyRobot enemy);
    
    /**
     * Update the internal structure of the gun
     * @param wave
     */
    public void update(RBulletWave wave) {
        if(wave.didHitEnemy()) {
            noteHit(!wave.isVirtual());   
        }  
    }
    
    
    
    public void updateNewRound() {
        // OPTIONAL in subclass
    }
    
    /**
     * Note that a shot was fired
     * @param real - whether the fired shot was real or simulated
     */
    public void noteShot(boolean real) {
        if(real)
            stat.realShotsFired++;
        else
            stat.simulatedShotsFired++;
    }
    
    /**
     * Note that a shot hit
     * @param real - whether the shot was real or simulated
     */
    public void noteHit(boolean real) {
        if(real)
            stat.realShotsHit++;
        else
            stat.simulatedShotsHit++;
    }
    
    /**
     * @return the statistics for this gun
     */
    public RGunStatistic getStats() {
        return stat;
    }
    
    public String getName() {
        return name;
    }
    
    public String toString() {
        return name + " real " + stat.getRealHitPercent() + "%, virtual " + stat.getVirtualHitPercent() + "%";
    }
    
    public String getOverview() {
        return toString();
    }
    
    
}
