package session2;
import robocode.*;


public class KingJulian extends Robot {
	int count = 0; // Keeps track of how long KJ has been searching for its target
	double gunTurnAmt; // How much to turn our gun when searching
	double previousEnergy = 100;
	int movementDirection = 1;
	String trackName;

	public void run() {
		trackName = null; // Initialize to not tracking anyone
//		setAdjustGunForRobotTurn(true); // Keep the gun still when we turn
		gunTurnAmt = 10; // Initialize gunTurn to 10

		trackName = null; // Initialize to not tracking anyone
		setAdjustGunForRobotTurn(true); // Keep the gun still when we turn
		gunTurnAmt = 10; // Initialize gunTurn to 10

		searchBot();
	}


	public void onScannedRobot(ScannedRobotEvent e) {
		smartFire(e);
//		moves perpendicularly to enemy (i hope)
		turnRight(e.getBearing()+100);
		fire(2);
		ahead(20);
		// If the bot has small energy drop, assume it fired
		dodge(e);
		// Track the energy level
		previousEnergy = e.getEnergy();
		if (e.getBearing() > -90 && e.getBearing() <= 90) {
			back(30);
		} else {
			ahead(50);
		}
	}

	public void smartFire(ScannedRobotEvent e){
		if (e.getDistance() < 15 || e.getDistance() >300){
			fire(1);
		}
		if (e.getDistance() <=200){
			fire (4);
		}else{
			fire(2);
		}
	}
	public void dodge (ScannedRobotEvent e){
		double changeInEnergy = previousEnergy-e.getEnergy();
		if (changeInEnergy>0 && changeInEnergy<=3) {
			// DodgeBullet!
			movementDirection =-movementDirection;
			ahead(50);
		}
	}
	public void searchBot(){
		while (trackName == null){
			turnGunLeft(45);
		}
	}
	public void onHitWall(HitWallEvent e) { 
		turnRight (190);
		ahead (200);
	}

	public void onHitRobot(HitRobotEvent e) {
		movementDirection *= -1; }

	public void onHitByBullet(HitByBulletEvent e){ 
		ahead (-150); 
	}
}