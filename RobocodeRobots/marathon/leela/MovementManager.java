package marathon.leela;

import java.awt.geom.*;
import java.awt.*;
import java.util.*;


import robocode.*;
import robocode.util.Utils;

/**
 * This class is responsible for managing Durandal's movement
 * @author Matthew Chun-Lum
 *
 */
public class MovementManager implements EnemyManagerListener {

    private HashMap<String, ForcePoint> forcePoints;
    
    private ForcePoint windPoint;
    
    private HashMap<String, EnemyRobot> enemies; // reference to Durandal's enemy map
    
    private Leela reference;
    
    public static double width;
    public static double height;
    
    public ForceVector moveVector;
    
    /**
     * Constructor
     * @param enemies
     */
    public MovementManager() {
        moveVector = null;
    }
    
    /**
     * Sets up the initial force points and the other variables
     */
    public void setInitialState(Leela reference, double width, double height) {
        this.width = width;
        this.height = height;
        forcePoints = new HashMap<String, ForcePoint>();
//        forcePoints.put("NORTH WALL", new Wall(new Point2D.Double(width / 2, height), Constants.DEFAULT_WALL_MAGNITUDE, Wall.NORTH));
//        forcePoints.put("SOUTH WALL",new Wall(new Point2D.Double(width / 2, 0), Constants.DEFAULT_WALL_MAGNITUDE, Wall.SOUTH));
//        forcePoints.put("EAST WALL",new Wall(new Point2D.Double(0, height / 2), Constants.DEFAULT_WALL_MAGNITUDE, Wall.EAST));
//        forcePoints.put("WEST WALL",new Wall(new Point2D.Double(width, height / 2), Constants.DEFAULT_WALL_MAGNITUDE, Wall.WEST));
        forcePoints.put("CENTER",new ForcePoint(new Point2D.Double(width / 2, height / 2), Constants.DEFAULT_CENTER_MAGNITUDE, 3));
        forcePoints.put("SWIVEL", new SwivelPoint(reference, Constants.DEFAULT_SWIVEL_MAGNITUDE));
        this.reference = reference;
    }
    
    /**
     * Called by EnemyManager when a robot is updated
     */
    public void enemyUpdated(EnemyRobot enemy) {
        if(forcePoints.containsKey(enemy.getName())) {
            forcePoints.get(enemy.getName()).setLocation((Point2D.Double) enemy.getLocation().clone());
        } else {
            forcePoints.put(enemy.getName(), enemy.getForcePoint());
        }
    }
    
    /**
     * Returns the vector sum of all of the force points on the battlefield
     * @param target
     * @return a ForceVector representing the vector given the target
     */
    public ForceVector getVectorSum(Point2D.Double target) {
        double xSum = 0;
        double ySum = 0;
       
        for(ForcePoint point : forcePoints.values()) {
            ForceVector vect = point.getVectorAt(target);
            xSum += vect.x;
            ySum += vect.y;
        }
        
       return new ForceVector(xSum, ySum, Math.sqrt(xSum * xSum + ySum * ySum));
    }
    
    /**
     * Instructs the movement manager to determine the next move
     */
    public void makeMove() {
        Point2D.Double location = reference.getLocation();
        ForceVector vector1 = getVectorSum(location);
        
        Point2D.Double projectedLocation = new Point2D.Double(location.x + vector1.x, location.y + vector1.y);
        ForceVector vector2 = getVectorSum(projectedLocation).getCoterminal(Constants.MOVEMENT_FACTOR * vector1.magnitude);
        
        moveVector = ForceVector.sumVectors(vector1, vector2);
        
        double angle = moveVector.getAngle();
        
        setBackAsFront(angle, Constants.MOVEMENT_FACTOR * moveVector.magnitude);
    }
    
    /**
     * Draws the force points
     */
    public void draw(Graphics2D g) {
        if(moveVector != null)
            moveVector.draw(g, reference.getLocation());
        
        if(!forcePoints.isEmpty())
            for(ForcePoint point : forcePoints.values())
                if(reference.getLocation() != null) 
                    point.draw(g, reference.getLocation());
    }
    
    // -------- PRIVATE HELPERS --------- //
    /**
     * Moves the robot a specified angle and distance in the most efficient manner
     */
    private void setBackAsFront(double goAngle, double distance) {
        double angle = Utils.normalRelativeAngle(goAngle - reference.getHeadingRadians());
        if (Math.abs(angle) > (Math.PI/2)) {
            if (angle < 0) {
                reference.setTurnRightRadians(Math.PI + angle);
            } else {
                reference.setTurnLeftRadians(Math.PI - angle);
            }
            reference.setBack(distance);
        } else {
            if (angle < 0) {
                reference.setTurnLeftRadians(-1*angle);
           } else {
               reference.setTurnRightRadians(angle);
           }
            reference.setAhead(distance);
        }
    }
}
