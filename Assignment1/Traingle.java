import stanford.karel.SuperKarel;

/**
 * 
 */

/**
 * @author admin
 *
 */
public class Traingle extends SuperKarel {

    public void run() {
        buildTriangle();
        putBeeper();
    }

    private void buildTriangle() {
        initialBeeper();
        buildLine();
        stepUp();
        if(frontIsClear()) {
            buildTriangle();
        }
    }
    
    private void initialBeeper() {
        if(frontIsClear()) {
            putBeeper();
            move();
        }
    }
    
    private void buildLine() {
        putBeeper();
        if(frontIsClear()) {
            move();
            buildLine();
            move();
        } else {
            turnAround();
        }
    }
    
    private void stepUp() {
        turnRight();
        if(frontIsClear()) {
            move();
            turnRight();
        }
    }
    
}
