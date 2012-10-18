package rampancy.tycho;

import robocode.*;
import robocode.util.Utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.*;

public class Tycho extends AdvancedRobot {
   
    private Point2D.Double location;
    private Point2D.Double enemyLocation;
    
    private ArrayList<Wave> enemyWaves;
    private ArrayList<BulletWave> bulletWaves;
    private ArrayList<Integer> surfDirections;
    private ArrayList<Double> surfBearings;
    
    private static HashMap<String, EnemyRobot>enemies = new HashMap<String, EnemyRobot>();
    
    public int direction;
    
    public void run() {
        setInitialState();
        
        while(getRadarTurnRemainingRadians() == 0) {
            turnRadarRightRadians(Helper.MAX_RADAR_TRACKING_AMOUNT);
        }
    }
 
    public void onScannedRobot(ScannedRobotEvent e) {
        location = new Point2D.Double(getX(), getY());
        
        String name = e.getName();
        EnemyRobot enemy = enemies.get(name);
        if(enemy == null) {
            enemy = new EnemyRobot(e, this);
            enemies.put(name, enemy);
        } else {
            enemy.update(e);
        }
        
        setTurnRadarRightRadians(Utils.normalRelativeAngle(enemy.getAbsoluteBearing() - getRadarHeadingRadians() * 2));

        surf();
               
        double power = StandardGun.getSuggestedBulletPower(enemy);
        
        if (enemy.getVelocity() != 0) {
            if (Math.sin(e.getHeadingRadians() - enemy.getAbsoluteBearing()) * e.getVelocity() < 0)
                direction = -1;
            else
                direction = 1;
        }
        double[] currentStats = enemy.getGuessFactorArray();
        int[] currentReadings = enemy.getCurrentReadingArray();
        int currentIndex = enemy.getCurrentReadingIndex();

        BulletWave newWave = new BulletWave(location, getTime(), power, enemy.getAbsoluteBearing(), direction, currentStats, currentReadings, currentIndex);
        
        int bestindex = 15;
        for (int i=0; i<31; i++)
            if (currentStats[bestindex] < currentStats[i])
                bestindex = i;
        
        double guessfactor = (double)(bestindex - (Helper.GUESS_FACTORS - 1) / 2) / ((Helper.GUESS_FACTORS  - 1) / 2);
        double angleOffset = direction * guessfactor * Helper.maxEscapeAngle(Helper.bulletVelocity(power));
        setTurnGunRightRadians(Utils.normalRelativeAngle(enemy.getAbsoluteBearing() - getGunHeadingRadians() + angleOffset));
        
        if(e.getDistance() < 200 || Math.random() > 0.6) {
            setFireBullet(power);
            enemy.addIncomingBullet(newWave);
        }
                
        focusRadar(e);
    
    }
    
    public void onHitByBullet(HitByBulletEvent e) {
        if(enemies.containsKey(e.getName())) {
            enemies.get(e.getName()).processBulletHit(e);
        }
        setTurnRadarRightRadians(Utils.normalRelativeAngle(e.getBearingRadians() - getRadarHeadingRadians() * 2));
    }
    
    public void onHitRobot(HitRobotEvent e) {
        Helper.setBackAsFront(this, -Math.PI/4);
    }
    
    public void onPaint(Graphics2D g) {
        if(!enemies.isEmpty())
            for(EnemyRobot enemy : enemies.values())
                enemy.draw(g);
        super.onPaint(g);
    }
    
    public Point2D.Double getLocation() {
        return (Point2D.Double) location.clone();
    }
    
    // ---------- Private Helpers ----------- //
    
    private void focusRadar(ScannedRobotEvent e) {
        double radarBearingOffset =  Utils.normalRelativeAngle(getRadarHeadingRadians() - (e.getBearingRadians() + getHeadingRadians()));  
        setTurnRadarLeftRadians(radarBearingOffset + (Helper.nonZeroSign(radarBearingOffset) * (Helper.MAX_RADAR_TRACKING_AMOUNT / 2)));
    }
    
    private void surf() {
        Wave wave = getClosestEnemyWave();
        if(wave == null)
            return;
        
        double safetyLeft = evaluateSafety(wave, -1);
        double safetyRight = evaluateSafety(wave, 1);
        
        double angle = Helper.absoluteBearing(wave.getOrigin(), location);
        
        if(safetyLeft < safetyRight) {
            angle = Helper.wallSmoothing(location, angle - (Math.PI/2), -1);
        } else {
            angle = Helper.wallSmoothing(location, angle + (Math.PI/2), 1);
        }
        
        Helper.setBackAsFront(this, angle);
    }
    
    /*
     * Computes the best factor based on the wave in question
     */
    private double evaluateSafety(Wave wave, int direction) {
        int index = Helper.getFactorIndex(wave, predictPosition(wave, direction));
        int distance = Helper.getDistanceSegment(wave.getOrigin().distance(location));
        return wave.getEnemy().getStatistic(distance, index);
    }
    
    /*
     * predicts the position
     */
    private Point2D.Double predictPosition(Wave wave, int direction) {
        Point2D.Double predicted = (Point2D.Double) location.clone();
        double predictedVelocity = getVelocity();
        double predictedHeading = getHeadingRadians();
        double maxTurning, moveAngle, moveDirection;
        
        int tickCount = 0;
        
        while(tickCount < 500) {
            moveAngle = Helper.wallSmoothing(predicted, Helper.absoluteBearing(wave.getOrigin(), predicted) + (direction * (Math.PI/2)), direction) - predictedHeading;
            moveDirection = 1;
            
            if(Math.cos(moveAngle) < 0) {
                moveAngle += Math.PI;
                moveDirection = -1;
            }
            
            maxTurning = Math.PI/720d*(40d - 3d * Math.abs(predictedVelocity));
            predictedHeading = Utils.normalRelativeAngle(predictedHeading + Helper.limit(-maxTurning, moveAngle, maxTurning));
            
            predictedVelocity += (predictedVelocity * moveDirection < 0 ? 2 * moveDirection : moveDirection);
            predictedVelocity = Helper.limit(-8, predictedVelocity, 8);
            
            predicted = Helper.project(predicted, predictedHeading, predictedVelocity);
            
            tickCount++;
            
            if (wave.intercepted(predicted, tickCount))
               break;
        }
        
        return predicted;
    }
    
    /**
     * Returns the closest wave
     */
    private Wave getClosestEnemyWave() {
        double closest = 500000;
        Wave closestWave = null;
        for(EnemyRobot enemy : enemies.values()) {
            Wave wave = enemy.getClosestWave();
            if(wave != null) {
                double distance = wave.distanceFrom(location);
                if(distance < closest) {
                    closestWave = wave;
                    closest = distance;
                }
            }
        }
        return closestWave;
    }
    
    private boolean facingMe(double heading) {
        
        return false;
    }
    
    private void setInitialState() {
        setColors(Color.orange, Color.yellow, Color.red, Color.white, Color.orange);
        location = new Point2D.Double(getX(), getY());
        enemyWaves = new ArrayList<Wave>();
        bulletWaves = new ArrayList<BulletWave>();
        surfDirections = new ArrayList<Integer>();
        surfBearings = new ArrayList<Double>();
        
        if(!enemies.isEmpty()) {
            for(EnemyRobot enemy : enemies.values())
                enemy.reset(this);
        }
        
        direction = -1;
        
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
    }
}
