
import robocode.*;
import robocode.util.Utils;


/**
 * @author Matthew Chun-Lum
 *
 */
public class AngleTest extends Robot {

    private double enemyEnergy;
    private boolean wasShotAt;
    
    public void run() {
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        enemyEnergy = 100;
        wasShotAt = false;
        while(true) {
            turnRadarRight(360);
        }
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
        //stop();
        // find the distance to the enemy
        double distanceToEnemy = e.getDistance();
        
        // find the bearing to the enemy
        double bearingToEnemy = e.getBearing();
        
        // compute the absolute bearing
        double absoluteBearing = bearingToEnemy + getHeading();
        
        // find the enemy coordinates
        
        // figure out how far in terms of x and y the enemy
        // is from us
        double enemyDistX = distanceToEnemy * EpgyUtil.sin(absoluteBearing);
        double enemyDistY = distanceToEnemy * EpgyUtil.cos(absoluteBearing);
        
        // add the offsets to our current position to compute
        // the coordinate
        double enemyX = getX() + enemyDistX;
        double enemyY = getY() + enemyDistY;
        
        double guess = 0;
        
        double difference = e.getEnergy() - enemyEnergy;
        enemyEnergy = e.getEnergy();
        
        if(difference >= 0.1 && difference <= 3.0) {
            System.out.println("Enemy fired!");
            wasShotAt = true;
        }
        
        // turn gun to the enemy
        turnRadarRight(absoluteBearing - getRadarHeading());
        turnGunRight(absoluteBearing - getGunHeading() + guess);
        fire(3.0);
        if(wasShotAt) {
            ahead(150);
            turnRight(30);
        } else {
            scan();
        }
    }
}
