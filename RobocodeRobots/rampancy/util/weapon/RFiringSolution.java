/**
 * 
 */
package rampancy.util.weapon;

/**
 * @author Matthew Chun-Lum
 *
 */

import java.awt.*;

import rampancy.util.RDrawableObject;
import rampancy.util.RPoint;
import robocode.util.Utils;

public class RFiringSolution {
    
    public static final Color DEFAULT_COLOR = Color.green;

    public Color color;
    public RPoint intendedHitLocation;
    public double power;
    public double firingAngle;
    public double reliabilityFactor;
    public double guessFactor;
    public long anticipatedFlightTime;
    public RDrawableObject drawableObject;
    public RGun gun;
    
    public RFiringSolution(double power, double firingAngle, double reliabilityFactor, double guessFactor, RGun gun) {
        this(power, firingAngle, reliabilityFactor, guessFactor, DEFAULT_COLOR, gun);
    }
    
    public RFiringSolution(double power, double firingAngle, double reliabilityFactor, double guessFactor, Color color, RGun gun) {
        this(null, power, firingAngle, reliabilityFactor, guessFactor, color, gun);
    }
    
    public RFiringSolution(RPoint intendedHitLocation, double power, double firingAngle, double reliabilityFactor, double guessFactor, RGun gun) {
        this(intendedHitLocation, power, firingAngle, reliabilityFactor, guessFactor, DEFAULT_COLOR, gun);
    }
    
    public RFiringSolution(RPoint intendedHitLocation, double power, double firingAngle, double reliabilityFactor, double guessFactor, Color color, RGun gun) {
        this(intendedHitLocation, power, firingAngle, reliabilityFactor, guessFactor, color, gun, null);
    }
    
    public RFiringSolution(RPoint intendedHitLocation, double power, double firingAngle, double reliabilityFactor, double guessFactor, Color color, RGun gun, RDrawableObject drawableObject) {
        this(intendedHitLocation, power, firingAngle, reliabilityFactor, guessFactor, color, gun, drawableObject, -1);
    }
    
    public RFiringSolution(RPoint intendedHitLocation, double power, double firingAngle, double reliabilityFactor, double guessFactor, Color color, RGun gun, RDrawableObject drawableObject, long anticipatedFlightTime) {
        this.intendedHitLocation = intendedHitLocation == null ? null : intendedHitLocation.getCopy();
        this.power               = power;
        this.firingAngle         = Utils.normalRelativeAngle(firingAngle);
        this.reliabilityFactor   = reliabilityFactor;
        this.guessFactor         = guessFactor;
        this.color               = color;
        this.drawableObject      = drawableObject;
        this.gun = gun;
        this.anticipatedFlightTime = anticipatedFlightTime;
    }
    
    /**
     * Copy constructor
     * @param solution
     */
    public RFiringSolution(RFiringSolution solution) {
        this(solution.intendedHitLocation,
             solution.power,
             solution.firingAngle,
             solution.reliabilityFactor,
             solution.guessFactor,
             solution.color,
             solution.gun,
             solution.drawableObject);
    }
}
