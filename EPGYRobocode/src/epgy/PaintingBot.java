/**
 * 
 */
package epgy;

import java.awt.*;
import java.awt.geom.Line2D;

import robocode.util.Utils;

import epgy.util.*;

/**
 * 
 * @author Matthew Chun-Lum
 *
 */
public class PaintingBot extends EPGYBot {

    public void setup() {
        // set the color of different parts of the robot
        setColors(Color.green, Color.white, Color.blue, Color.white, Color.blue);

    }
    
    public void onPaint(Graphics2D g) {
        
        // we need to project our location in front of us to make the
        // end point of the line. This is done using the EPGYUtil.project
        // method. Project will take a location, an angle, and a distance.
        EPGYPoint endPoint = EPGYUtil.project(getLocation(), getHeadingRadians(), 100);
        
        // set the color for the line
        g.setColor(Color.red);
        
        // make the line
        Line2D.Double line = new Line2D.Double(getLocation(), endPoint);
        
        // draw the line
        g.draw(line);

        // if we have scanned a robot, we want to draw a circle
        // around the last known position of the robot
        if(eLastScannedBot != null) {
            
            // create the enemy's location
            EPGYPoint enemyLocation = new EPGYPoint(getScannedEnemyX(), getScannedEnemyY());
            
            // set the color for the oval
            g.setColor(Color.magenta);
            
            // use the EPGYUtil.drawOval method to draw a circle
            // around the enemy
            EPGYUtil.drawOval(enemyLocation, 30, g);
        }
    }
    
    /* (non-Javadoc)
     * @see epgy.util.EPGYBot#doGun(java.lang.String)
     */
    @Override
    public void doGun(String enemyName) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see epgy.util.EPGYBot#doMovement(java.lang.String)
     */
    @Override
    public void doMovement(String enemyName) {
        setTurn(30);
        setMove(100);

    }

    /* (non-Javadoc)
     * @see epgy.util.EPGYBot#doRadar(java.lang.String)
     */
    @Override
    public void doRadar(String enemyName) {
        double radarBearingOffset =  Utils.normalRelativeAngle(getRadarHeadingRadians() - (getScannedEnemyBearingRadians() + getHeadingRadians()));  
        setTurnRadarLeftRadians(radarBearingOffset + (EPGYUtil.nonZeroSign(radarBearingOffset) * (EPGYBot.MAX_RADAR_TRACKING_AMOUNT)));
    }

}
