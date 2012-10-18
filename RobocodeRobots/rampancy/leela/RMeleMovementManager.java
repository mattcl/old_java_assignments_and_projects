/**
 * 
 */
package rampancy.leela;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.*;

import rampancy.*;
import rampancy.standard.RDefaultSurfingSegmentArray;
import rampancy.util.*;
import rampancy.util.movement.*;
import rampancy.util.vector.*;
import rampancy.util.wave.REnemyWave;
import rampancy.util.wave.REnemyWaveWithStats;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RMeleMovementManager implements RMovementManager {

    private RampantRobot reference;
    private RVectorField vectorField;
    private HashMap<String, RRepulsiveObject> repulsiveObjects;
    private RVector lastMoveVector;

    public RMeleMovementManager(RampantRobot reference) {
        this.reference = reference;
        double width = reference.getBattleFieldWidth();
        double height = reference.getBattleFieldHeight();
        vectorField = new RVectorField(width, height, 0.02);
        setupRepulsiveObjects();
    }

    public RMoveChoice computeBestMove(REnemyRobot enemy) {
        updateRepulsivePoints(enemy);
        updateVectorField(enemy);
        lastMoveVector = new RVector(reference.getLocation());
        for(RRepulsiveObject point : repulsiveObjects.values()) {
            RVector force = point.getForceAtPoint(reference.getLocation());
            if(force != null && force.magnitude > 0) {
                lastMoveVector.add(force);
            }
        }
        
        if(lastMoveVector.magnitude == 0)
            return null;
        
        RPoint endPoint = lastMoveVector.getEndPoint();
        return new RMoveChoice(endPoint, lastMoveVector.getAngle(), 100, 8, 0, 0, 0, 0);
    }

    public void draw(Graphics2D g) {
        if(vectorField != null)
            vectorField.draw(g);
        
        if(lastMoveVector != null)
            lastMoveVector.draw(g);

    }

    public void updateReference(RampantRobot reference) {
        this.reference = reference;
        setupRepulsiveObjects();
    }
    
    public void removeWave(REnemyWave wave) {
        repulsiveObjects.remove(wave.hashCode() + "");
    }

    private void updateRepulsivePoints(REnemyRobot enemy) {
        List<REnemyWave> waves = RampantRobot.getWaveManager().getEnemyWaves();
        for(REnemyWave wave : waves) {
            if(wave instanceof REnemyWaveWithStats) {
                REnemyWaveWithStats eWave = (REnemyWaveWithStats) wave;
                double angle = RUtil.computeAbsoluteBearing(eWave.getOrigin(), reference.getLocation());
                RPoint dangerPoint = RUtil.project(eWave.getOrigin(), angle, eWave.getDistanceTraveled());
                double danger = eWave.getDangerForLocation(dangerPoint, RDefaultSurfingSegmentArray.NUM_GUESS_FACTORS);
                repulsiveObjects.put(wave.hashCode() + "", new RRepulsivePoint(dangerPoint, 200, 1000, 100));
            }
        }
        
        repulsiveObjects.put(enemy.getName(), 
                new RRepulsivePoint(enemy.getCurrentState().location.getCopy(), 300, 3000, 100));

    }

    private void updateVectorField(REnemyRobot enemy) {
        vectorField.update(repulsiveObjects.values());
    }
    
    private void setupRepulsiveObjects() {
        double width = reference.getBattleFieldWidth();
        double height = reference.getBattleFieldHeight();
        repulsiveObjects = new HashMap<String, RRepulsiveObject>();   
        Rectangle bfRect = RampantRobot.getGlobalBattlefield().innerRect;
        repulsiveObjects.put("Center", new RRepulsivePoint(width / 2, height / 2, 200, 1000, 1.5));
        repulsiveObjects.put("Lower Left", new RRepulsivePoint(bfRect.x, bfRect.y, 150, 200000, 1));
        repulsiveObjects.put("Lower Right", new RRepulsivePoint(bfRect.getMaxX(), bfRect.y, 150, 200000, 1));
        repulsiveObjects.put("Upper Left", new RRepulsivePoint(bfRect.x, bfRect.getMaxY(), 150, 200000, 1));
        repulsiveObjects.put("Upper Right", new RRepulsivePoint(bfRect.getMaxX(), bfRect.getMaxY(), 150, 200000, 1));
        repulsiveObjects.put("Left Wall", new RRepulsiveLine(bfRect.x, bfRect.y, bfRect.x, bfRect.getMaxY(), 75, 20000, 1.0));
        repulsiveObjects.put("Right Wall", new RRepulsiveLine(bfRect.getMaxX(), bfRect.y, bfRect.getMaxX(), bfRect.getMaxY(), 75, 20000, 1.0));
        repulsiveObjects.put("Top Wall", new RRepulsiveLine(bfRect.x, bfRect.getMaxY(), bfRect.getMaxX(), bfRect.getMaxY(), 75, 20000, 1.0));
        repulsiveObjects.put("Bottom Wall", new RRepulsiveLine(bfRect.x, bfRect.y, bfRect.getMaxX(), bfRect.y, 75, 20000, 1.0));
    }

}
