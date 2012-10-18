import stanford.karel.*;
public class KarelContestProject1 extends SuperKarel {

	public void run() {
		move();
		move();
		turnLeft();
		makeRectangle();
		turnLeft();
		stepUp();
		for(int i=0; i < 2; i++) {
			makeHalfSmallRectangle();
		}
		turnRight();
		stepUp();
		turnRight();
		makeWhiteRectangle();
		stepUp();
		makeDoorKnob();
		turnRight();
		for(int i=0; i < 14; i++) {
			magicalRow();
			moveToNextRow();
		}
		turnRight();
		for(int i=0; i < 14; i++) {
			move();
		}
		turnLeft();
	}

	private void makeRectangle() {
		while(frontIsClear()) {
			if(frontIsClear()) {
				for(int i = 0;i < 19; i++) {
					move();
					paintCorner(BLUE);
				}
				turnRight();
				if(frontIsClear()) {
					for(int i = 0;i < 9; i++) {
						move();
						paintCorner(BLUE);
					}
				}
				if(leftIsClear()) {
					turnRight();
				}else{
					turnLeft();
				}

			}
		}
	}
	private void makeHalfSmallRectangle() {
		//		while(frontIsClear()) {
		if(frontIsClear()) {
			for(int i = 0;i < 17; i++) {
				move();
				paintCorner(BLUE);
			}
			turnRight();
			if(frontIsClear()) {
				for(int i = 0;i < 7; i++) {
					move();
					paintCorner(BLUE);
				}
			}
			if(leftIsClear()) {
				turnRight();
			}else{
				turnLeft();
			}

		}
	}
	
	private void makeWhiteRectangle() {
		while(facingEast()) {
			for(int i = 0;i < 5; i++) {
				move();
				paintCorner(WHITE);
			}
			turnLeft();
			for(int i = 0;i < 15; i++) {
				move();
				paintCorner(WHITE);
			}
			turnLeft();
		}
		while(facingWest()) {
			for(int i = 0;i < 5; i++) {
				move();
				paintCorner(WHITE);
			}
			turnLeft();
			for(int i = 0;i < 15; i++) {
				move();
				paintCorner(WHITE);
			}
			turnLeft();
		}
	}
	
	private void paintCornerRandomColor() {
		if (random(0.1)) {
			paintCorner (BLUE);
		}else if (random (0.1)) {
			paintCorner (BLACK);
		}else if (random (0.1)) {
			paintCorner (GREEN);
		}else if (random (0.1)) {
			paintCorner (RED);
		}else if (random (0.1)) {
			paintCorner (GREEN);
		}else if (random (0.1)) {
			paintCorner (YELLOW);
		}else if (random (0.1)) {
			paintCorner (MAGENTA);
		}else if (random (0.1)) {
			paintCorner (ORANGE);
		}else if (random (0.1)) {
			paintCorner (CYAN);
		}else if (random (0.1)) {
			paintCorner (GRAY);
		}
	}
	
	//starts facing east
	//ends facing north
	private void stepUp() {
		move();
		turnLeft();
		move();
	}
	private void makeDoorKnob() {
		for(int i=0; i < 6; i++) {
			move();
		}
		putBeeper();
		turnAround();
		for(int i=0; i < 6; i++) {
			move();
		}
		turnAround();
	}
	
	private void magicalRow() {
		paintCornerRandomColor();
		move();
		paintCornerRandomColor();
		move();
		paintCornerRandomColor();
		move();
		paintCornerRandomColor();
		move();
		turnAround();
		for(int i=0; i < 4; i++) {
			move();
		}
		turnAround();
	}
	private void moveToNextRow() {
		turnLeft();
		move();
		turnRight();
	}
	private void magicalDoor() {
		for(int i=0; i < 14; i++) {
			magicalRow();
			moveToNextRow();
		}
		turnRight();
		for(int i=0; i < 14; i++) {
			move();
		}
		turnLeft();
	}
}
