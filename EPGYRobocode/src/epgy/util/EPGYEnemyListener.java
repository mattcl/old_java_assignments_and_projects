/**
 * 
 */
package epgy.util;

/**
 * Specifies the criteria for an class to be an EPGYEnemyListener
 * @author Matthew Chun-Lum
 *
 */
public interface EPGYEnemyListener {

    /**
     * Invoked when an enemyRobot is updated
     * @param enemy
     */
    public abstract void enemyUpdated(EPGYEnemyRobot enemy);
    
    /**
     * Invoked when an enemyRobot fires a shot
     * @param enemy
     */
    public abstract void shotFired(EPGYEnemyRobot enemy);
}
