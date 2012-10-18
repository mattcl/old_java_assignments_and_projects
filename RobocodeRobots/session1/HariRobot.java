package session1;

import robocode.JuniorRobot;

public class HariRobot extends JuniorRobot {
    public void run()
    {
        setColors(blue, yellow, red, green, red);
        scan360();
    }
    
    
    public void scan360() {
        turnGunRight(360);
    }
    
    public void onScannedRobot()
    {   
        int Distance = scannedDistance;     
        turnGunTo(scannedAngle);
        fire(3.0);
        ahead(90);
        fire(3.0);
        back(90);
    }
    public void onHitWall()
    {
        
        turnRight(180);
        int z = hitWallBearing;
        int c = scannedDistance;
        if(z<=90)
        {
            turnTo(-90);
            if(c<=100)
            {
                fire(3.0);
            }
            else
            {
                        ahead(100);
                        turnRight(90);
                        ahead(80);
                }
        
        }
    
        else if(z>90 && z<=180)
        {
            turnTo(0);
            if(c<=100)
            {
                fire(3.0);
            }
            else
            {
                        ahead(100);
                        turnRight(90);
                        ahead(80);
                }
        }
        else if(z>180 && z<=270)
        {
            turnTo(90);
            if(c<=100)
            {
                fire(3.0);
            }
            else
            {
                        ahead(100);
                        turnRight(90);
                        ahead(80);
                }
        }
        else if(z>270 && z<0)
        {
            turnTo(180);
            if(c<=100)
            {
                fire(3.0);
            }
            else
            {
                        ahead(100);
                        turnRight(90);
                        ahead(80);
                }
        }
        
    }
    
    
    
    public void onHitByBullet()
    {
        turnRight(90);
        ahead(100);
        
    }
    public void onHitByRam()
    {
        fire(3.0);
        fire(3.0);
        fire(3.0);
        fire(3.0);
        fire(3.0);
        
        
        
    }
    
    
    
    
    
    
}