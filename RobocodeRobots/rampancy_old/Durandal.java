/** 
 * Durandal.java
 */
package rampancy_old;
import rampancy_old.management.*;
import rampancy_old.statistics.WeaponStatistic;
import rampancy_old.util.*;

import java.awt.geom.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.util.*;
import robocode.*;
import robocode.util.*;

/**
 * Main class
 * 
 * Durandal is a WaveSurfing, Guess Factor targeting megabot.
 * 
 * @author Matthew Chun-Lum
 *
 */
public class Durandal extends RampantRobot {

    private static EnemyManager enemyManager = new EnemyManager();
    private static WaveManager waveManager = new WaveManager();
    private static StatisticsManager statsManager = new StatisticsManager();
    private static MovementManager movementManager = new MovementManager();
    private static TargetingManager targetingManager = new TargetingManager();
    
    private Point2D.Double location;
    
    private static boolean willPaintWaves = false;
    private static boolean willPaintMovement = false;
    
    /**
     * Run method. Invoked at the start of every round.
     */
    public void run() {
        updateLocation();
        setInitialState();
        
        while(getRadarTurnRemainingRadians() == 0) {
            turnRadarRightRadians(Constants.MAX_RADAR_TRACKING_AMOUNT);
        }
    }
    
    /**
     * Invoked when we scan another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        focusRadar(e);
        updateLocation();

        EnemyRobot enemy = enemyManager.processScannedRobotEvent(e);
        waveManager.update();
        if(enemy != null) {
            movementManager.updateCurrentState(enemy);
            movementManager.computeNextMove(enemy);
            targetingManager.targetEnemy(enemy);
        }
    }
    
    /**
     * Invoked when Durandal is hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {
        waveManager.processHitEvent(e);
    }
    
    public void onBulletHit(BulletHitEvent e) {
        WeaponStatistic stat = statsManager.getWeaponStatsForEnemy(e.getName());
        if(stat != null) {
            stat.getGFGunStats().noteShotHit();
        }
    }
    
    /**
     * If there's a skipped turn, print it out to the console
     */
    public void onSkippedTurn(SkippedTurnEvent e) {
        System.out.println("Skipped turn! " + e.getTime());
    }
    
    /**
     * Invoked if Durandal wins a round
     */
    public void onBattleEnded(BattleEndedEvent e) {
        statsManager.printBattleStats();
    }
    
    /**
     * Invoked when the paint option is enabled
     */
    public void onPaint(Graphics2D g) {
        if(willPaintWaves)
            waveManager.draw(g);
    }
    
    /**
     * Handles key typed events (used for debugging)
     * @param e
     */
    public void onKeyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_W:
                willPaintWaves = !willPaintWaves;
        }
    }
    
    // --------- Private Helpers ---------- //
    
    /*
     * Sets the initial state of Durandal and Durandal's managers
     */
    private void setInitialState() {
        setColors(Color.black, Color.cyan, Color.green, Color.white, Color.blue);
        movementManager.setInitialState(this, getBattleFieldWidth(), getBattleFieldHeight());
        targetingManager.setInitialState(this);
        targetingManager.addListener(waveManager);
        enemyManager.setInitialState(this);
        waveManager.setInitialState(this);
        statsManager.setInitialState(this);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        noteMaxVelocity(8.0);
    }
    
    /*
     * Updates Durandal's location
     */
    private void updateLocation() {
        location = new Point2D.Double(getX(), getY());
    }
    
    /**
     * Credit: Voidious
     * Focuses the radar
     * @param e
     */
    private void focusRadar(ScannedRobotEvent e) {
        double factor = 2;
        if(getOthers() > 1) { // cause Durandal to occasionally lose the lock.
            factor = 0.2;
        }
        double radarBearingOffset =  Utils.normalRelativeAngle(getRadarHeadingRadians() - (e.getBearingRadians() + getHeadingRadians()));  
        setTurnRadarLeftRadians(radarBearingOffset + (Util.nonZeroSign(radarBearingOffset) * (Constants.MAX_RADAR_TRACKING_AMOUNT / factor)));
    }
    
    // ------------- Getters and Setters ------------ //
    
    /**
     * @return a reference to the wave manager
     */
    public WaveManager getWaveManager() {
        return waveManager;
    }
    
    /**
     * @return a reference to the statistics manager
     */
    public StatisticsManager getStatisticsManager() {
        return statsManager;
    }
    
    /**
     * @return a reference to the enemy manager
     */
    public EnemyManager getEnemyManager() {
        return enemyManager;
    }
    
    /**
     * @return a reference to the movement manager
     */
    public MovementManager getMovementManager() {
        return movementManager;
    }
    
    /**
     * @return a reference to the targeting manager
     */
    public TargetingManager getTargetingManager() {
        return targetingManager;
    }
    
    /**
     * @return Durandal's current location
     */
    public Point2D.Double getLocation() {
        return location;
    }
    
    /**
     * @return a String representation of Durandal
     */
    public String toString() {
        return "Durandal";
    }
}
