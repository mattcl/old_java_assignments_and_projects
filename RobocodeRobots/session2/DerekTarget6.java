package session2;

import robocode.*;

import java.awt.Color;


public class DerekTarget6 extends Robot {
	public void run(){
	setColors(Color.BLACK, Color.DARK_GRAY, Color.BLACK, Color.RED, Color.RED);
	turnTo(90);
	if(getX()<(getBattleFieldWidth()/2)){
		turnGunLeft(180);
		ahead((getX()-getBattleFieldWidth()));
	}else{
		ahead(getBattleFieldWidth()-getX());
	}
	turnTo(180);
	ahead(getBattleFieldHeight()-(getBattleFieldHeight()-getX()));
	turnTo(0);
	turnGunLeft(90);
	ahead(getBattleFieldHeight());
	while (true){
		ahead(200);
		if (getY()>580){
			turnGunRight(180);
			ahead(-getBattleFieldHeight());
			turnGunRight(180);
		}
	}
	}
	
	private void turnTo(double degrees){
		turnRight(degrees - getHeading());
	}
		
	public void onScannedRobot(ScannedRobotEvent e){
		ahead(1);
		fire(3);
		ahead(-1);
	}
	

}
