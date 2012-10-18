/**
 * RampantRobotState.java
 */
package rampancy_old.util;

import rampancy_old.RampantRobot;

/**
 * @author Matthew Chun-Lum
 *
 */
public class RampantRobotState extends RobotState{
    
    /**
     * Constructor
     * @param robot
     * @param enemy
     */
    public RampantRobotState(RampantRobot robot, EnemyRobot enemy, RampantRobotState previous) {
        super(robot.getLocation(),
                Util.computeAbsoluteBearing(enemy.getLocation(), robot.getLocation()),
                robot.getHeadingRadians(),
                (previous == null ? 0 : previous.heading),
                robot.getLocation().distance(enemy.getLocation()),
                (previous == null ? 0 : previous.distance),
                robot.getVelocity(),
                (previous == null ? 0 : previous.velocity));
    }
}
