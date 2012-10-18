import stanford.karel.*;
public class MultiplicationKarel extends SuperKarel {

    public void run() {
        move();
        multiply();
        move();
    }
    
    public void multiply() {
        if(beepersPresent()) {
            pickBeeper();
            multiply();
            add();
            turnAround();
            moveToAnswer();
        } else {
            move();
        }
    }
    
    public void add() {
        if(beepersPresent()) {
            pickBeeper();
            add();
            moveToAnswer();
            turnAround();
            putBeeper();
        }
    }
    
    public void moveToAnswer() {
        move();
        move();
        putBeeper();
        turnAround();
        move();
        move();
    }
}