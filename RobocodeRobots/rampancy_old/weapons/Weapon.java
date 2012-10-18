/**
 * Weapon.java
 */
package rampancy_old.weapons;

import rampancy_old.*;
import rampancy_old.util.*;

import java.awt.geom.*;
import java.awt.*;

/**
 * This is the abstract weapon class
 * All weapons must subclass off this class
 * @author Matthew Chun-Lum
 *
 */
public abstract class Weapon {
    
    private String name;
    private Color drawColor;
    private int weaponIndex;
    
    /**
     * Constructor
     * @param color
     */
    public Weapon(String name, Color drawColor, int weaponIndex) {
        this.name = name;
        this.drawColor = drawColor;
        this.weaponIndex = weaponIndex;
    }
    
    /**
     * @return the name of this weapon
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return the color for this weapon
     */
    public Color getColor() {
        return drawColor;
    }
    
    /**
     * @return the index for this weapon
     */
    public int getWeaponIndex() {
        return weaponIndex;
    }
    
    /**
     * Computes the shot power to use with the given enemy
     * @param enemy
     * @return the shot power
     */
    abstract public double computeShotPower(EnemyRobot enemy);
    
    /**
     * Computes the appropriate guessFactor to use with
     * the passed segment
     * @param segment
     * @return the guessFactor index
     */
    abstract public double computeGuessFactor(double[] segment);
}
