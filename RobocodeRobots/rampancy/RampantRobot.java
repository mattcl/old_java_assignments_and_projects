/**
 * RampantRobot.java
 */
package rampancy;

import rampancy.util.*;
import rampancy.util.movement.RMoveChoice;
import rampancy.util.wave.*;
import rampancy.util.weapon.RFiringSolution;
import robocode.AdvancedRobot;
import robocode.BattleEndedEvent;
import robocode.Bullet;
import robocode.BulletHitBulletEvent;
import robocode.BulletHitEvent;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public abstract class RampantRobot extends AdvancedRobot {
    public static final int MAX_HISTORY_DEPTH = 1000;

    public static RBattlefield       globalBattlefield;

    public static REnemyManager      enemyManager;
    public static RWaveManager       waveManager;
    public static RMovementManager   movementManager;
    public static RTargetingManager  targetingManager;
    public static RStatisticsManager statisticsManager;


//    /**
//     * @return a reference to the RampantRobot
//     */
//    public static RampantRobot getGlobalReference() {
//        return globalReference;
//    }

    /**
     * @return a reference to the Battlefield
     */
    public static RBattlefield getGlobalBattlefield() {
        return globalBattlefield;
    }

    /*
     * Static wrappers for managers
     */
    public static REnemyManager getEnemyManager()           { return enemyManager; }
    public static RWaveManager getWaveManager()             { return waveManager; }
    public static RMovementManager getMovementManager()     { return movementManager; }
    public static RTargetingManager getTargetingManager()   { return targetingManager; }
    public static RStatisticsManager getStatisticsManager() { return statisticsManager; }

    protected static boolean drawMovement = true;
    protected static boolean drawGun = true;
    protected static boolean drawWaves = true;
    protected static boolean drawEnemy = true;
    
    protected RPoint location;
    protected LinkedList<RRobotState> states;
    protected String lastType;
    protected long fireTime = 0;
    protected long ticksSinceLastVirtualBullet = 0;
    protected boolean processingShot;
    protected RFiringSolution lockedSolution;
    protected REnemyRobot lockedEnemy;

    public RampantRobot() {
        super();        
        states = new LinkedList<RRobotState>();
        lastType = "";
    }

    public void run() {
        super.run();
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);
        globalBattlefield = new RBattlefield((int) getBattleFieldWidth(), (int) getBattleFieldHeight());
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        updateState(e);
        REnemyRobot enemy = enemyManager.findOrCreateByName(e.getName());
        if(enemy != null) {
            // update the state of the scanned enemy
            enemy.update(e);

            // update all of our waves
            waveManager.update();

            // move in the best way possible
            // (as determined by the manager)
            RMoveChoice choice = movementManager.computeBestMove(enemy);
            if(choice != null)
                RUtil.setBackAsFront(this, choice.angle, 100);

            // target the enemy robot
            ArrayList<RFiringSolution> firingSolutions = new ArrayList<RFiringSolution>(targetingManager.getBestFiringSolutions(enemy));
            if(!firingSolutions.isEmpty()) {
                lockFiringSolution(enemy, firingSolutions.get(0));
                simulateRemainingSolutions(enemy, firingSolutions);
            } else if(!processingShot) {
                turnGunToTarget(enemy);
            }

            if(attemptShot()) {
                processingShot = false;
            }
            //attemptToShoot(enemy, firingSolution);
        }
        
    }

    public void onHitByBullet(HitByBulletEvent e) {
        Bullet bullet = e.getBullet();
        REnemyWave wave = waveManager.getWaveForEnemyBullet(bullet);
        if(wave != null) {
            RPoint hitLocation = new RPoint(bullet.getX(), bullet.getY());
            statisticsManager.getMovementStatistics().noteHitByBullet(wave, hitLocation);
        }
    }
    
    public void onBulletHitBullet(BulletHitBulletEvent e){
        Bullet bullet = e.getBullet();
        REnemyWave wave = waveManager.getWaveForEnemyBullet(bullet);
        if(wave != null) {
            RPoint hitLocation = new RPoint(bullet.getX(), bullet.getY());
            statisticsManager.getMovementStatistics().noteHitByBullet(wave, hitLocation);
        }
    }
    
    public void onBulletHit(BulletHitEvent e) {
        Bullet bullet = e.getBullet();
        REnemyRobot enemy = enemyManager.get(e.getName());
        if(enemy == null)
            return;

        RBulletWave wave = waveManager.getWaveForBullet(bullet);
        if(wave == null) {
            return;
        }

        if(!enemy.getName().equalsIgnoreCase(wave.getTarget().getName()))
            return;
    }
    
    public void onBattleEnded(BattleEndedEvent e) {
        
    }
    
    public void onKeyTyped(KeyEvent e) {
        int key = e.getKeyChar();
        switch(key) {
        case 'w':
            drawWaves = !drawWaves;
            break;
        case 'e':
            drawEnemy = !drawEnemy;
            break;
        case 'g':
            drawGun = !drawGun;
            break;
        case 'm':
            drawMovement = !drawMovement;
            break;
        }
    }

    public void updateState(ScannedRobotEvent e) {
        states.push(new RRobotState(this, e));
        if(states.size() > MAX_HISTORY_DEPTH)
            states.removeLast();
    }

    /**
     * @return the current state
     */
    public RRobotState getCurrentState() {
        if(states.isEmpty())
            return null;
        return states.get(0);
    }

    /**
     * @return the last state of the robot
     */
    public RRobotState getLastState() {
        if(states.isEmpty())
            return getCurrentState();
        return states.getFirst();
    }

    /**
     * @return the last usable state of the RampantRobot
     */
    public RRobotState getLastUsableState() {
        if(states.size() < 3)
            return getLastState();
        return states.get(2);
    }

    /**
     * @return a reference to the robot's location
     */
    public RPoint getLocation() {
        RRobotState state = getCurrentState();
        if(state == null || state.location == null)
            return new RPoint(getX(), getY());
        return state.location.getCopy();
    }

    /**
     * @return a copy of the robot's location
     */
    public RPoint getCopyOfLocation() {
        return getLocation().getCopy();
    }

    /* (non-Javadoc)
     * @see robocode.Robot#onPaint(java.awt.Graphics2D)
     */
    public void onPaint(Graphics2D g) {
        if(globalBattlefield != null)
            globalBattlefield.draw(g);

        if(enemyManager != null && drawEnemy)
            enemyManager.draw(g);

        if(waveManager != null && drawWaves)
            waveManager.draw(g);

        if(movementManager != null && drawMovement)
            movementManager.draw(g);
    }

    protected boolean attemptShot() {

        if(!processingShot) {
            return false;
        }

        if(fireTime <= getTime() && getGunTurnRemainingRadians() == 0) {
            if(lockedSolution.intendedHitLocation != null) {
                double distance = getLocation().distance(lockedSolution.intendedHitLocation);
                double desiredVelocity = distance / (lockedSolution.anticipatedFlightTime - 1);
                lockedSolution.power = RUtil.computeBulletPower(desiredVelocity);
            }
            if(setFireBullet(lockedSolution.power) != null) {
                RBulletWave wave = new RBulletWave(lockedEnemy, lockedSolution, false);
                lockedSolution.gun.noteShot(true);
                waveManager.add(wave);
                if(true || !lockedSolution.gun.getName().equals(lastType)) {
                    lastType = lockedSolution.gun.getName();
                    //System.out.println("Using " + lastType + "\nwith gf " + lockedSolution.guessFactor + "\nwith reliability " + lockedSolution.reliabilityFactor);
                }
            }
            return true;
        }
        return false;
    }

    protected boolean lockFiringSolution(REnemyRobot enemy, RFiringSolution firingSolution) {
        if(!processingShot) {
            lockedEnemy = enemy;
            lockedSolution = firingSolution;
            setTurnGunRightRadians(Utils.normalRelativeAngle(lockedSolution.firingAngle));
            fireTime = getTime() + 1;
            processingShot = true;
            return true;
        }

        return false;
    }

    protected void simulateRemainingSolutions(REnemyRobot enemy, ArrayList<RFiringSolution> firingSolutions) {
        if(getGunHeat() == 0 && getEnergy() != 0) {
            int i = 1;

            for(; i < firingSolutions.size(); i++) {
                RBulletWave wave = new RBulletWave(enemy, firingSolutions.get(i), true);
                firingSolutions.get(i).gun.noteShot(false);
                waveManager.add(wave);
            }
        }

    }

    protected void turnGunToTarget(REnemyRobot enemy) {
        RRobotState enemyState = enemy.getCurrentState();
        double gunBearingToTarget = enemyState.absoluteBearing - getGunHeadingRadians();
        gunBearingToTarget = Utils.normalRelativeAngle(gunBearingToTarget);
        setTurnGunRightRadians(gunBearingToTarget);
    }


}
