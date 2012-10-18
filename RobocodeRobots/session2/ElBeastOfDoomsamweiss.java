package session2;

import java.awt.Color;

import robocode.*;
public class ElBeastOfDoomsamweiss extends Robot {
	public void run(){
		setAllColors(Color.BLUE);
//		ahead(200);
		setBulletColor(Color.RED); 
		turnTo(90);
		moveToWallStart();
		turnRight(90);
		turnGunTo(-90);
		while (true){
				moveToWall();

//				ahead();
//				turnGunRight(360);
//				moveToWallStart();
//				turnGunRight(360);
//				while (true){
//					moveToWall();
//					turnGunRight(360);
//					turnRight(180);
//					turnGunRight(360);
//				}
			}
		}
	private void moveToWallStart(){
		turnTo(90);
		ahead(100000000);
	}
	private void moveToWall(){


		ahead(100000000);
		ahead(-100000000);
	}
	
	private void turnTo(double degrees){
		turnRight(degrees - getHeading());
	}
	public void onRobotDeath(RobotDeathEvent event){
		resume() ;
	}
	private void turnGunTo(double degrees){
		turnGunRight(degrees - getGunHeading());
	}
	public void onScannedRobot(ScannedRobotEvent e){

		double toradar = getRadarHeading();
//		turnGunTo(toradar);
//		stop();
		
//		double dist =e.getDistance();
//		if(dist>=400){
//		fire(1);
//		}else{
//		if(dist>=200){
//			fire(2);
//		}
//		else{
			fire(3);
			ahead(1);
			ahead(-1);
//			turnGunTo(-90);
		}

//		}

//		double direction = getRadarHeading();
//		turnTo(direction);
//		ahead(20);
//		turnRadarRight(25);
//		turnRadarLeft(50);
//		turnRadarRight(25);

//		}
//	public void onHitWall(HitWallEvent event) {
//		turnRight(180);
//		ahead(50);
	}
//	}

