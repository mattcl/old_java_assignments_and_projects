/**
 * EnemyState.java
 */
package rampancy_old.util;

import java.awt.geom.Point2D;

import rampancy_old.util.*;

/**
 * This class contains the state information for a given enemy
 * @author Matthew Chun-Lum
 *
 */
public class EnemyState extends RobotState {

    public double lastAbsoluteBearing;
    public double energy;
    public double lastEnergy;
    public double bulletPower;
    public long timeSinceVelocityChange;
    public int moveTimes;
    public int directionTraveling;
    
    public EnemyState(EnemyRobot enemy) {
        super(enemy.getLocation(), 
                enemy.getAbsoluteBearing(),
                enemy.getHeading(), 
                enemy.getLastHeading(), 
                enemy.getDistance(), 
                enemy.getLastDistance(), 
                enemy.getVelocity(), 
                enemy.getLastVelocity());
        
        lastAbsoluteBearing = enemy.getLastAbsoluteBearing();
        energy = enemy.getLastEnergy();
        bulletPower = enemy.getBulletPower();
        moveTimes = enemy.getMoveTimes();
        directionTraveling = enemy.getDirectionTraveling();
        timeSinceVelocityChange = enemy.getTimeSinceVelocityChange();
    }
}
