package contest2010;

import stanford.karel.*;

//Brandon Liu
public class KarelContestProject extends SuperKarel {
	public void run() {
		makeRectangle();
//		paintScreen();
//		turnRight();
//		moveToWall();
//		turnRight();
//		turnAround();
	}

	//Karel is facing east
		private void makeRectangle() {
			while(frontIsClear()) {
				for(int i = 0;i < 19; i++) {
					paintCorner(YELLOW);
					move();
//					paintCorner(YELLOW);
					}
				turnLeft();
				for(int i = 0;i < 10; i++) {
					paintCorner(YELLOW);
					move();
//					paintCorner(YELLOW);
				}
				turnLeft();
			}
		}

	//colors row, then goes back
	private void paintRow() {
		if(leftIsClear()) {
		paintCorner(BLACK);
		while(frontIsClear()) {
			move();
			paintCorner(BLACK);
		}
		while(frontIsBlocked()) {
			turnAround();
			if(rightIsClear()) {
				moveToWall();
				turnAround();
			}
		}
	}else{
		paintCorner(BLACK);
		while(frontIsClear()) {
			move();
			paintCorner(BLACK);
		}
	}
	}

	//if Karel is facing East in far left of row
	//leaves Karel east in next row
	private void moveToNextRow() {
		turnLeft();
		move();
		turnRight();
	}
	private void paintScreen() {
		while(frontIsClear()) {
			paintRow();
			if(leftIsClear()) {
				moveToNextRow();
			}
		}
	}
private void moveToWall() {
	while(frontIsClear()) {
		move();
	}
}
}

