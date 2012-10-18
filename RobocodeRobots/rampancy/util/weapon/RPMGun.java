/**
 * 
 */
package rampancy.util.weapon;

import rampancy.RampantRobot;
import rampancy.util.RBattlefield;
import rampancy.util.RDrawableObject;
import rampancy.util.REnemyRobot;
import rampancy.util.RPoint;
import rampancy.util.RRobotState;
import rampancy.util.RUtil;
import rampancy.util.movement.MovSim;
import rampancy.util.movement.MovSimStat;
import rampancy.util.wave.RBulletWave;
import robocode.util.Utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RPMGun extends RGun {
    public static final String PM_TYPE = "pattern matching gun";
    
    public static final int NUM_MATCH_STATES = 9;
    public static final int MAX_TICKS = 40;
    
    HashMap<String, ArrayList<RRobotState>> robotHistories;
    private ArrayList<RPoint> examinedLocations;
    
    public RPMGun() {
        super(PM_TYPE);
        robotHistories = new HashMap<String, ArrayList<RRobotState>>();
        examinedLocations = new ArrayList<RPoint>();
        this.getStats().minPercent = 25.0;
    }

    /* (non-Javadoc)
     * @see rampancy.util.weapon.RGun#getFiringSolution(rampancy.util.REnemyRobot)
     */
    @Override
    public RFiringSolution getFiringSolution(REnemyRobot enemy) {
        ArrayList<RRobotState> states = enemy.getLastNStates(REnemyRobot.MAX_HISTORY_SIZE);
        
        if(states.size() < NUM_MATCH_STATES * 2) return null;
        ArrayList<RRobotState> needle = new ArrayList<RRobotState>(states.subList(0, NUM_MATCH_STATES - 1));
        ArrayList<RRobotState> haystack = new ArrayList<RRobotState>(states.subList(NUM_MATCH_STATES, states.size() - 1));
        
        
        // find the best match for the needle in the haystack
        int bestMatch = -1;
        double bestPercentDifference = 100000;
        
        for(int i = haystack.size() - 1; i > NUM_MATCH_STATES * 2 - 1; i--) {
            double cumulativePercentDifference = 0;
            for(int j = needle.size() - 1; j >= 0; j--) {
                RRobotState haystackState = haystack.get(i - j);
                RRobotState needleState = needle.get(j);
                
                double deltaHDiff = RUtil.percentDifference(needleState.deltaH, haystackState.deltaH);
                double deltaTHDiff = RUtil.percentDifference(needleState.heading, haystackState.heading);
                double deltaVDiff = RUtil.percentDifference(needleState.deltaV, haystackState.deltaV);
                double deltaTVDiff = RUtil.percentDifference(needleState.velocity, haystackState.velocity);
                double deltaWall = RUtil.percentDifference(needleState.distanceFromWallCategory, haystackState.distanceFromWallCategory);
                double deltaDirection = RUtil.percentDifference(needleState.directionTraveling, haystackState.directionTraveling);
                double deltaTimeSinceStop = RUtil.percentDifference(needleState.timeSinceStop, haystackState.timeSinceStop);
                double delatTimeDC = RUtil.percentDifference(needleState.timeSinceDirectionChange, haystackState.timeSinceDirectionChange);
                //cumulativePercentDifference += (deltaHDiff + deltaTVDiff);
                cumulativePercentDifference += RUtil.square(needleState.deltaH - haystackState.deltaH) + RUtil.square(needleState.velocity -haystackState.velocity);
            }
            
            cumulativePercentDifference /= 1.0;//NUM_MATCH_STATES;
            
            if(cumulativePercentDifference < bestPercentDifference) {
                bestMatch = i - NUM_MATCH_STATES;
                bestPercentDifference = cumulativePercentDifference;
            }
        }
        
        if(bestMatch < 0) return null;
        
        ArrayList<RRobotState> futureStates = new ArrayList<RRobotState>(states.subList(0, bestMatch));
        Collections.reverse(futureStates);
        ArrayList<PMSolution> solutions = completeSolution(enemy, futureStates, enemy.getReference().getGunHeadingRadians());
        
        
        if(solutions.isEmpty())
            return null;
        
        PMSolution bestSolution = solutions.get(0);
        double bestPower = 0;
        int i = 0;
        for(; i < solutions.size(); i++) {
            if(solutions.get(i).numTicks > MAX_TICKS)
                break;
            if(solutions.get(i).power > bestPower) {
                bestSolution = solutions.get(i);
                bestPower = bestSolution.power;
            }
        }
        PMDrawableObject drawableObject = new PMDrawableObject(examinedLocations);
        
        return new RFiringSolution(bestSolution.predictedHitLocation, 
                bestSolution.power,
                bestSolution.firingAngle,
                bestPercentDifference,
                0,
                Color.PINK,
                this,
                drawableObject,
                i + 1);
    }

    /* (non-Javadoc)
     * @see rampancy.util.weapon.RGun#update(rampancy.util.wave.RBulletWave)
     */
    @Override
    public void update(RBulletWave wave) {
        super.update(wave);
    }
    
    // Private
    private ArrayList<PMSolution> completeSolution(REnemyRobot enemy, ArrayList<RRobotState> futureStates, double currentGunHeading) {
        RRobotState currentState = enemy.getCurrentState();
        examinedLocations = new ArrayList<RPoint>();
        ArrayList<PMSolution> solutions = new ArrayList<PMSolution>();
        
        double x = currentState.location.x;
        double y = currentState.location.y;
        double velocity = currentState.velocity;
        double heading  = currentState.heading;
        RBattlefield bf = RampantRobot.getGlobalBattlefield();
        RPoint fireLocation = enemy.getReference().getCopyOfLocation();
        
        for(int i = 0; i < Math.min(futureStates.size(), MAX_TICKS); i++) {
            RRobotState state = futureStates.get(i);
            MovSimStat[] predicted = MovSim.futurePos(1, x, y, velocity, 
                                                      velocity + state.deltaV, 
                                                      heading, (velocity == 0 ? 0 : 1000), state.deltaH, 
                                                      MovSim.defaultMaxTurnRate, 
                                                      bf.width, bf.height);
            RPoint predictedPoint = new RPoint(predicted[0].x, predicted[0].y);
            examinedLocations.add(predictedPoint);
            double distance = fireLocation.distance(predictedPoint);
            double requiredVelocity = distance / (double) (i + 1);
            double requiredPower = RUtil.computeBulletPower(requiredVelocity);
            if(requiredPower >= 0.1 && requiredPower <= 3.0) { // if the required power is valid
                double firingAngle = Utils.normalAbsoluteAngle(RUtil.computeAbsoluteBearing(fireLocation, predictedPoint));
                firingAngle = Utils.normalRelativeAngle(firingAngle - currentGunHeading);
                
                if(Math.abs(firingAngle) <= 0.5) {
                    solutions.add(new PMSolution(predictedPoint, requiredPower, firingAngle, i + 1));
                }
            }
            x = predicted[0].x;
            y = predicted[0].y;
            velocity = predicted[0].v;
            heading  = predicted[0].h;
        }
        return solutions;
    }
    
    class PMSolution {
        RPoint predictedHitLocation;
        double power;
        double firingAngle;
        int numTicks;
        
        public PMSolution(RPoint predicted, double power, double angle, int ticks) {
            this.predictedHitLocation = predicted;
            this.power = power;
            this.firingAngle = angle;
            this.numTicks = ticks;
        }
    }
    
    class PMDrawableObject implements RDrawableObject {
        
        private List<RPoint> points;
        
        public PMDrawableObject(List<RPoint> points) {
            this.points = points;
        }

        /* (non-Javadoc)
         * @see rampancy.util.RDrawableObject#draw(java.awt.Graphics2D)
         */
        public void draw(Graphics2D g) {
            g.setColor(Color.magenta);
            for(RPoint point : points) {
                RUtil.drawOval(point, 5, g);
            }
            
        }
        
    }
    

}
