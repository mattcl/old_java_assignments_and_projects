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

/*
 * Name: 
 * Section Leader: 
 */

public class Renzo extends SuperKarel {

/*The whole program is cut into three parts: forming the squares, 
 * erasing the beepers, and finally getting back to position to form
 * the squares again. This works for any map with a width or height more than 1. 
 * But then again, who would want to use THOSE dimensions for a CONCENTRIC SQUARE program?
 */
    
    public void run() {
        formSquares();
        eraseBeepers();
        restart();
    }
    
/*The square-forming function*/
    
    public void formSquares() {
        while (frontIsClear()) {
            oneRandomSquare();
        }
        turnAround();
    }
    
/*Makes a red line for a square*/
    
    public void redLine() {
        while (frontIsClear()&&noBeepersPresent()) {
            paintCorner(RED);
            if (noBeepersPresent()) {
                putBeeper();
            }
            if (frontIsClear()) {
                move();
            }
        }   
        turnLeft();
        fixSquare();
    }
    
/*Makes a blue line for a square*/
    
    public void blueLine() {
        while (frontIsClear()&&noBeepersPresent()) {
            paintCorner(BLUE);
            if (noBeepersPresent()) {
                putBeeper();
            }
            if (frontIsClear()) {
                move();
            }
        }   
        turnLeft(); 
        fixSquare();
    }
    
/*Makes a cyan line for a square*/
    
    public void cyanLine() {
        while (frontIsClear()&&noBeepersPresent()) {
            paintCorner(CYAN);
            if (noBeepersPresent()) {
                putBeeper();
            }
            if (frontIsClear()) {
                move();
            }
        }   
        turnLeft(); 
        fixSquare();
    }
    
/*Makes a yellow line for a square*/
    
    public void yellowLine() {
        while (frontIsClear()&&noBeepersPresent()) {
            paintCorner(YELLOW);
            if (noBeepersPresent()) {
                putBeeper();
            }
            if (frontIsClear()) {
                move();
            }
        }   
        turnLeft(); 
        fixSquare();
    }
    
/*Makes a green line for a square*/
    
    public void greenLine() {
        while (frontIsClear()&&noBeepersPresent()) {
            paintCorner(GREEN);
            if (noBeepersPresent()) {
                putBeeper();
            }
            if (frontIsClear()) {
                move();
            }
        }   
        turnLeft(); 
        fixSquare();
    }
    
/*Makes an orange line for a square*/
    
    public void orangeLine() {
        while (frontIsClear()&&noBeepersPresent()) {
            paintCorner(ORANGE);
            if (noBeepersPresent()) {
                putBeeper();
            }
            if (frontIsClear()) {
                move();
            }
        }   
        turnLeft(); 
        fixSquare();
    }
    
/*Makes a magenta line for a square*/
    
    public void magentaLine() {
        while (frontIsClear()&&noBeepersPresent()) {
            paintCorner(MAGENTA);
            if (noBeepersPresent()) {
                putBeeper();
            }
            if (frontIsClear()) {
                move();
            }
        }   
        turnLeft(); 
        fixSquare();
    }
        
/*Makes one square of a random color*/
    
    public void oneRandomSquare() {
        if (random(0.09)) { 
            for (int i=0;i<4;i++) {
                orangeLine();
            }
            newSquare();
        } else if (random(0.14)) {
            for (int i=0;i<4;i++) {
                greenLine();
            }
            newSquare();
        } else if (random(0.21)) {
            for (int i=0;i<4;i++) {
                magentaLine();
            }
            newSquare();
        } else if (random(0.28)) {
            for (int i=0;i<4;i++) {
                yellowLine();
            }
            newSquare();
        } else if (random(0.33)) {
            for (int i=0;i<4;i++) {
                redLine();
            }
            newSquare();
        } else if (random(0.4)) {
            for (int i=0;i<4;i++) {
                cyanLine();
            }
            newSquare();
        } else {
            for (int i=0;i<4;i++) {
                blueLine();
            }
            newSquare();
        }
    }
    
/*Gets Karel ready to make a new square*/
    
    public void newSquare() {
        if (notFacingSouth()) {
            if (rightIsClear()) {
                turnRight();
            }
        }
        moveToWall();
        turnRight();
        moveToWall();
        turnAround();
        while (beepersPresent()&&frontIsClear()) {
            turnLeft();
            if (frontIsClear()) {
                move();
            }
            turnRight();
            if (frontIsClear()) {
                move();
            }
        }
    }
    
/*Moves Karel until blocked*/
    
    public void moveToWall() {
        while (frontIsClear()) {
            move();
        }
    }
    
/*After making one line, positions Karel to make the next*/
    
    public void fixSquare() {
        if (beepersPresent()) {
            turnLeft();
            if(frontIsClear()) {
                move();
            }
            if(rightIsClear()) {
                turnRight();
            }
            if(frontIsClear()) {
                move();
            }
        }
    }
    
/*Makes Karel erase all beepers made while producing squares*/
    
    public void eraseBeepers() {
        while (frontIsClear()) {
            if (beepersPresent()) {
                pickBeeper();
            }
            move();
            if (noBeepersPresent()) {
                turnAround();
                move();
                turnRight();
                move();
                if (noBeepersPresent()) {
                    moveToWall();
                }
            }
            if(frontIsBlocked()&&beepersPresent()) {
                turnLeft();
            }
        }
    }
    
/*Gets Karel ready to make squares again*/
    
    public void restart() {
        if (notFacingSouth()) {
            turnAround();
        }
        moveToWall();
        turnRight();
        moveToWall();
        turnAround();
    }
    
    // Note: This is an unfortunate hack to correct a
    // shortfall in our new Eclipse plugin. Don't worry
    // about (you won't be tested on it and aren't expected
    // to understand what's going on). However, don't
    // delete it, or you won't be able to run your Karel
    // program.
    public static void main(String[] args) {
        String[] newArgs = new String[args.length + 1];
        System.arraycopy(args, 0, newArgs, 0, args.length);
        newArgs[args.length] = "code=" + new SecurityManager(){
            public String className() {
                return this.getClassContext()[1].getCanonicalName();
            }
        }.className();
        SuperKarel.main(newArgs);
    }
}
