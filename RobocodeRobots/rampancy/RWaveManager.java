/**
 * 
 */
package rampancy;

import rampancy.util.*;
import rampancy.util.wave.RBulletWave;
import rampancy.util.wave.REnemyWave;
import rampancy.util.wave.RWave;
import robocode.Bullet;

import java.awt.Graphics2D;
import java.util.List;

/**
 * @author Matthew Chun-Lum
 *
 */
public interface RWaveManager extends REnemyListener{

    public void updateReference(RampantRobot reference);
    
    /**
     * Adds a wave to the wave manager
     * @param wave the wave to add to the wave manager
     */
    public void add(RWave wave);
    
    /**
     * @return the closest wave to the RampantRobot's current location
     */
    public RWave getClosestWave();
    
    /**
     * @param location
     * @return the closest wave to the given location
     */
    public RWave getClosestWave(RPoint location);
    
    /**
     * @param bullet
     * @return the bullet wave corresponding to the given bullet
     */
    public RBulletWave getWaveForBullet(Bullet bullet);
    
    /**
     * @return the closest wave to the RampantRobot's current location
     */
    public REnemyWave getClosestEnemyWave();
    
    /**
     * @param location
     * @return the closest wave to the given location
     */
    public REnemyWave getClosestEnemyWave(RPoint location);
    
    /**
     * @param bullet
     * @return the enemy wave that corresponds to the given bullet
     */
    public REnemyWave getWaveForEnemyBullet(Bullet bullet);
    
    /**
     * @return a list of the enemy waves
     */
    public List<REnemyWave> getEnemyWaves();
    
    /**
     * updates the positions of all waves
     */
    public void update();
    
    /**
     * Removes all the waves from the manager
     */
    public void clearWaves();
    
    /**
     * @param g
     */
    public void draw(Graphics2D g);

}
