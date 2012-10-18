/**
 * StandardGun.java
 */
package rampancy_old.weapons;

import java.awt.Color;

import rampancy_old.util.*;

/**
 * This is the standard gun with a distance-based shot power algorithm
 * @author Matthew Chun-Lum
 *
 */
public abstract class GuessFactorGun {

    public static final String NAME = Constants.STANDARD_GUN_NAME;
    public static final Color DEFAULT_COLOR = Constants.STANDARD_GUN_COLOR;
    public static final int WEAPON_INDEX = Constants.STANDARD_GUN_INDEX;

    public static double computeGuessFactor(double[] segment) {
        int bestindex = (segment.length - 1) / 2;
        for (int i=0; i<segment.length; i++)
            if (segment[bestindex] < segment[i])
                bestindex = i;
        return (double)(bestindex - (segment.length - 1) / 2) / ((segment.length - 1) / 2);
    }

    public static double computeShotPower(EnemyRobot enemy) {
        double shotPower;
        if(enemy.getDistance() < 100) {
            shotPower = 3.0;
        } else {
            shotPower = (1 - enemy.getDistance() / 1500.0) * 3.0;
        }
        
        return Util.limit(0.1, shotPower, 3.0);
    }
}
