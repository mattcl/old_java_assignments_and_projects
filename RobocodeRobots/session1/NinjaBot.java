package session1;

import robocode.*;

public class NinjaBot extends JuniorRobot {
    private int turnAmt=20;
    private int robotDistance;
    private int rotation=1;
    private int hitCounter=0;
    private boolean init=true;
    
    public void run(){
        setColors(black, black, black, black, black);
        while(true){
            findRobot();
        }
    }
    
    private void findRobot(){
        while(init) turnGunRight(10);
        turnGunRight(turnAmt);
        turnAmt*=2;
        turnGunLeft(turnAmt);
        turnAmt*=2;
    }
    
    public void onScannedRobot(){
        robotDistance=scannedDistance;
        init=false;
        turnAmt=10;
        turnTo(scannedAngle-90);
        bearGunTo(90);
        fireShot();
        if(!gunReady) bearGunTo(90);
        circle();
    }
    
    private void fireShot(){
        fire((1000-robotDistance)/300);
    }
    
    private void circle(){
        int turnDistance=(int)((31.4*robotDistance)/180)*rotation;
        if(rotation==1) turnAheadRight(turnDistance, 10);
        else turnAheadLeft(turnDistance, 10);
    }
    
    public void onHitWall(){
        fireShot();
        reverse();
    }
    
    public void onHitRobot(){
        turnGunTo(hitRobotAngle);
        fire(3);
        doNothing();
    }
    
    public void onHitByBullet(){
        hitCounter++;
        if(hitCounter>=3) reverse();
        
    }
    
    private void reverse(){
        if(rotation==1) rotation=-1;
        else rotation=1;
        hitCounter=0;
    }
}
