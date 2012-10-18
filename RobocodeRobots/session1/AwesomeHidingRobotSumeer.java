package session1;

import robocode.JuniorRobot;
public class AwesomeHidingRobotSumeer extends JuniorRobot {
    public void run(){
        setColors(green, blue, black);
        turnGunRight(360);
        ahead(100);
        onScannedRobot();
        if(false)
            turnGunRight(360);
    }
    

    public void onScannedRobot(){
        turnTo(scannedAngle);
        fire(3);
        fire(3);
        fire(3);
        fire(3);
        fire(3);
        fire(3);
        fire(3);
        fire(3);
    }
}