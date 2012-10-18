package session1;
import robocode.JuniorRobot;


public class Aw350m3HaXMatt extends JuniorRobot {

    public void run() {
        setColors(yellow, yellow, black, white, blue);
        while (true) {
            turnLeft(5);
        }
    }
    public void onScannedRobot(){
        turnTo(scannedAngle);
        fire();
        ahead(200);
    }
    public void onHitRobot(){
        fire(3);
        ahead(40);
    }
}
