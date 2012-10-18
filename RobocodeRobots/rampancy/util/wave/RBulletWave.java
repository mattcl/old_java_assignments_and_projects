/**
 * 
 */
package rampancy.util.wave;

import java.awt.*;
import java.awt.geom.Line2D;

import rampancy.RampantRobot;
import rampancy.util.RDrawableObject;
import rampancy.util.REnemyRobot;
import rampancy.util.RPoint;
import rampancy.util.RRectangle;
import rampancy.util.RRobotState;
import rampancy.util.RUtil;
import rampancy.util.weapon.RFiringSolution;
import robocode.util.Utils;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RBulletWave extends RWave {
    public static final Color DEFAULT_COLOR     = Color.green;
    public static final Color ANTICIPATED_COLOR = Color.pink;
    public static final int ANTICIPATED_RADIUS  = 4;
    
    protected REnemyRobot target;
    protected RRobotState targetState;
    protected RRobotState creatorState;
    protected RFiringSolution firingSolution;
    protected RPoint bulletLocation;
    protected double absoluteFiringAngle;
    protected boolean didHit;
    
    /**
     * @param target
     * @param power
     * @param anticipatedImpactLocation
     */
    public RBulletWave(REnemyRobot target, RFiringSolution firingSolution) {
        this(target, firingSolution, false);
    }
    
    /**
     * @param target
     * @param power
     * @param anticipatedImpactLocation
     * @param virtual
     * @param color
     */
    public RBulletWave(REnemyRobot target, RFiringSolution firingSolution, boolean virtual) {
        super(target.getReference().getCopyOfLocation(),
                target.getReference().getTime(),
              firingSolution.power,
              firingSolution.color,
              virtual,
              firingSolution.drawableObject);
        this.target = target;
        this.targetState = target.getCurrentState().getCopy();
        this.creatorState = target.getReference().getCurrentState();
        this.firingSolution = firingSolution;
        this.absoluteFiringAngle = Utils.normalAbsoluteAngle(target.getReference().getGunHeadingRadians() + firingSolution.firingAngle);
        this.bulletLocation = origin.getCopy();
        didHit = false;
    }
    
    public void update(long time) {
        super.update(time);
        bulletLocation = RUtil.project(origin, absoluteFiringAngle, distanceTraveled);
        if(RUtil.pointOnRobot(bulletLocation, getTarget())) {
            didHit = true;
        }
    }
    
    public boolean didBreak() {
        return (distanceTraveled > target.getCurrentState().location.distance(origin) + 50);
    }
    
    /**
     * @return the target
     */
    public REnemyRobot getTarget() {
        return target;
    }

    /**
     * @return the targetState
     */
    public RRobotState getTargetState() {
        return targetState;
    }

    /**
     * @return the creatorState
     */
    public RRobotState getCreatorState() {
        return creatorState;
    }
    
    public RFiringSolution getFiringSolution() {
        return firingSolution;
    }

    /**
     * @return the anticipatedImpactLocation
     */
    public RPoint getAnticipatedImpactLocation() {
        return firingSolution.intendedHitLocation;
    }
    
    public double getAbsoluteFiringAngle() {
        return absoluteFiringAngle;
    }
    
    public RPoint getBulletLocation() {
        return bulletLocation;
    }
    
    public boolean didHitEnemy() {
        return didHit;
    }

    /**
     * Draw the anticipated location as well
     */
    public void draw(Graphics2D g) {
        if(isVirtual()) { 
            g.setColor(color);
            RUtil.fillOval(getBulletLocation(), 3, g);
            return;
        }
        
        if(drawableObject != null)
            drawableObject.draw(g);
        
        if(firingSolution.intendedHitLocation == null) {
            super.draw(g);
            return;
        }
        
        g.setColor(ANTICIPATED_COLOR);
        RUtil.fillOval(firingSolution.intendedHitLocation, ANTICIPATED_RADIUS, g);
        g.draw(new RRectangle(firingSolution.intendedHitLocation));
        double angle = RUtil.computeAbsoluteBearing(getOrigin(), firingSolution.intendedHitLocation);
        RPoint currentPosition = RUtil.project(getOrigin(), angle, getDistanceTraveled());
        if(getDistanceTraveled() > getOrigin().distance(firingSolution.intendedHitLocation))
            return;
        g.draw(new Line2D.Double(currentPosition, firingSolution.intendedHitLocation));
    }
    
}
