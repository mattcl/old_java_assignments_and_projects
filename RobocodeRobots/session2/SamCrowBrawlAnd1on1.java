package session2;

import robocode.*;

import java.awt.Color;

public class SamCrowBrawlAnd1on1 extends Robot {
	public void run() {
		setColors(Color.ORANGE, Color.GREEN, Color.ORANGE, Color.YELLOW, Color.ORANGE);
		while(true) {
			turnGunRight(360);
			ahead(100);
			turnGunRight(360);
			ahead(-100);
		}
	}
	public void turnTo(double degrees) {
		turnRight(degrees - getHeading());
	}
	public void turnRadarTo(double degrees) {
		turnRadarRight(degrees - getRadarHeading());
	}
	public void turnGunTo(double degrees) {
		turnGunRight(degrees - getGunHeading());
	}
	public void onScannedRobot(ScannedRobotEvent e) {
		if(e.getDistance() > 300) {
			fire(1);
		}else {
			fire(2);
		}
		ahead(getBattleFieldHeight()/9);
		turnLeft(35);
	}
	public void onBulletHit(BulletHitEvent event) {
		ahead(getBattleFieldWidth()/7);
		turnRight(20);
		ahead(getBattleFieldHeight()/8);
		turnLeft(72);
	}
}