/**
 * Durandal.java
 */
package rampancy;

import java.awt.Color;
import java.awt.Graphics2D;

import rampancy.standard.*;
import rampancy.util.RUtil;
import rampancy.util.weapon.*;
import robocode.BattleEndedEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class Durandal extends RampantRobot {
    
    public static final double MAX_RADAR_TRACKING_AMOUNT = Math.PI / 4.0;
    
    
    public static final RGun[] guns = new RGun[] {
        //new RDCGun(),
        new RAGFGun(),
        //new RPMGun()
        new RCTGun()
    };
    
    public void onPaint(Graphics2D g) {
        super.onPaint(g);
    }
    
    public void run() {
        super.run();
        initialSetup();
        updateState(null);
        targetingManager.updateNewRound();
        
        while(getRadarTurnRemainingRadians() == 0) {
            turnRadarRightRadians(MAX_RADAR_TRACKING_AMOUNT);
        }
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
        focusRadar(e);
        super.onScannedRobot(e);
    }
    
    public void onBattleEnded(BattleEndedEvent e) {
        if(targetingManager instanceof RDefaultTargetingManager) {
            System.out.println(((RDefaultTargetingManager) targetingManager).getGunStatistics());
        }
    }
    
    // Private Helpers
    private void initialSetup() {
        setColors(Color.black, new Color(0x0D5E10), new Color(0x0D5E10), Color.white, Color.blue);
        // set the enemyManager if necessary
        if(enemyManager == null)
            enemyManager = new RDefaultEnemyManager(this);
        enemyManager.updateReference(this);
        enemyManager.resetAll();
        
        if(waveManager == null)
            waveManager = new RDefaultWaveManager(this);
        waveManager.updateReference(this);
        waveManager.clearWaves();
        
        if(movementManager == null)
            movementManager = new RDefaultMovementManager(this);
        movementManager.updateReference(this);
        
        if(targetingManager == null)
            targetingManager = new RDefaultTargetingManager(guns);
        
        if(statisticsManager == null)
            statisticsManager = new RDefaultStatisticsManager(); 
    }
    
    /**
     * Credit: Voidious
     * Focuses the radar
     * @param e
     */
    private void focusRadar(ScannedRobotEvent e) {
        double factor = 3;
        if(getOthers() > 1) { // occasionally lose the lock against multiple bots.
            factor = 0.2;
        }
        double radarBearingOffset =  Utils.normalRelativeAngle(getRadarHeadingRadians() - (e.getBearingRadians() + getHeadingRadians()));  
        setTurnRadarLeftRadians(radarBearingOffset + (RUtil.nonZeroSign(radarBearingOffset) * (MAX_RADAR_TRACKING_AMOUNT / factor)));
    }
}
