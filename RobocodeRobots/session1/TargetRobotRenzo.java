package session1;

import robocode.JuniorRobot;

//A Renzo Bautista Robot. Chases down targets and pwns them. 

public class TargetRobotRenzo extends JuniorRobot {

    boolean atRightWall;
    boolean atLeftWall;
    
    public void run() {
        setColors (yellow, black, yellow, black, black);
        int distanceWestWall = robotX;
        int distanceEastWall = fieldWidth - robotX;
        if (distanceWestWall<=distanceEastWall) {
            turnTo(270);
            atLeftWall=true;
        } else if (distanceEastWall<distanceWestWall) {
            turnTo(90);
            atRightWall=true;
        }
        ahead(3000);
        turnTo(0);
        turnGunRight(360);
    }
    
    public void onScannedRobot() {
        System.out.println("got him");
        int enemyDistance = scannedDistance;
        int enemyDirection = scannedAngle;
        turnTo(enemyDirection);
        ahead(enemyDistance-100);
        turnGunTo(enemyDirection);
        fire(3);
    }
    
    public void onHitByRobot() {
        turnGunTo(hitRobotAngle);
        fire(3);
    }
    
}
