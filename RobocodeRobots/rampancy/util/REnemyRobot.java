/**
 * REnemyRobot.java
 */
package rampancy.util;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;

import rampancy.RampantRobot;
import robocode.*;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class REnemyRobot {
    public static final int MAX_HISTORY_SIZE = 1500;
    public static final int BOT_RADIUS = 18;
    
    public static final Stroke absoluteDangerStroke = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 4, 10 }, 0);
    public static final Stroke desiredMinStroke     = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 4, 20 }, 0);
    public static final Stroke desiredMaxStroke     = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 4, 20 }, 0);

    protected String name;
    protected int shotsFired;
    protected int shotsHit;
    protected double minSafeDistance;
    protected double preferredSafeDistance;
    
    protected ArrayList<REnemyListener> listeners;
    
    protected ArrayList<RRobotState> states;
    protected ArrayList<Double> trackedBearings;
    protected ArrayList<Integer> trackedDirections;
    
    protected Ellipse2D.Double absoluteDangerZone;
    protected Ellipse2D.Double desiredMinDistance;
    protected Ellipse2D.Double desiredMaxDistance;
    
    protected RampantRobot reference;
    
    /**
     * @param name the name of the enemy robot
     */
    public REnemyRobot(String name, RampantRobot reference) {
        this.name = name;
        listeners = new ArrayList<REnemyListener>();
        states = new ArrayList<RRobotState>();
        resetState();
        shotsFired = 0;
        shotsHit = 0;
        this.reference = reference;
    }
    
    public void updateReference(RampantRobot reference) {
        this.reference = reference;
    }
    
    public RampantRobot getReference() {
        return reference;
    }
    
    /**
     * @return the name of the enemy robot
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return {@code true} if a shot was fired
     */
    public boolean shotFired() {
        RRobotState curr = getCurrentState();
        RRobotState last = getLastState();
        if(last != null) {
            double diff = last.energy - curr.energy;
            return diff >= 0.1 && diff <= 3.0 ;
        }
        
        return false;
    }
    
    /**
     * @return the power of the shot fired (0 if no shot was fired)
     */
    public double getShotPower() {
        if(!shotFired()) 
            return 0;
        
        return getLastState().energy - getCurrentState().energy;
    }
    
    /**
     * @return the number of shots fired
     */
    public int getShotsFired() {
        return shotsFired;
    }
    
    /**
     * @return the number of shots that hit their target
     */
    public int getShotsHit() {
        return shotsHit;
    }
    
    /**
     * increments the shotsHit var
     */
    public void noteShotHit() {
        shotsHit++;
    }
    
    /**
     * @return the minimum safe distance to this robot
     */
    public double getMinSafeDistance() {
        return minSafeDistance;
    }
    
    public double getPreferredSafeDistance() {
        return preferredSafeDistance;
    }
    
    /**
     * Given a ScannedRobotEvent, adds a state to the list of states
     * @param e
     */
    public void addState(ScannedRobotEvent e) {
        states.add(0, new RRobotState(this, e));
        if(states.size() >= MAX_HISTORY_SIZE)
            states.remove(states.size() - 1); // remove the last state
    }
    
    /**
     * @return the current state of the enemy
     */
    public RRobotState getCurrentState() {
        if(states.size() > 0)
            return states.get(0);
        return null;
    }
    
    /**
     * @return the last state of the enemy or {@code null} if there exists
     * only one state
     */
    public RRobotState getLastState() {
        if(states.size() > 1)
            return states.get(1);
        return null;
    }
    
    /**
     * @param n
     * @return returns up to the last n states
     */
    public ArrayList<RRobotState> getLastNStates(int n) {
        if(states.isEmpty())
            return null;
        return new ArrayList<RRobotState>(states.subList(0, Math.min(n-1, states.size())));
    }
    
    /**
     * @returns all of the states
     */
    public ArrayList<RRobotState> getStates() {
        return states;
    }
    
    /**
     * Clears the states in the list
     */
    public void clearStates() {
        states.clear();
    }
    
    /**
     * @return the last direction of a RampantRobot that the enemy
     * could have used to shoot
     */
    public int getLastUsableSurfDirection() {
        if(trackedDirections.size() > 2)
            return trackedDirections.get(2);
        return 0;
    }
    
    /**
     * @return the last bearing of a RampantRobot that the enemy
     * could have used to shoot
     */
    public double getLastUsableBearing() {
        if(trackedBearings.size() > 2)
            return trackedBearings.get(2);
        return 0;
    }
    
    /**
     * Invoked at the beginning of each round.
     */
    public void resetState() {
        //clearStates();
        trackedBearings = new ArrayList<Double>();
        trackedDirections = new ArrayList<Integer>();
    }
    
    /**
     * Invoked when the enemy is scanned
     * Do not call manually
     * @param e
     */
    public void update(ScannedRobotEvent e) {
        addState(e);
        updateTracking(e);
        if(shotFired()) {
            notifyShotFired();
            shotsFired++;
        }
        updateZones();
        notifyListeners();
    }
    
    public void draw(Graphics2D g) {        
        RRobotState state = getCurrentState();
        if(state == null)
            return;
        
        g.setColor(Color.white);
        g.draw(new RRectangle(state.location));
    }

    // ---------- Private ---------- //
    private void updateZones() {
        RRobotState state = getCurrentState();
        if(state == null)
            return;
        
        RPoint location = state.location;
        double absBearing = Utils.normalAbsoluteAngle(state.absoluteBearing + Math.PI);
        double maxEscapeAngle = RUtil.computeMaxEscapeAngle(RUtil.computeBulletVelocity(0.1));

        minSafeDistance = 30 / Math.sin(maxEscapeAngle);
        
        preferredSafeDistance = 230;
        
        absoluteDangerZone = new Ellipse2D.Double(location.x - minSafeDistance, 
                                                  location.y - minSafeDistance, 
                                                  minSafeDistance * 2, 
                                                  minSafeDistance * 2);
        
        desiredMinDistance = new Ellipse2D.Double(location.x - preferredSafeDistance, 
                                                  location.y - preferredSafeDistance, 
                                                  preferredSafeDistance * 2, 
                                                  preferredSafeDistance * 2);
        
        desiredMaxDistance = new Ellipse2D.Double(location.x - 400, 
                                                  location.y - 400, 
                                                  400 * 2, 
                                                  400 * 2);
        
    }
    
    /*
     * Updates the tracking arrays
     */
    private void updateTracking(ScannedRobotEvent e) {
        double lateralVelocity = reference.getVelocity() * Math.sin(e.getBearingRadians());
        int direction = lateralVelocity >= 0 ? 1 : -1;
        trackedDirections.add(0, direction);
        trackedBearings.add(0, getCurrentState().absoluteBearing + Math.PI);
    }
    
    // ---------- Listener Code ----------- //
    
    /**
     * @param listener
     * @return {@code true} if the listener was added to the list
     */
    public boolean addListener(REnemyListener listener) {
        if(listeners.contains(listener))
            return false;
        
        return listeners.add(listener);
    }
    
    /**
     * @param listener
     * @return {@code true} if the listener was removed fromt the list
     */
    public boolean removeListener(REnemyListener listener) {
        return listeners.remove(listener);
    }
    
    /**
     * Removes all listeners
     */
    public void removeAllListeners() {
        listeners.clear();
    }
    
    /**
     * Notifies all listeners
     */
    public void notifyListeners() {
        for(REnemyListener listener : listeners)
            listener.enemyUpdated(this);
    }
    
    /**
     * Notifies listeners of a shot fired
     */
    public void notifyShotFired() {
        for(REnemyListener listener : listeners)
            listener.shotFired(this);
    }
}
