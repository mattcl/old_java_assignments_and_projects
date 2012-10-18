/**
 * RampantRobot.java
 */
package rampancy_old;

import java.awt.geom.Point2D;

import rampancy_old.management.*;
import robocode.AdvancedRobot;

/**
 * @author Matthew Chun-Lum
 *
 */
public abstract class RampantRobot extends AdvancedRobot {

    public double maxVelocity;
    
    public abstract Point2D.Double getLocation();
    public abstract StatisticsManager getStatisticsManager();
    public abstract WaveManager getWaveManager();
    public abstract MovementManager getMovementManager();
    
    /**
     * @param d
     */
    public void noteMaxVelocity(double maxVel) {
        maxVelocity = maxVel;   
    }
    
    public double getMaxVelocity() {
        return maxVelocity;
    }
}
