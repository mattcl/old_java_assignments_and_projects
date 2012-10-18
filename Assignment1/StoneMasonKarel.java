/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

/**
 * @author admin
 *
 */
public class StoneMasonKarel extends SuperKarel {

	/**
     * 
     */
    private static final int STEPS = 4;

    public void run() {
	    while(frontIsClear()) {
	        fixColumn();
	        findColumn();
	    }
	}
	
	
	private void findColumn() {
	    if(frontIsClear())
    	    for(int i = 0; i < STEPS; i++)
    	        move();
	}
	
	private void fixColumn() {
	    turnLeft();
	    if(noBeepersPresent())
	        putBeeper();
	    
	    while(frontIsClear()) {
	        move();
	        if(noBeepersPresent()) 
	            putBeeper();
	    }
	    climbDown();
	}
	
	private void climbDown() {
	    turnAround();
	    while(frontIsClear())
	        move();
	    turnLeft();
	}	
}
