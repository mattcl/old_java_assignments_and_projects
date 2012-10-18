/**
 * REnemyManager.java
 */
package rampancy;

import rampancy.util.*;

import java.awt.*;
import java.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public interface REnemyManager {

    public void updateReference(RampantRobot reference);
    
    /**
     * If the enemy is not being tracked, add a new {@link REnemyRobot}
     * instance and return that. Otherwise, simply retrieve the enemy
     * that corresponds to the passed name.
     * @param name the name of the enemy to look for
     * @return the {@link REnemyRobot} instance that corresponds
     * to the passed name
     */
    public REnemyRobot findOrCreateByName(String name);
    
    /**
     * Adds a enemy to the enemy manager. Will replace the old enemy if it exists
     * @param name the name of the enemy to add to the manager
     * @return the {@link REnemyRobot} instance for the new enemy
     */
    public REnemyRobot add(String name);
    
    /**
     * @param name the name of the enemy to retrieve
     * @return a {@link REnemyRobot} or {@code null} if the
     * enemy is not being tracked by the manager
     */
    public REnemyRobot get(String name);
    
    /**
     * @param name the name of the enemy to remove from the manager
     * @return {@code true} if the enemy was present
     */
    public boolean remove(String name);
    
    /**
     * Resets the tracking arrays and states of all enemy robots
     * currently tracked by the manager
     */
    public void resetAll();
    
    /**
     * @param g
     */
    public void draw(Graphics2D g);
}
