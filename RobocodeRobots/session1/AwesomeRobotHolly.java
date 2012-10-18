package session1;

import robocode.JuniorRobot;

public class AwesomeRobotHolly extends JuniorRobot {
    public void run() {
        System.out.println("I'm in run.");
        turnGunRight(360);
    }
    private void killRobot() {
        System.out.println("I'm in killRobot.");
        turnTo(scannedAngle);
        fire(3.0);
    }
    public void onScannedRobot() {
        System.out.println("I'm in onScannedRobot.");
        System.out.println("scannedAngleIs "+scannedAngle);
        killRobot();
    }
    public void onHitWall() {
        System.out.println("I'm in onHitWall.");
        if(hitWallBearing > 0) {
            turnLeft(180);
            ahead(100);
        }   else 
            turnRight(180);
        ahead(100);
    }

    public void onHitRobot() {
        System.out.println("I'm in onHitRobot.");
        ahead(25);
    }
    public void onHitByRobot() {
        System.out.println("I'm in onHitByRobot.");
        turnBackRight(50, 50);
        turnAheadLeft(50, 50);
    }
    public void onHitByRam() {
        System.out.println("I'm in onHitByRam.");
        System.out.println("Hit by ram");
        turnBackLeft(50, 50);
    }
    public void onHitRam() {
        System.out.println("I'm in onHitRam.");
        turnBackLeft(100, 50);
        System.out.println("Hit ram");
    }
}




