/**
 * 
 */
package rampancy.util.weapon;

import rampancy.RampantRobot;
import rampancy.standard.*;
import rampancy.util.*;
import rampancy.util.wave.RBulletWave;
import rampancy_old.util.Util;
import robocode.util.Utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RDCGun extends RGun {
    
    public static final String DC_GUN = "dynamic clustering gun";
    
    private RDefaultKDTree kdTree;
    
    public RDCGun() {
        super(DC_GUN);
        kdTree = new RDefaultKDTree();
    }

    /* (non-Javadoc)
     * @see rampancy.util.weapon.RGun#getFiringSolution(rampancy.util.REnemyRobot)
     */
    @Override
    public RFiringSolution getFiringSolution(REnemyRobot enemy) {
        RRobotState enemyState = enemy.getCurrentState();
        ArrayList<RDefaultKDPoint> points = kdTree.getNearestPoints(enemyState);
        
        double bestGuessFactor = 0;
        double bestProbability = 0.5;
        
        double[] probabilityDensity = new double[201];
        
        if(!points.isEmpty()) {
            double p = -1.0;
            for(int i = 0; i < 201; i++) {
                probabilityDensity[i] = kernelDensityEstimate(p, points, 36.0/enemyState.distance);
                if(probabilityDensity[i] > bestProbability) {
                    bestGuessFactor = p;
                    bestProbability = probabilityDensity[i];
                }
                p += 0.01;
            }
        }
        
        
        double power;
        if(enemyState.distance < 100) {
            power = 3.0;
        } else {
            power = (1 - enemyState.distance / 1500.0) * 3.0;
        }
        
        power =  Util.limit(0.1, power, 3.0);
        double maxEscapeAngle = RUtil.computeMaxEscapeAngle(RUtil.computeBulletVelocity(power));
        double offsetAngle = enemyState.directionTraveling * bestGuessFactor * maxEscapeAngle;
        offsetAngle = Utils.normalRelativeAngle(offsetAngle);
        
        double maxPreciseEscapeAngle = RUtil.computePreciceMaxEscapeAngle(RUtil.computeBulletVelocity(power), 
                enemy.getReference(), enemy, RUtil.nonZeroSign(enemyState.directionTraveling * bestGuessFactor));
        
        double preciseOffsetAngle = enemyState.directionTraveling * bestGuessFactor *maxPreciseEscapeAngle;
        preciseOffsetAngle = Utils.normalRelativeAngle(preciseOffsetAngle);
        
        double gunBearingToTarget = enemyState.absoluteBearing - enemy.getReference().getGunHeadingRadians();
        gunBearingToTarget = Utils.normalRelativeAngle(gunBearingToTarget);
        
        DCDrawableObject drawableObject = null;//new DCDrawableObject(probabilityDensity);
        return new RFiringSolution(null, power, gunBearingToTarget + preciseOffsetAngle, bestProbability, bestGuessFactor, Color.cyan, this, drawableObject);
    }

    /* (non-Javadoc)
     * @see rampancy.util.weapon.RGun#update(rampancy.util.wave.RBulletWave)
     */
    @Override
    public void update(RBulletWave wave) {
        super.update(wave);

        double desiredDirection = RUtil.computeAbsoluteBearing(wave.getOrigin(), wave.getTarget().getLastState().location);
        double angleOffset = Utils.normalRelativeAngle(desiredDirection - wave.getTargetState().absoluteBearing);
        double guessFactor = Math.max(-1, Math.min(1, angleOffset / RUtil.computeMaxEscapeAngle(wave.getVelocity()))) * wave.getTargetState().directionTraveling;
        kdTree.addPoint(wave.getTargetState(), guessFactor);
    }
    
    private double kernelDensityEstimate(double targetGuessFactor, ArrayList<RDefaultKDPoint> points, double bandwidth) {
        double scale = 1.0 / (points.size() * bandwidth * Math.sqrt(2 * Math.PI));
        double sigma = 0;
        for(RDefaultKDPoint point : points) {
            sigma += Math.exp(-RUtil.square(targetGuessFactor - point.guessFactor) / (2.0 * bandwidth * bandwidth));
        }
        return scale * sigma;
    }

    /* (non-Javadoc)
     * @see rampancy.util.weapon.RGun#updateNewRound()
     */
    @Override
    public void updateNewRound() {
        kdTree.rebalance();
    }
    
    class DCDrawableObject implements RDrawableObject {

        public double[] probabilityDensity;
        public GeneralPath curve;
        public double max;
        public int max_index;
        
        public DCDrawableObject(double[] probabilityDensity) {
            this.probabilityDensity = Arrays.copyOf(probabilityDensity, probabilityDensity.length);
            max = 0;
            max_index = 0;
            for(int i = 0; i < probabilityDensity.length; i++)
                if(probabilityDensity[i] > max) {
                    max = probabilityDensity[i];
                    max_index = i;
                }
            // normalize
            if(max != 0)
                for(int i = 0; i < probabilityDensity.length; i++)
                    this.probabilityDensity[i] = (probabilityDensity[i] / max) * 200.0;
            
            curve = new GeneralPath(GeneralPath.WIND_EVEN_ODD, probabilityDensity.length);
            curve.moveTo(0, 0);
            for(int i = 0; i < probabilityDensity.length; i += 3) {
                curve.lineTo(i * .39, Math.round(this.probabilityDensity[i]));
            }
        }
        
        public void draw(Graphics2D g) {
            Color lastColor = g.getColor();
            g.setColor(Color.white);
            g.draw(curve);
            g.setColor(Color.red);
            RUtil.drawOval(new RPoint(max_index * 12 + 10, probabilityDensity[max_index]), 3, g);
            g.setColor(lastColor);
        }
        
    }
    

}
