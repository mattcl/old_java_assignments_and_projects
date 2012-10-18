import stanford.karel.*;
public class MidpointThree extends SuperKarel {
    
    public void run() {
        while(frontIsClear()) {
            placeOneBeeper();
        }
    }
    
    private void placeOneBeeper() {
        while(frontIsClear()) {
            if(beepersPresent()) {
                move();
            } else {
                putBeeper();
                move();
                if(beepersPresent()) {
                    turnAround();
                    move();
                    putBeeper();
                    moveToWall();
                    pickOneFromEach();
                    returnToCenter();
                } else {
                    moveToWall();
                }
            }
        }
        if(notFacingSouth()) {
            turnAround();
        }
    }
    
    private void moveToWall() {
        while(frontIsClear()) {
            move();
        }
    }
    
    private void pickOneFromEach() {
        turnAround();
        while(beepersPresent()) {
            pickBeeper();
            if(frontIsClear()) {
                move();
            }
        }
    }
    
    private void returnToCenter() {
        turnAround();
        while(noBeepersPresent()) {
            if(frontIsClear()) {
                move();
            }
        }
        turnLeft();
    }
    
}
