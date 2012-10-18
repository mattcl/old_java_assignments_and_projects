package marathon.tycho;

public class StandardGun {
    
    public static double getSuggestedBulletPower(EnemyRobot enemy) {
        return Math.min(3, Math.max(.1, getPowerForDistance(enemy.getDistance())));
    }
       
    // ------------ Private Helpers ------------- //
    
    /*
     * Returns the appropriate shot power for the standard gun given the distance
     */
    private static double getPowerForDistance(double distance) {
        if(distance < 50) {
            return 3.0;
        }
        return (1 - distance / 1400.0) * 3.0;
    }
}
