/**
 * 
 */
package rampancy.durandal;

import java.awt.Graphics2D;

import rampancy.RMovementManager;
import rampancy.RampantRobot;
import rampancy.util.REnemyRobot;
import rampancy.util.movement.RMoveChoice;

/**
 * @author Matthew Chun-Lum
 *
 */
public class DurandalMovementManager implements RMovementManager {
    
    private RampantRobot reference;

    public RMoveChoice computeBestMove(REnemyRobot enemy) {
        // TODO Auto-generated method stub
        return null;
    }


    public void draw(Graphics2D g) {
        // TODO Auto-generated method stub
    }

    public void updateReference(RampantRobot reference) {
        this.reference = reference;
    }

}
