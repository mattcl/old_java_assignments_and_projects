/**
 * 
 */
package rampancy.util.vector;

import java.util.*;
import java.awt.Color;
import java.awt.Graphics2D;

import rampancy.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RVectorField {
    
    public static final int MAX_MAGNITUDE = 40;
    
    public RVector[][] field;
    public int rows;
    public int cols;
    public double maxMagnitude; 
    
    public RVectorField(double width, double height, double density) {
        rows = (int) Math.round(height * density);
        cols = (int) Math.round(width * density);
        field = new RVector[rows][cols];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                field[i][j] = new RVector(new RPoint(j * (width / cols), i * (height / rows)));
            }
        }
        System.out.println("Rows: " + rows + " Cols: " + cols);
    }
    
    public void update(Collection<RRepulsiveObject> repulsivePoints) {
        maxMagnitude = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                boolean renewed = false;
                RPoint origin = field[i][j].getOrigin();
                field[i][j].setMagnitude(0);
                for(RRepulsiveObject point : repulsivePoints) {
                    RVector force = point.getForceAtPoint(origin);
                    if(force != null) {
                        if(!renewed) {
                            field[i][j] = force;
                            renewed = true;
                        } else {
                            field[i][j].add(force);
                        }
                    }
                }
                if(field[i][j].getMagnitude() > maxMagnitude) {
                    maxMagnitude = field[i][j].getMagnitude();
                }
            }
        }
    }
    
    public void draw(Graphics2D g) {
        double max = Math.min(maxMagnitude, MAX_MAGNITUDE);
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                double mag = field[i][j].getMagnitude();
                if(mag > 0) {
                    field[i][j].draw(g, 15, new Color((int) Math.min(255, Math.round(100 + mag / max * 155)), 0, 50));
                } else {
                    field[i][j].draw(g, 15);
                }
            }
        }
    }
}
