/**
 * TargetingManager.java
 */
package rampancy_old.management;

import rampancy_old.*;
import rampancy_old.statistics.*;
import rampancy_old.util.*;
import rampancy_old.weapons.*;
import robocode.util.Utils;

import java.util.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * This class handles Durandal's targeting
 * @author Matthew Chun-Lum
 *
 */
public class TargetingManager {
    
    private RampantRobot reference;
    
    private ArrayList<TargetingManagerListener> listeners;
    
    private double fireSwitch;
    
    private int numReadings;
    
    public TargetingManager() {
        listeners = new ArrayList<TargetingManagerListener>();
        numReadings = 0;
    }
    
    /**
     * Sets the reference to Durandal
     * @param reference
     */
    public void setInitialState(RampantRobot reference) {
        this.reference = reference;
        fireSwitch = 0;
    }
    
    /**
     * Targets the specified enemy robot
     * @param enemy
     */
    public void targetEnemy(EnemyRobot enemy) {
        
        // handle the case where the enemy is disabled
        if(enemy.getEnergy() == 0) {
            reference.setTurnGunRightRadians(Utils.normalRelativeAngle(enemy.getAbsoluteBearing() - reference.getGunHeadingRadians()));
            reference.fire(0.1);
            return;
        }
        
        WeaponStatistic stat = reference.getStatisticsManager().getWeaponStatsForEnemy(enemy.getName());
        FiringSolution firingSolution = stat.getFiringSolution(reference, enemy, 0);
        BulletWave bullet = new BulletWave(enemy, (Point2D.Double) reference.getLocation().clone(), reference.getTime(), firingSolution);

        reference.setTurnGunRightRadians(Utils.normalRelativeAngle(enemy.getAbsoluteBearing() - reference.getGunHeadingRadians() + firingSolution.offsetAngle));

        if(reference.setFireBullet(firingSolution.power) != null) {
            if(firingSolution.anticipated != null)
                System.out.println("Using PM Gun");
            bullet.setWillDraw(true);
            stat.getGFGunStats().noteShotFired();
        }

        notifyListeners(bullet);
    }
    
    // -------- Listener Code ---------- //
    
    /**
     * Adds a listener to the list of listeners
     * @param listener
     */
    public void addListener(TargetingManagerListener listener) {
        if(!listeners.contains(listener))
            listeners.add(listener);
    }
    
    /**
     * Removes a listener from the list if it exists
     * @param listener
     * @return {@code true} if the listener was removed
     */
    public boolean removeListener(TargetingManagerListener listener) {
        return listeners.remove(listener);
    }
    
    /**
     * Notifies the listeners that multiple bullets were fired
     * @param waves
     */
    public void notifyListeners(ArrayList<BulletWave> waves) {
        for(TargetingManagerListener listener : listeners)
            listener.bulletsFired(waves);
    }
    
    /**
     * Notifies the listeners that a single bullet was fired
     * @param wave
     */
    public void notifyListeners(BulletWave wave) {
        for(TargetingManagerListener listener : listeners)
            listener.bulletFired(wave);
    }
}
