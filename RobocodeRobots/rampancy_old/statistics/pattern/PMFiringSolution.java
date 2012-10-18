/**
 * 
 */
package rampancy_old.statistics.pattern;

import java.awt.geom.Point2D;

/**
 * @author Matthew Chun-Lum
 *
 */
public class PMFiringSolution {

    public double power;
    public double offset;
    public Point2D.Double anticipated;
    
    public PMFiringSolution(double power, double offset, Point2D.Double anticipated) {
        this.power = power;
        this.offset = offset;
        this.anticipated = anticipated;
    }
    
}
