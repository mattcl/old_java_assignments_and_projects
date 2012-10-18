/**
 * EnemyListener.java
 */
package rampancy_old.management;

import rampancy_old.util.*;

/**
 * Interface for objects that need to listen to changes with EnemyMovements
 * @author Matthew Chun-Lum
 *
 */
public interface EnemyListener {
    
    public void enemyUpdated(EnemyRobot enemy);
}
