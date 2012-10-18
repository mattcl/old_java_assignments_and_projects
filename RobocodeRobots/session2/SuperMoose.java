package session2;

import java.awt.Color;

import robocode.*;

public class SuperMoose extends Robot {

    public void run(){
        setColors(Color.BLACK, Color.BLACK, Color.YELLOW, Color.WHITE, Color.YELLOW);
        turnTo(0);
        ahead(5000);
        turnGunRight(180);
        while(true){
            ahead(50);
            turnGunLeft(180);
            turnGunRight(180);
        }
    }

    public void onHitWall(HitWallEvent event){
       turnRight(90);
        
       
    }
    private void turnTo(double degrees){
        turnRight(degrees-getHeading());
    }
    public void onScannedRobot(ScannedRobotEvent e){   
        if(e.getDistance()<200){
            fire(3);
            fire(3);
        }else if(e.getDistance()>100&&e.getDistance()<350){
            fire(2.5);
        }else{
            fire(1.5);

                }
    }
    public void onHitRobot(HitRobotEvent event) {
        if (event.getBearing() > -90 && event.getBearing() <= 90) {
            back(100);
        } else {
            ahead(100);
        }
    }
    public void onHitByBullet(HitByBulletEvent event){
    	ahead(300);
    }
}


