
package session2;

import java.awt.Color;

import robocode.*;

public class MyFirstRobot_John extends Robot {

	private int numTimes = 0;

	public void run() {
		setColors(Color.RED, Color.RED, Color.RED, Color.RED,Color.RED);
		while (true){
			turnGunRight(360);
			ahead(100);
			turnGunRight(360);
			back(100);
		}
	}

	private void turnTo(double degrees){
		turnRight(degrees - getHeading());
	}

	public void onScannedRobot(ScannedRobotEvent e){
		System.out.println("I scanned a robot" + numTimes);
		if (e.getDistance()<50);{
			for (int i=0;i<7;i++){
				fire(3);
			}
		}
		if (e.getDistance()<150 && e.getDistance()>50); {
			fire(3);
		}if (e.getDistance()>150 && e.getDistance()<400); {
			fire(2);
		} if (e.getDistance()>400); {
			fire(1);

		}
	}
}

