import stanford.karel.*;
public class MidpointTwo extends SuperKarel {

    public void run() {
        geometry();
        putBeeper();
        turnAround();
    }
    
    public void geometry() {
        while(frontIsClear())
            slopeOne();
        turnAround();
        while(leftIsClear()) {
            slopeHalf();
        }
    }
   
    public void slopeOne() {
       tryMove();
       turnLeft();
       tryMove();
       turnRight();
    }
   
    public void slopeHalf() {
        tryMove();
        turnLeft();
        tryMove();
        tryMove();
        turnRight();
    }
    
    public void tryMove() {
        if(frontIsClear())
            move();
    }
}
