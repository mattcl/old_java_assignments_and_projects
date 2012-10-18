import stanford.karel.*;
public class RedJava extends SuperKarel {
    
    public void run() {
        paintSquare();
        paint();
    }
    
    public void paint() {
        while(notFacingNorth())
            if(frontIsClear()) {
                move();
                paintSquare();
            } else {
                moveBack();
                moveUpRow();
                paintSquare();
            }
    }
    
    public void paintSquare() {
        if(beepersPresent()) {
            pickBeeper();
            paintCorner(RED);
        } else if(random(0.5)) {
            paintCorner(BLACK);
        } else {
            paintCorner(WHITE);
        }
    }
    
    public void moveBack() {
        turnAround();
        while(frontIsClear())
            move();
        turnRight();
    }
    
    public void moveUpRow() {
        if(frontIsClear()) {
            move();
            turnRight();
        }
    }
}
