/**
 * 
 */
package rampancy.micro;

import java.awt.*;

import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class Harbinger extends AdvancedRobot {
    
    private static StringBuffer history;
    private static double previousHeading;
    
    private double targetX, targetY;
    private double moveDist;
    private double lastEnergy;
    
    public void run() {
        setColors(new Color(0x660C17), new Color(0x454545), new Color(0x454545), Color.white, new Color(0x454545));
        this.setAdjustGunForRobotTurn(true);
        this.setAdjustRadarForGunTurn(true);
        this.setAdjustRadarForRobotTurn(true);
        
        if(history == null)
            history = new StringBuffer();
        
        while(getRadarTurnRemainingRadians() == 0) {
            setTurnRadarLeftRadians(Math.PI * 5);
        }
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
        double radarBearingOffset =  Utils.normalRelativeAngle(getRadarHeadingRadians() - (e.getBearingRadians() + getHeadingRadians()));  
        int sign = (radarBearingOffset < 0 ? -1 : 1 );
        setTurnRadarLeftRadians(radarBearingOffset + sign * (Math.PI / 6));
        
        // record state in history
        double heading = e.getHeadingRadians();
        char dh = toChar(Utils.normalRelativeAngle(heading - previousHeading));
        previousHeading = heading;
        char vel = toChar(e.getVelocity());
        history.append("A" + dh + "B" + vel);
        
        double absB = Utils.normalAbsoluteAngle(e.getBearingRadians() + getHeadingRadians());
        double x = getX();
        double y = getY();
        
        targetX = projectX(x, absB, e.getDistance());
        targetY = projectY(y, absB, e.getDistance());
        
        double power = e.getEnergy() == 0 ? 0.1 : e.getDistance() < 30 ? 3.0 : -1;
        double maxPower = getEnergy() < 30 ? 0.5 : 2.0;
        // gun code
        if(history.length() > 320) {
            int ind = history.indexOf(history.substring(history.length() - 32, history.length()));
            if(e.getEnergy() > 0 && power != 3.0 && ind != -1 && ind < (history.length() - 32 - 4 * 20)) {
                String match = history.substring(ind, history.length() - 32);
                for(int i = 0; i < match.length(); i = i + 4) {
                    heading += toDouble(match.charAt(i+1));
                    double velocity = toDouble(match.charAt(i+3));
                    targetX = projectX(targetX, heading, velocity);
                    targetY = projectY(targetY, heading, velocity);
                    double pow = (20.0 - distance(x, y, targetX, targetY)/(i/4+1)) / 3.0;
                    if(pow > power) {
                        power = pow;
                    }
                    
                    if(pow > maxPower)
                        break;
                }
            }
            double angle = Utils.normalRelativeAngle(Utils.normalAbsoluteAngle(Math.atan2(targetX - x, targetY - y)) - getGunHeadingRadians());
            setTurnGunRightRadians(angle);
            if(power >= 0.1) {
                setFire(power);
            }
        }
        
        // movement
        if(e.getEnergy() > lastEnergy) {
            moveDist = Math.random() * 20000 - 10000;
        }
        lastEnergy = e.getEnergy();
        setAhead(moveDist);
        setTurnRightRadians(Utils.normalRelativeAngle(absB + Math.PI / 2 - getHeadingRadians()));
    }
    
    public void onHitWall(HitWallEvent e) {
        moveDist = -moveDist;
    }
    
    public char toChar(double value) {
        return (char) (value * 1000 + 32767);
    }
    
    public double toDouble(char ch) {
        return (double) ((int) ch - 32767) / (1000.0);
    }
    
    public double projectX(double sx, double angle, double dist) {
        return sx + Math.sin(angle) * dist;
    }
    
    public double projectY(double sy, double angle, double dist) {
        return sy + Math.cos(angle) * dist;
    }

    public double distance(double x1, double y1, double x2, double y2) {
        x1 -= x2;
        y1 -= y2;
        return Math.sqrt(x1 * x1 + y1 * y1);
    }
}
