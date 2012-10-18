/**
 * WeaponStatistic.java
 */
package rampancy_old.statistics;

import java.util.Arrays;

import rampancy_old.RampantRobot;
import rampancy_old.data.*;
import rampancy_old.management.MovementManager;
import rampancy_old.util.*;
import rampancy_old.util.tree.*;
import rampancy_old.weapons.*;

/**
 * This class tracks weapon data for use by the TargetingManager
 * @author Matthew Chun-Lum
 *
 */
public class WeaponStatistic {

    private String enemyName;
    private GuessFactorGunStatistic GFGunStat;
    private PatternMatchingGunStatistic PMGunStat;

    public WeaponStatistic(String enemyName) {
        this.enemyName = enemyName;
        GFGunStat = new GuessFactorGunStatistic();
        PMGunStat = new PatternMatchingGunStatistic();
    }

    /**
     * @param enemy
     * @return a firing solution based on the enemy's current state
     */
    public FiringSolution getFiringSolution(RampantRobot reference, EnemyRobot enemy, int weapon) {
        FiringSolution fs = PMGunStat.getFiringSolution(reference, enemy);
        if(fs.deviation < 0.3 && fs.power > 0.4)
            return fs;
        return GFGunStat.getFiringSolution(enemy);
    }
    
    public GuessFactorGunStatistic getGFGunStats() {
        return GFGunStat;
    }
    
    public PatternMatchingGunStatistic getPMGunStats() {
        return PMGunStat;
    }
    
    /**
     * @return the name of the enemy this stat is monitoring
     */
    public String getEnemyName() {
        return enemyName;
    }


}
