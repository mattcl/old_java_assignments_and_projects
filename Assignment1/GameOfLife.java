import stanford.karel.*;
public class GameOfLife extends SuperKarel {
    public void run() {
        initialSweep();
        turnRight();
        while(true) {
            exploreWorld();
            fixGeneration();
        }
    }
    
    public void initialSweep() {
        while(notFacingNorth()) {
            if(frontIsClear()) {
                move();
            } else {
                moveBack();
                moveUpRow();
            }
            if(beepersPresent()) {
                paintCorner(WHITE);
            } else {
                paintCorner(BLACK);
            }
        }
    }

    public void exploreWorld() {
        while(notFacingNorth()) {
            if(frontIsClear()) {
                move();
            } else {
                moveBack();
                moveUpRow();
            }
            checkSquare();
        }
    }
    
    public void fixGeneration() {
        turnRight();
        while(notFacingNorth()) {
            if(frontIsClear()) {
                move();
            } else {
                moveBack();
                moveUpRow();
            }
            if(beepersPresent()) {
                pickBeeper();
                if(beepersPresent()) {
                    pickBeeper();
                    if(beepersPresent()) {
                        // Dead cell
                        clearSquare();
                        paintCorner(BLACK);
                    } else {
                        // New cell
                        putBeeper();
                        paintCorner(WHITE);
                    }
                } else {
                    // surviving cell
                    putBeeper();
                    paintCorner(WHITE);
                }
            }
        }
        turnRight();
    }
    
    /*
     * Rules:
     * 
     *  1. Any live cell with fewer than two live neighbors dies
     *  2. Any live cell with more than three neighbors dies
     *  3. Any live cell with two or three live neighbors lives on to the next generation
     *  4. Any dead cell with exactly three live neighbors becomes a live cell
     */
    public void checkSquare() {
        if(beepersPresent()) {
            pickBeeper();
            if(noBeepersPresent()) { // Make sure we don't have a new cell
                countNeighbors();
                if(beepersPresent()) { // One neighbor
                    pickBeeper();
                    if(noBeepersPresent()) {
                        // Die because of rule 1
                        markDead();
                    } else { // Two neighbors
                        pickBeeper();
                        if(beepersPresent()) { // Three neighbors
                            pickBeeper();
                            if(beepersPresent()) { // Four neighbors
                                // Die because of rule 2
                                markDead();
                            } else {
                                // Cell is alive
                                putBeeper();
                            }
                        } else {
                            // Cell is alive
                            putBeeper();
                        }
                    }
                } else {
                    // Die because of rule 1
                    markDead();
                }
            } else {
                // this is a new live cell
                putBeeper();
            }
        } else {
            countNeighbors();
            if(beepersPresent()) { // One neighbor
                pickBeeper();
                if(beepersPresent()) { // Two neighbors
                    pickBeeper();
                    if(beepersPresent()) { // Three neighbors
                        pickBeeper();
                        if(noBeepersPresent()) { // Exactly three neighbors
                            // Cell comes to life because of rule 4
                            markNew();
                        } else {
                            clearSquare();
                        }
                    }
                }
            }
        }
    }
    
    public void markDead() {
        clearSquare();
        for(int i = 0; i < 3; i++)
            putBeeper();
    }
    
    public void markNew() {
        markDead();
        pickBeeper();
    }
    
    public void clearSquare() {
        while(beepersPresent()) {
            pickBeeper();
        }
    }
    
    public void countNeighbors() {
        checkNorth();
        checkSouth();
        checkEast();
        checkWest();
        checkNorthEast();
        checkSouthWest();
        checkNorthWest();
        checkSouthEast();
    }
    
    // ----------------- N, S, E, W ---------------- //
    
    public void checkNorth() {
        turnLeft();
        checkAhead();
    }
    
    public void checkSouth() {
        checkAhead();
    }
    
    public void checkEast() {
        turnRight();
        checkAhead();
    }
    
    public void checkWest() {
        checkAhead();
    }
    
    public void checkAhead() {
        if(frontIsClear()) {
            move();
            turnAround();
            if(beepersPresent()) {
                pickBeeper();
                if(beepersPresent()) {
                    pickBeeper();
                    if(beepersPresent()) {
                        // This is a dead cell, still count it
                        markDead();
                        move();
                        putBeeper();
                    } else {
                        // This is a new cell, don't count it
                        markNew();
                        move();
                    }
                } else {
                    // This is an surviving cell
                    putBeeper();
                    move();
                    putBeeper();
                }
            } else {
                move();
            }
        } else {
            turnAround();
        }
    }
    
    // ---------- NE, NW, SW, SE ------------ //
    public void checkNorthEast() {
        checkDiagonal();
    }
    
    public void checkSouthWest() {
        checkDiagonal();
    }
    
    public void checkNorthWest() {
        turnLeft();
        checkDiagonal();
    }
    
    public void checkSouthEast() {
        checkDiagonal();
        turnRight();
    }
    
    public void checkDiagonal() {
        if(frontIsClear()) {
            move();
            turnLeft();
            if(frontIsClear()) {
                move();
                turnAround();
                if(beepersPresent()) {
                    pickBeeper();
                    if(beepersPresent()) {
                        pickBeeper();
                        if(beepersPresent()) {
                            // This is a dead cell, still count it
                            markDead();
                            moveDiagonal();
                            putBeeper();
                        } else {
                            // This is a new cell, don't count it
                            markNew();
                            moveDiagonal();
                        }
                    } else {
                        // This is a surviving cell, count it
                        putBeeper();
                        moveDiagonal();
                        putBeeper();
                    }
                } else {
                    moveDiagonal();
                }
            } else {
                turnLeft();
                move();
            }
        } else {
            turnAround();
        }
    }
    
    public void moveDiagonal() {
        move();
        turnRight();
        move();
    }
    
    // ---------- Basic Movement Methods --------- //
    public void moveBack() {
        turnAround();
        while(frontIsClear())
            move();
        turnAround();
    }
    
    public void moveUpRow() {
        turnLeft();
        if(frontIsClear()) {
            move();
            turnRight();
        } else {
            moveBack();
        }
    }
    
//  Note: This is an unfortunate hack to correct a
//  shortfall in our new Eclipse plugin. Don't worry
//  about (you won't be tested on it and aren't expected
//  to understand what's going on). However, don't
//  delete it, or you won't be able to run your Karel
//  program.
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

