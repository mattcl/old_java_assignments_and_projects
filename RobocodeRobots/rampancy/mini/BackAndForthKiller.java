/**
 * 
 */
package rampancy.mini;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.*;

import rampancy.util.RPoint;
import rampancy.util.RUtil;
import robocode.*;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class BackAndForthKiller extends AdvancedRobot {

    private HashMap<String, Enemy> yLocations;
    private ArrayList<String> deadBots;
    
    private double desiredGunAngle;

    private int wallDir;
    private boolean hitWall;
    int shotCount = 0;

    public void run() {
        setColors(Color.RED, Color.RED, Color.RED);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);
        hitWall = false;
        yLocations = new HashMap<String, Enemy>();
        deadBots = new ArrayList<String>();
        wallDir = 1;
        if(getX() < getBattleFieldWidth() / 2.0) {
            wallDir = -1;
        }
        double gunTurnAngle = wallDir == 1 ? 3 * Math.PI / 2 : Math.PI / 2;
        desiredGunAngle = gunTurnAngle;
        setTurnGunRightRadians(desiredGunAngle - getGunHeadingRadians());
        while(getRadarTurnRemainingRadians() == 0) {
            setTurnRadarLeftRadians(3 * Math.PI);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        if(!deadBots.contains(e.getName())) {
            RPoint loc = new RPoint(getX(), getY());
            double absB = Utils.normalAbsoluteAngle(e.getBearingRadians() + getHeadingRadians());
            loc = RUtil.project(loc, absB, e.getDistance());
            yLocations.put(e.getName(), new Enemy(e.getName(),loc.y));
        }
        
        double closestDist = 50000;
        double closestY = 5000;
        String closestName = "";
        double myY = getY();
        for(Enemy enemy : yLocations.values()) {
            if(Math.abs(enemy.yLoc - myY) < closestDist) {
                closestDist = Math.abs(enemy.yLoc - myY);
                closestY = enemy.yLoc;
                closestName = enemy.name;
            }
        }

        if(!hitWall) {
            if(Math.PI / 2 - getHeadingRadians() != 0) {
                setTurnRightRadians(Math.PI / 2 - getHeadingRadians());
            } else {
                setAhead(5000 * wallDir);
            }
        } else if(getGunHeadingRadians() == desiredGunAngle) {
            if(Math.abs(myY - closestY) < 2) {
                if(setFireBullet(3.0) != null) {
                    shotCount++;
                    if(shotCount * 16 >= 100) {
                        shotCount = 0;
                        yLocations.remove(closestName);
                        deadBots.add(closestName);
                    }
                }
            } else {
                setAhead(closestY - myY);
            }
        } else if(getGunHeadingRadians() != desiredGunAngle) {
            setTurnGunRightRadians(desiredGunAngle - getGunHeadingRadians());
        }
        setTurnRadarLeftRadians(3 * Math.PI);
    }

    public void onHitWall(HitWallEvent e) {
        if(!hitWall) {
            setAhead(0);
            setTurnRightRadians(0 - getHeadingRadians());
            hitWall = true;
        }
    }
    
    public void onHitRobot(HitRobotEvent e) {
        if(!hitWall) {
            if(!deadBots.contains(e.getName())) {
                deadBots.add(e.getName());
                yLocations.remove(e.getName());
            }
            setTurnGunRightRadians(Utils.normalRelativeAngle(e.getBearingRadians() + getHeadingRadians() - getGunHeadingRadians()));
            setFireBullet(3);
        }
    }
}

class Enemy {
    public String name;
    public double yLoc;
    public Enemy(String name, double yLoc) {
        this.name = name;
        this.yLoc = yLoc;
    }
}
