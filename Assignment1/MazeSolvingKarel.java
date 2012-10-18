import stanford.karel.*;
public class MazeSolvingKarel extends SuperKarel {

    public void run() {
        markEdge();
        while(noBeepersInBag() && !cornerColorIs(GRAY))
            search();
        followPath();
    }
    
    public void search() {
        if(beepersPresent()) {
            pickBeeper();
            markHasPath();
        }
        if(!cornerColorIs(GRAY) && noBeepersInBag()) {
            if(cornerColorIs(GREEN)) {
                // We're on an edge cell
                // so we have to search and mark ourself blue
                turnAround();
                if(frontIsClear()) {
                    move();
                    if(cornerColorIs(CYAN)) {
                        moveBackOne();
                        markIgnore();
                    } else {
                        moveBackOne();
                        markJustVisited();
                    }
                } else {
                    markJustVisited();
                }
                turnAround();
            }
            if(cornerColorIs(BLUE) || cornerColorIs(CYAN)) {
                // We've been here before
                if(cornerColorIs(BLUE))
                    markTrail();
                turnAround();
                searchRight();
                searchRight();
                searchRight();
                turnLeft();
                if(noBeepersInBag()) {
                    markVisited();
                    examineNeighbors();
                    if(cornerColorIs(YELLOW))
                        markVisited();
                    else
                        markDead();
                }
                if(cornerColorIs(CYAN))
                    markTrail();
            } else if(!cornerColorIs(RED) && !cornerColorIs(MAGENTA)) {
                markEdge();
            }
        }
    }
 
    public void examineNeighbors() {
        turnAround();
        examineRight();
        examineRight();
        examineRight();
        turnLeft();
    }
    
    public void examineRight() {
        turnRight();
        if(frontIsClear()) {
            move();
            if(cornerColorIs(GRAY)) {
                moveBackOne();
            } else if(cornerColorIs(RED)) {
                moveBackOne();
                markDead();
            } else if(cornerColorIs(MAGENTA)) {
                markEdge();
                moveBackOne();
            } else {
                moveBackOne();
                markHasPath();
            }
        }
    }
    
    public void searchRight() {
        turnRight();
        if(frontIsClear() && noBeepersInBag()) {
            move();
            if(!cornerColorIs(MAGENTA))
                search();
            turnAround();
            move();
            turnAround();
        }
    }
    
    public void followPath() {
        while(!cornerColorIs(YELLOW)) {
            turnLeft();
            if(frontIsClear()) {
                move();
                if(cornerColorIs(GRAY) || cornerColorIs(BLUE)) {
                    moveBackOne();
                    turnAround();
                }
            } else {
                turnAround();
            }
        }
    }
    
    public void moveBackOne() {
        turnAround();
        move();
        turnAround();
    }
    
    public void markVisited() {
        paintCorner(BLUE);
    }
    
    public void markJustVisited() {
        paintCorner(CYAN);
    }
    
    public void markIgnore() {
        paintCorner(MAGENTA);
    }
    
    public void markEdge() {
        paintCorner(GREEN);
    }
    
    public void markTrail() {
        paintCorner(RED);
    }
    
    public void markDead() {
        paintCorner(GRAY);
    }
    
    public void markHasPath() {
        paintCorner(YELLOW);
    }
}
