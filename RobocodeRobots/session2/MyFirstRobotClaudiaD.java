package session2;

import robocode.*;
import robocode.ScannedRobotEvent;
import java.awt.Color;

public class MyFirstRobotClaudiaD extends Robot {
	private int numTimes=0;
	double currentX;
	double currentY;
	double[]safety=new double[15];
	double distanceToEnemy;
	double bearingToEnemy;	
	double absoluteBearing;
	double enemyVelocity;
	double enemyTargetX;
	double enemyTargetY;
	double enemyX;
	double enemyY;
	double enemyHeading;
	double interval;
	double enemyEnergy;
	double myBearing;
	public void run () {
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);
		setColors(Color.GREEN, Color.YELLOW, Color.BLUE, Color.ORANGE, Color.GREEN);
		updateLocation();
		System.out.println(currentX);
		System.out.println(currentY);
		enemyEnergy=100;
		while(true){
			turnRadarRight(360);
			updateLocation();
			if(enemyHeading>315&&enemyHeading<=45||enemyHeading>135&&enemyHeading<=225){
				if(enemyX>=currentX){
					moveTo(enemyX-100,enemyY);
				}else{
					moveTo(enemyX+100,enemyY);
				}
			}else{
				if(enemyY>=currentY){
					moveTo(enemyX,enemyY-100);
				}else{
					moveTo(enemyX,enemyY+100);
				}
			}
		}


	}


	private void moveTo(double x, double y){
		updateLocation();
		double hypotenuse=EpgyUtil.distanceTo(currentX,currentY,x,y);
		double angle=EpgyUtil.bearingToLocation(currentX, currentY, x, y);
		turnTo(angle);
		ahead(hypotenuse);
	}

	private void updateLocation(){
		currentX=getX();
		currentY=getY();
	}


	private void turnTo (double degrees) {
		turnRight (degrees-getHeading ());
	}
	public void onBulletMissed(BulletMissedEvent e){
		e.getBullet();
		getHeading();

	}
	public void onHitByBullet(HitByBulletEvent e){
		updateLocation();
		double angle=EpgyUtil.bearingToLocation(currentX,currentY,enemyX, enemyY);
		System.out.println(angle);
		if(angle>=337.5&&angle<=360){
			smoothBins(0);
		}else if(angle>=315&&angle<337.5){
			smoothBins(1);
		}else if(angle>=292.5&&angle<315){
			smoothBins(2);
		}else if (angle>=270&&angle<292.5){
			smoothBins(3);
		}else if (angle>=247.5&&angle<270){
			smoothBins(4);
		}else if (angle>=225&&angle<247.5){
			smoothBins(5);
		}else if (angle>=202.5&&angle<225){
			smoothBins(6);
		}else if(angle>=180&&angle<202.5){
			smoothBins(7);
		}else if(angle>=157.5&&angle<180){
			smoothBins(8);
		}else if(angle>=135&&angle<157.5){
			smoothBins(9);
		}else if(angle>=112.5&&angle<135){
			smoothBins(10);
		}else if(angle>=90&&angle<112.5){
			smoothBins(11);
		}else if(angle>=67.5&&angle<90){
			smoothBins(12);
		}else if(angle>=45&&angle<67.5){
			smoothBins(13);
		}else if(angle>=22.5&&angle<45){
			smoothBins(14);
		}else{
			smoothBins(15);
		}
	}






	private void smoothBins(int index){
		for(int i=0; i<15; i++){
			safety[i]+=1.0/(Math.pow(index-i, 2)+1);
		}
	}

	public void onScannedRobot (ScannedRobotEvent e) {

		double radar=getRadarHeading();
		stop();
		turnLeft(getRadarHeading()-radar);
		double distanceToEnemy=e.getDistance();
		double bearingToEnemy=e.getBearing();	
		double absoluteBearing= bearingToEnemy+getHeading();
		double enemyDistX=distanceToEnemy*EpgyUtil.sin(absoluteBearing);
		double enemyDistY=distanceToEnemy*EpgyUtil.cos(absoluteBearing);
		enemyX=getX()+enemyDistX;
		enemyY=getY()+enemyDistY;
//		System.out.println("Enemy is at("+enemyX+","+enemyY+")");
		updateLocation();
		enemyVelocity=e.getVelocity();
		enemyHeading=e.getHeading();
		if(enemyVelocity==0&&distanceToEnemy<150){
			turnGunRight(absoluteBearing-getGunHeading());
			fire(3);
		}else if(enemyVelocity==0){
			turnGunRight(absoluteBearing-getGunHeading());
			fire(1.5);
		}else{
			interval=11.25;
			double bulletTicks;
			if(distanceToEnemy>=250){
				bulletTicks=15;
			}else{
				bulletTicks=5;
			}

			for(int j=0; j<360/interval; j++){
				if(enemyHeading>=interval*j&&enemyHeading<=interval*(j+1)){
					enemyTargetX=enemyX+enemyVelocity*bulletTicks*EpgyUtil.sin(enemyHeading);
					enemyTargetY=enemyY+enemyVelocity*bulletTicks*EpgyUtil.cos(enemyHeading);

					System.out.println(enemyTargetX);
					System.out.println(enemyTargetY);

				}
			}

			double distance =EpgyUtil.distanceTo(currentX, currentY, enemyTargetX, enemyTargetY);
			double power= (distance/bulletTicks-20)/-3;
			if(power<0.1){
				power=0.1;
			}else if(power>3){
				power=3.0;
			}

			double angle=EpgyUtil.bearingToLocation(currentX, currentY, enemyTargetX, enemyTargetY);
			turnGunRight(angle-getGunHeading());
			fire(power);

		}

		double difference=enemyEnergy-e.getEnergy();
		enemyEnergy=e.getEnergy();
		double lowest=safety[0];
		if(difference>=0.1&&difference<=3.0){
			for(int j=0; j<15; j++){
				if(safety[j]<=lowest){
					safety[j]=lowest;
				}
			}
		}

		for(int k=0; k<15; k++){
			if(safety[k]==lowest){
				turnRight(k*22.5);
				ahead(100);
			}else{
				k++;
			}
		}
		ahead(150);
		enemyHeading=e.getHeading();


		turnRadarRight(absoluteBearing-getRadarHeading());
		System.out. println ("I scanned a robot");
		numTimes++;
		turnRadarRight(45);
		turnRadarLeft(45);
		scan();
	}


	public void onHitRobot(HitRobotEvent e){
		ahead(-100);
		ahead(100);
		ahead(-100);
	}

	public void onHitWall(HitWallEvent e){
		turnRight(90);
	}
}



