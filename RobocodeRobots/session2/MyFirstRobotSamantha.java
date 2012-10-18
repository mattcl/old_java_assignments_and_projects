package session2;

import java.awt.Color;

import robocode.*;

public class MyFirstRobotSamantha extends Robot {
	/*Robot for one on one and brawl.*/
	double times = 0;
	double times2 = 0;
//	double enemyEnergy = 100;
	public void run(){
		setRobotColors();
		this.setAdjustGunForRobotTurn(true);
		while(true){
			turnGunRight(360);
			checkNumberOfRobots();
			ahead(100);
			
		}
	}
	private void checkNumberOfRobots(){
		int others = getOthers();
		System.out.println("There are "+ others + " other robots");
	}
	private void setRobotColors(){
		setColors(Color.CYAN, Color.GREEN,Color.YELLOW,Color.RED,Color.ORANGE);
	}

	private void turnTo(double degrees){
		turnRight(degrees-getHeading());
	}
	public void onScannedRobot(ScannedRobotEvent e){
		System.out.println("I scanned a robot "+ times + (" times"));
		times++;
		double enemyEnergy=e.getEnergy();
		double distanceToEnemy=e.getDistance();
		double enemyBearing=e.getBearing();
		double absoluteBearing=enemyBearing + getHeading();
		double enemyDistX=distanceToEnemy*EpgyUtil.sin(absoluteBearing);
		double enemyDistY=distanceToEnemy*EpgyUtil.cos(absoluteBearing);
		double enemyX=getX()+enemyDistX;
		double enemyY=getY()+enemyDistY;
		System.out.println("Enemy Coordinate is at " + enemyX + ", " + enemyY + ".");
		double enemyVelocity=e.getVelocity();
		if (e.getVelocity()>=0 && e.getVelocity()<=2 ){
			double guess = .5;
			turnGunRight(absoluteBearing-getGunHeading() + guess);
			fire(3);
		}else if (e.getVelocity()>=3 && e.getVelocity()<=6 ){
			double guess = 1.5;
			turnGunRight(absoluteBearing-getGunHeading() + guess);
			fire(2);
		}else if (e.getVelocity()>=6 && e.getVelocity()<=8 ){
			double guess = 2;
			turnGunRight(absoluteBearing-getGunHeading() + guess);
			fire(2);
		}else{
		turnGunRight(absoluteBearing-getGunHeading());
		fire(2);
		turnTo(absoluteBearing);
		fire(1);
		}
		double distanceToEnemyTwo=500;
		if (distanceToEnemy>distanceToEnemyTwo){
			turnTo(absoluteBearing);
			//change right here//
			ahead(50);	
		}
		double energyDifference=e.getEnergy()-enemyEnergy;
		if(energyDifference>=0.1&&energyDifference<=3.0){
			System.out.println("Enemy Fired.");
			//change right here//
			turnRight(90);
			ahead(50);
		}
			enemyEnergy=e.getEnergy();
		}
//		double enemyEnergyTwo=e.getEnergy();
//		if (enemyEnergy>enemyEnergyTwo){
//			turnRight(30);
//			ahead(30);
//			turnLeft(30);
//		}
	/*Tells the robot what to do in the event of being hit by a bullet.*/
	public void onHitByBullet(HitByBulletEvent e){
		System.out.println("I've been shot."); 
		turnRight(e.getBearing());
		//change right here//
//		ahead(25);
		scan();
		
	}
	/*Tells the robot what to do in the event of hitting another robot with a bullet.*/
	public void onBulletHit (BulletHitEvent e){
		System.out.println("I've shot another robot "+ times2 + (" times"));
		times++;
		stop();
		fire(1);
	}
}