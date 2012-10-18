/**
 * 
 */
package first_bot;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class SimpleBot extends AdvancedRobot {
    
    public static final double MAX_RADAR_TRACKING_AMOUNT = Math.PI / 4.0;
    
    public void run() {
        this.setAdjustGunForRobotTurn(true);
        this.setAdjustRadarForGunTurn(true);
        this.setAdjustRadarForRobotTurn(true);
        
        while(getRadarTurnRemainingRadians() == 0) {
            turnRadarRightRadians(MAX_RADAR_TRACKING_AMOUNT);
        }
    }

    /* (non-Javadoc)
     * @see robocode.Robot#onScannedRobot(robocode.ScannedRobotEvent)
     */
    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        double absoluteBearing = event.getBearingRadians() + getHeadingRadians();
        absoluteBearing = Utils.normalAbsoluteAngle(absoluteBearing);
        setTurnGunRightRadians(Utils.normalRelativeAngle(getGunHeadingRadians() - absoluteBearing));
        setFire(3.0);
    }
    
    

}
