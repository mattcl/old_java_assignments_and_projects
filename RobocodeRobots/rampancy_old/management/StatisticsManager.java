/**
 * StatisticsManager.java
 */
package rampancy_old.management;

import rampancy_old.*;
import rampancy_old.data.*;
import rampancy_old.statistics.*;
import rampancy_old.util.*;
import rampancy_old.weapons.FiringSolution;
import rampancy_old.weapons.Weapon;
import robocode.HitByBulletEvent;

import java.util.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * This class manages data gathered from enemy robot movements and
 * from Durandal's movement/attack
 * @author Matthew Chun-Lum
 *
 */
public class StatisticsManager implements EnemyListener {
    
    private RampantRobot reference;
    
    private HashMap<String, MovementStatistic> movementStats;
    private HashMap<String, WeaponStatistic> weaponStats;
    
    /**
     * Constructor
     */
    public StatisticsManager() {
        movementStats = new HashMap<String, MovementStatistic>();
        weaponStats = new HashMap<String, WeaponStatistic>();
    }
    
    /**
     * Sets the reference to Durandal
     * @param reference
     */
    public void setInitialState(RampantRobot reference) {
        this.reference = reference;
        if(movementStats.size() > 0)
            for(MovementStatistic stat : movementStats.values())
                stat.rebuildTree();
    }
    
    /**
     * Determines the danger given a wave and direction
     * @param wave
     * @param direction
     * @return the danger of the particular wave
     */
    public double getDanger(Point2D.Double predictedLocation, EnemyWave wave) {
        MovementStatistic enemyStat = movementStats.get(wave.getCreator().getName());
        if(enemyStat == null) {
            System.out.println("EnemyStat NULL error");
            return 0.0;
        }
//        return enemyStat.getFactorForIndex(index);
        return enemyStat.getDanger(wave, predictedLocation);
    }
    
    /**
     * Logs the data for the hit event
     * @param e
     */
    public void processHitByBulletEvent(String name, EnemyWave wave, Point2D.Double target) {
        if(movementStats.containsKey(name)) {
            movementStats.get(name).logHit(wave, target);
            //movementStats.get(name).logVirtualHit(wave, target);
        }
    }
    
    /**
     * @param enemy
     * @return a computed firing solution for the passed enemy
     */
    public FiringSolution getFiringSolution(EnemyRobot enemy, int weapon) {
        if(weaponStats.containsKey(enemy.getName())) {
            return weaponStats.get(enemy.getName()).getFiringSolution(reference, enemy, weapon);
        }
        return null;
    }
    
    /**
     * @param name
     * @return a reference to the weapon statistics object for the specified enemy
     */
    public WeaponStatistic getWeaponStatsForEnemy(String name) {
        return weaponStats.get(name);
    }
    
    public MovementStatistic getMovementStatsForEnemy(String name) {
        return movementStats.get(name);
    }
    
    /**
     * Adds an enemy to the manager
     * @param enemy
     */
    public void addEnemy(EnemyRobot enemy) {
        MovementStatistic moveStat = new MovementStatistic(enemy.getName());
        movementStats.put(enemy.getName(), moveStat);
        enemy.addListener(moveStat);
        
        weaponStats.put(enemy.getName(), new WeaponStatistic(enemy.getName()));
    }
    
    /**
     * Prints out the stats to the console
     */
    public void printBattleStats() {
        String stats = "\nBATTLE STATISTICS:\n";
        for(String key : weaponStats.keySet()) {
            stats += StatisticsAnalyzer.analyze(weaponStats.get(key), movementStats.get(key)).toString();
        }
        System.out.println(stats);
    }
    
    /**
     * Invoked when an enemy that the manager is listening to gets updated
     */
    public void enemyUpdated(EnemyRobot enemy) {
        // TODO Auto-generated method stub
        
    }
    

    
}
