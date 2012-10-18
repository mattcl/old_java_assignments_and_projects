package session1;

import robocode.JuniorRobot;

public class BrawlSumeer extends JuniorRobot {
    public void run(){
        turnAheadLeft(200,130);
        turnBackRight(200,130);     
        turnAheadLeft(200,130);
        turnBackRight(150,130);
        
        turnAheadRight(200,170);
        System.out.println("4th command");
        turnAheadRight(200,170);
        turnBackRight(200,170);     
        turnBackLeft(150,170);  
        
    }
    

    public void onHitWall(){
        if(hitWallBearing >-90||hitWallBearing<90){
            turnBackRight(300,130);
            turnBackLeft(300,130);
            turnAheadLeft(200,130);
            turnAheadRight(200,130);
        }
        else if(hitWallBearing >90||hitWallBearing<-90){
            turnAheadLeft(300,130);
            turnAheadRight(300,130);        
            turnBackRight(200,130);
            turnBackLeft(200,130);
        }
    }

    public void hitByRobot(){
        turnBackLeft(300,130);
    }
    
    
    public void onHitRobot(){
        turnBackLeft(300,130);
    }

    public void onScannedRobot(){
        int distance=scannedDistance;
        if(distance <100)if(scannedHeading<0)
            turnGunTo(scannedAngle-5);
        fire(3);
        if(distance <100)if(scannedHeading>0)
            turnGunTo(scannedAngle+5);
        fire(3);
        if(distance >100)if(scannedHeading<0)
            turnGunTo(scannedAngle-5);
        fire(3);
        if(distance >100)if(scannedHeading>0)
            turnGunTo(scannedAngle+5);
        fire(3);
    }



}