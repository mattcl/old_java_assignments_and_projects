/**
 * EnemyManager.java
 */
package rampancy_old.management;

import rampancy_old.*;
import rampancy_old.util.*;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

import java.util.*;
import java.awt.*;

/**
 * This class tracks enemy movements and provides updates
 * to objects listening to enemy movements.
 * @author Matthew Chun-Lum
 *
 */
public class EnemyManager {

    private HashMap<String, EnemyRobot> enemies;
    
    private RampantRobot reference;
    
    /**
     * Default constructor
     */
    public EnemyManager() {
        enemies = new HashMap<String, EnemyRobot>();
    }
    
    /**
     * Processes a ScannedRobotEvent
     * @param e
     * @return the EnemyRobot scanned
     */
    public EnemyRobot processScannedRobotEvent(ScannedRobotEvent e) {
        EnemyRobot enemy;
        if(enemies.containsKey(e.getName())) {
            enemy = getEnemy(e.getName());
            enemy.update(e);
            enemy.updateVariationProfile();
            reference.getStatisticsManager().getWeaponStatsForEnemy(e.getName()).getPMGunStats().logState(enemy);
        } else {
            enemy = new EnemyRobot(e, reference);
            enemy.addListener(reference.getWaveManager());
            addEnemy(enemy);
        }
        return enemy;
    }
    
    /**
     * Gets an enemy from the map
     * @param name the name of the enemy
     * @return an <code>EnemyRobot</code> or <code>null</code>
     */
    public EnemyRobot getEnemy(String name) {
        return enemies.get(name);
    }
    
    /**
     * Adds an enemy to the map of enemies if the enemy is not already being tracked
     * @param enemy the enemy to add
     * @return <code>true</code> if the enemy was added to the map
     */
    public boolean addEnemy(EnemyRobot enemy) {
        if(enemies.containsKey(enemy.getName()))
            return false;
        
        enemies.put(enemy.getName(), enemy);
        reference.getStatisticsManager().addEnemy(enemy);
        System.out.println("EnemyManager: Tracking: " + enemy.getName());
        return true;
    }
    
    /**
     * Removes an enemy from the map
     * @param enemy the enemy to remove
     * @return <code>true</code> if the enemy was removed
     */
    public boolean removeEnemy(EnemyRobot enemy) {
        return removeEnemy(enemy.getName());
    }
    
    /**
     * Removes an enemy from the map
     * @param name the name of the enemy to remove
     * @return <code>true</code> if the enemy was removed
     */
    public boolean removeEnemy(String name) {
        return (enemies.remove(name) != null);
    }
    
    /**
     * Sets the reference to Durandal 
     * @param reference
     */
    public void setInitialState(RampantRobot reference) {
        this.reference = reference;
        for(EnemyRobot enemy : enemies.values())
            enemy.setReference(reference);
    }
    
    public void draw(Graphics2D g) {
        if(!enemies.isEmpty())
            for(EnemyRobot enemy : enemies.values())
                enemy.draw(g);
    }
    
    /**
     * Returns a string representation of the EnemyManager
     */
    public String toString() {
        return "EnemyManager: Tracked enemies: " + enemies.size() + " ";
    }
}
