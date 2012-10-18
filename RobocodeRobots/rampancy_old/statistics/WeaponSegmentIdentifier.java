/**
 * WeaponSegmentIndentifier.java
 */
package rampancy_old.statistics;

/**
 * This is a dumb struct for holding the indexes we need
 * to retrieve the appropriate segment from a weapon statistic
 * @author Matthew Chun-Lum
 *
 */
public class WeaponSegmentIdentifier {

    public int distance;
    public int velocity;
    public int lateralVelocity;
    public int moveTimes;
    public int acceleration;
    public int heading;
    public int deltaHeading;
    public int nearWall;
    public int weapon;
    public int bin;
    
    /**
     * Default constructor
     * @param dist
     * @param vel
     * @param moveT
     * @param head
     * @param nearW
     * @param weapon
     */
    public WeaponSegmentIdentifier(int dist, int vel, int lateralVel, int acceleration, int moveT, int head, int deltaH, int nearW, int weapon) {
        this.distance = dist;
        this.velocity = vel;
        this.lateralVelocity = lateralVel;
        this.moveTimes = moveT;
        this.heading = head;
        this.deltaHeading = deltaH;
        this.nearWall = nearW;
        this.weapon = weapon;
        this.acceleration = acceleration;
    }
    
    /**
     * Copy constructor
     * @param id
     */
    public WeaponSegmentIdentifier(WeaponSegmentIdentifier id) {
        this.distance = id.distance;
        this.velocity = id.velocity;
        this.moveTimes = id.moveTimes;
        this.heading = id.heading;
        this.nearWall = id.nearWall;
        this.weapon = id.weapon;
        this.acceleration = id.acceleration;
        this.deltaHeading = id.deltaHeading;
        this.lateralVelocity = id.lateralVelocity;
    }
    
    /**
     * A string representation of this identifier
     */
    public String toString() {
        return "Dist: " + distance + " Vel: " + velocity + " moveT: " + moveTimes + " head: " + heading + " nearW: " + nearWall + " weapon: " + weapon;
    }
}
