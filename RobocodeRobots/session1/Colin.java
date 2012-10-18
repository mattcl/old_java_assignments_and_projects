package session1;

import robocode.JuniorRobot;
public class Colin extends JuniorRobot {
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(Math.floor(Math.random()*1));
        }
        backAndForth();
    }
    public void backAndForth() {
        while (true) {
            ahead(100);
            scan360();
            turnLeft(45);
        }
    }
    public void scan360() {
        if (gunReady == true) {
            for (int x = 0; x < 36; x++) 
                turnGunRight(20);
        }

    }
    public boolean mv = false;
    public void onScannedRobot() {
        turnGunTo(scannedAngle);
        if (scannedDistance <100)  {
            fire();
        }
        else {
            turnTo(scannedAngle);
            int x = scannedDistance-100;
            ahead(x);
        }
        if (mv)
            ahead(100);
        else
            back(100);

    }
    public void onHitByBullet() {
        ahead(100);
        turnLeft(90);
        back(100);
    }
    public void onHitRobot() {
        onHitWall();
    }
    public void onHitWall() {
        back(250);
        turnLeft(90);
        ahead(200);
    }
    public void onWin() {
        System.out.println("The Bringer of Death strikes again!");
    }
    public void onLoss() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(Math.floor(Math.random()*1));
        }
        System.out.println("");
    }
}