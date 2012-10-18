/**
 * 
 */
package rampancy.whuphs;

import java.awt.Color;
import java.awt.Graphics2D;
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
 *
 */
public class WhuphsMovementManager implements RMovementManager {
    public static final double MIN_DISTANCE = 230;
    public static final double MIN_WAVE_ORBIT_DISTANCE = 230;

    public static final Color CW_COLOR = new Color(0, 0, 255);
    public static final Color CCW_COLOR = new Color(0, 200, 200);

    private ArrayList<RMoveChoice> examinedMovementChoices;
    private REnemyWave dummyWave;
    private RampantRobot reference;
    private int direction;

    /**
     * Constructor
     */
    public WhuphsMovementManager(RampantRobot reference) {
        examinedMovementChoices = new ArrayList<RMoveChoice>();
        this.reference = reference;
        direction = 1;
    }

    public void updateReference(RampantRobot reference) {
        this.reference = reference;
        if(Math.random() < 0.7)
            direction = -direction;
    }

    /* (non-Javadoc)
     * @see rampancy_v2.RMovementManager#computeBestMove()
     */
    public RMoveChoice computeBestMove(REnemyRobot enemy) {
        dummyWave = new REnemyWave(enemy);
        ArrayList<RMoveChoice> movementChoices = getBestOrbitMove(new MoveStruct(reference, enemy, dummyWave));
        if(movementChoices.isEmpty()) 
            return null;

        examinedMovementChoices = new ArrayList<RMoveChoice>(movementChoices);
        Collections.sort(movementChoices, new Comparator<RMoveChoice>() {

            public int compare(RMoveChoice o1, RMoveChoice o2) {
                return (int) (o1.danger - o2.danger);
            }

        });

//        if(Math.random() < 0.01)
//            direction = -direction;

        int tempDirection = direction == 0 ? 1 : direction;

        for(RMoveChoice choice : movementChoices) {
            if(choice.direction == tempDirection)
                return choice;
        }
        return null;
    }

    /* (non-Javadoc)
     * @see rampancy_v2.RMovementManager#draw(java.awt.Graphics2D)
     */
    public void draw(Graphics2D g) {
        REnemyWave wave = RampantRobot.getWaveManager().getClosestEnemyWave(reference.getLocation());
        if(wave == null)
            wave = dummyWave;

        if(wave == null)
            return;

        double goAngle = RUtil.computeOrbitAngle(reference.getCopyOfLocation(), wave, 0.0, 1);
        double goAngleCCW = RUtil.computeOrbitAngle(reference.getCopyOfLocation(), wave, 0.0, -1);

        RPoint cw = RUtil.project(reference.getCopyOfLocation(), goAngle, RUtil.WALL_STICK);
        RPoint ccw = RUtil.project(reference.getCopyOfLocation(), goAngleCCW, RUtil.WALL_STICK);

        g.setColor(Color.blue);
        RUtil.drawOval(reference.getCopyOfLocation(), (int) RUtil.WALL_STICK, g);

        g.setColor(CW_COLOR);
        RUtil.drawOval(cw, 25, g);
        RUtil.fillOval(cw, 3, g);
        g.setColor(CCW_COLOR);
        RUtil.drawOval(ccw, 25, g);
        RUtil.fillOval(ccw, 3, g);

        g.setColor(Color.blue);
        for(RMoveChoice choice : examinedMovementChoices)
            choice.draw(g);
    }

    // ---------- Private Helpers ----------- //

    private ArrayList<RMoveChoice> getBestOrbitMove(MoveStruct solution) {
        ArrayList<RMoveChoice> choices = new ArrayList<RMoveChoice>();

        // simulate clockwise
        simulateOrbitDirection(solution.reference.getLocation(), solution.reference.getHeadingRadians(), solution.reference.getVelocity(),
                1, 0, solution, choices);

        // simulate counterclockwise
        simulateOrbitDirection(solution.reference.getLocation(), solution.reference.getHeadingRadians(), solution.reference.getVelocity(),
                -1, 0, solution, choices);

        return choices;
    }

    private void simulateOrbitDirection(RPoint fromLocation, double fromHeading, double fromVelocity, int direction, int timeOffset, MoveStruct solution, ArrayList<RMoveChoice> movementChoices) {
        if(solution.wave.intercepted(fromLocation, timeOffset))
            return;

        RPoint orbitLocation = solution.enemy.getCurrentState().location;

        double orbitDistance = orbitLocation.distance(fromLocation);

        double goAngle = RUtil.computeAbsoluteBearing(orbitLocation, fromLocation);
        double attackAngle = computeAttackAngle(goAngle, fromLocation, fromHeading, fromVelocity, direction, timeOffset, solution);


        goAngle = RUtil.wallSmoothing(fromLocation, goAngle + (Math.PI / 2.0 + attackAngle) * direction, 
                direction, orbitDistance);

        MovSimStat next = predictPosition(fromLocation, fromHeading, goAngle, fromVelocity, MovSim.defaultMaxVelocity, direction);
        RPoint nextLocation = new RPoint(next.x, next.y);

        // log this point as a movement choice
        logOrbitLocation(nextLocation, timeOffset, direction, solution, movementChoices);

        simulateOrbitDirection(nextLocation, next.h, next.v, direction, timeOffset + 1, solution, movementChoices);
    }


    private void logOrbitLocation(RPoint location, int timeOffset, int direction, MoveStruct solution, ArrayList<RMoveChoice> movementChoices) {
        double danger = 0;

        RPoint orbitLocation = solution.enemy.getCurrentState().location;

        if(location.distance(orbitLocation) < solution.enemy.getPreferredSafeDistance())
            danger += 100000.0 / RUtil.square(location.distance(orbitLocation));

        double orbitDistance = orbitLocation.distance(location);

        double goAngle = RUtil.computeAbsoluteBearing(orbitLocation, solution.reference.getLocation());
        double attackAngle = computeAttackAngle(goAngle, solution.reference.getLocation(), solution.reference.getHeadingRadians(), solution.reference.getVelocity(), direction, timeOffset, solution);


        goAngle = RUtil.wallSmoothing(solution.reference.getLocation(), goAngle + (Math.PI / 2.0 + attackAngle) * direction, 
                direction, orbitDistance);

        RMoveChoice moveChoice = new RMoveChoice(location, goAngle, -1, 8.0, danger, 0, timeOffset, direction);
        moveChoice.color = direction > 0 ? CW_COLOR : CCW_COLOR;
        movementChoices.add(moveChoice);
    }

    private double computeAttackAngle(double goAngle, RPoint fromLocation, double fromHeading, double fromVelocity, double direction, int timeOffset, MoveStruct solution) {
        double attackAngle = 0;
        REnemyRobot enemy = solution.enemy;
        RRobotState enemyState = enemy.getLastState();
        if(enemyState == null)
            enemyState = enemy.getCurrentState();
        if(fromLocation.distance(enemyState.location) < enemy.getPreferredSafeDistance()) { // way too vulnerable
            int enemyDirection = enemyState.directionTraveling;
            // if we are traveling in opposite directions we might collide
            double orbitAngle = goAngle + (Math.PI / 2.0 * direction);
            if(enemyDirection != direction) {
                double delta1 = Math.abs(orbitAngle - enemyState.heading);
                double delta2 = Math.abs(orbitAngle - Utils.normalAbsoluteAngle(enemyState.heading + Math.PI));
                attackAngle = -Math.min(delta1, delta2);
            } else {
                attackAngle = -0.05;
            }
        }
        return attackAngle;
    }

    /*
     * Accurately predicts the future position
     * Credit goes to Albert for the MovSim part
     */
    private MovSimStat predictPosition(RPoint fromLocation, double currentHeading, double desiredHeading, double currentVelocity, double maxVelocity, double direction) {     
        double angleToTurn = desiredHeading - currentHeading;
        int moveDirection = 1;
        if(Math.cos(angleToTurn) < 0) {
            angleToTurn += Math.PI;
            moveDirection = -1;
        }
        angleToTurn = Utils.normalRelativeAngle(angleToTurn);
        RBattlefield bf = RampantRobot.getGlobalBattlefield();
        MovSimStat[] nextTick = MovSim.futurePos(1, fromLocation.x, fromLocation.y, currentVelocity, maxVelocity, 
                currentHeading, 1000 * moveDirection, angleToTurn, MovSim.defaultMaxTurnRate, bf.width, bf.height);     
        return nextTick[0];
    }
}

class MoveStruct {

    public MoveStruct(RampantRobot reference, REnemyRobot enemy,
            REnemyWave wave) {
        this.reference = reference;
        this.enemy = enemy;
        this.wave = wave;
    }
    public RampantRobot reference;
    public REnemyRobot enemy;
    public REnemyWave wave;
}
