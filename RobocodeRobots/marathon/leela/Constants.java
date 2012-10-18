package marathon.leela;

/**
 * A dummy class that simply contains constants used by various components of Durandal
 * @author Matthew Chun-Lum
 *
 */
public abstract class Constants {
    public static final double MAX_RADAR_TRACKING = Math.PI / 4;
    
    public static final double DEFAULT_DISTANCE_FACTOR = 2;
    public static final double DEFAULT_WALL_MAGNITUDE   = 2000000;
    public static final double DEFAULT_CENTER_MAGNITUDE = 500000;
    public static final double DEFAULT_SWIVEL_MAGNITUDE = 150;
    public static final double DEFAULT_ENEMY_MAGNITUDE  = 1000000;
    
    public static final double WALL_TOLERANCE = 120;
    
    public static final double MOVEMENT_FACTOR = 0.6;
    
    public static final double INITIAL_ENERGY = 100;
    
    // Segmentation constants
    public static final int NUM_BINS = 47;
    public static final int NUM_GUESS_FACTOR_BINS = 31;
    public static final int NUM_DISTANCE_SEGMENTS = 21;
    public static final int NUM_VELOCITY_SEGMENTS = 9;
    public static final int NUM_MOVE_TIME_SEGMENTS = 20;
    public static final int NUM_BEARING_SEGMENTS = 16;
    public static final int NUM_HEADING_SEGMENTS = 16;
    
    // Constants for rolling averages
    public static final double STANDARD_WEIGHT = 1;
    public static final double STANDARD_ROLL_DEPTH = 200;
}
