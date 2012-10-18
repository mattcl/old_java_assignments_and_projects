/**
 * 
 */
package rampancy.util.weapon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import rampancy.RampantRobot;
import rampancy.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RCTGun extends RGun {
    public static final String CT_GUN = "circular targeting gun";

    public RCTGun() {
        super(CT_GUN);
        this.getStats().minPercent = 40.0;
    }

    /* (non-Javadoc)
     * @see rampancy.util.weapon.RGun#getFiringSolution(rampancy.util.REnemyRobot)
     */
    @Override
    public RFiringSolution getFiringSolution(REnemyRobot enemy) {
        ArrayList<RPoint> examinedLocations = new ArrayList<RPoint>();

        double velocity = enemy.getCurrentState().velocity;
        double deltaH = enemy.getCurrentState().deltaH;
        double curHeading = enemy.getCurrentState().heading;
        RPoint targetLocation = enemy.getCurrentState().location;

        double bestShotPower = enemy.getCurrentState().distance < 20 ? 3.0 : 0.5;
        double maxShotPower = 2.5;
        double maxEscapeAngle = RUtil.computeMaxEscapeAngle(maxShotPower);

        int time = 1;
        for(; time < 100; time++) {
            curHeading += deltaH;
            RPoint simulated = targetLocation.projectTo(curHeading, velocity);
            if(!RampantRobot.getGlobalBattlefield().adjustedInnerRect.contains(targetLocation))
                break;

            double power = RUtil.computeRequiredBulletPower(enemy.getReference().getLocation(), targetLocation, time);
            if(power > maxShotPower)
                break;

            if(power > bestShotPower)
                bestShotPower = power;

            targetLocation = simulated;
            examinedLocations.add(simulated);
        }
        
        double firingAngle = targetLocation.computeAbsoluteBearingFrom(enemy.getReference().getLocation()) - enemy.getReference().getGunHeadingRadians();

        return new RFiringSolution(targetLocation.getCopy(), bestShotPower, firingAngle, -1, -1, Color.yellow, this, new CTDrawableObject(examinedLocations, targetLocation), time);   
    }

    class CTDrawableObject implements RDrawableObject {

        ArrayList<RPoint> examinedLocations;
        RPoint targetLocation;

        public CTDrawableObject(ArrayList<RPoint> examinedLocations, RPoint targetLocation) {
            this.examinedLocations = examinedLocations;
            this.examinedLocations.remove(targetLocation);
            this.targetLocation = targetLocation;
        }
        public void draw(Graphics2D g) {
            for(RPoint pt : examinedLocations) {
                g.setColor(Color.yellow);
                RUtil.fillOval(pt, 3, g);
            }
            g.setColor(Color.red);
            RUtil.fillOval(targetLocation, 4, g);
            g.draw(targetLocation.getBotRect());

        }
    }

}
