package contest2010;

import java.awt.*;
import stanford.karel.*;

/* Program: 12 Dice Karel
 * Purpose: This program will roll 12 dices for the user and give the user a epic light show. It serves no specific purpose but to provide the user with some laughs, as their eyesight gets ruined.
 * Programmer: Christopher Shum
 * Date: Stanford EPGY Summer Session 1 (24 June 2010)
 */

public class KarelContest extends SuperKarel {
	public void run(){
		while (true) {
			paintBackground();
			paintAllSquares();
			putDiceInBoxes();
			pause(1000);
		}
	}

	/* Make Karel paint the background white, create the borders and draw the line*/
	private void paintBackground() {
		paintScreen(WHITE);
		paintBorder();
		paintLines();
	}

	/* Make Karel create the borders of the screen with a random color at the corner */
	private void paintBorder() {
		turnLeft();
		paintRowPlusRandom(BLACK);
		for (int i=0; i<3; i++) {
			turnRight ();
			paintRowPlusRandom(BLACK);
		}
		turnAround();
	}

	/* Make Karel draw the trisecting lines with a random color every 9 spaces at the intersections*/
	private void paintLines() {
		turnLeft();
		moveSpaces(10);
		paintCornerRandomColor();
		turnRight();
		move10SpacesAndColor(4);
		turnLeft();
		moveSpaces(10);
		paintCornerRandomColor();
		turnLeft();
		move10SpacesAndColor(4);
		paintCornerRandomColor();
		for (int i=0; i<2; i++) {
			turnRight();
			moveSpaces(10);
		}
		paintCornerRandomColor();
		turnRight();
		move10SpacesAndColor(3);
		turnLeft();
		paintCornerRandomColor();
		moveSpaces(10);
		turnLeft();
		paintCornerRandomColor();
		move10SpacesAndColor(3);
		turnRight();
		moveSpaces(10);
		paintCornerRandomColor();
		turnRight();
		move10SpacesAndColor(3);
		turnRight();
		moveToWall();
	}

	/* Make Karel put a smaller square border on the face of a dice in the middle of the 9x9 square
	 * Assume that Karel starts facing East in the right bottom corner of the square
	 * Leaves Karel facing East in the right bottom corner of the square
	 */
	private void paintSquares() {
		stepUp();
		turnLeft();
		for (int j=0; j<4; j++) {
			for (int i=0; i<5; i++) {
				paintCorner(BLACK);
				move();
			}
			paintCornerRandomColor();
			move();
			turnRight();
		}
		turnAround();
		move();
		turnRight();
		move();
		turnAround();
	}

	/* Make Karel paint the square border onto the faces of all the dices*/
	private void paintAllSquares() {
		moveToFirstSquare();
		paintSquares();
		for (int i=0; i<3; i++) {
			moveToNextSquare();
			paintSquares();
		}
		for (int i=0; i<2; i++) {
			moveToNextRowInSquare();
			paintSquares();
			for (int j=0; j<3; j++) {
				moveToNextSquare();
				paintSquares();
			}
		}
		turnAround();
		moveToWall();
		turnLeft();
		move();
		turnRight();
	}

	/* Make Karel paint a row or the remaining space in front of it a certain color specified within in the brackets*/
	private void paintRow(Color cornerColor) {
		paintCorner(cornerColor);
		while (frontIsClear()) {
			move();
			if (beepersPresent()) {
				pickBeeper();
			}
			paintCorner(cornerColor);
		}	
	}

	/* Make Karel paint the entire screen a certain color specified within the brakcets*/
	private void paintScreen(Color cornerColor) {
		while (leftIsClear()) {
			paintRow(cornerColor);
			goToNextRowAbove();	
		}
		paintRow(cornerColor);
		turnRight();
		moveToWall();
		turnRight();
		moveToWall();
		turnAround();
	}

	/* Make Karel paint a square a color chosen at random*/
	private void paintCornerRandomColor() {
		if (random (0.1)) {
			paintCorner (BLUE);
		}else if (random (0.11)){
			paintCorner (BLACK);			
		}else if (random (0.125)) {
			paintCorner (GRAY);
		}else if (random (0.14)) {
			paintCorner (GREEN);
		}else if (random (0.16)) {
			paintCorner (PINK);
		}else if (random (0.2)) {
			paintCorner (ORANGE);
		}else if (random (0.25)) {
			paintCorner (YELLOW);
		}else if (random (0.33)) {
			paintCorner (RED);
		}else if (random (0.5)) {
			paintCorner (DARK_GRAY);
		}else {
			paintCorner (MAGENTA);
		}
	}

	/* Make Karel paint a row or the space in front of it a certain color specified within the brackets 
	 * but makes it paint the last s pace a random color*/
	private void paintRowPlusRandom(Color cornerColor) {
		while (frontIsClear()) {
			move();
			paintCorner(cornerColor);
		}
		paintCornerRandomColor();

	}

	/* Make Karel lay out the face of a dice with a one displayed
	 * Assuming Karel starts at the right bottom corner of the 9x9 square
	 * Leaves Karel at the right bottom corner of the 9x9 square
	 */
	private void oneDice() {
		turnLeft();
		for (int i=0; i<2; i++) {
			moveSpaces(4);
			turnRight();
		}
		putBeeper();
		for (int i=0; i<2; i++) {
			moveSpaces(4);
			turnRight();
		}
		turnRight();
	}

	/* Make Karel lay out the face of a dice with a two displayed
	 * Assuming Karel starts at the right bottom corner of the 9x9 square
	 * Leaves Karel at the right bottom corner of the 9x9 square
	 */
	private void twoDice() {
		stepUpTwoSpaces();
		putBeeper();
		turnLeft();
		moveSpaces(4);
		turnRight();
		moveSpaces(4);
		putBeeper();
		returnToRightBottomCorner();
	}

	/* Make Karel lay out the face of a dice with a three displayed
	 * Assuming Karel starts at the right bottom corner of the 9x9 square
	 * Leaves Karel at the right bottom corner of the 9x9 square
	 */
	private void threeDice() {
		for (int i=0; i<3; i++) {
			stepUpTwoSpaces();
			putBeeper();
		}
		returnToRightBottomCorner();
	}

	/* Make Karel lay out the face of a dice with a four displayed
	 * Assuming Karel starts at the right bottom corner of the 9x9 square
	 * Leaves Karel at the right bottom corner of the 9x9 square
	 */
	private void fourDice() {
		stepUpTwoSpaces();
		putBeeper();
		turnLeft();
		for (int i=0; i<3; i++) {
			moveSpaces(4);
			putBeeper();
			turnRight();
		}
		turnLeft();
		moveSpaces(2);
		turnRight();
		moveSpaces(6);
		turnAround();		
	}

	/* Make Karel lay out the face of a dice with a five displayed
	 * Assuming Karel starts at the right bottom corner of the 9x9 square
	 * Leaves Karel at the right bottom corner of the 9x9 square
	 */
	private void fiveDice() {
		stepUpTwoSpaces();
		putBeeper();
		turnLeft();
		for (int i=0; i<3; i++) {
			moveSpaces(4);
			putBeeper();
			turnRight();
		}
		turnRight();
		stepUpTwoSpaces();
		putBeeper();
		turnAround();
		for (int i=0; i<2; i++) {
			moveSpaces(4);
			turnRight();
		}
		turnRight();

	}

	/* Make Karel lay out the face of a dice with a six displayed
	 * Assuming Karel starts at the right bottom corner of the 9x9 square
	 * Leaves Karel at the right bottom corner of the 9x9 square
	 */
	private void sixDice() {
		stepUpTwoSpaces();
		putBeeper();
		turnLeft();
		for (int i=0; i<2; i++) {
			moveSpaces(2);
			putBeeper();
		}
		turnRight();
		moveSpaces(4);
		turnRight();
		putBeeper();
		for (int i=0; i<2; i++) {
			moveSpaces(2);
			putBeeper();
		}
		moveSpaces(2);
		turnRight();
		moveSpaces(6);
		turnAround();
	}

	/* Make Karel create the pattern of a dice face chosen at random*/
	private void randomDiceRoll() {
		if (random (0.16)) {
			oneDice();
		}else if (random (0.2)){
			twoDice();
		}else if (random (0.25)){
			threeDice();
		}else if (random (0.33)){
			fourDice();
		}else if (random (0.5)){
			fiveDice();
		}else{
			sixDice();
		}
	}

	/* Make Karel place the randomly chosen dice faces in the 9x9 squares*/
	private void putDiceInBoxes() {
		moveToFirstSquare();
		randomDiceRoll();
		for (int i=0; i<3; i++) {
			moveToNextSquare();
			randomDiceRoll();
		}
		for (int i=0; i<2; i++) {
			moveToNextRowInSquare();
			randomDiceRoll();
			for (int j=0; j<3; j++) {
				moveToNextSquare();
				randomDiceRoll();
			}
		}
	}

	/* Make Karel go up one space and go right one space
	 * Assuming that Karel is facing East
	 * Leaves Karel facing East
	 */
	private void stepUp() {
		turnLeft();			
		move();
		turnRight();
		move();
	}

	/* Make Karel go up by two spaces and go right by two spces
	 * Assuming that Karel is facing East
	 * Leaves Karelfacing East
	 */
	private void stepUpTwoSpaces() {
		turnLeft();
		moveSpaces(2);
		turnRight();
		moveSpaces(2);
	}

	/* Make Karel move to the wall that is facing*/
	private void moveToWall() {
		while (frontIsClear()) {
			move();
		}
	}

	/* Make Karel move a number of spaces specified within the brackets*/
	private void moveSpaces(int times) {
		for (int i=0; i<times; i++) {
			move();
		}
	}

	/* Make Karel move forward a space and paint it black 10 times*/
	private void move10SpacesAndColor(int times) {
		for (int i=0; i<times; i++) {
			for (int j=0; j<10; j++) {
				move();
				paintCorner(BLACK);
			}
			paintCornerRandomColor();	
		}
	}

	/* Make Karel move to the right bottom corner of the left uppermost 9x9 square*/
	private void moveToFirstSquare() {
		turnRight();
		moveSpaces(21);
		turnRight();
		move();
	}

	/* Make Karel move to the 9x9 square that is directly to the right of the one it is currently in
	 * Assuming that Karel is in the bottom right corner of the first square
	 * Leaves Karel in the bottom right corner of the second square
	 */
	private void moveToNextSquare() {
		moveSpaces(10);
	}

	/* Make Karel moves to the start of the row above it that is still within the perimeter of the 9x9 square
	 * Assuming Karel is facing East at the end of the row
	 * Leaves Karel facing East at the start of the row above it
	 */
	private void moveToNextRowInSquare() {
		turnAround();
		moveToWall();
		turnLeft();
		moveSpaces(10);
		turnLeft();
		move();
	}

	/* Make Karel go to row above that is not confided by the perimeter of the 9x9 square
	 * Assuming Karel is facing East at the end of a row
	 * Leaves Karel one row above, facing East and the start of a row
	 */
	private void goToNextRowAbove() {
		turnAround();
		moveToWall();
		turnRight();
		move();
		turnRight();
	}

	/* Make Karel move to the right bottom corner of a square depending on its starting placement*/
	private void returnToRightBottomCorner() {
		turnRight();
		moveSpaces(6);
		turnRight();
		moveSpaces(6);
		turnAround();
	}

	/*Make Karel pause for a certain period of time that is specified within the brackets*/
	private void pause(int time) {
		try {
			Thread.sleep (time);
		} catch ( InterruptedException e) {
			e.printStackTrace();
		}
	}
}