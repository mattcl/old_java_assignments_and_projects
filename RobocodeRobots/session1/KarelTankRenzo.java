package session1;

import robocode.JuniorRobot;
import robocode.control.BattlefieldSpecification;

// A Renzo Bautista Robot. Stays at the walls, constantly moving until hit or rammed. 
// Shoots targets based on their distance and velocity. 
// Vessel of the very well-known Java robot Karel. Can also turn into a Super form. 

public class KarelTankRenzo extends JuniorRobot {

    public void run() {
        setColors(white,blue,black); //our colors are white, blue and black
        int distanceToNorth = fieldHeight - robotY;
        int distanceToSouth = robotY;
        int distanceToEast = fieldWidth - robotX;
        int distanceToWest = robotX;
        if (distanceToNorth<=distanceToSouth &&distanceToNorth<=distanceToEast && distanceToNorth<=distanceToWest) {
            turnTo(0);
        } else if (distanceToSouth<distanceToNorth && distanceToSouth<distanceToEast && distanceToSouth<distanceToWest) {
            turnTo(180);
        } else if (distanceToEast<distanceToNorth && distanceToEast<distanceToSouth && distanceToEast<distanceToWest) {
            turnTo(90);
        } else if (distanceToWest<distanceToNorth && distanceToWest<distanceToSouth && distanceToWest<distanceToEast) {
            turnTo(270);
        } //turn towards the nearest wall
        ahead(3000); //move til we hit a wall
        while (true) {
            ahead(100);
            turnGunRight(360);
        } 
    }

    public void onHitWall() { //if we hit a wall
        turnTo(hitWallAngle-90); //turn to an angle parallel to the wall
        setColors(white,blue,black,gray,blue);
        ahead(150); // move 150 pixels
        if (robotY<37) { //if the robot is in the bottom wall
            ahead(fieldWidth); //strafe the whole wall
        }
        if (robotX<37) { //if the robot is in the left wall
            ahead(fieldHeight); //strafe the whole wall
        }
    }// the above two if statements were created due to a glitch that makes KarelTank turn
    //after every movement
    
    public void onScannedRobot() { //when a robot is seen
        int enemyDistance = scannedDistance;
        int enemyVelocity = scannedVelocity;
        turnGunTo(scannedAngle); //turn gun to the robot
        if (enemyDistance<=200&&enemyVelocity<7) { //and if we're near...
                setColors(red,blue,yellow,gray,red); //turn into SuperKarelTank
                turnGunLeft((int) Math.acos(scannedVelocity/scannedDistance));
                fire(3); //kill it with a level 3 bullet!
        } else if (enemyDistance<=300&&enemyDistance>150&&enemyVelocity<3){ //but if we're not so near...
            turnGunLeft((int) Math.acos(scannedVelocity/scannedDistance)); 
            fire(1.1); // hit it with a level 1.1 bullet!
        } else {
            turnGunLeft((int) Math.acos(scannedVelocity/scannedDistance));
            fire(0.5);
        }
    }
    
    public void onHitByRobot() { //if a robot rams us...
        turnTo(hitRobotAngle-90); //turn to an angle perpendicular to the robot...
        ahead(300); //and RUN!
    }
    
    public void onHitByBullet() { //if a bullet hits us...
        turnTo(hitByBulletAngle-90); //turn to an angle perpendicular to where the bullet hit
        ahead(300); //and run to avoid future hits
    }
}