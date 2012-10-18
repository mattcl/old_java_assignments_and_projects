/**
 * PaternMatchingGunStatistic.java
 */
package rampancy_old.statistics;

import rampancy_old.RampantRobot;
import rampancy_old.statistics.pattern.*;
import rampancy_old.util.*;
import rampancy_old.weapons.FiringSolution;
import rampancy_old.weapons.PatternMatchingGun;

import java.awt.geom.Point2D;
import java.util.*;
/**
 * @author Matthew Chun-Lum
 *
 */
public class PatternMatchingGunStatistic {
    public static final int MAX_HISTORY_LENGTH = 1000;
    public static final int SEARCH_DEPTH = 60;
    public static final int LOG_DEPTH = 20;
    
    private ArrayList<PMState> movementHistory;
    private int shotsFired;
    private int shotsHit;
    private double hitPercentage;
    private double closestDistance;
    
    public PatternMatchingGunStatistic() {
        movementHistory = new ArrayList<PMState>();

    }
    
    /**
     * Logs the current state of the enemy robot in the movementHistory
     * @param enemy
     */
    public void logState(EnemyRobot enemy) {
        PMState state = new PMState(enemy);
        movementHistory.add(state);
        
        // keep the size within reasonable limits
        if(movementHistory.size() > MAX_HISTORY_LENGTH)
            movementHistory.remove(0);
    }
    
    /**
     * @param enemy
     * @return a firing solution based on the enemy's current state
     */
    public FiringSolution getFiringSolution(RampantRobot reference, EnemyRobot enemy) {
        EnemyState enemyState = new EnemyState(enemy);
        int[] profile = enemy.getVariationProfile().getProfile();

        PMMatch match = getMatchForPattern(enemy.getMovementLog());
        
        PMFiringSolution fs = PatternMatchingGun.computOffsetAngle(reference.getMovementManager().getBattlefield(), match, enemy, reference.getLocation());
 
        if(fs == null) {
            fs = new PMFiringSolution(0.1, 0, null);
        }
        return new FiringSolution(enemyState, fs.offset, fs.power, null, PatternMatchingGun.DEFAULT_COLOR, fs.anticipated, (match == null ? 100 : match.distance));
    }
    
    public PMMatch getMatchForPattern(ArrayList<PMState> pattern) {
        int index = getIndexOfClosestMatch(pattern);
        return new PMMatch(getProjectedState(index + pattern.size()), closestDistance);
    }
    
    public ArrayList<PMState> getProjectedState(int index) {
        if(index >= movementHistory.size() - 1 || index == -1)
            return null;
        return new ArrayList<PMState>(movementHistory.subList(index, Math.min(movementHistory.size(), index + SEARCH_DEPTH)));
    }
    
    public int getIndexOfClosestMatch(ArrayList<PMState> pattern) {
        int closest = -1;
        closestDistance = 100000;
        for(int i = 0; i < movementHistory.size() - LOG_DEPTH; i++) {
            double distance = computeDistance(pattern, i);
            if(distance < closestDistance) {
                closest = i;
                closestDistance = distance;
            }
        }
        return closest;
    }
    
    private double computeDistance(ArrayList<PMState> pattern, int startingIndex) {
        double distance = 0;
        int i, j;
        for(i = startingIndex, j = 0; i < movementHistory.size() && j < pattern.size(); i++, j++) {
            distance += movementHistory.get(i).distance(pattern.get(j));
        }
        
        if(movementHistory.size() - startingIndex < pattern.size())
            distance *= 4;
        
        return distance;
    }
    
}
