/**
 * 
 */
package rampancy.util.wave;

import java.awt.*;

import rampancy.RampantRobot;
import rampancy.standard.RDefaultMovementStatistic;
import rampancy.util.*;
import rampancy.util.movement.RMovementStatistic;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class REnemyWaveWithStats extends REnemyWave {

    private double[] guessFactors;
    /**
     * @param creator
     */
    public REnemyWaveWithStats(REnemyRobot creator) {
        super(creator);
        RMovementStatistic movementStatistic = RampantRobot.getStatisticsManager().getMovementStatistics();
        if(movementStatistic instanceof RDefaultMovementStatistic) {
            guessFactors = ((RDefaultMovementStatistic) movementStatistic).getGuessFactorArray(this);
        }
    }
    
    /**
     * This is a convenience method since wa already have
     * a guess factor array. This is more of a side effect
     * of wanting to draw the "danger" areas on screen
     * @param location
     * @param numBins
     * @return the danger for a given location
     */
    public double getDangerForLocation(RPoint location, int numBins) {
        double offset = computeOffsetAngle(location);
        double factor = Utils.normalRelativeAngle(offset) / RUtil.computeMaxEscapeAngle(getVelocity()) * getDirection();
        int index = RUtil.computeBin(factor, numBins);
        return guessFactors[index];
    }
    
    /**
     * Draws the danger of different locations on the wave
     */
    public void draw(Graphics2D g) {
        if(guessFactors == null) {
            super.draw(g);
        } else {
            double largest = 0;
            for(int i = 0; i < guessFactors.length; i++)
                if(guessFactors[i] > largest)
                    largest = guessFactors[i];
            for(int i = 0; i < guessFactors.length; i++) {
                double danger = guessFactors[i];
                double factor = RUtil.getGuessFactorForIndex(i, guessFactors.length);
                double maxEscapeAngle = RUtil.computeMaxEscapeAngle(getVelocity()) * factor * direction;
                
                RPoint location = RUtil.project(getOrigin(), getDirectionAngle() + maxEscapeAngle, getDistanceTraveled() + velocity);
                Color dangerColor = new Color(0, 0, 200);
                if(danger > 0.01) {
                    dangerColor = new Color((int) (155 * (danger / largest)) + 100, 0, 0);
                }
                g.setColor(dangerColor);
                RUtil.fillOval(location, 4, g);
            }
        }
    }

}
