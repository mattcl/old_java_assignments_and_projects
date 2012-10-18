/**
 * VariationProfile.java
 */
package rampancy_old.util.tree;

import java.util.Arrays;

import rampancy_old.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class VariationProfile {

    private int[] profile;
    private boolean alternate;
    
    public VariationProfile() {
        profile = new int[Segments.BRANCH_COUNTS_BY_TYPE.length];
        alternate = false;
    }
    
    /**
     * Updates the profile with the passed enemy
     * @param enemy
     */
    public void update(EnemyRobot enemy) {
        EnemyState enemyState = new EnemyState(enemy);
        for(byte i = 0; i < profile.length; i++) {
            int branchIndex = Segments.getBranchIndex(enemyState, i);   
            if(alternate)
                branchIndex = -branchIndex;
            
            profile[i] += branchIndex;
        }
        alternate = !alternate;
    }
    
    /**
     * @return a pointer to the profile array
     */
    public int[] getProfile() {
        return profile;
    }
}
