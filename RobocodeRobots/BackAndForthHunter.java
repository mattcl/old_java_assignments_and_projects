import java.util.*;

import robocode.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class BackAndForthHunter extends Robot {

    public HashMap<String, Double> enemyYCoordinates;
    
    public void run() {
        enemyYCoordinates = new HashMap<String, Double>();
        setAdjustRadarForGunTurn(false);
        setAdjustGunForRobotTurn(true);
        
        moveToWestWall();
        turnGunRight(90 - getGunHeading());
        while(true) {
            if(getX() > 75)
                moveToWestWall();
            if(getGunHeading() != 90)
                turnGunRight(90 - getGunHeading());
            moveUp();
            moveDown();
        }
    }
    
    public void moveToClosestEnemy() {
        double myY = getY();
        double closest = 5000;
        for(Double d : enemyYCoordinates.values()) {
            if(Math.abs(closest) > Math.abs(myY - d)) {
                closest = myY - d;
            }
        }
        if(closest > 0) {
            moveDown();
        } else {
            moveUp();
        }
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
        if(!enemyYCoordinates.containsKey(e.getName())) {
            double y = getY() + (EpgyUtil.cos(e.getBearing() + getHeading()) * e.getDistance());
            enemyYCoordinates.put(e.getName(), y);
        }
        double velocity = getVelocity();
        stop(true);
        fire(3.0);
        fire(3.0);
        fire(3.0);
    }
    
    public void onRobotDeath(RobotDeathEvent e) {
        enemyYCoordinates.remove(e.getName());
    }
    
    public void moveUp() {
        turnTo(0);
        ahead(getBattleFieldHeight());
    }
    
    public void moveDown() {
        ahead(-getY());
    }
    
    public void moveToWestWall() {
        turnTo(270);
        ahead(getX());
    }
    
    public void onHitWall(HitWallEvent e) {
        stop();
    }
    
    public void onHitRobot(HitRobotEvent e) {
        moveUp();
    }
    
    public void turnTo(double angle) {
        turnRight(angle - getHeading());
    }
}
