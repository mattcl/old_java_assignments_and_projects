package session2;

import java.awt.Color;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class PatheticBrawAdamZuckerl extends Robot 
{
	double xMove;
	double yMove;
	double distance;
	double theta;
	double height;
	double width;
	
	public void run() 
	{
		setGunColor(Color.RED);
		setRadarColor(Color.GRAY);
		setBulletColor(Color.RED);
		setScanColor(Color.BLUE);
		setBodyColor(Color.GREEN);
		height = getBattleFieldHeight();
		width = getBattleFieldWidth();
		EpgyUtil.bearingToLocation(getX(), getY(), width, height);
		ahead(EpgyUtil.distanceTo(getX(), getY(), width, height));
		turnTo(90);
		while(true)
		{
		   aheadTurnRight(width);
		}

	}

	private void turnTo(double degrees)
	{
		turnRight(degrees - getHeading());
	}
	private void turnGunTo(double degrees)
	{
		turnGunRight(degrees - getGunHeading());
	}
	private void aheadTurnRight(double degrees)
	{
		for(int i = 0; i < degrees; i++);
		{
			ahead(50);
			turnRight(20);
		}
	}
	private void aheadTurnLeft(double degrees)
	{
		for(int i = 0; i < degrees; i++);
		{
			ahead(50);
			turnLeft(30);
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e ) 
	{
		System.out.println("I scanned a robot" );
		fire(Math.random()* 3 + 1);
		turnGunTo(e.getBearing());
		ahead(e.getDistance());
		fire(Math.random()* 3 + 1);
	}
	public void onHitByBullet(HitByBulletEvent h)
	{
		System.out.println("I've been hit!");
		turnTo(h.getBearing());
		fire(3);
		aheadTurnLeft(-12.5);
	}
	public void onHitRobot(HitRobotEvent c)
	{
		System.out.println("I've crashed!");
		turnTo(c.getBearing());
		fire(3);
	}
	public void onHitWall(HitWallEvent w)
	{
		aheadTurnRight(-12);
	}
}



