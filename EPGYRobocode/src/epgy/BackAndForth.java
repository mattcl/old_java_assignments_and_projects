package epgy;
import robocode.JuniorRobot;

public class BackAndForth extends JuniorRobot{
    public void run() {
        setColors(0x333333, 120, 20330, 0xFFFFFF, 0xFFFFFF);
        turnTo(270);
        ahead(robotX);
        while(true) {
            turnRight(180);
            ahead(fieldWidth);
        }
    }
}
