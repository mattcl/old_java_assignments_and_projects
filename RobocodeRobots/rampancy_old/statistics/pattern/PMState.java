/**
 * PMState.java
 */
package rampancy_old.statistics.pattern;

import rampancy_old.util.EnemyRobot;

/**
 * @author Matthew Chun-Lum
 *
 */
public class PMState {

    public double velocity;
    public double deltaHeading;
    
    public PMState(EnemyRobot enemy) {
        velocity = enemy.getVelocity();
        deltaHeading = enemy.getHeading() - enemy.getLastHeading();
    }
    
    public double distance(PMState targetState) {
        return Math.sqrt(Math.pow(targetState.velocity - velocity, 2) + Math.pow(targetState.deltaHeading - deltaHeading, 2));
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(deltaHeading);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(velocity);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PMState other = (PMState) obj;
        if (Double.doubleToLongBits(deltaHeading) != Double
                .doubleToLongBits(other.deltaHeading))
            return false;
        if (Double.doubleToLongBits(velocity) != Double
                .doubleToLongBits(other.velocity))
            return false;
        return true;
    }
    
}
