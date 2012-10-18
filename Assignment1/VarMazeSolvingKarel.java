import java.awt.Color;

import stanford.karel.SuperKarel;

/**
 * 
 */

/**
 * @author Matt Chun-Lum
 *
 */
public class VarMazeSolvingKarel extends SuperKarel {
    
    private boolean found;
    private boolean depthBeforeBranch;
    
    public void run() {
        int initialDir = getDirection();
        int d = 1;
        while(!found) {
            search(d++);
            face(initialDir);
        }
    }
    
    // search with a depth
    public void search(int depth) {
       if(beepersPresent()) {
           pickBeeper();
           found = true;
       } else {
           if(inDeadEnd()) {
               paintCorner(Color.GRAY);
           }
       }
    }

    
    public boolean inDeadEnd() {
        return (frontIsBlocked() && leftIsBlocked() && rightIsBlocked());
    }
    
    // Direction control
    
    public void face(int dir) {
        switch(dir) {
        case 0:
            faceNorth();
            break;
        case 1:
            faceEast();
            break;
        case 2:
            faceSouth();
            break;
        case 3:
            faceWest();
        }
    }
    
    public void faceNorth() {
        while(!facingNorth()) {
            turnLeft();
        }
    }
    
    public void faceSouth() {
        while(!facingSouth()) {
            turnLeft();
        }
    }
    
    public void faceEast() {
        while(!facingEast()) {
            turnLeft();
        }
    }
    
    public void faceWest() {
        while(!facingWest()) {
            turnLeft();
        }
    }
    
}
