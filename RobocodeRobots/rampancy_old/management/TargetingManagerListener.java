/**
 * TargetingManagerListener.java
 */
package rampancy_old.management;

import java.util.*;

import rampancy_old.util.*;

/**
 * This is the interface for objects that want to listen to the
 * targeting manager
 * @author Matthew Chun-Lum
 *
 */
public interface TargetingManagerListener {
    
    /**
     * Invoked by the targeting manager
     * @param wave
     */
    public void bulletFired(BulletWave wave);
    
    /**
     * Invoked by the targeting manager
     * @param waves
     */
    public void bulletsFired(ArrayList<BulletWave> waves);
}
