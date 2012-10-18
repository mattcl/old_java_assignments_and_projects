/*
 * File: KarelContest.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

/*
 * Name: Eric Lifland
 * Section Leader: 
 */

public class Eric extends SuperKarel {

    public void run() {
        moveToStart();
        epgy();
        moveToNextRow();
        stanford();
    }   

    private void epgy(){
        e();
        p();
        g();
        y();
    }
    
    private void stanford(){
        s();
        t();
        a();
        n();
        moveToLastRow();
        f();
        o();
        r();
        d();
    }
    
    private void moveToStart(){
        turnLeft();
        moveTen();
        moveTen();
        moveTen();
        moveFive();
        moveThree();
        turnRight();
    }
    
    private void moveToNextRow(){
        turnAround();
        moveToWall();
        turnLeft();
        moveTen();
        moveFive();
        turnLeft();
    }
    
    private void moveToLastRow(){
        turnAround();
        moveToWall();
        turnLeft();
        moveTwo();
        moveTen();
        turnLeft();
    }
    
    private void paintColor(){
        if(random(0.75)){
            paintCorner(RED);
        }
        else{ 
            paintCorner(BLACK);
        }
    }


    private void moveToWall(){
        while(frontIsClear()){
            move();
        }
    }
    //MOVES: beeper is on some because originally they placed beepers instead of coloring.

    private void moveFourBeeper(){
        for(int i = 0; i < 4; i++){
            move();
            paintColor();
        }
    }

    private void moveFiveBeeper(){
        for(int i = 0; i < 5; i++){
            move();
            paintColor();
        }
    }
    private void moveFive(){
        for(int i = 0; i < 5; i++){
            move();
        }
    }   
    private void moveTen(){
        for(int i = 0; i < 10; i++){
            move();
        }
    }   
    private void moveTenBeeper(){
        for(int i = 0; i < 10; i++){
            move();
            paintColor();
        }
    }   

    private void moveFour(){
        for(int i = 0; i < 4; i++){
            move();
        }
    }

    private void moveEight(){
        for(int i = 0; i < 8; i++){
            move();
        }
    }


    private void moveNine(){
        for(int i = 0; i < 9; i++){
            move();
        }
    }

    private void moveTwoBeeper(){
        for(int i = 0; i < 2; i++){
            move();
            paintColor();
        }
    }   
    private void moveTwo(){
        for(int i = 0; i < 2; i++){
            move();
        }
    }

    private void moveThree(){
        for(int i = 0; i < 3; i++){
            move();
        }
    }

    private void moveThreeBeeper(){
        for(int i = 0; i < 3; i++){
            move();
            paintColor();
        }
    }

    private void moveSixBeeper(){
        for(int i = 0; i < 6; i++){
            move();
            paintColor();
        }
    }

    private void moveSix(){
        for(int i = 0; i < 6; i++){
            move();
        }
    }

    private void moveSevenBeeper(){
        for(int i = 0; i < 7; i++){
            move();
            paintColor();
        }
    }

    private void moveOne(){
        for(int i = 0; i < 1; i++){
            move();
        }
    }

    private void moveOneBeeper(){
        for(int i = 0; i < 1; i++){
            move();
            paintColor();
        }
    }

    //Diagonal - all diagonals are beepers

    private void oneDiagonalUpLeft(){
        //starts at bottom facing up.
        moveOne();
        turnLeft();
        moveOneBeeper();
        turnRight();
    }

    private void oneDiagonalUpRight(){
        //starts at bottom facing up.
        moveOne();
        turnRight();
        moveOneBeeper();
        turnLeft();
    }

    private void oneDiagonalDownLeft(){
        //starts at top facing down
        moveOne();
        turnRight();
        moveOneBeeper();
        turnLeft();
    }

    private void oneDiagonalDownRight(){
        //starts at top facing down
        moveOne();
        turnLeft();
        moveOneBeeper();
        turnRight();

    }



    private void twoDiagonalUpLeft(){
        oneDiagonalUpLeft();
        oneDiagonalUpLeft();
    }

    private void twoDiagonalUpRight(){
        oneDiagonalUpRight();
        oneDiagonalUpRight();
    }

    private void twoDiagonalDownLeft(){
        oneDiagonalDownLeft();
        oneDiagonalDownLeft();
    }

    private void twoDiagonalDownRight(){
        oneDiagonalDownRight();
        oneDiagonalDownRight();
    }

    private void fiveDiagonalDownRight(){
        oneDiagonalDownRight();
        oneDiagonalDownRight();
        oneDiagonalDownRight();
        oneDiagonalDownRight();
        oneDiagonalDownRight();
    }


    //LETTERS:

    private void e(){
        paintColor();
        moveSixBeeper();
        turnAround();
        moveSix();
        turnRight();
        moveFiveBeeper();
        turnRight();
        moveFiveBeeper();
        turnAround();
        moveFive();
        turnRight();
        moveFiveBeeper();
        turnRight();
        moveSixBeeper();
        turnAround();
        moveSix();
        turnLeft();
        moveTen();
        turnLeft();
        moveNine();
    }

    private void p(){
        paintColor();
        turnLeft();
        moveTenBeeper();
        turnRight();
        moveFiveBeeper();
        turnRight();
        oneDiagonalDownRight();
        moveThreeBeeper();
        oneDiagonalDownLeft();
        turnRight();
        moveFiveBeeper();
        turnLeft();
        moveFive();
        turnLeft();
        moveNine();

    }

    private void g(){
        paintColor();
        moveSixBeeper();
        turnLeft();
        moveFiveBeeper();
        turnLeft();
        moveTwoBeeper();
        turnAround();
        moveTwo();
        turnRight();
        moveFive();
        turnRight();
        moveSix();
        turnRight();
        moveTenBeeper();
        turnRight();
        moveSixBeeper();
        turnRight();
        moveTen();
        turnLeft();
        moveThree();    
    }

    private void y(){
        moveFour();
        turnLeft();
        paintColor();
        moveFiveBeeper();
        twoDiagonalUpLeft();
        oneDiagonalUpLeft();
        moveTwoBeeper();
        turnRight();
        moveSix();
        turnRight();
        paintColor();
        moveTwoBeeper();
        twoDiagonalDownLeft();
        oneDiagonalDownLeft();
        moveFive();
        turnLeft();
        moveFive();
    }

    private void s(){
        turnLeft();
        moveOne();
        paintColor();
        turnAround();
        oneDiagonalDownRight();
        turnLeft();
        moveThreeBeeper();
        turnLeft();
        twoDiagonalUpRight();
        moveTwoBeeper();
        twoDiagonalUpLeft();
        turnLeft();
        moveThreeBeeper();
        turnRight();
        oneDiagonalUpLeft();
        moveTwoBeeper();
        oneDiagonalUpRight();
        turnRight();
        moveFourBeeper();
        turnRight();
        oneDiagonalDownRight(); 
        moveNine();
        turnLeft();
        moveThree();
    }

    private void t(){
        moveThree();
        turnLeft();
        paintColor();
        moveTenBeeper();
        turnLeft();
        moveThreeBeeper();
        turnAround();
        moveThree();
        moveThreeBeeper();
        turnRight();
        moveTen();
        turnLeft();
        moveThree();
    }

    private void a(){
        paintColor();
        turnLeft();
        moveSevenBeeper();
        oneDiagonalUpRight();
        twoDiagonalUpRight();
        turnAround();
        oneDiagonalDownRight();
        twoDiagonalDownRight();
        turnRight();
        moveSixBeeper();
        turnAround();
        moveSix();
        turnRight();
        moveSevenBeeper();
        turnLeft();
        moveThree();
    }

    private void n(){
        paintColor();
        turnLeft();
        moveTenBeeper();
        turnAround();
        fiveDiagonalDownRight();
        oneDiagonalDownRight();
        turnAround();
        moveSixBeeper();
        turnAround();
        moveSix();
        moveFourBeeper();
        turnLeft();
        moveThree();
    }

    private void f(){
        paintColor();
        turnLeft();
        moveFiveBeeper();
        turnRight();
        moveFiveBeeper();
        turnAround();
        moveFive();
        turnRight();
        moveFiveBeeper();
        turnRight();
        moveSixBeeper();
        turnRight();
        moveTen();
        turnLeft();
        moveThree();

    }

    private void o(){
        moveTwo();
        turnLeft();
        twoDiagonalUpLeft();
        moveSixBeeper();
        twoDiagonalUpRight();
        turnRight();
        moveTwoBeeper();
        turnRight();
        twoDiagonalDownRight();
        moveSixBeeper();
        twoDiagonalDownLeft();
        turnRight();
        moveTwoBeeper();
        turnAround();
        moveEight();
    }

    private void r(){
        paintColor();
        turnLeft();
        moveTenBeeper();
        turnRight();
        moveThreeBeeper();
        turnRight();
        twoDiagonalDownRight();
        moveOneBeeper();
        twoDiagonalDownLeft();
        turnRight();
        moveThreeBeeper();
        turnAround();
        move();
        turnRight();
        fiveDiagonalDownRight();
        turnLeft();
        moveThree();
    }

    private void d(){
        paintColor();
        turnLeft();
        moveTenBeeper();
        turnRight();
        moveFourBeeper();
        turnRight();
        twoDiagonalDownRight();
        moveSixBeeper();
        twoDiagonalDownLeft();
        turnRight();
        moveFourBeeper();
        moveEight();
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
