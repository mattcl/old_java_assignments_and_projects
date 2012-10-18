package contest2010;

// William Kim's Contest//
import stanford.karel.SuperKarel;
import java.awt.Color;

public class WillsContest extends SuperKarel {
	public void run() {
		paintBackground();
		createGrassField();
		createKarelBody();
		createKarelFace();
		drawSmile();
		changeSmile();
		moveToWall();
		turnRight();
		moveALot(8);
		turnRight();
		moveALot(9);
		blink();
		createEye();
		createKnife();
		bleed();
	}

	private void bleed() {
		bleedOut(3);
		move();
		turnRight();
		move();
		turnRight();
		bleedOut(3);
		turnLeft();
		turnAround();
		move();
		turnAround();
		bleedOut(3);
		move();
		turnRight();
		move();
		turnRight();
		bleedOut(3);
		moveALot(2);
		turnRight();
		moveALot(3);
		bleedOut(3);
		turnAround();
		moveALot(8);
		turnLeft();
		moveALot(12);
		paintRed(12);
		toNextRow();
		moveALot(12);
		turnAround();
		paintRed(10);
		toNextRow();
		moveALot(10);
		turnAround();
		paintRed(3);
		turnAround();
		moveToWall();
	}

	private void createKnife() {
		turnRight();
		moveToWall();
		turnLeft();
		moveALot(1);
		turnLeft();
		paintBlue(2);
		turnLeft();
		paintBlue(2);
		toNextRow();
		paintBlue(5);
		toNextRow();
		paintBlue(3);
		turnLeft();
		paintBlue(2);
		turnLeft();
		move();
		turnLeft();
		paintBlue(4);
		paintSilver(6);
		turnLeft();
		move();
		turnLeft();
		move();
		paintSilver(5);
		turnAround();
		moveALot(6);
		turnAround();
	}

	private void bleedOut(int times) {
		for (int i = 0; i < times; i++) {
			move();
			paintRed(1);
			turnLeft();
			move();
			paintRed(1);
			turnRight();
		}
	}

	private void blink() {
		for (int i = 0; i < 3; i++) {
			createEye();
			stall(10);
			deleteEye();
		}
	}

	private void createEye() {
		turnLeft();
		paintBlack(3);
		toNextRow();
		move();
		paintBlack(1);
		move();
		paintBlack(1);
		turnAround();
		moveALot(4);
		toNextRow();
		move();
		paintBlack(3);
	}

	private void deleteEye() {
		turnAround();
		for (int i = 0; i < 3; i++) {
			paintCorner(CYAN);
			move();
		}
		turnLeft();
		for (int i = 0; i < 3; i++) {
			for (int i1 = 0; i1 < 2; i1++) {
				paintCorner(CYAN);
				move();
			}
			turnLeft();
		}
	}

	private void changeSmile() {
		stall(22);
		turnAround();
		moveALot(11);
		turnRight();
		move();
		turnRight();
		eraseSmile();
		turnLeft();
		moveALot(4);
		turnLeft();
		drawSmile();
		turnRight();
	}

	private void createKarelBody() {
		for (int i = 0; i < 5; i++) {
			paintRed(18);
			turnAround();
			toNextRow();
			turnAround();
			moveALot(18);
			turnAround();
		}
		moveALot(17);
		turnLeft();
		paintRed(23);
		turnLeft();
		move();
		turnLeft();
		move();
		paintRed(23);
		turnRight();
		move();
		turnRight();
		paintRed(24);
		turnRight();
		moveALot(2);
		turnAround();
		paintRed(3);
		turnAround();
		moveALot(3);
		turnLeft();
		move();
		turnLeft();
		move();
		paintRed(18);
		toNextRow();
		move();
		paintRed(17);
		turnAround();
		moveALot(18);
		turnLeft();
		paintRed(26);
		turnLeft();
		move();
		turnRight();
		paintRed(5);
		toNextRow();
		moveALot(2);
		paintRed(4);
		moveALot(25);
		turnLeft();
		move();
		turnLeft();
		paintRed(29);
		turnAround();
		moveALot(4);
		turnLeft();
		moveALot(5);
		turnLeft();
		addLeg();
		turnLeft();
		paintRed(18);
		turnAround();
		moveALot(17);
		toNextRow();
		move();
	}

	private void createKarelFace() {
		for (int i = 0; i < 23; i++) {
			paintCyan(16);
		}
		turnRight();
		moveALot(28);
		turnLeft();
		moveALot(3);
	}

	private void createGrassField() {
		for (int i = 0; i < 5; i++) {
			paintRowGreen();
			toNextRow();
		}

		for (int i = 0; i < 7; i++) {
			if (frontIsClear()) {
				move();
				if (frontIsClear()) {
					move();
				}
			}
			paintGreen();
			turnLeft();
			paintGreen();
			turnRight();
			paintGreen();
			paintCorner(GREEN);
			turnLeft();
			move();
			turnRight();
			paintCorner(GREEN);
			turnRight();
			moveALot(2);
			turnLeft();
		}
		turnAround();
		moveToWall();
		turnRight();
		moveALot(4);
		turnRight();
		moveALot(8);
		addLeg();
	}

	private void paintRowGreen() {
		while (frontIsClear()) {
			paintBlock(GREEN);
			move();
		}
		paintBlock(GREEN);
		turnAround();
		moveToWall();
	}

	private void toNextRow() {
		turnRight();
		move();
		turnRight();
	}

	private void moveALot(int times) {
		for (int i = 0; i < times; i++) {
			move();
		}
	}

	private void paintBlock(Color w) {
		paintCorner(w);
	}

	private void paintBlack(int times) {
		for (int i = 0; i < times; i++) {
			paintCorner(BLACK);
			move();
		}
	}

	private void paintGreen() {
		paintCorner(GREEN);
		move();
	}

	private void paintSilver(int times) {
		for (int i = 0; i < times; i++) {
			paintCorner(LIGHT_GRAY);
			move();
			paintCorner(LIGHT_GRAY);
		}
	}

	private void paintBlue(int times) {
		for (int i = 0; i < times; i++) {
			paintCorner(BLUE);
			move();
			paintCorner(BLUE);
		}
	}

	private void paintRed(int times) {
		for (int i = 0; i < times; i++) {
			paintCorner(RED);
			move();
		}
	}

	private void paintCyan(int times) {
		for (int i = 0; i < times; i++) {
			paintCorner(CYAN);
			move();
		}
		turnAround();
		moveALot(16);
		toNextRow();
	}

	private void addLeg() {
		for (int i = 0; i < 2; i++) {
			paintBlack(5);
			turnAround();
			toNextRow();
			turnAround();
			moveALot(5);
			turnAround();
		}
		for (int i = 0; i < 3; i++) {
			paintBlack(3);
			turnAround();
			toNextRow();
			turnAround();
			moveALot(5);
			turnAround();
			moveALot(2);
		}
	}

	private void drawSmile() {
		for (int i = 0; i < 3; i++) {
			paintCorner(BLACK);
			move();
			turnLeft();
			move();
			turnRight();
		}
		paintBlack(4);
		for (int i = 0; i < 4; i++) {
			paintCorner(BLACK);
			turnRight();
			move();
			turnLeft();
			move();
		}
	}

	private void eraseSmile() {
		for (int i = 0; i < 3; i++) {
			paintCorner(RED);
			move();
			turnLeft();
			move();
			turnRight();
		}
		paintRed(4);
		for (int i = 0; i < 4; i++) {
			paintCorner(RED);
			turnRight();
			move();
			turnLeft();
			move();
		}
	}

	private void stall(int times) {
		for (int i = 0; i < times; i++) {
			moveALot(8);
			turnAround();
			moveALot(8);
			turnAround();
		}
	}

	private void paintBackground() {
		for (int i = 0; i < 49; i++) {
			while (frontIsClear()) {
				paintCorner(YELLOW);
				if (frontIsClear()) {
					move();
				}
			}
			paintCorner(YELLOW);
			turnAround();
			moveToWall();
			toNextRow();
		}
		while (frontIsClear()) {
			paintCorner(YELLOW);
			if (frontIsClear()) {
				move();
			}
		}
		paintCorner(YELLOW);
		turnAround();
		moveToWall();
		turnLeft();
		moveToWall();
		turnLeft();
	}

	private void moveToWall() {
		while (frontIsClear()) {
			move();
		}
	}
}
