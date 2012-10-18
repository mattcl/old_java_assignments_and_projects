import stanford.karel.*;
public class ClimbingKarel extends SuperKarel {
    public void run() {
        findBeeper();
    }

    private void findBeeper() {
        if(beepersPresent()) {
            pickBeeper();
        } 
        if(noBeepersPresent()) {
            if(frontIsBlocked()) 
                step();
            else 
                turnRight();
            findBeeper();
        }
    }

    private void step() {
        turnLeft();
        while(rightIsBlocked() && frontIsClear()) {
            move();
        }
        if(frontIsClear() || rightIsClear()) {
            turnRight();
            move();
        }
    }
}
