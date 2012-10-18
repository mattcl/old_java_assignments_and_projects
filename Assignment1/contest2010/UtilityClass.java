package contest2010;

import stanford.karel.*;

public class UtilityClass extends SuperKarel {
	
	// Moving commands
	public void moveIfClear() {
		if (frontIsClear()) {
			move();
		}
	}
	public void moveWhileClear() {
		while (frontIsClear()) {
			move();
		}
	}
	public void moveMore(int t) {
		for (int i = 0; i < t; i++) {
			move();
		}
	}
	// Facing commands.
	public void faceEast() {
		while (notFacingEast()) {
			turnLeft();
		}
	}
	public void faceNorth() {
		while (notFacingNorth()) {
			turnLeft();
		}
	}
	public void faceSouth() {
		while (notFacingSouth()) {
			turnLeft();
		}
	}
	public void faceWest() {
		while (notFacingWest()) {
			turnLeft();
		}
	}

}
