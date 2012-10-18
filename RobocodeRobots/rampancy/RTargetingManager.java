/**
 * RTargetingManager.java
 */
package rampancy;

/**
 * @author Matthew Chun-Lum
 *
 */
import rampancy.util.*;
import rampancy.util.wave.RBulletWave;
import rampancy.util.weapon.RFiringSolution;

import java.awt.Graphics2D;
import java.util.*;

public interface RTargetingManager {
    
    /**
     * @param enemy
     * @return the best firing solution given a specified enemy
     */
    public List<RFiringSolution> getBestFiringSolutions(REnemyRobot enemy);
    
    /**
     * Updates the targeting manager's guns
     * @param wave
     */
    public void updateGuns(RBulletWave wave);
    
    /**
     * Perform updates to the targeting manager at the beginning of every round
     */
    public void updateNewRound();
    
    /**
     * Draws targeting data on-screen
     * @param g
     */
    public void draw(Graphics2D g);

}
