package session2;

import java.awt.Color;

import robocode.DeathEvent;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class FourthHorse extends Robot {
	public void run(){
		double gunHeat = getGunHeat();
		setColors(Color.BLACK, Color.RED, Color.BLACK, Color.CYAN, Color.RED);
		setAdjustGunForRobotTurn(false);
		while(true){
			turnRight(120);
			turnLeft(720);
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
		double distanceToMove = e.getDistance();
		if(e.getDistance() < 30){
			fire(3);
		}
		fire(0.9);
		stop();
		ahead(distanceToMove);

	}

	public void onHitRobot(HitRobotEvent e){
		stop();
		fire(3);
		fire(3);
		fire(3);
		fire(3);
	}
	
	public void onDeath(DeathEvent e){
		System.out.println("Screw thee robot with screws and hammers");
	}
	
	public void onWin(WinEvent e){
		System.out.println("Booyah you SUX!");
	}
}
