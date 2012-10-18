/**
 * 
 */
package rampancy.standard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.*;

import rampancy.*;
import rampancy.util.*;
import rampancy.util.movement.MovSim;
import rampancy.util.movement.MovSimStat;
import rampancy.util.movement.RMoveChoice;
import rampancy.util.wave.REnemyWave;
import rampancy.util.wave.REnemyWaveWithStats;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 * Movement strategy:
 * --------------------------------
 * Identify the nearest wave
 * Simulate movement in either direction
 * Select the best location
 *
 */
public class RDefaultMovementManager implements RMovementManager {
    public static final double MIN_DISTANCE = 73.875;
    public static final double MIN_WAVE_ORBIT_DISTANCE = 230;
    public static final double MAX_ORBIT_DISTANCE = 475;
    
    public static final Stroke ABSOLUTE_STROKE  = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 4, 10 }, 0);
    public static final Stroke MIN_ORBIT_STROKE = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 4, 20 }, 0);
    public static final Stroke MAX_ORBIT_STROKE = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 4, 20 }, 0);

    public static final Color ABSOLUTE_STROKE_COLOR = Color.red;
    public static final Color MIN_ORBIT_STROKE_COLOR = Color.yellow;
    public static final Color MAX_ORBIT_STROKE_COLOR = new Color(0xA65CB5);
    
    public static final Color CW_COLOR = new Color(0, 0, 255);
    public static final Color CCW_COLOR = new Color(0, 200, 200);

    public static final int CW  = 1;
    public static final int CCW = -1;

    private ArrayList<RMoveChoice> examinedMovementChoices;
    private MoveStruct lastMoveStruct;
    private RampantRobot reference;
    private int direction;
    private REnemyWave lastWave;
    
    protected Ellipse2D.Double absoluteDangerZone;
    protected Ellipse2D.Double minOrbitZone;
    protected Ellipse2D.Double maxOrbitZone;

    /**
     * Constructor
     */
    public RDefaultMovementManager(RampantRobot reference) {
        examinedMovementChoices = new ArrayList<RMoveChoice>();
        this.reference = reference;
        direction = 1;
    }

    public void updateReference(RampantRobot reference) {
        this.reference = reference;
    }

    /* (non-Javadoc)
     * @see rampancy_v2.RMovementManager#computeBestMove()
     */
    public RMoveChoice computeBestMove(REnemyRobot enemy) {
        REnemyWave enemyWave = RampantRobot.getWaveManager().getClosestEnemyWave(reference.getLocation());
        REnemyWave secondWave = null;
        if(RampantRobot.getWaveManager() instanceof RDefaultWaveManager) {
            secondWave = ((RDefaultWaveManager) RampantRobot.getWaveManager()).getNthClosestEnemyWave(reference.getLocation(), 2);
        }
        if(enemyWave == null) {
            enemyWave = new REnemyWaveWithStats(enemy);
        }
        examinedMovementChoices.clear();
        lastWave = enemyWave;
        MoveStruct moveStruct = new MoveStruct(reference, enemy, enemyWave, secondWave);
        simulateMove(moveStruct);
        if(examinedMovementChoices.isEmpty()) {
            return null;
        }
        lastMoveStruct = moveStruct;
        computeDangerForMoveChoices(moveStruct);
        sortMoveChoices();
        setDangerColors();

        direction = examinedMovementChoices.get(0).direction;

        examinedMovementChoices.get(0).color = Color.pink;
        return examinedMovementChoices.get(0);
    }

    /* (non-Javadoc)
     * @see rampancy.RMovementManager#draw(java.awt.Graphics2D)
     */
    public void draw(Graphics2D g) {
        
        if(examinedMovementChoices == null || examinedMovementChoices.isEmpty())
            return;
        
        for(RMoveChoice moveChoice : examinedMovementChoices)
            moveChoice.draw(g);
        
        RPoint enemyLocation = lastMoveStruct.enemy.getCurrentState().location;
        
        absoluteDangerZone = new Ellipse2D.Double(enemyLocation.x - MIN_DISTANCE, 
                enemyLocation.y - MIN_DISTANCE, 
                MIN_DISTANCE * 2, 
                MIN_DISTANCE * 2);

        minOrbitZone = new Ellipse2D.Double(enemyLocation.x - MIN_WAVE_ORBIT_DISTANCE, 
                enemyLocation.y - MIN_WAVE_ORBIT_DISTANCE, 
                MIN_WAVE_ORBIT_DISTANCE * 2, 
                MIN_WAVE_ORBIT_DISTANCE * 2);
        
        maxOrbitZone = new Ellipse2D.Double(enemyLocation.x - MAX_ORBIT_DISTANCE, 
                enemyLocation.y - MAX_ORBIT_DISTANCE, 
                MAX_ORBIT_DISTANCE * 2, 
                MAX_ORBIT_DISTANCE * 2);
        
        Stroke stroke = g.getStroke();
        
        g.setColor(ABSOLUTE_STROKE_COLOR);
        g.setStroke(ABSOLUTE_STROKE);
        g.draw(absoluteDangerZone);
        
        g.setColor(MIN_ORBIT_STROKE_COLOR);
        g.setStroke(MIN_ORBIT_STROKE);
        g.draw(minOrbitZone);
        
        g.setColor(MAX_ORBIT_STROKE_COLOR);
        g.setStroke(MAX_ORBIT_STROKE);
        g.draw(maxOrbitZone);
        g.setStroke(stroke);
        
        double distance = lastMoveStruct.enemy.getCurrentState().distance;
        RPoint midpoint = enemyLocation.projectTo(enemyLocation.computeAbsoluteBearingTo(reference.getLocation()), distance / 2);
        
        // draw distance line
        if(distance < MIN_WAVE_ORBIT_DISTANCE) {
            g.setColor(Color.yellow);
        } else if(distance < MAX_ORBIT_DISTANCE) {
            g.setColor(Color.DARK_GRAY);
        } else {
            g.setColor(MAX_ORBIT_STROKE_COLOR);
        }
        g.draw(new Line2D.Double(reference.getLocation(), enemyLocation));
        
        g.setColor(Color.white);
        g.drawString(RUtil.roundToPrecision(distance, 1) + "", (int) midpoint.x, (int) midpoint.y);
    }

    /**
     * Simulate the complete movement for the given wave
     * @param moveStruct
     */
    private void simulateMove(MoveStruct moveStruct) {
        MoveProfile startProfileCW = new MoveProfile(reference.getCopyOfLocation(), -5000, reference.getHeadingRadians(), 
                reference.getHeadingRadians(), reference.getVelocity(), 8.0, CW, 0);
        startProfileCW.initialOrbitAngle = computeOrbitAngle(startProfileCW, moveStruct);
        MoveProfile startProfileCCW = startProfileCW.getCopy();
        startProfileCCW.direction = CCW;
        startProfileCCW.initialOrbitAngle = computeOrbitAngle(startProfileCCW, moveStruct);
        recursiveSimulateMove(CW, startProfileCW, moveStruct);
        recursiveSimulateMove(CCW, startProfileCCW, moveStruct);

    }

    /**
     * Recursively simulate movement in the given direction
     * @param direction
     * @param currentProfile
     * @param moveStruct
     */
    private void recursiveSimulateMove(int direction, MoveProfile currentProfile, MoveStruct moveStruct) {
        if(!moveStruct.wave.intercepted(currentProfile.fromLocation, currentProfile.futureTicks)) {
            //simulateStop(currentProfile, moveStruct);
            simulateMoveDirection(direction, currentProfile, moveStruct);
        }
    }

    private void simulateMoveDirection(int direction, MoveProfile currentProfile, MoveStruct moveStruct) {
        MoveProfile moveProfile = currentProfile.getCopy();
        moveProfile.desiredHeading = computeOrbitAngle(currentProfile, moveStruct);
        MoveProfile predictedProfile = predictPosition(moveProfile);
        logLocation(predictedProfile, moveStruct);
        recursiveSimulateMove(direction, predictedProfile, moveStruct);
    }

    private void logLocation(MoveProfile currentProfile, MoveStruct moveStruct) {
        double distance = currentProfile.fromLocation.distance(moveStruct.wave.getOrigin());
        double danger = 0;
        double guessFactor = RUtil.getFactorIndex(moveStruct.wave, currentProfile.fromLocation, RDefaultSurfingSegmentArray.NUM_GUESS_FACTORS);
        RMoveChoice moveChoice = new RMoveChoice(currentProfile.fromLocation, currentProfile.initialOrbitAngle, distance, currentProfile.maxVelocity, 
                danger, guessFactor, currentProfile.futureTicks, currentProfile.direction);
        examinedMovementChoices.add(moveChoice);
    }

    private double computeOrbitAngle(MoveProfile currentProfile, MoveStruct moveStruct) {
        RPoint orbitLocation = moveStruct.wave.getOrigin();
        double distanceToCenterOfOrbit = orbitLocation.distance(currentProfile.fromLocation);
        double absB = RUtil.computeAbsoluteBearing(orbitLocation, currentProfile.fromLocation);
        double orbitAngle = absB + Math.PI/2 * currentProfile.direction;
        double attackAngle = computeAttackAngle(currentProfile, moveStruct) + orbitAngle;
        double wallSmoothedAngle = RUtil.wallSmoothing(currentProfile.fromLocation, 
                attackAngle, currentProfile.direction, distanceToCenterOfOrbit);
        return Utils.normalAbsoluteAngle(wallSmoothedAngle);
    }

    private double computeAttackAngle(MoveProfile currentProfile, MoveStruct moveStruct) {
        double distanceToEnemy = currentProfile.fromLocation.distance(moveStruct.enemy.getCurrentState().location);
        double distanceToWaveOrigin = currentProfile.fromLocation.distance(moveStruct.wave.getOrigin());
        if(distanceToEnemy < MIN_DISTANCE) {
            return -(Math.PI / 4) * currentProfile.direction;
        } else if(distanceToEnemy < MAX_ORBIT_DISTANCE) {
            if(distanceToEnemy < moveStruct.enemyDistance && moveStruct.enemy.getCurrentState().directionTraveling != currentProfile.direction) {
                // we're on a collision course with the enemy
                // adjust our oribt to prevent the collsion
                return -(Math.PI / 10) * currentProfile.direction;
            }
            
            return 0;
        } else { 
            return (Math.PI / 8) * currentProfile.direction;
        }
        
    }

    private void computeDangerForMoveChoices(MoveStruct moveStruct) {
        REnemyWaveWithStats statWave = null;
        REnemyWaveWithStats secondStatWave = null;
        if(moveStruct.wave instanceof REnemyWaveWithStats) {
            statWave = (REnemyWaveWithStats) moveStruct.wave;
        }
//        if(moveStruct.secondWave != null && moveStruct.secondWave instanceof REnemyWaveWithStats) {
//            secondStatWave = (REnemyWaveWithStats) moveStruct.secondWave;
//        }
        for(RMoveChoice moveChoice : examinedMovementChoices) {
            double danger = statWave.getDangerForLocation(moveChoice.destination, RDefaultSurfingSegmentTree.NUM_BINS);
            if(secondStatWave != null) {
                danger += secondStatWave.getDangerForLocation(moveChoice.destination, RDefaultSurfingSegmentTree.NUM_BINS);
            }
            double dist = moveChoice.destination.distance(moveStruct.enemy.getCurrentState().location);
            if(dist < 200) {
                danger += (1.0 - dist/200.0) * 100;
            }
            moveChoice.danger += danger;
//            for(RMoveChoice subMove : examinedMovementChoices) {
//                if(!moveChoice.equals(subMove) && subMove.destination.getBotRect().contains(moveChoice.destination)) {
//                    subMove.danger += danger;
//                }
//            }
        }
    }

    private void sortMoveChoices() {
        Collections.sort(examinedMovementChoices, new Comparator<RMoveChoice>() {
            public int compare(RMoveChoice o1, RMoveChoice o2) {
                return RUtil.sign(o1.danger - o2.danger);
            }
        });
    }
    
    private void setDangerColors() {
        double largest = examinedMovementChoices.get(examinedMovementChoices.size() - 1).danger;
        for(RMoveChoice moveChoice : examinedMovementChoices) {
            Color dangerColor = new Color(0, 0, 200);
            if(moveChoice.danger > 0.01) {
                dangerColor = new Color((int) (155 * (moveChoice.danger / largest)) + 100, 0, 0);
            }
            moveChoice.color = dangerColor;
        }
    }

    /*
     * Accurately predicts the future position
     * Credit goes to Albert for the MovSim part
     */
    private MoveProfile predictPosition(MoveProfile currentProfile) {     
        double angleToTurn = currentProfile.desiredHeading - currentProfile.currentHeading;
        int moveDirection = 1;
        if(Math.cos(angleToTurn) < 0) {
            angleToTurn += Math.PI;
            moveDirection = -1;
        }
        RPoint fromLocation = currentProfile.fromLocation;
        angleToTurn = Utils.normalRelativeAngle(angleToTurn);
        RBattlefield bf = RampantRobot.getGlobalBattlefield();
        MovSimStat[] nextTick = MovSim.futurePos(1, fromLocation.x, fromLocation.y, 
                currentProfile.currentVelocity, currentProfile.maxVelocity, 
                currentProfile.currentHeading, 1000 * moveDirection, angleToTurn, 
                MovSim.defaultMaxTurnRate, bf.width, bf.height);     
        MovSimStat sim =  nextTick[0];
        return new MoveProfile(new RPoint(sim.x, sim.y), currentProfile.initialOrbitAngle, sim.h, sim.h, sim.v, 
                currentProfile.maxVelocity, currentProfile.direction, currentProfile.futureTicks + 1);
    }

}

class MoveStruct {

    public MoveStruct(RampantRobot reference, REnemyRobot enemy,
            REnemyWave wave, REnemyWave secondWave) {
        this.reference = reference;
        this.enemy = enemy;
        this.wave = wave;
        this.secondWave = secondWave;
        this.enemyDistance = reference.getLocation().distance(enemy.getCurrentState().location);
        this.waveDistance = reference.getLocation().distance(wave.getOrigin());
    }
    public RampantRobot reference;
    public REnemyRobot enemy;
    public REnemyWave wave;
    public REnemyWave secondWave;
    public double enemyDistance;
    public double waveDistance;
}

class MoveProfile {

    public MoveProfile(RPoint fromLocation, double initialOrbitAngle, double currentHeading, 
            double desiredHeading, double currentVelocity, 
            double maxVelocity, int direction, int futureTicks) {
        this.fromLocation = fromLocation;
        this.initialOrbitAngle = initialOrbitAngle;
        this.currentHeading = currentHeading;
        this.desiredHeading = desiredHeading;
        this.currentVelocity = currentVelocity;
        this.maxVelocity = maxVelocity;
        this.direction = direction;
        this.futureTicks = futureTicks;
    }

    public MoveProfile getCopy() {
        return new MoveProfile(fromLocation.getCopy(), initialOrbitAngle, currentHeading, 
                desiredHeading, currentVelocity, maxVelocity, direction, futureTicks);
    }

    public RPoint fromLocation;
    public double initialOrbitAngle;
    public double currentHeading;
    public double desiredHeading;
    public double currentVelocity;
    public double maxVelocity;
    public int direction;
    public int futureTicks;
}
