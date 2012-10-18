import stanford.karel.*;
public class MultiplicationKarel2 extends SuperKarel {

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
