/**
 * FiringSolution.java
 */
package rampancy_old.weapons;

import java.awt.Color;
import java.awt.geom.Point2D;


import rampancy_old.util.*;
import rampancy_old.util.tree.*;

/**
 * This is a dumb struct for returning firing data
 * @author Matthew Chun-Lum
 *
 */
public class FiringSolution {
    public EnemyState enemyState;
    public double offsetAngle;
    public double power;
    public double guessFactor;
    public Segment segment;
    public Color color;
    public Point2D.Double anticipated;
    public double deviation;
    
    public FiringSolution(EnemyState enemyState, double offsetAngle, double bulletPower, Segment segment, Color color) {
        this.enemyState = enemyState;
        this.offsetAngle = offsetAngle;
        this.power = bulletPower;
        this.segment = segment;
        this.color = color;
    }
    
    public FiringSolution(EnemyState enemyState, double offsetAngle, double bulletPower, Segment segment, Color color, Point2D.Double anticipated, double deviation) {
        this.enemyState = enemyState;
        this.offsetAngle = offsetAngle;
        this.power = bulletPower;
        this.segment = segment;
        this.color = color;
        this.anticipated = anticipated;
        this.deviation = deviation;
    }
}
