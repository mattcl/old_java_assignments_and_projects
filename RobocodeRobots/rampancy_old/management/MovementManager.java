/**
 * MovementManager.java
 */
package rampancy_old.management;

import java.awt.geom.Point2D;
import java.util.*;
import java.awt.Graphics2D;

import rampancy_old.*;
import rampancy_old.statistics.MovementStatistic;
import rampancy_old.util.*;
import robocode.util.Utils;

/**
 * This class handles Durandal's movement calculation
 * @author Matthew Chun-Lum
 *
 */
public class MovementManager {

    public static final double WALL_STICK = 160;
    public static final int SEARCH_DEPTH = 1;
    private static final int MAX_HISTORY_DEPTH = 5;

    public static Battlefield battlefield;

    private RampantRobot reference;
    private MovSim movementSimulator;

    private ArrayList<RampantRobotState> movementHistory;

    private int lastDirection;

    public static Battlefield getBattlefield() {
        if(battlefield == null)
            System.out.println("Accessing a null battlefield!");
        return battlefield;
    }
    
    /**
     * Default constructor
     */
    public MovementManager() {
        movementSimulator = new MovSim();
    }

    /**
     * Sets the reference to Durandal
     * @param reference
     */
    public void setInitialState(RampantRobot reference, double battlefieldWidth, double battlefieldHeight) {
        this.reference = reference;
        if(battlefield == null)
            battlefield = new Battlefield(battlefieldWidth, battlefieldHeight);
        movementHistory = new ArrayList<RampantRobotState>();
    }

    /**
     * Adds the robot's current state to the movement history
     * @param enemy
     */
    public void updateCurrentState(EnemyRobot enemy) {
        int historyDepth = movementHistory.size();
        RampantRobotState last = (historyDepth > 0 ? movementHistory.get(0) : null);
        movementHistory.add(0, new RampantRobotState(reference, enemy, last));
        if(historyDepth >= MAX_HISTORY_DEPTH)
            movementHistory.remove(MAX_HISTORY_DEPTH);
    }

    /**
     * @return a {@link RampantRobotState} representing the robot's state two ticks ago
     */
    public RampantRobotState getLastUsableState() {
        return (movementHistory.size() < 3 ? null : movementHistory.get(2));
    }

    /**
     * Compute the next move the robot should make
     */
    public void computeNextMove(EnemyRobot enemy) {
        EnemyWave surfableWave = reference.getWaveManager().getClosestEnemyWaveToImpact(reference.getLocation());

        if(surfableWave == null) {
            reference.getWaveManager().createVirtualWave(enemy);
        } else {
            double angleAdjustment = computeAngleAdjustment(enemy);   
            double[] options = new double[3];
            for(int i = 0; i < options.length; i++)
                options[i] = getMinimumDanger(angleAdjustment, i);

            int min = getMinimumIndex(options);
            double goAngle = Util.computeAbsoluteBearing(surfableWave.getOrigin(), reference.getLocation());
            switch(min) {
                case 0:
                    //goAngle = wallSmoothing(reference.getLocation(), goAngle + Math.PI / 2 + angleAdjustment, 1);
                    //Util.setBackAsFront(reference, goAngle, 0);
                    return;
                case 1: // right
                    goAngle = wallSmoothing(reference.getLocation(), goAngle + Math.PI / 2 + angleAdjustment, 1);
                    lastDirection = 1;
                    break;
                case 2:
                    goAngle = wallSmoothing(reference.getLocation(), goAngle - Math.PI / 2 - angleAdjustment, -1);
                    lastDirection = -1;

            }
            Util.setBackAsFront(reference, goAngle, 200); 
        }

    }

    /**
     * Precisely predicts the location the wave will intercept at given the start location
     * and the direction we wish to orbit
     * @param startLocation
     * @param wave
     * @param direction
     * @return
     */
    public MovSimStat predictedPosition(MovSimStat state, EnemyWave wave, double angleAdjustment, int direction) {
        MovSim sim = new MovSim();
        Point2D.Double predictedPosition = new Point2D.Double(state.x, state.y);
        double moveAngle, moveDir;

        MovSimStat predictedState = state;

        int tickOffset = state.offset;
        
        for(int i = tickOffset; i < tickOffset + 500; i++) {  
            moveAngle = wallSmoothing(predictedPosition, 
                    Util.computeAbsoluteBearing(wave.getOrigin(), predictedPosition) + (direction * (Math.PI / 2) + direction * angleAdjustment), 
                    direction);
            moveAngle -= predictedState.h;

            moveDir = 1;

            if(Math.cos(moveAngle) < 0) {
                moveAngle += Math.PI;
                moveDir = -1;
            }

            moveAngle = Utils.normalRelativeAngle(moveAngle);

            MovSimStat[] stats = sim.futurePos(1, predictedPosition, 
                    predictedState.v,
                    reference.getMaxVelocity(),
                    predictedState.h, 
                    1000 * moveDir, 
                    moveAngle, 
                    battlefield.width, battlefield.height);
            MovSimStat stat = stats[0];

            predictedPosition = new Point2D.Double(stat.x, stat.y);
            predictedState = stat;
            predictedState.offset = i;

            if(wave.intercepted(predictedPosition, i))
                break;
        }

        return predictedState;
    }

    /**
     * @return the location Durandal will be at after the next tick.
     */
    public MovSimStat nextMovSim() {
        return movSim(1);
    }

    /**
     * @return the location Durandal will be at after numTicks.
     */
    public MovSimStat movSim(int numTicks) {
        if (movementSimulator != null) {
            MovSimStat[] next = movementSimulator.futurePos(numTicks, reference);
            return next[numTicks - 1];
        }
        return null;
    }

    /**
     * Compensate for the walls during movement
     * @param botLocation
     * @param angle
     * @param orientation
     * @return
     */
    public double wallSmoothing(Point2D.Double botLocation, double angle, int orientation) {
        if(orientation == 0) orientation = 1;
        while (!battlefield.contains(Util.project(botLocation, angle, WALL_STICK))) {
           angle += orientation * 0.05;
        }
        return angle;
    }
    
    public void draw(Graphics2D g) {
        
    }

    // -------------------- Private Helpers --------------------- //

    /*
     * Computes the proper angle adjustment to bring the robot close to the enemy or to increase distance
     */
    private double computeAngleAdjustment(EnemyRobot enemy) {
        double distance = reference.getLocation().distance(enemy.getLocation());

        double angle = 0;

        double orbitDistance = Constants.IDEAL_DISTANCE + getSuggestedOrbitAdjustment(enemy);

        if(distance < Constants.ABSOLUTE_MINIMUM_DISTANCE) {
            angle = -Math.PI / 10;
        } else if (distance < orbitDistance) {
            angle = -Math.PI / 12;
        } else if (distance > orbitDistance + 50) {
            angle = Math.PI / 12;
        }

        return angle;
    }

    private double getSuggestedOrbitAdjustment(EnemyRobot enemy) {
        double adjustment = 0;
        MovementStatistic enemyStat = reference.getStatisticsManager().getMovementStatsForEnemy(enemy.getName());
        if(enemyStat.getHitPercentage() < 0.05) {
            adjustment = (1 - enemyStat.getHitPercentage()) * -60;
        } else if(enemyStat.getHitPercentage() < 0.15) {
            adjustment = (1 - enemyStat.getHitPercentage()) * 100;
        } else if(enemyStat.getHitPercentage() > 0.17) {
            adjustment = (1- enemyStat.getHitPercentage()) * 250;
        }

        return adjustment;
    }

    /*
     * Determine the minimum danger for a given movement choice
     */
    private double getMinimumDanger(double angleAdjustment, int initialOption) {
        MovSimStat[] initialStats = new MovSim().futurePos(1, reference);
        MovSimStat initialStat = initialStats[0];
        initialStat.offset = 1;
        return recursiveGetMinimumDanger(initialStat, null, angleAdjustment, initialOption, SEARCH_DEPTH);
    }

    /*
     * Recursively determine the minimum danger
     */
    private double recursiveGetMinimumDanger(MovSimStat state, EnemyWave ignore, double angleAdjustment, int initialOption, int depth) {
        Point2D.Double zeroPoint = new Point2D.Double(state.x, state.y);
        EnemyWave surfableWave = reference.getWaveManager().getClosestEnemyWaveToImpact(zeroPoint, ignore, state.offset);
        
        if(surfableWave == null) 
            return 0;

        MovSimStat predictedZero = predictedPosition(state, surfableWave, angleAdjustment, lastDirection);
        zeroPoint = new Point2D.Double(predictedZero.x, predictedZero.y);

        MovSimStat predictedLeft = predictedPosition(state, surfableWave, angleAdjustment, -1);
        Point2D.Double leftPoint = new Point2D.Double(predictedLeft.x, predictedLeft.y);

        MovSimStat predictedRight = predictedPosition(state, surfableWave, angleAdjustment, 1);
        Point2D.Double rightPoint = new Point2D.Double(predictedRight.x, predictedRight.y);


        double[] options = getOptionValues(zeroPoint, leftPoint, rightPoint, surfableWave, initialOption);

        //if(surfableWave.isVirtual())
        //    options[0] += 2000;

        int minIndex = (initialOption == -1 ? getMinimumIndex(options) : initialOption);

        double value = 0;
        if(--depth > 0) {
            switch(minIndex) {
                case 0: 
                    value = recursiveGetMinimumDanger(state, surfableWave, angleAdjustment, -1, depth);
                    break;
                case 1: 
                    value = recursiveGetMinimumDanger(predictedRight, surfableWave, angleAdjustment, -1, depth);
                    break;
                case 2: 
                    value = recursiveGetMinimumDanger(predictedLeft, surfableWave, angleAdjustment, -1, depth);
                    break;
            }
        }

        return options[minIndex] + value;
    }

    /*
     * returns an array of the danger values for each option
     */
    private double[] getOptionValues(Point2D.Double zero, Point2D.Double left, Point2D.Double right, EnemyWave wave, int initialOption) {
        double[] options = new double[3];
        if(initialOption < 0 ) {
            options[2] = reference.getStatisticsManager().getDanger(left, wave);
            options[1] = reference.getStatisticsManager().getDanger(right, wave);
            options[0] = reference.getStatisticsManager().getDanger(zero, wave);
        } else {
            switch(initialOption) {
                case 0:
                    options[0] = reference.getStatisticsManager().getDanger(zero, wave);
                    break;
                case 1:
                    options[1] = reference.getStatisticsManager().getDanger(right, wave);
                    break;
                case 2:
                    options[2] = reference.getStatisticsManager().getDanger(left, wave);            
            }
        } 
        return options;
    }

    /*
     * Gets the index for the minimum value of the passed array
     */
    private int getMinimumIndex(double[] options) {
        int minIndex = 1;   
        for(int i = 0; i < options.length; i++)
            if(options[minIndex] > options[i])
                minIndex = i;

        return minIndex;
    }
}
