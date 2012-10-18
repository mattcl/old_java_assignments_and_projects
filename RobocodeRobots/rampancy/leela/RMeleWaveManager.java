/**
 * 
 */
package rampancy.leela;

import rampancy.RMovementManager;
import rampancy.RWaveManager;
import rampancy.RampantRobot;
import rampancy.util.*;
import rampancy.util.wave.RBulletWave;
import rampancy.util.wave.REnemyWave;
import rampancy.util.wave.REnemyWaveWithStats;
import rampancy.util.wave.RWave;
import robocode.Bullet;

import java.awt.Graphics2D;
import java.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RMeleWaveManager implements RWaveManager {

     protected ArrayList<REnemyWave> enemyWaves;
     protected ArrayList<RBulletWave> bulletWaves;
     protected RampantRobot reference;
     
     /**
      * Constructor
      */
     public RMeleWaveManager(RampantRobot reference) {
         enemyWaves = new ArrayList<REnemyWave>();
         bulletWaves = new ArrayList<RBulletWave>();
         this.reference = reference;
     }
     
     public void updateReference(RampantRobot reference) {
         this.reference = reference;
     }
     
     public List<REnemyWave> getEnemyWaves() {
         return enemyWaves;
     }
     
     /* (non-Javadoc)
      * @see rampancy_v2.RWaveManager#update()
      */
     public void update() {;
         long time = reference.getTime();
         for(int i = enemyWaves.size() - 1; i >= 0; i--) {
             REnemyWave wave = enemyWaves.get(i);
             wave.update(time);
             if(wave.didBreak(reference.getLocation())) {
                 RMovementManager movementManager = RampantRobot.getMovementManager();
                 if(movementManager instanceof RMeleMovementManager) {
                     ((RMeleMovementManager) movementManager).removeWave(wave);
                 }
                 enemyWaves.remove(wave);
                 // do other stuff
             }
                 
         }
         
         for(int i = bulletWaves.size() - 1; i >= 0; i--) {
             RBulletWave wave = bulletWaves.get(i);
             wave.update(time);
             if(wave.didBreak()) {
                 RampantRobot.getStatisticsManager().getTargetingStatistics().noteHitOnEnemy(wave.getTarget(), wave);
                 RampantRobot.getTargetingManager().updateGuns(wave);
                 bulletWaves.remove(wave);
             }
         }
     }
    
    /* (non-Javadoc)
     * @see rampancy.RWaveManager#add(rampancy.util.RWave)
     */
    public void add(RWave wave) {
        if(wave instanceof REnemyWave)
            enemyWaves.add((REnemyWave) wave);
        
        if(wave instanceof RBulletWave)
            bulletWaves.add((RBulletWave) wave);

    }

    /* (non-Javadoc)
     * @see rampancy.RWaveManager#getClosestEnemyWave()
     */
    public REnemyWave getClosestEnemyWave() {
        RPoint location = reference.getCopyOfLocation();
        return getClosestEnemyWave(location);
    }

    /* (non-Javadoc)
     * @see rampancy.RWaveManager#getClosestEnemyWave(rampancy.util.RPoint)
     */
    public REnemyWave getClosestEnemyWave(RPoint location) {
        double closest = 500000;
        REnemyWave closestWave = null;
        for(REnemyWave wave : enemyWaves) {
            double time = wave.timeToImpact(location);
            if(time < closest) {
                closest = time;
                closestWave = wave;
            }
        }
        
        return closestWave;
    }
    

    /* (non-Javadoc)
     * @see rampancy_v2.RWaveManager#getWaveForBullet(robocode.Bullet)
     */
    public REnemyWave getWaveForEnemyBullet(Bullet bullet) {
        RPoint referenceLocation = reference.getCopyOfLocation();
        REnemyWave hitWave = null;
        for(REnemyWave wave : enemyWaves) {
            if(Math.abs(wave.getDistanceTraveled() - referenceLocation.distance(wave.getOrigin())) < 50 &&
                    Math.round(RUtil.computeBulletVelocity(bullet.getPower()) * 10) == Math.round(wave.getVelocity() * 10)) {
                hitWave = wave;
                break;
            }
        }
        return hitWave;
    } 

    /* (non-Javadoc)
     * @see rampancy.RWaveManager#getClosestWave()
     */
    public RWave getClosestWave() {
        RPoint location = reference.getCopyOfLocation();
        return getClosestWave(location);
    }

    /* (non-Javadoc)
     * @see rampancy.RWaveManager#getClosestWave(rampancy.util.RPoint)
     */
    public RWave getClosestWave(RPoint location) {
        double closest = 500000;
        RWave closestWave = null;
        for(RWave wave : bulletWaves) {
            double time = wave.timeToImpact(location);
            if(time < closest) {
                closest = time;
                closestWave = wave;
            }
        }
        
        return closestWave;
    }
    
    /* (non-Javadoc)
     * @see rampancy_v2.RWaveManager#getWaveForBullet(robocode.Bullet)
     */
    public RBulletWave getWaveForBullet(Bullet bullet) {
        double closest = 50000;
        RBulletWave closestWave = null;
        for(RBulletWave wave : bulletWaves) {
            double time = wave.timeToImpact(new RPoint(bullet.getX(), bullet.getY()));
            if(time < closest) {
                closest = time;
                closestWave = wave;
            }
        }
        return closestWave;
    }

    /* (non-Javadoc)
     * @see rampancy_v2.RWaveManager#clearWaves()
     */
    public void clearWaves() {
        enemyWaves.clear();
        bulletWaves.clear();
    }   
    
    /* (non-Javadoc)
     * @see rampancy_v2.util.REnemyListener#shotFired(rampancy_v2.util.REnemyRobot)
     */
    public void shotFired(REnemyRobot enemy) {
        add(new REnemyWaveWithStats(enemy));
        removeVirtualEnemyWaves();
    }
    
    /* (non-Javadoc)
     * @see rampancy_v2.util.REnemyListener#enemyUpdated(rampancy_v2.util.REnemyRobot)
     */
    public void enemyUpdated(REnemyRobot enemy) { /* Ignored */ }

    /* (non-Javadoc)
     * @see rampancy_v2.RWaveManager#draw(java.awt.Graphics2D)
     */
    public void draw(Graphics2D g) {
        for(REnemyWave wave : enemyWaves)
            wave.draw(g);
        
        for(RBulletWave wave : bulletWaves)
            wave.draw(g);
    }
    
    private void removeVirtualEnemyWaves() {
        for(int i = enemyWaves.size() - 1; i >= 0; i--) {
            if(enemyWaves.get(i).isVirtual()) {
                enemyWaves.remove(i);
            }
        }
    }
}
