package session2;

import java.awt.Color;

import robocode.DeathEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class Apocalypse extends Robot {
	public void run(){
		double gunHeat = getGunHeat();
		setColors(Color.BLACK, Color.BLACK, Color.BLACK, Color.RED, Color.BLACK);
		setAdjustGunForRobotTurn(false);
		while(true){
			while(true){
				double regen = getEnergy();
				if(regen<50){
					back(200);
				}
				turnRight(360);
				turnLeft(360);
			}

		}
		
	}

	private void moveTo(double x, double y){
		double currentX = getX();
		double currentY = getY();
		turnTo(270);
		ahead(getBattleFieldWidth()-getX());
		turnTo(180);
		ahead(getBattleFieldHeight()-getY());
		turnTo(0);
		ahead(y);
		turnTo(90);
		ahead(x);
	}

	private void turnTo (double degrees){	
		turnRight(degrees - getHeading());	
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		double regen = getEnergy();
		double distanceToMove = e.getDistance();
		if(e.getDistance() < 40){
			if(regen<40){
				for(int i = 0; i < 5; i ++){
					back(40);
					fire(3);
				}
				
			}
			fire(3);
		}
		fire(1);
		stop();
		if(regen > 50){
		ahead(distanceToMove);
		}

	}

	public void onHitRobot(HitRobotEvent e){
		stop();
		double regen = getEnergy();
		if(regen<50){
			back(200);
		}
		fire(3);
		fire(3);
		fire(3);
		fire(3);
	}
	public void onHitWall(HitWallEvent e){
		double regen = getEnergy();
		if( regen< 40){
			moveTo(100,100);
		}
	}
	
	public void onDeath(DeathEvent e){
		System.out.println("Crap.");
	}
	
	public void onWin(WinEvent e){
		System.out.println("Apocalypse!!!!!");
	}
}
