package session2;

import robocode.*;

public class Raksha_Brawler extends Robot {
	public void run()
	{
		double width= getBattleFieldWidth();
		double height= getBattleFieldHeight();
		while(true)
		{
			turnTo(90);
			ahead(width);
			turnGunRight(360);
			turnTo(180);
			ahead(height);
			turnGunRight(360);
			turnTo(270);
			ahead(width);
			turnGunRight(360);
			turnTo(0);
			ahead(height);
		}
	}
	private void turnTo(double degrees)
	{
		turnRight(degrees-getHeading());
	}
	public void onScannedRobot(ScannedRobotEvent e)
	{
		fireBullet(3);
		getEnergy();
		fireBullet(3);
		getEnergy();
		fireBullet(3);
		getEnergy();
		fireBullet(3);
		getEnergy();
		fireBullet(3);
		getEnergy();
		fireBullet(3);
		ahead(50);
	}
}