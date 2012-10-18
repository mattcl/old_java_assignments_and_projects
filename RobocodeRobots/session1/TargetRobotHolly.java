package session1;

import robocode.JuniorRobot;

public class TargetRobotHolly extends JuniorRobot {
    public void run() {
        turnGunRight(360);
    }
    private void killRobot() {
        turnTo(scannedAngle);
        fire(3.0);
    }
    public void onScannedRobot() {
        killRobot();
    }
}