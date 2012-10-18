/**
 * 
 */
package rampancy.util.vector;

import rampancy.util.RPoint;

/**
 * @author Matthew Chun-Lum
 *
 */
public interface RRepulsiveObject {
    
    public RVector getForceAtPoint(RPoint point);
}
