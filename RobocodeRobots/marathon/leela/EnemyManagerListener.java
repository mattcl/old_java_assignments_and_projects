package marathon.leela;

/**
 * This interface is for objects that need to listen for enemy updates
 * @author Matthew Chun-Lum
 *
 */
public interface EnemyManagerListener {
    
    /**
     * Invoked when an enemy robot is updated. Provides a reference to the robot
     * @param enemy
     */
    public void enemyUpdated(EnemyRobot enemy);
}
