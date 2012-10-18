/**
 * DefaultEnemyManager.java
 */
package rampancy.standard;

import rampancy.*;
import rampancy.util.*;

import java.awt.Graphics2D;
import java.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RDefaultEnemyManager implements REnemyManager {
    
    private HashMap<String, REnemyRobot> enemies;
    private RampantRobot reference;
    
    public RDefaultEnemyManager(RampantRobot reference) {
        enemies = new HashMap<String, REnemyRobot>();
        this.reference = reference;
    }
    
    public void updateReference(RampantRobot reference) {
        this.reference = reference;
        for(REnemyRobot enemy : enemies.values())
            enemy.updateReference(reference);
    }

    /* (non-Javadoc)
     * @see rampancy.REnemyManager#add(java.lang.String)
     */
    public REnemyRobot add(String name) {
        System.out.println("Tracking enemy: " + name);
        enemies.put(name, new REnemyRobot(name, reference));
        REnemyRobot enemy = enemies.get(name);
        enemy.addListener(RampantRobot.getWaveManager());
        return enemy;
    }

    /* (non-Javadoc)
     * @see rampancy.REnemyManager#findOrCreateByName(java.lang.String)
     */
    public REnemyRobot findOrCreateByName(String name) {
        if(enemies.containsKey(name))
            return get(name);
        return add(name);
    }

    /* (non-Javadoc)
     * @see rampancy.REnemyManager#get(java.lang.String)
     */
    public REnemyRobot get(String name) {
        return enemies.get(name);
    }

    /* (non-Javadoc)
     * @see rampancy.REnemyManager#remove(java.lang.String)
     */
    public boolean remove(String name) {
        return (enemies.remove(name) != null);
    }
    
    /* (non-Javadoc)
     * @see rampancy.REnemyManager#resetAll()
     */
    public void resetAll() {
        for(REnemyRobot enemy : enemies.values())
            enemy.resetState();
    }

    /* (non-Javadoc)
     * @see rampancy.REnemyManager#draw(rampancy.Graphcs2D)
     */
    public void draw(Graphics2D g) {
        for(REnemyRobot enemy : enemies.values())
            enemy.draw(g);
    }

}
