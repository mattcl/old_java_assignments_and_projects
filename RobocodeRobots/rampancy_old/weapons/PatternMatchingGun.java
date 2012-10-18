/**
 * PatternMatchingGun.java
 */
package rampancy_old.weapons;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.*;

import rampancy_old.statistics.pattern.*;
import rampancy_old.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public abstract class PatternMatchingGun {
    public static final String NAME = Constants.PM_GUN_NAME;
    public static final Color DEFAULT_COLOR = Constants.PM_GUN_COLOR;
    public static final int WEAPON_INDEX = Constants.PM_GUN_INDEX;

    public static double computeShotPower(double deviation) {
        double shotPower;
        if(deviation < 0.5)
            shotPower = 2.0;
        else
            shotPower = 1.0;
        
        return Util.limit(0.1, shotPower, 3.0);
    }

    
    
    public static PMFiringSolution computOffsetAngle(Battlefield bf, 
                                           PMMatch match, 
                                           EnemyRobot enemy, 
                                           Point2D.Double fireLocation) {
        if(match == null || match.pattern == null || match.pattern.isEmpty())
            return null;

        PMFiringSolution fs = determineInterceptLocation(bf, match, enemy, fireLocation);
        if(fs == null)
            return null;
        double enemyAbsBearing = enemy.getAbsoluteBearing();
        double bearingToIntercept = Util.computeAbsoluteBearing(fireLocation, fs.anticipated);
        double offset = bearingToIntercept - enemyAbsBearing;
        fs.offset = offset;
        return fs;
    }
    
    public static PMFiringSolution determineInterceptLocation(Battlefield bf, 
                                                            PMMatch match, 
                                                            EnemyRobot enemy, 
                                                            Point2D.Double fireLocation) {
        double x = enemy.getLocation().x;
        double y = enemy.getLocation().y;
        double heading = enemy.getHeading();
        
        ArrayList<PMState> pattern = match.pattern;
        
        
        Point2D.Double bestIntercept = null;
        double bestPower = 0.1;
        
        for(int i = 0; i < pattern.size(); i++) {
            MovSimStat predicted = predictPosition(x, y, heading, pattern.get(i), bf);
            Point2D.Double predictedPoint = new Point2D.Double(predicted.x, predicted.y);
            double power = 3.0;
            while(power >= 0.1) {
                if(fireLocation.distance(predictedPoint) <= Util.computeBulletVelocity(power) * (i + 1)) {
                    if(power > bestPower) {
                        bestPower = power;
                        bestIntercept = predictedPoint;
                    }
                }
                power -= 0.1;
            }
              
            x = predicted.x;
            y = predicted.y;
            heading = predicted.h;
        }
        
        if(bestIntercept == null) {
            return null;
        } else {
            return new PMFiringSolution(bestPower, 0, bestIntercept);
        }
    }
    
    private static MovSimStat predictPosition(double x, double y, double heading, PMState state, Battlefield bf) {
        MovSim sim = new MovSim();
        double distanceRemaining = 1000;
        double battleFieldW = bf.width;
        double battleFieldH = bf.height;
        
        MovSimStat[] stat = sim.futurePos(1, x, y, 
                                          state.velocity, 
                                          state.velocity, 
                                          heading, 
                                          distanceRemaining, 
                                          state.deltaHeading, 
                                          10.0, battleFieldW, 
                                          battleFieldH);
        
        return stat[0];
    }
}
