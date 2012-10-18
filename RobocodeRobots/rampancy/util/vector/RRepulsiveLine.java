/**
 * 
 */
package rampancy.util.vector;

import java.awt.geom.*;

import rampancy.util.*;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RRepulsiveLine extends Line2D.Double implements RRepulsiveObject {

    private static final long serialVersionUID = 264103247777266395L;
    public double angle;
    public double maxDistance;
    public double danger;
    public double multiplier;
    
    public RRepulsiveLine(double x1, double y1, double x2, double y2, double maxDistance, double danger, double multiplier) {
        this(new RPoint(x1, y1), new RPoint(x2, y2), maxDistance, danger, multiplier);
    }
    
    public RRepulsiveLine(RPoint start, RPoint end, double maxDistance, double danger, double multiplier) {
        super(start, end);
        this.maxDistance = maxDistance;
        this.danger = danger;
        this.multiplier = multiplier;
        angle = RUtil.computeAbsoluteBearing(start, end);
    }

    public RVector getForceAtPoint(RPoint point) {
        double dist = ptLineDist(point);
        if(dist > maxDistance)
            return null;
        
        double computedDanger = danger / (dist * dist * (1.0 /multiplier));
        int dir = relativeCCW(point);
        
        double computedAngle = Utils.normalAbsoluteAngle(angle + dir *Math.PI / 2);
        return new RVector(point, computedAngle, computedDanger);
        
    }

}
