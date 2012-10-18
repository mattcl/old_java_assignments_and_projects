/**
 * 
 */
package rampancy.nano;

import robocode.*;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class Atropos extends AdvancedRobot {
    
    static double lastEnergy;
    static double moveDistance;
    
    public void run() {
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);
        
        while(getRadarTurnRemaining() == 0)
            setTurnRadarRightRadians(Math.PI * 5);
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
        double radarBearingOffset =  Utils.normalRelativeAngle(getRadarHeadingRadians() - (e.getBearingRadians() + getHeadingRadians()));  
        double sign = (radarBearingOffset < 0 ? -Math.PI / 6 : Math.PI / 6);
        setTurnRadarLeftRadians(radarBearingOffset + sign);
        
        if(e.getEnergy() < lastEnergy) {
            moveDistance = Math.random() * 100 + 100;
            if(Math.random() > 0.5)
                moveDistance = -moveDistance;
            setAhead(moveDistance);
        }
        lastEnergy = e.getEnergy();
        setTurnRightRadians(Utils.normalRelativeAngle(e.getBearingRadians() + Math.PI / 2));
        
        setTurnGunRightRadians(Utils.normalRelativeAngle(e.getBearingRadians() + getHeadingRadians() - getGunHeadingRadians()));
        setFire(0.2);
    }
    
    public void onHitWall(HitWallEvent e) {
        setAhead(-moveDistance);
    }
}
