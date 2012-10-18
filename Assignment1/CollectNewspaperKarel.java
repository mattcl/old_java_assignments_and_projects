/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {

    public void run() {
        moveToWall();
        climbMountain();
        reorient();
        moveToWall();
    }

    private void climbMountain() {
        if(frontIsBlocked()) {
            stepUp();
            climbMountain();
        }
        stepDown();
    }

    private void stepUp() {
        turnLeft();
        move();
        turnRight();
    }

    private void stepDown() {
        move();
        turnRight();
        move();
        turnLeft();
    }

    private void moveToWall() {
        while(frontIsClear())
            move();
    }

    private void reorient() {
        while(notFacingEast())
            turnLeft();
    }

}
