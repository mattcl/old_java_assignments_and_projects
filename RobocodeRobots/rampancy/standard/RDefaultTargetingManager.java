/**
 * 
 */
package rampancy.standard;

import java.awt.Color;
import java.awt.Graphics2D;

import rampancy.RTargetingManager;
import rampancy.RampantRobot;
import rampancy.util.*;
import rampancy.util.wave.RBulletWave;
import rampancy.util.weapon.*;
import robocode.util.Utils;

import java.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RDefaultTargetingManager implements RTargetingManager {
    
    private RDisabledEnemyGun disabledEnemyGun;
    private ArrayList<RGun> guns;
    
    private ArrayList<RFiringSolution> examinedSolutions;
    
    public RDefaultTargetingManager(RGun[] guns) {
        disabledEnemyGun = new RDisabledEnemyGun();
        this.guns = new ArrayList<RGun>();
        for(int i = 0; i < guns.length; i++)
            this.guns.add(guns[i]);
    }
    
    /* (non-Javadoc)
     * @see rampancy_v2.RTargetingManager#getBestFiringSolution(rampancy_v2.util.REnemyRobot)
     */
    public List<RFiringSolution> getBestFiringSolutions(REnemyRobot enemy) {
        
        ArrayList<RFiringSolution> firingSolutions = new ArrayList<RFiringSolution>();
        
        if(enemy.getCurrentState().energy == 0) { // disabled
            firingSolutions.add(disabledEnemyGun.getFiringSolution(enemy));
        } else {
            for(RGun gun : guns) {
                RFiringSolution fs = gun.getFiringSolution(enemy);
                if(fs != null) {
                    firingSolutions.add(fs);
                }
            }
            
            if(firingSolutions.isEmpty()) {
                return firingSolutions;
            }
            
            RFiringSolution bestSolution = firingSolutions.get(0);
            double bestHitPercent = 0;
            
            for(RFiringSolution solution : firingSolutions) {
                double percent = solution.gun.getStats().getWeightedHitPercent();
                if(percent > bestHitPercent) {
                    bestHitPercent = percent;
                    bestSolution = solution;
                }
            }
            
            firingSolutions.remove(bestSolution);
            firingSolutions.add(0, bestSolution);
        }
        
        return firingSolutions;
    }

    /* (non-Javadoc)
     * @see rampancy_v2.RTargetingManager#draw(java.awt.Graphics2D)
     */
    public void draw(Graphics2D g) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see rampancy.RTargetingManager#updateGuns(rampancy.util.wave.RBulletWave)
     */
    public void updateGuns(RBulletWave wave) {
        for(RGun gun : guns)
            if(gun.getName().equals(wave.getFiringSolution().gun.getName()))
                gun.update(wave);
    }

    /* (non-Javadoc)
     * @see rampancy.RTargetingManager#updateNewRound()
     */
    public void updateNewRound() {
        System.out.println("Gun Stats\n************");
        for(RGun gun : guns) {
            System.out.println(gun.toString());
        }
        System.out.println();
        
        
        for(RGun gun : guns) {
            gun.updateNewRound();
        }
    }
    
    public String getGunStatistics() {
        String str = "Targeting manager statistics:\n****************\n";
        for(RGun gun : guns)
            str += gun.getOverview() + "\n";
        return str + "\n";
    }
}
