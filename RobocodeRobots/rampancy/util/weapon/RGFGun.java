/**
 * 
 */
package rampancy.util.weapon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.Arrays;

import rampancy.RampantRobot;
import rampancy.standard.RDefaultSegmentTree;
import rampancy.util.*;
import rampancy.util.data.segmentTree.*;
import rampancy.util.wave.RBulletWave;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RGFGun extends RGun {
    
    public static final int ROLL_DEPTH = 10;
    public static final String GF_GUN = "guess factor gun";
    
    private RDefaultSegmentTree segmentTree;
    
    public RGFGun() {
        super(GF_GUN);
        segmentTree = new RDefaultSegmentTree();
    }

    /* (non-Javadoc)
     * @see rampancy.util.weapon.RGun#getFiringSolution(rampancy.util.REnemyRobot)
     */
    @Override
    public RFiringSolution getFiringSolution(REnemyRobot enemy) {
        RRobotState enemyState = enemy.getCurrentState();
        RSTNode leaf = segmentTree.getSegmentForState(enemyState);
        
        int bestIndex = RUtil.indexOfLargest(leaf.getGuessFactors());
        double guessFactor = RUtil.getGuessFactorForIndex(bestIndex, leaf.getGuessFactors().length);
        
        double power;
        if(enemy.getCurrentState().distance < 100) {
            power = 3.0;
        } else {
            power = (1 - enemy.getCurrentState().distance / 1500.0) * 3.0;
        }
        
        power =  RUtil.limit(0.1, power, 3.0);

        double offsetAngle = enemyState.directionTraveling * guessFactor * RUtil.computeMaxEscapeAngle(RUtil.computeBulletVelocity(power));
        offsetAngle = Utils.normalRelativeAngle(offsetAngle);
        
        double maxPreciseEscapeAngle = RUtil.computePreciceMaxEscapeAngle(RUtil.computeBulletVelocity(power), 
                enemy.getReference(), enemy, RUtil.nonZeroSign(enemyState.directionTraveling * guessFactor));
        double preciseOffsetAngle = enemyState.directionTraveling * guessFactor * maxPreciseEscapeAngle;
        preciseOffsetAngle = Utils.normalRelativeAngle(preciseOffsetAngle);
        
        double gunBearingToTarget = enemyState.absoluteBearing - enemy.getReference().getGunHeadingRadians();
        gunBearingToTarget = Utils.normalRelativeAngle(gunBearingToTarget);
        
        GFDrawableObject drawableObject = new GFDrawableObject(leaf.getGuessFactors());
        return new RFiringSolution(null,
                power, 
                gunBearingToTarget + preciseOffsetAngle, 
                0, 
                guessFactor, 
                Color.green, 
                this, 
                drawableObject);
    }

    /* (non-Javadoc)s
     * @see rampancy.util.weapon.RGun#update(rampancy.util.wave.RBulletWave)
     */
    @Override
    public void update(RBulletWave wave) {
        super.update(wave);
        RSTNode leaf = segmentTree.getSegmentForState(wave.getTargetState());
        double desiredDirection = RUtil.computeAbsoluteBearing(wave.getOrigin(), wave.getTarget().getLastState().location);
        double angleOffset = Utils.normalRelativeAngle(desiredDirection - wave.getTargetState().absoluteBearing);
        
        double guessFactor = Math.max(-1, Math.min(1, angleOffset / RUtil.computeMaxEscapeAngle(wave.getVelocity()))) * wave.getTargetState().directionTraveling;
        int index = RUtil.computeBin(guessFactor, leaf.getGuessFactors().length);
        RSTNode.updateGuessFactors(leaf, index, 2.0, ROLL_DEPTH);
    }
    
    public String getOverview() {
        String str = super.getOverview() + "\n";
        str += segmentTree.toString();
        return str;
    }
    
    class GFDrawableObject implements RDrawableObject {

        public double[] guessFactors;
        public GeneralPath curve;
        public double max;
        public int max_index;
        
        public GFDrawableObject(double[] guessFactors) {
            this.guessFactors = Arrays.copyOf(guessFactors, guessFactors.length);
            max = 0;
            max_index = 0;
            for(int i = 0; i < guessFactors.length; i++)
                if(guessFactors[i] > max) {
                    max = guessFactors[i];
                    max_index = i;
                }
            // normalize
            if(max != 0)
                for(int i = 0; i < guessFactors.length; i++)
                    this.guessFactors[i] = (guessFactors[i] / max) * 200.0;
            
            curve = new GeneralPath(GeneralPath.WIND_EVEN_ODD, guessFactors.length);
            curve.moveTo(0, 0);
            for(int i = 0; i < guessFactors.length; i++) {
                curve.lineTo(i * 12 + 10, (int) Math.round(this.guessFactors[i]));
            }
        }
        
        public void draw(Graphics2D g) {
            Color lastColor = g.getColor();
            g.setColor(Color.white);
            g.draw(curve);
            g.setColor(Color.red);
            RUtil.drawOval(new RPoint(max_index * 12 + 10, guessFactors[max_index]), 3, g);
            g.setColor(lastColor);
        }
        
    }

}
