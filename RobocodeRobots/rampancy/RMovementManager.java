/**
 * RMovementManager.java
 */
package rampancy;

import rampancy.util.*;
import rampancy.util.movement.RMoveChoice;

import java.awt.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public interface RMovementManager {

    public void updateReference(RampantRobot reference);
    
    /**
     * Computes the best next move,
     * may optionally use the supplied enemy
     * to aid with the choice
     * @return the "best" movement choice
     */
    public RMoveChoice computeBestMove(REnemyRobot enemy);
    
    /**
     * @param g
     */
    public void draw(Graphics2D g);
}
