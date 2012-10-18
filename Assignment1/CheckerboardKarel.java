import stanford.karel.*;
public class CheckerboardKarel extends SuperKarel {
   
	public void run() {
	    if(frontIsBlocked())
	        turnLeft();
	    checker();
	}
	
	public void checker() {
	    moveForwardOneOrTwo();
	    turnAround();
        while(frontIsClear())
            move();
        turnAround();
        moveUp();
        if(frontIsClear())
            checker();
	}
	
	public void moveForwardOneOrTwo() {
	    putBeeper();
	    if(frontIsClear())
            move();
	    if(frontIsClear()) {
	        move();
	        moveForwardOneOrTwo();
	    }
	}
	
	public void moveUp() {
	    if(beepersPresent() && frontIsClear())
	        move();
	    turnLeft();
	    if(frontIsClear()) {
	        move();
	        turnRight();
	    }
	}
}