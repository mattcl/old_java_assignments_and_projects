/**
 * 
 */
package rampancy_old.statistics.pattern;

/**
 * @author Matthew Chun-Lum
 *
 */
import java.util.*;

public class PMMatch {
    public ArrayList<PMState> pattern;
    public double distance;
    
    public PMMatch(ArrayList<PMState> pattern, double distance) {
        this.pattern = pattern;
        this.distance = distance;
    }
}
