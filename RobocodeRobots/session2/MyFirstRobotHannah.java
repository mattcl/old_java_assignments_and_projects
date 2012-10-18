package session2;

import robocode.*;

public class MyFirstRobotHannah extends Robot {
	
	double distanceToEnemy;
	double enemyEnergy;
	double distanceToTop;
	double distanceToRight;
	double distanceToBottom;
	double distanceToLeft;
	
	public void run() {
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		enemyEnergy = 100;
		double bearingToWall = EpgyUtil.bearingToLocation(getX(), getY(), 0, getY());
		turnTo(bearingToWall);
		double distanceToWall = EpgyUtil.distanceTo(getX(), getY(), 0, getY());
		ahead(distanceToWall);
		while (true) {
		distanceToTop = EpgyUtil.distanceTo(getX(), getY(), getX(), getBattleFieldHeight());
		moveAlongLeft();
		distanceToRight = EpgyUtil.distanceTo(getX(), getY(), getBattleFieldWidth(), getY());
		turnTo(90);
		moveAlongTop();
		distanceToBottom = EpgyUtil.distanceTo(getX(), getY(), getX(), 0);
		turnTo(180);
		moveAlongRight();
		distanceToLeft = EpgyUtil.distanceTo(getX(), getY(), 0, getY());
		turnTo(270);
		moveAlongBottom();
		}
		

	}

	private void turnTo(double degrees) {
		turnRight(degrees - getHeading());
	}
	private void turnRadarTo(double degrees) {
		turnRadarRight(degrees - getRadarHeading());
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		double enemyBearing = e.getBearing();
		double absoluteBearing = enemyBearing + getHeading();
		turnGunRight(absoluteBearing - getGunHeading());
		fire(1);

		double enemyEnergyDifference = enemyEnergy - e.getEnergy();
		enemyEnergy = e.getEnergy();
		double enemyVelocity = e.getVelocity();
		
		if (enemyVelocity == 0) {
			fire(3);
		}else{
		turnGunRight(absoluteBearing - getGunHeading());
		fire(2);
		}
		if (enemyEnergyDifference >= 0.1 && enemyEnergyDifference <= 3.0) {
			System.out.println("Enemy fired.");
			back(100);
		}
		scan();
	}

	private void moveAlongLeft() {
		while (distanceToTop >= 25) {
		turnTo(0);
		ahead(5);
		turnRadarTo(0);
		turnRadarRight(180);
		turnRadarLeft(90);
		distanceToTop = EpgyUtil.distanceTo(getX(), getY(), getX(), getBattleFieldHeight());
		}
	}
	
	private void moveAlongTop() {
		while (distanceToRight >= 25) {
			ahead(5);
			turnRadarTo(90);
			turnRadarRight(180);
			turnRadarLeft(90);
			distanceToRight = EpgyUtil.distanceTo(getX(), getY(), getBattleFieldWidth(), getY());
		}
	}
	
	private void moveAlongRight() {
		while (distanceToBottom >= 25) {
			ahead(5);
			turnRadarTo(180);
			turnRadarRight(180);
			turnRadarLeft(90);
			distanceToBottom = EpgyUtil.distanceTo(getX(), getY(), getX(), 0);
		}
	}
	
	private void moveAlongBottom() {
		while (distanceToLeft >= 25) {
			ahead(5);
			turnRadarTo(90);
			turnRadarRight(180);
			turnRadarLeft(90);
			distanceToLeft = EpgyUtil.distanceTo(getX(), getY(), 0, getY());
		}
	}
}
