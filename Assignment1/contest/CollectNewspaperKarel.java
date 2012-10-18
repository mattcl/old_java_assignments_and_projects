package contest;
/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

/*Instructions: the world must be 20x20. The green lines/notes at the bottome are not 
 * part of my code. Do not make it go at the maximum speed.
 * Name: Jenny Paz
 * Section Leader:
 */

public class CollectNewspaperKarel extends SuperKarel {

	public void run() {
		grass();
		sky();
		turnAround();
		goToWall();
		turnAround();
		pool();
		turnRight();
		move5();
		playInPool();
		goToSun();
		sun();
		goToPool();
		playMoreInPool();
	}

	private void goToSun() {
		move10();
		move5();
		turnRight();
		move6();
		move3();
	}
	private void pool() {
		row1();
		goBackToNewLine();
		row2();
		goBackToNewLine();
		row3();
		goBackToNewLine();
		row4();
		goBackToNewLine();
		row4();
		goBackToNewLine();
		row4();
		goBackToNewLine();
		row4();
		goBackToNewLine();
		row3();
		goBackToNewLine();
		row2();
		goBackToNewLine();
		row1();
	}
	private void row1() {
		for (int i=0; i<5; i++) {
			move();
		}
		for (int i=0; i<10; i++) {
			paintCorner(LIGHT_GRAY);
			move();
		}
		for (int i=0; i<4; i++) {
			move();
		}
	}
	private void row2() {
		for (int i=0; i<4; i++) {
			move();
		}
		paintLightGray();
		for (int i=0; i<10; i++) {
			paintCorner(CYAN);
			move();
		}
		paintLightGray();
		for (int i=0; i<3; i++) {
			move();
		}
	}
	private void row3() {
		for (int i=0; i<3; i++){
			move();
		}
		paintLightGray();
		for (int i=0; i<12; i++) {
			paintCorner(CYAN);
			move();
		}
		paintLightGray();
		for (int i=0; i<2; i++) {
			move();
		}
	}
	private void row4() {
		for (int i=0; i<2; i++) {
			move();
		}
		paintLightGray();
		for (int i=0; i<14; i++) {
			paintCorner(CYAN);
			move();
		}
		paintLightGray();
		move();
	}
	private void goBack() {
		turnAround();
		goToWall();
	}

	private void goToWall() {
		while (frontIsClear()) {
			move();
		}
	}

	private void goToNewLine() {
		goToWall();
		if (frontIsBlocked()) {
			turnRight();
			move();
			turnRight();
		}
	}

//	Makes Karel do goBack and goToNewLine
	private void goBackToNewLine() {
		goBack();
		goToNewLine();
	}
	private void paintLightGray() {
		paintCorner(LIGHT_GRAY);
		move();
	}
	private void move5() {
		for (int i=0; i<5; i++) {
			move();
		}
	}
	private void move10() {
		for (int i=0; i<10; i++) {
			move();
		}
	}
	private void move3() {
		for (int i=0; i<3; i++) {
			move();
		}
	}
	private void move4() {
		for (int i=0; i<4; i++) {
			move();
		}
	}
	private void move6() {
		for (int i=0; i<6; i++) {
			move();
		}
	}
	private void playInPool() {
		turnRight();
		move10();
		move5();
		turnRight();
		move3();
		turnRight();
		move10();
		turnRight();
		move6();
		turnRight();
		move10();
		turnRight();
		move3();
	}
	private void sun() {
		while (frontIsClear()) {
			paintCorner(YELLOW);
			move();
		}
		paintCorner(YELLOW);
		turnRight();
		move();
		turnRight();
		for (int i=0; i<6; i++) {
			paintCorner(YELLOW);
			move();
		}
		turnLeft();
		move();
		turnLeft();
		move();
		while (frontIsClear()) {
			move();
			paintCorner(YELLOW);
		}
		paintCorner(YELLOW);
		turnRight();
		move();
		turnRight();
		for (int i=0; i<4; i++) {
			paintCorner(YELLOW);
			move();
		}
		turnLeft();
		move();
		turnLeft();
		move();
		while (frontIsClear()) {
			move();
			paintCorner(YELLOW);
		}
		paintCorner(YELLOW);
		turnRight();
		move();
		turnRight();
		for (int i=0; i<2; i++) {
			paintCorner(YELLOW);
			move();
		}
		turnLeft();
		move();
		turnLeft();
		move();
		move();
		paintCorner(YELLOW);
	}
	private void goToPool() {
		turnAround();
		move4();
		turnLeft();
		move6();
	}
	private void playMoreInPool() {
		while (noBeepersPresent()) {
			move5();
			turnRight();
			move10();
			move();
			turnRight();
		}
	}
	private void grass() {
		for (int i=0; i<10; i++) {
			while (frontIsClear()) {
				paintCorner(GREEN);
				move();
			}
			paintCorner(GREEN);
			goBackToNewLine();
		}
	}
	private void sky() {
		for (int i=0; i<9; i++) {
			while (frontIsClear()) {
				paintCorner(BLUE);
				move();
			}
			paintCorner(BLUE);
			goBackToNewLine();
			while (frontIsClear()) {
				paintCorner(BLUE);
				move();
			}
			paintCorner(BLUE);
		}
		turnRight();
		goToWall();
		turnLeft();
	}


//	move();
//	move();
//	move();
//	move3();
//	if (beepersPresent()) {
//	pickBeeper();
//	}


//	private void move3() {
//	move();
//	move();
//	move();
//	}

//	Note: This is an unfortunate hack to correct a
//	shortfall in our new Eclipse plugin. Don't worry
//	about (you won't be tested on it and aren't expected
//	to understand what's going on). However, don't
//	delete it, or you won't be able to run your Karel
//	program.
	public static void main(String[] args) {
		String[] newArgs = new String[args.length + 1];
		System.arraycopy(args, 0, newArgs, 0, args.length);
		newArgs[args.length] = "code=" + new SecurityManager(){
			public String className() {
				return this.getClassContext()[1].getCanonicalName();
			}
		}.className();
		SuperKarel.main(newArgs);
	}
}