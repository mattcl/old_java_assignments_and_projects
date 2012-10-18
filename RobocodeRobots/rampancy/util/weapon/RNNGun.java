/**
 * 
 */
package rampancy.util.weapon;

import rampancy.util.REnemyRobot;
import rampancy.util.wave.RBulletWave;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RNNGun extends RGun {
    
    public static final String NN_GUN = "neural network gun";
    
    /**
     * @param name
     */
    public RNNGun() {
        super(NN_GUN);
    }

    /* (non-Javadoc)
     * @see rampancy.util.weapon.RGun#getFiringSolution(rampancy.util.REnemyRobot)
     */
    @Override
    public RFiringSolution getFiringSolution(REnemyRobot enemy) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see rampancy.util.weapon.RGun#update(rampancy.util.wave.RBulletWave)
     */
    @Override
    public void update(RBulletWave wave) {
        // TODO Auto-generated method stub

    }

}
