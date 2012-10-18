/**
 * 
 */
package rampancy.util.weapon;

import java.text.DecimalFormat;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RGunStatistic {
    
    public double simulatedShotsFired;
    public double simulatedShotsHit;
    public double realShotsFired;
    public double realShotsHit;
    public double minPercent;
    
    public RGunStatistic() {
        simulatedShotsFired = 0;
        simulatedShotsHit = 0;
        realShotsFired = 0;
        realShotsHit = 0;
        minPercent = -1;
    }

    public double getRealHitPercent() {
        return realShotsFired != 0 ? Math.round(realShotsHit / realShotsFired * 1000) / 10.0  : 0;
    }
    
    public double getVirtualHitPercent() {
        return simulatedShotsFired != 0 ? Math.round(simulatedShotsHit / simulatedShotsFired * 1000) / 10.0  : 0;
    }
    
    public double getWeightedHitPercent() {
        double realPercent = getRealHitPercent();
        double virtualPercent = getVirtualHitPercent();
        double virtualWeight = 1.0;
        if(realPercent < virtualPercent) {
            virtualWeight = 20.0;
        } else if(realPercent > virtualPercent) {
            virtualWeight = 0;
        }
        double avePercent = (getRealHitPercent() + getVirtualHitPercent() * virtualWeight) / (virtualWeight + 1);
        return avePercent > minPercent ? avePercent : 0.0; 
    }
}
