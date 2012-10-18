/**
 * 
 */
package rampancy.mini;

import java.awt.geom.Point2D;

import robocode.AdvancedRobot;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class DwarfStar extends AdvancedRobot {

    public void run() {
        
    }
    
    public void setBackAsFront(double goAngle, double dist) {
        double angle = Utils.normalRelativeAngle(goAngle - getHeadingRadians());
        if (Math.abs(angle) > (Math.PI/2)) {
            if (angle < 0) {
                setTurnRightRadians(Math.PI + angle);
            } else {
                setTurnLeftRadians(Math.PI - angle);
            }
            setBack(dist);
        } else {
            if (angle < 0) {
                setTurnLeftRadians(-1*angle);
            } else {
                setTurnRightRadians(angle);
            }
            setAhead(dist);
        }
    }
}

class DwarfWave {
    public Point2D.Double origin;
}
