// Seung Hee Han

package contest2010;

import java.awt.Color;
import stanford.karel.SuperKarel;


public class KarelContest_SeungHee extends SuperKarel {
	public void run() {
		sky24hrs();
	}

	private void sky24hrs() {
		while(beepersInBag()){
			dawnDuskSky();
			blueSky();
			turningToEveningSky();
			eveningSky();
			dawnDuskSky();
			nightSky();
		}
	}
	private void moveToWall() {
		while(frontIsClear()) {
			move();
		}
		turnAround();
	}
	private void moveNCorners(int numOfSteps) {
		for(int i = 0; i < numOfSteps; i++) {
			if(frontIsClear()){
				move();
			}
		}
	}

	private void paintNCorners(Color cornerColor, int corners) {
		for(int i = 0; i < corners; i++) {
			paintCorner(cornerColor);
			move();
		}
		turnAround();
		move();
	}

	private void nextRow() {
		turnLeft();
		if(frontIsClear()) {
			move();
		}
		turnRight();
	}

	private void paintRow(Color cornerColor) {
		while(frontIsClear()) {
			paintCorner(cornerColor);
			move();
		}
		paintCorner(cornerColor);
	}

	private void paintNRows(Color cornerColor, int lines) {
		for(int i = 0; i < lines; i++) {
			paintRow(cornerColor);
			turnAround();
			moveToWall();
			nextRow();
		}
	}

	private void wait(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException ie) {
		}
	}

	private void Sky(Color cornerColor) {
		paintNRows(cornerColor, 44);
		turnRight();
		moveNCorners(44);
		turnLeft();
		egypt();
	}

	private void dawnDuskSky() {
		makeGround();
		paintNRows(RED, 15);
		paintNRows(ORANGE, 11);
		paintNRows(YELLOW, 12);
		paintNRows(WHITE, 1);
		paintNRows(CYAN, 3);
		paintNRows(BLUE, 2);
		egypt();
		wait(3000);
	}

	private void blueSky() {
		Sky(CYAN);
		wait(4000);
	}

	private void turningToEveningSky() {
		turnLeft();
		moveToWall();
		turnLeft();
		for(int i = 0; i < 4; i++) {
			turnRight();
			moveNCorners(7);
			turnLeft();
			paintNRows(BLUE, 8);
			turnRight();
			moveNCorners(8);
			turnLeft();
			wait(2000);
		}
	}

	private void eveningSky() {
		turnRight();
		moveNCorners(14);
		turnLeft();
		paintNRows(BLUE, 14);
		turnRight();
		moveNCorners(14);
		turnLeft();
		egypt();
		wait(3000);
		turnRight();
		moveToWall();
		turnRight();
	}


	private void nightSky() {
		Sky(BLACK);
		turnLeft();
		moveNCorners(13);
		turnRight();
		makeStars();
		turnRight();
		moveToWall();
		turnRight();
		wait(5000);
	}

	private void makeStars() {
		while(leftIsClear()) {
			if(random(0.03)) {
				paintCorner(WHITE);
			}
			move();
			if(frontIsBlocked()) {
				turnAround();
				moveToWall();
				nextRow();
			}
		}

	}

	private void makeEgyptGod() {
		godFeet();
		godLegs();
		godBodyHead();
		goingDownToArm();
		godArm();
		moveToOtherArm();
		godArm();
		godKarelBackToGround();
		godMoveKarelBackToOrigin();
	}

	private void godFeet() {
		moveNCorners(2);
		paintNCorners(ORANGE, 2);
		turnAround();
		moveNCorners(2);
		paintNCorners(ORANGE, 2);
		moveNCorners(6);
		camelGodNextRow();
	}

	private void godLegs() {
		for(int i = 0; i <2; i++) {
			moveNCorners(3);
			paintCorner(ORANGE);
			moveNCorners(2);
			paintCorner(ORANGE);
			turnAround();
			moveNCorners(5);
			camelGodNextRow();
		}
	}
	
	private void godBodyHead() {
		for(int i = 0 ; i < 6; i++) {
			moveNCorners(3);
			paintNCorners(ORANGE, 3);
			moveNCorners(5);
			camelGodNextRow();
		}
	}

	private void godArm() {
		stepingUpAndPainting();
	}

	private void goingDownToArm() {
		turnRight();
		moveNCorners(4);
		turnLeft();
	}

	private void moveToOtherArm() {
		turnAround();
		moveNCorners(4);
	}

	private void stepingUpAndPainting() {
		paintNCorners(ORANGE, 2);
		turnRight();
		move();
		turnRight();
		paintNCorners(ORANGE, 2);
	}
	
	// origin is the very bottom left point above the ground, which the 6 lines of orange.
	private void godMoveKarelBackToOrigin() {
		turnRight();
		moveToWall();
	}

	private void godKarelBackToGround() {
		turnLeft();
		moveNCorners(7);
	}
	
	private void moveKarelToStartEgyptGod() {
		moveNCorners(38);
	}

	private void makeCamel() {
		camelFeet();
		camelLegs();
		makeCamelBodyTail();
		camelHeadHump();
		camelMoveKarelBackToOrigin();
	}

	private void camelFeet() {
		moveNCorners(2);
		paintNCorners(ORANGE, 2);
		turnAround();
		moveNCorners(2);
		paintNCorners(ORANGE, 2);
		moveNCorners(6);
		camelGodNextRow();
	}

	private void camelLegs() {
		for(int i = 0; i < 2; i++) {
			moveNCorners(3);
			paintNCorners(ORANGE, 1);
			turnAround();
			moveNCorners(3);
			paintNCorners(ORANGE, 1);
			moveNCorners(6);
			camelGodNextRow();
		}
	}

	// origin is the very bottom left point above the ground, which the 6 lines of orange.
	private void camelMoveKarelBackToOrigin() {
		turnAround();
		moveToWall();
		turnRight();
		moveNCorners(6);
		turnLeft();
	}

	private void camelGodNextRow () {
		turnAround();
		nextRow();
	}

	private void makeCamelBodyTail() {
		camelBodyTailStep1();
		camelBodyTailStep2();
		camelBodyTailStep3();
	}

	private void camelHeadHump() {
		paintNCorners(ORANGE, 2);
		turnAround();
		moveNCorners(2);
		paintNCorners(ORANGE, 4);
		moveNCorners(5);
		turnAround();
	}

	private void camelBodyTailStep1() {
		moveNCorners(3);
		paintNCorners(ORANGE, 4);
		turnAround();
		moveNCorners(2);
		paintNCorners(ORANGE, 2);
		moveNCorners(9);
		camelGodNextRow();
	}

	private void camelBodyTailStep2() {
		move();
		paintNCorners(ORANGE, 6);
		turnAround();
		moveNCorners(2);
		paintCorner(ORANGE);
		turnAround();
		moveNCorners(8);
		camelGodNextRow();
	}

	private void camelBodyTailStep3() {
		move();
		paintCorner(ORANGE);
		moveNCorners(2);
		paintNCorners(ORANGE, 6);
		moveNCorners(8);
		camelGodNextRow();
	}

	private void makePyramid() {
		for(int i = 12; i > 0; i = i - 1) {
			for(int n = 0; n < 2; n++){
				paintNCorners(YELLOW, i);
				moveNCorners(i);
			}
			turnLeft();
			move();
			turnRight();
		}
		pyramidMoveKarelOnGround();
		pyramidMoveKarelBackToOrigin();
	}
	
	private void moveKarelToStartPyramid() {
		moveNCorners(25);
	}

	// origin is the very bottom left point above the ground, which the 6 lines of orange.
	private void pyramidMoveKarelBackToOrigin() {
		turnRight();
		moveToWall();
	}

	private void pyramidMoveKarelOnGround() {
		turnRight();
		moveNCorners(12);
	}
	
	private void placingKarelOnGround() {
		turnRight();
		moveToWall();
		moveNCorners(6);
		turnRight();
	}

	private void makeGround() {
		paintNRows(ORANGE, 6);
	}

	private void egypt() {
		placingKarelOnGround();
		moveNCorners(2);
		makeCamel();
		moveKarelToStartPyramid();
		makePyramid();
		moveKarelToStartEgyptGod();
		makeEgyptGod();
	}
}