package contest;



/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

/*
 * Name: John You           
 * Section Leader: Osvaldo
 */

public class JohnYou extends SuperKarel {

    public void run() {
        // Karel writes EPGY
        e();
        p();
        g();
        y();
        exclamationMark();
        shiny();

    }
    private void e() {
        blueLine();
        turnAround();
        returning();
        blueLine();
        turnAround();
        returning();
        blueLine();
    }       

    private void moveToWall() {
        while(frontIsClear()){
            move();
        }
    }
    private void redLine() {
        for (int i=0;i<7;i++) {
            paintCorner(RED);
            move();
        }
    }
    private void blueLine() {
        for (int i=0;i<8;i++){
            paintCorner(BLUE);
            move();
        }
    }
    private void returning() {
        moveToWall();
        turnRight();
        redLine();
        turnRight();
    }
    private void p() {
        rightMove();
        turnLeft();
        move();
        turnLeft();
        while (frontIsClear()) {
            paintCorner(GRAY);
            move();
        }
        paintCorner(GRAY);
        turnRight();
        for (int i=0;i<5;i++) {
            paintCorner(GREEN);
            move();
        }
        turnRight();
        for (int i=0;i<5;i++){
            paintCorner(CYAN);
            move();
        }
        turnRight();
        for (int i=0;i<5;i++){
            paintCorner(MAGENTA);
            move();
        }
        turnLeft();
        for (int i=0;i<9;i++){
            move();
        }
        turnLeft();
        for (int i=0;i<7;i++){
            move();
        }


    }
    private void g() {
        for (int i=0;i<7;i++){
            paintCorner(LIGHT_GRAY);
            move();
        }
        turnLeft();
        for (int i=0;i<7;i++){
            paintCorner(YELLOW);
            move();
        }   
        turnLeft();
        for (int i=0;i<4;i++){
            move();
        }   
        turnAround();
        for (int i=0;i<8;i++){
            paintCorner(PINK);
            move();
        }   
        turnRight();
        for (int i=0;i<4;i++){
            paintCorner(PINK);
            move();
        }   
        moveToWall();
        turnRight();
        for (int i=0;i<11;i++){
            move();
        }
        turnRight();    
        for (int i=0;i<14;i++){
            move();
            paintCorner(BLACK);}    
        turnRight();
        for (int i=0;i<7;i++){
            move();
            paintCorner(RED);}  
        turnRight();
        for (int i=0;i<5;i++){
            move();
            paintCorner(BLUE);} 
        turnLeft();
        for (int i=0;i<5;i++){
            move();
        }
        turnLeft();
        for (int i=0;i<5;i++){
            move();
        }
        turnAround();
    }
    private void y() {
        paintCorner(MAGENTA);
        downDiagonal();
        paintCorner(GREEN);
        move();
        turnRight();
        move();
        paintCorner(ORANGE);
        turnLeft();
        move();
        turnLeft();
        move();
        paintCorner(DARK_GRAY);
        move();
        turnRight();
        move();
        turnAround();
        paintCorner(BLUE);
        for (int i=0;i<2;i++){
            move();
        }
        turnLeft();
        for (int i=0;i<3;i++){
            move();
        }
        for (int i=0;i<11;i++){
            paintCorner(CYAN);
            move();
        }
        paintCorner(CYAN);
    }


    private void downDiagonal(){
        move();
        turnLeft();
        move(); 
    }
    private void exclamationMark() {
        turnLeft();
        for (int i=0;i<4;i++){
            move();
        }
        turnLeft();
        for (int i=0;i<3;i++){
            move();
        }
        while (frontIsClear()) {
            paintCorner(RED);
            move();
        }
        paintCorner(RED);
        turnLeft();
        for (int i=0;i<5;i++){
            move();
        }
        turnLeft();
        for (int i=0;i<8;i++){
            move();
        }
        turnRight();
        for (int i=0;i<24;i++){
            move();
        }
        turnLeft();
        for (int i=0;i<5;i++){
            move();
        }
        rightMove();
        rightMove();
        turnLeft();
        turnAround();
        moveToWall();
        rightMove();

    }
    private void rightMove() {
        turnRight();
        moveToWall();
    }
    private void shiny(){
        while (true) {
            paintCorner(RED);
            paintCorner(MAGENTA);
            paintCorner(BLUE);
            paintCorner(PINK);
            paintCorner(GREEN);
            paintCorner(GRAY);
            paintCorner(CYAN);
        }
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
