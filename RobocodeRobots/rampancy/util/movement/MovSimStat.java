/**
 * MovSimStat.java
 */
package rampancy.util.movement;

/**
 * This is a simple struct to store statistics returned by MovSim's futurePosition method
 * @author Albert
 *
 */
public class MovSimStat {
    public double x;
    public double y;
    public double v;
    public double h;
    public double w;
    public int offset;
    
    public MovSimStat(double x, double y, double v, double h, double w) {
        this.x = x; this.y = y; this.v = v; this.h = h; this.w = w; 
    }
}
