/**
 * WaveManager.java
 */
package rampancy_old.management;

import rampancy_old.*;
import rampancy_old.util.*;

import java.util.*;
import java.awt.*;
import java.awt.geom.Point2D;

import robocode.*;

/**
 * This class manages collections of {@link EnemyWave} and {@link BulletWave}
 * @author Matthew Chun-Lum
 *
 */
public class WaveManager implements EnemyListener, TargetingManagerListener {
    private ArrayList<EnemyWave> enemyWaves;
    private ArrayList<BulletWave> bulletWaves;
    
    private RampantRobot reference;
    
    /**
     * Default constructor
     */
    public WaveManager() {

    }
    
    /**
     * Sets the initial state by assigning the reference
     * and dumping the old wave arrays.
     * @param reference
     */
    public void setInitialState(RampantRobot reference) {
        this.reference = reference;
        enemyWaves = new ArrayList<EnemyWave>();
        bulletWaves = new ArrayList<BulletWave>();
    }
    
    public void createVirtualWave(EnemyRobot enemy) {
        if(enemyWaves.isEmpty())
            enemyWaves.add(enemy.getVirtualBullet());
    }
    
    /**
     * @return the closest EnemyWave that we could surf
     */
    public EnemyWave getClosestEnemyWave() {
        double closest = 10000000;
        EnemyWave closestWave = null;
        
        for(EnemyWave wave : enemyWaves) {
            double dist = wave.distanceFrom(reference.getLocation());
            if(dist > wave.getVelocity() && dist < closest) {
                closestWave = wave;
                closest = dist;
            }
        }
        
        return closestWave;
    }
    
    /**
     * @param location
     * @return the enemy wave that will hit first. 
     * This is not necessarily the closest wave since
     * a faster wave will hit us first.
     */
    public EnemyWave getClosestEnemyWaveToImpact(Point2D.Double location) {
        return getClosestEnemyWaveToImpact(location, null);
    }
    
    /**
     * @param location
     * @param ignoreWave
     * @return the enemy wave that will hit first. 
     * This is not necessarily the closest wave since
     * a faster wave will hit us first.
     */
    public EnemyWave getClosestEnemyWaveToImpact(Point2D.Double location, EnemyWave ignoreWave) {
        return getClosestEnemyWaveToImpact(location, ignoreWave, 0);
    }
    
    /**
     * @param location
     * @param ignoreWave
     * @param timeOffset
     * @return the enemy wave that will hit first. 
     * This is not necessarily the closest wave since
     * a faster wave will hit us first.
     */
    public EnemyWave getClosestEnemyWaveToImpact(Point2D.Double location, EnemyWave ignoreWave, long timeOffset) {
        long timeToImpact = 1000000;
        EnemyWave closest = null;
        
        for(EnemyWave wave : enemyWaves) {
            if(ignoreWave == null || !wave.equals(ignoreWave)) {
                long time = wave.timeToImpact(location, timeOffset);
                if(time < timeToImpact && time >= 0) {
                    closest = wave;
                    timeToImpact = time;
                }
            }
        }
        
        return closest;
    }
    
    
    
    public int getNumWaves() {
        return enemyWaves.size();
    }
    
    /**
     * Processes a bullet hit event.
     * Finds the wave that hit us and passes the information
     * on to the statistics manager
     * @param e
     */
    public void processHitEvent(HitByBulletEvent e) {
        if(!enemyWaves.isEmpty()) {
            Point2D.Double hitLocation = new Point2D.Double(e.getBullet().getX(), e.getBullet().getY());
            EnemyWave hitWave = null;
            for(EnemyWave wave : enemyWaves) {
                if(Math.abs(wave.getDistanceTraveled() - reference.getLocation().distance(wave.getOrigin())) < 50 &&
                        Math.round(Util.computeBulletVelocity(e.getBullet().getPower()) * 10) == Math.round(wave.getVelocity() * 10)) {
                    hitWave = wave;
                    break;
                }
            }
            
            if(hitWave != null) {
                reference.getStatisticsManager().processHitByBulletEvent(e.getName(), hitWave, hitLocation);
                enemyWaves.remove(hitWave);
            }
        }
    }
    
    /**
     * Updates the waves in the list
     */
    public void update() {
        for(int i = enemyWaves.size() - 1; i >= 0; i--) {
            EnemyWave wave = enemyWaves.get(i);
            wave.update(reference.getTime());
            if(wave.didBreak(reference.getLocation())) {
                //reference.getStatisticsManager().getMovementStatsForEnemy(wave.getCreator().getName()).logVirtualHit(wave, reference.getLocation());
                enemyWaves.remove(i);
            }
        }
        
        for(int i = bulletWaves.size() - 1; i >= 0; i--) {
            BulletWave wave = bulletWaves.get(i);
            if(wave.checkHit(reference.getTime()))
                bulletWaves.remove(i);
        }
    }
    
    /**
     * If an enemy has been updated, check if it has fired.
     * If it has, then add the wave to the list of EnemyWaves
     */
    public void enemyUpdated(EnemyRobot enemy) {
        if(enemy.hasFired()) {
            removeVirtualWaves();
            enemyWaves.add(0, enemy.getLastShot());
        }
    }
    
    /* (non-Javadoc)
     * @see rampancy.management.TargetingManagerListener#bulletFired(rampancy.util.BulletWave)
     */
    public void bulletFired(BulletWave wave) {
        bulletWaves.add(wave);
    }
    
    /* (non-Javadoc)
     * @see rampancy.management.TargetingManagerListener#bulletsFired(java.util.ArrayList)
     */
    public void bulletsFired(ArrayList<BulletWave> waves) {
        bulletWaves.addAll(waves);
    } 
    
    /**
     * Draws the waves
     * @param g
     */
    public void draw(Graphics2D g) {
        for(EnemyWave wave : enemyWaves)
            wave.draw(g);
        
        for(BulletWave wave : bulletWaves)
            wave.draw(g);
    }   
    
    // --------------- Private ---------------- //
    
    /*
     * Removes the virtual waves from the list
     */
    private void removeVirtualWaves() {
        Iterator<EnemyWave> waves = enemyWaves.iterator();
        while(waves.hasNext()) {
            EnemyWave wave = waves.next();
            if(wave.isVirtual())
                waves.remove();
        }
    }
}
