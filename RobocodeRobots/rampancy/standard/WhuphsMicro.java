/**
 * 
 */
package rampancy.standard;

import rampancy.util.*;
import robocode.*;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class WhuphsMicro extends AdvancedRobot {
    
    private static final double MAX_RADAR_TRACKING_AMOUNT = Math.PI / 6;
    private static String states;

    public void run() {
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);
        while(getRadarTurnRemainingRadians() == 0) {
            turnRadarRightRadians(MAX_RADAR_TRACKING_AMOUNT * 6);
        }
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
        // focus radar
        double radarBearingOffset =  Utils.normalRelativeAngle(getRadarHeadingRadians() - (e.getBearingRadians() + getHeadingRadians()));  
        setTurnRadarLeftRadians(radarBearingOffset + (RUtil.nonZeroSign(radarBearingOffset) * (MAX_RADAR_TRACKING_AMOUNT)));
    }

}
