package session1;

import robocode.JuniorRobot;

public class SixEric extends JuniorRobot {
    public void run(){
        setColors(black, blue, gray);
        startingPoint();
        while(true){
            turnGunRight(180);
            ahead(300);
        }

    }

    private void startingPoint(){
        width();
        ahead(1000);
    }

    private void width(){
        if(fieldWidth - robotX > robotX){
            turnTo(270);
        }
        else {
            turnTo(90);
        }

    }



    public void onScannedRobot() {
        if(others <= 2){
            if(scannedDistance <= 75 || scannedVelocity <= 2){
                turnGunTo(scannedAngle);
                fire(2.5);
            } 
            else if(scannedDistance > 75){
                turnGunTo(scannedAngle);
                fire(0.5);
            }
        }

        else {
            if(scannedDistance <= 175  || scannedVelocity <= 4){
                turnGunTo(scannedAngle);
                fire(3);
            } 
            else if(scannedDistance > 175){
                turnGunTo(scannedAngle);
                fire();
            }
        }
    }

    public void onHitByBullet() {
        turnGunTo(hitByBulletAngle);
        fire(2);
    }


    public void onHitWall() {
        turnRight(90);
        turnGunRight(180);
    }



}





