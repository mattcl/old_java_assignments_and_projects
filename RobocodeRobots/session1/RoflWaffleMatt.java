package session1;

import robocode.JuniorRobot;

public class RoflWaffleMatt extends JuniorRobot {

    public void run(){
        setColors(white, blue, black, blue, black);
        while (true){
            turnAheadRight(200,360);
        }
    }
    public void onScannedRobot() {
        fire(2);
        
    }
    public void onHitRobot() {
        fire(2);
        turnBackLeft(50,90);
    }
    public void onHitWall(){
        turnBackLeft(75,90);
    }
    public void onHitByRobot(){
        turnBackLeft(50,90);
    }
}
