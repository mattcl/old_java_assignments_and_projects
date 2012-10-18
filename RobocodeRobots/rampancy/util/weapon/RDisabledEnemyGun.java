/**
 * 
 */
package rampancy.util.weapon;

import rampancy.RampantRobot;
import rampancy.util.REnemyRobot;
import rampancy.util.RRobotState;
import rampancy.util.wave.RBulletWave;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RDisabledEnemyGun extends RGun {
    
    public static final String DE_GUN = "disabled enemy gun";

    /**
     * @param name
     */
    public RDisabledEnemyGun() {
        super(DE_GUN);
    }

    /* (non-Javadoc)
     * @see rampancy.util.weapon.RGun#getFiringSolution(rampancy.util.REnemyRobot)
     */
    @Override
    public RFiringSolution getFiringSolution(REnemyRobot enemy) {
        RRobotState enemyState = enemy.getCurrentState();
        double gunBearingToTarget = enemyState.absoluteBearing - enemy.getReference().getGunHeadingRadians();
        gunBearingToTarget = Utils.normalRelativeAngle(gunBearingToTarget);
        
        return new RFiringSolution(0.1, gunBearingToTarget, 1.0, 0, this);
    }

    /* (non-Javadoc)
     * @see rampancy.util.weapon.RGun#update(rampancy.util.wave.RBulletWave)
     */
    @Override
    public void update(RBulletWave wave) {
        // TODO Auto-generated method stub

    }

}
