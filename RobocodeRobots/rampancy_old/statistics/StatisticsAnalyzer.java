/**
 * StatisticsAnalyzer.java
 */
package rampancy_old.statistics;

/**
 * This class analyzes the statistics gathered by the stats manager
 * @author Matthew Chun-Lum
 *
 */
public abstract class StatisticsAnalyzer {
    
    /**
     * Analyzes a passed set of statistics
     * @param weaponStats
     * @param moveStats
     * @return
     */
    public static StatisticsReport analyze(WeaponStatistic weaponStats, MovementStatistic moveStats) {
        
        StatisticsReport report = new StatisticsReport();
        
        report.enemyName = weaponStats.getEnemyName();
        report.numGeneratedBranches = weaponStats.getGFGunStats().getNumGeneratedBranches();
        report.numTerminalBranches = weaponStats.getGFGunStats().getNumTerminalBranches();
        report.computeSuccessRates(weaponStats.getGFGunStats().getShotsFired(), weaponStats.getGFGunStats().getShotsHit());
        report.estimateMemoryConsumption();
        return report;
    }
    
}
