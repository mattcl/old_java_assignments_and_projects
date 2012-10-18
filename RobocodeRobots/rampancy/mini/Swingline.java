/**
 * 
 */
package rampancy.mini;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import rampancy.util.RPoint;
import robocode.*;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class Swingline extends AdvancedRobot {

    double shotsFired;
    double shotsHit;
    double hitPercentage;
    double lastHeading;
    RPoint targetLocation;
    RPoint myLocation;
    
    public void run() {
        setColors(new Color(0xED1A1A), new Color(0x911a10), new Color(0x911a10), Color.white, new Color(0x911a10));
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        
        shotsFired = 0;
        shotsHit = 0;
        hitPercentage = 0;
        
        while(getRadarTurnRemainingRadians() == 0) {
            setTurnRadarRightRadians(Math.PI * 5);
        }
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
        myLocation = new RPoint(getX(), getY()); 
        doRadar(e);
        doGun(e);
        
        lastHeading = e.getHeadingRadians();
        
    }
    
    public void doRadar(ScannedRobotEvent e) {
        double radarBearingOffset =  Utils.normalRelativeAngle(getRadarHeadingRadians() - (e.getBearingRadians() + getHeadingRadians()));  
        int sign = (radarBearingOffset < 0 ? -1 : 1 );
        setTurnRadarLeftRadians(radarBearingOffset + sign * (Math.PI / 6));
    }
    
    public void doGun(ScannedRobotEvent e) {
        double absBearing = e.getBearingRadians() + getHeadingRadians();
        targetLocation = project(myLocation, absBearing, e.getDistance());
        double heading = e.getHeadingRadians();
        double dh = heading - lastHeading;
        double bestPower = 0.5;
        double maxPower = computeMaxPower(e);
        double gunAngle = absBearing - getGunHeadingRadians();
        if(e.getEnergy() != 0) {
            for(int i = 0; i < 100; i++) {
                heading += dh;
                targetLocation = project(targetLocation, heading, e.getVelocity());
                double power = computeBulletPower(targetLocation.distance(myLocation) / (i + 1));
                if(power > maxPower || targetLocation.x > getBattleFieldWidth() || 
                        targetLocation.x < 0 || targetLocation.y < 0 || targetLocation.y > getBattleFieldHeight()) {
                    break;
                }
                if(power > bestPower) {
                    gunAngle = Utils.normalRelativeAngle(computeAbsoluteBearing(myLocation, targetLocation) - getGunHeadingRadians());
                    bestPower = power;
                }
            }
        }
        
        setTurnGunRightRadians(gunAngle);
        
        if(setFireBullet(bestPower) != null) {
            shotsFired++;
        }
    }

    private double computeMaxPower(ScannedRobotEvent e) {
        if(e.getEnergy() == 0) {
            return 0.1;
        }
        if(e.getDistance() < 30) {
            return 3.0;
        }
        if(hitPercentage == 0) {
            return 2.5;
        }
        return Math.max(0.1, Math.min((hitPercentage + 0.3) * 3.0, 3.0));
    }

    public void onBulletHit(BulletHitEvent e) {
        shotsHit++;
        hitPercentage = shotsHit / shotsFired;
    }
    
    public RPoint project(RPoint source, double angle, double length) {
        double x = source.x + Math.sin(angle) * length;
        double y = source.y + Math.cos(angle) * length;
        return new RPoint(x, y);
    }
    
    public double computeAbsoluteBearing(Point2D.Double source, Point2D.Double target) {
        return Utils.normalAbsoluteAngle(Math.atan2(target.x - source.x, target.y - source.y));
    }
    
    public double computeBulletPower(double velocity) {
        return Math.max(0.1, (20.0 - velocity) / 3.0);
    }
}
