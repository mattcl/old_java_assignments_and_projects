/**
 * 
 */
package epgy;

import java.awt.Color;

import robocode.util.Utils;
import epgy.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class ExampleBot extends EPGYBot {
    
    // define your private instance variables here
    private int direction;
    
    /*
     * This method is invoked at the beginning of every round 
     */
    public void setup() {
        // whatever is in here gets called at the beginning of each round
        
        // set our color
        // The colors are body color, gun color, radar color, bullet color, and scan arc color
        setColors(Color.BLUE, Color.MAGENTA, Color.CYAN, Color.GRAY, Color.GREEN);
    }
   
    /*
     * This method handles the gun behavior. It is invoked every
     * time we scan another robot with our radar. In order for
     * you to shoot, your gun must be cool enough to fire. The
     * 'canShoot()' method lets you know if your gun is cool
     * enough. As a side note, the more power you put into
     * your shot, the longer it will be before you can fire again
     */
    public void doGun(String enemyName) {

        // compute the angle we need to turn the gun to point directly at the enemy
        // think about how you would aim in front of an enemy
        double angle = getScannedGunBearingToEnemy();

        // check if our gun is cool enough to shoot
        if(canShoot()) {
            // shoot at the enemy using the angle we computed earlier
            // there is an interesting problem where our bullets will
            // be fired on the next turn, so we may want to adjust the
            // angle for this if we are not using a radar focusing algorithm
            shootWithAngleAndPower(angle, 2.0);

            // print a simple message to the console
            println("Shooting at " + enemyName);
        }

    }

    /*
     * This method handles movement. It is invoked every time
     * we scan another robot with our radar. This is a little
     * different from what you are familiar with, since you are
     * invoking commands like 'setMove' and 'setTurn.' At the 
     * end of your turn, Robocode will take your 'set' actions
     * and perform them. 
     */
    public void doMovement(String enemyName) {
        // just move in a simple circle
        // by moving forward and turning
        // at the same time, we will move in a circle
        setMove(90);
        setTurn(30);
        
    }

    
    /*
     * I've taken care of the radar for you, but if you want
     * more advanced radar movement, this is where you should
     * put it.
     */
    public void doRadar(String enemyName) {
        // the radar movement is handled for you, 
        // but if you want to overwrite it,
        // write your code here
    }

}
