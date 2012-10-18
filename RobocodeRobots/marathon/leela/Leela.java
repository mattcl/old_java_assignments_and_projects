package marathon.leela;

import java.awt.geom.*;
import java.awt.*;
import java.util.*;

import rampancy_old.util.Util;
import robocode.*;
import robocode.util.*;

/**
 * Main robot class.
 * @author Matthew Chun-Lum
 *
 */
public class Leela extends AdvancedRobot {
    
//    private static TargetingManager targetingManager = new TargetingManager();
//    private static EnemyStatisticsManager enemyStatisticsManager = new EnemyStatisticsManager();
    private static MovementManager movementManager = new MovementManager();
    private static EnemyManager enemyManager = new EnemyManager();
    
    private Point2D.Double location;
    private double bearing;
    
    public void run() {
        setInitialState();
    
        while(getRadarTurnRemainingRadians() == 0) {
            turnRadarRightRadians(Constants.MAX_RADAR_TRACKING);
        }
    }
    
    // ----------- INVOKED METHODS --------------- //
    
    public void onScannedRobot(ScannedRobotEvent e) {
        location = new Point2D.Double(getX(), getY());
        bearing = getHeadingRadians();
        
        if(!enemyManager.isTrackingEnemy(e.getName())) {
            enemyManager.addEnemy(e);
        } else {
            enemyManager.updateEnemy(e);
        }
        EnemyRobot enemy = enemyManager.getEnemy(e.getName());
        
        double shotPower;
        if(enemy.getDistance() < 100) {
            shotPower = 3.0;
        } else {
            shotPower = (1 - enemy.getDistance() / 1400.0) * 3.0;
        }
        
        shotPower = Util.limit(0.1, shotPower, 3.0);
        
        setTurnGunRightRadians(enemy.getAbsoluteBearing() - getGunHeadingRadians());
        this.setFire(shotPower);
        
        movementManager.makeMove();
        
        
    }
    
    /**
     * Custom draw actions when the robot is painted
     */
    public void onPaint(Graphics2D g) {
        //movementManager.draw(g);
    }
    
    // ---------- END INVOKED METHODS ----------- //
    
    /**
     * @return a reference to Leela's current location
     */
    public Point2D.Double getLocation() {
        return location;
    }
    
    
    /**
     * Returns a string representation of Leela
     */
    public String toString() {
        return "Leela: ";
    }
    
    // ------- PRIVATE HELPERS -------- //
    /**
     * Sets the initial state for every round
     */
    private void setInitialState() {
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        enemyManager.addListener(movementManager);
        enemyManager.setReference(this);
        movementManager.setInitialState(this, getBattleFieldWidth(), getBattleFieldHeight());
    }
}
