/**
 * SuperNode.java
 */
package rampancy_old.statistics;

import java.io.Serializable;

/**
 * This class stores information on a SuperNode
 * @author Matthew Chun-Lum
 *
 */
public class SuperNode implements Serializable {
    public byte[] path;
    public int visitCount;
    public double value;
    
    public SuperNode(WeaponSegmentIdentifier id, double value, int visitCount) {
        this.value = value;
        byte[] temp = {(byte) id.distance,
                (byte) id.lateralVelocity,
                (byte) id.heading,
                (byte) id.deltaHeading,
                (byte) id.nearWall,
                (byte) id.moveTimes,
                (byte) id.weapon,
                (byte) id.bin};
        this.path = temp;
        this.visitCount = visitCount;
    }
}
