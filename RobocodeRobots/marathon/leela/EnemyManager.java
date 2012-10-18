package marathon.leela;

import java.util.*;
import robocode.*;
import robocode.util.*;

/**
 * This class manages and tracks enemy movements, notifying listeners as needed
 * @author Matthew Chun-Lum
 *
 */
public class EnemyManager {
    
    private HashMap<String, EnemyRobot> enemies;
    private ArrayList<EnemyManagerListener> listeners;
    
    private Leela reference;
    
    /**
     * Constructor
     */
    public EnemyManager() {
        enemies = new HashMap<String, EnemyRobot>();
        listeners = new ArrayList<EnemyManagerListener>();
    }
    
    /**
     * Sets the reference to Leela
     * @param ref
     */
    public void setReference(Leela ref) {
        reference = ref;
        if(!enemies.isEmpty())
            for(EnemyRobot enemy : enemies.values())
                enemy.setReference(ref);
    }
    
    /**
     * Given a ScannedRobotEvent, adds a new robot to the enemies map
     * @param e
     */
    public void addEnemy(ScannedRobotEvent e) {
        EnemyRobot enemy = new EnemyRobot(e, reference);
        enemies.put(e.getName(), enemy);
        notifyListeners(enemy);
    }
    
    /**
     * Updates the enemy specified by the ScannedRobotEvent
     * @param e
     */
    public void updateEnemy(ScannedRobotEvent e) {
        if(enemies.containsKey(e.getName())) {
            EnemyRobot enemy = enemies.get(e.getName());
            enemy.update(e);
            notifyListeners(enemy);
        }
    }
    
    /**
     * Returns the enemy robot from the map that corresponds to the key
     * @param name
     * @return
     */
    public EnemyRobot getEnemy(String name) {
        return enemies.get(name);
    }
    
    /**
     * Removes the specified enemy (by name) from the map
     * @param name
     */
    public void removeEnemy(String name) {
        notifyListeners(getEnemy(name));
        enemies.remove(name);
    }
    
    /**
     * Removes the specified robot from the map
     * @param enemy
     */
    public void removeEnemy(EnemyRobot enemy) {
        notifyListeners(enemy);
        enemies.remove(enemy);
    }
    
    /**
     * @param name
     * @return true if the enemies map contains the specified enemy
     */
    public boolean isTrackingEnemy(String name) {
        return enemies.containsKey(name);
    }
    
    /**
     * Notifies all listeners that the passed EnemyRobot has been updated
     * @param enemy
     */
    public void notifyListeners(EnemyRobot enemy) {
        for(EnemyManagerListener listener : listeners)
            listener.enemyUpdated(enemy);
    }
    
    /**
     * Adds the passed listener to the list of listeners. If the listener has
     * previously been added, no action is taken
     * @param listener
     */
    public void addListener(EnemyManagerListener listener) {
        if(!listeners.contains(listener))
            listeners.add(listener);
    }
    
    /**
     * Removes the passed listener from the list of listeners
     * @param listener
     */
    public void removeListener(EnemyManagerListener listener) {
        listeners.remove(listener);
    }
}
