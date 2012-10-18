package session2;

import robocode.*;

public class OneOnOneRahulHandAdam extends Robot {

	private boolean canShoot =false;
	public void run() {


		turnTo (180);
		ahead (600-getY ());
		turnLeft (90);
		ahead (800-getX ());
		turnLeft (90);
		ahead (600);
		canShoot =true;
		while (true){


			turnRadarRight (360);
			
		}			
		 

//
//
//
//			EpgyUtil.bearingToLocation (getX(), getY (), 5,12);
//			turnTo (EpgyUtil.bearingToLocation(getX (), getY (), 5, 12));
//			ahead (150);
//			EpgyUtil.bearingToLocation (getX(), getY (), 3,16);
//			turnTo (EpgyUtil.bearingToLocation(getX (), getY (), 3, 16));
//			ahead (300);
//			EpgyUtil.bearingToLocation (getX(), getY (), 8,10);
//			turnTo (EpgyUtil.bearingToLocation(getX (), getY (), 8, 10));
//			ahead (450);
//			EpgyUtil.bearingToLocation (getX(), getY (), 2,7);
//			turnTo (EpgyUtil.bearingToLocation(getX (), getY (), 2, 7));
//			ahead (75);
//			EpgyUtil.bearingToLocation (getX(), getY (), 1,1);
//			turnTo (EpgyUtil.bearingToLocation(getX (), getY (), 1, 1));
//			ahead (360);
//			EpgyUtil.bearingToLocation (getX(), getY (), 0,0);
//			turnTo (EpgyUtil.bearingToLocation(getX (), getY (), 0, 0));
//			ahead (24);
//			EpgyUtil.bearingToLocation (getX(), getY (), 2,11);
//			turnTo (EpgyUtil.bearingToLocation(getX (), getY (), 2, 11));
//			ahead (100);
//			EpgyUtil.bearingToLocation (getX(), getY (), 6,4);
//			turnTo (EpgyUtil.bearingToLocation(getX (), getY (),6, 4));
//			ahead (75);
//			EpgyUtil.bearingToLocation (getX(), getY (), 4,2);
//			turnTo (EpgyUtil.bearingToLocation(getX (), getY (), 4, 2));
//			ahead (88);
//		}


	}	




	public void onScannedRobot (ScannedRobotEvent e){
		System.out.println ("I scanned a robot");
		double distanceToEnemy = e.getDistance ();
		double velocityToEnemy = e.getVelocity ();
        double bearingToEnemy= e.getBearing ();
        if(canShoot){
	        turnRight (bearingToEnemy);
	        fire (3);
	        fire (3);
	        fire (3);
        }
        
		
		
	}
	







	public void onHitRobot(HitRobotEvent event) {
		System.out.println ("I have hit!");
		fire (3);
		fire (3);
		fire (3);
	


	}

	private void turnTo(double degrees){
		turnRight(degrees-getHeading());
	}
	public void onHitByBullet (HitByBulletEvent e){

		double angleApartEnemy = e.getBearing ();

		System.out.println ("I have been hit! Yikes!");

		e.getBearing ();
		turnRight (angleApartEnemy);
		fire(3);








	}
	public void onRobotDeath(RobotDeathEvent event){
	String nameOfDead = event.getName ();
	System.out.println ("Sorry "+nameOfDead+ " that you had to kick the bucket." );

	}


}
















