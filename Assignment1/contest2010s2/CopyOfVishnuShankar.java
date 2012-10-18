package contest2010s2;

/*Name:Vishnu Shankar
 * //Program Description: This program generates a random decimal number between 0 and 1. Depending on the number, the program chooses from multiple scenarios. The multiple scenarios have many other random choices including 
// choosing different colors for different objects.  
//Program Warning: The program needs to be run multiple times so that one can evaluate all the different scenarios, colors, and locations of objects.
 //Run the program in java application CopyofVishnuShankar at almost full speed so that the different designs can be seen.
 */
import stanford.karel.*;

import java.awt.Color;
import java.lang.Math;

public class CopyOfVishnuShankar extends SuperKarel {

	private Color darkGreen;

	public void run(){
		darkGreen = new Color(0,150,0);
		//The if statements consist of different cases which make different patterns on java application
		for (int a=0; a<800; a++) {
			double rand = Math.random();
			if (rand>=0.1 && rand<=0.2) {
				caseOne();
			}
			else if (rand>=0.2 && rand<=0.3) {
				caseTwo();  
			}
			else if (rand>=0.3 && rand<=0.4) {
				caseThree();
			} 
			else if (rand>=0.4 && rand<=0.5) {
				caseFour();
			}
			else if (rand>=0.6 && rand<=0.7) {
				caseFive();
			}
			else if (rand>=0.7 && rand<=0.8) {
				caseSix();
			}
			else if (rand>=0.8 && rand<=0.85) {
				caseSeven();
			}
			else if (rand>=0.85 && rand<=0.95) {
				mosaic();
			}
			else if (rand>=0.95 && rand<=1.0) {
				caseEight();
			}
			for (int b=0; b<200; b++) {
				turnAround();
			}
			cleanUp();
		}
	}

	//After the program finishes, the screen is cleaned to start another scenario again.
	//After ever case happens the program cleans the whole screen and removes beepers.
	//Karel starts by facing east and stops by facing east.
	private void cleanUp() {
		setLocation (1,1);
		while (notFacingEast()) {
			turnLeft();
		}
		for (int a=0; a<10; a++) {
			for (int q=0; q<10; q++) {
				while (beepersPresent()) {
					pickBeeper();
				}

				paintCorner(null);
				if (frontIsClear()) {
					move();
				}
			}
			turnAround();
			movingToWall();
			turnAround();
			verticalStepUp();
		}
		setLocation (1,1);
		while (notFacingEast()) {
			turnLeft();

		}
	}

	//Makes random colors for other private voids.
	private Color incredibleColor1() {
		int green= (int)(Math.random()*255);
		int blue = (int)(Math.random()*255);
		int red = (int)(Math.random()*255);
		Color startColor = new Color(red, green, blue);
		return startColor;
	}

	//The basic code used to fill the screen.
	//Karel starts the program by facing east and finishes by facing west at the wall.
	private void caseOne() {
		double rand = Math.random();
		paintCorner(Color.BLACK);
		scenarioOne();
		nextBoxTwo();
		square();
		establishPattern();
		if (rand>0.5) {
			nextBox2(incredibleColor1());
		}  else if (rand<0.5){
			nextBox2(incredibleColor1());
		}
		turnAround();
		for (int i=0; i<5; i++) {
			move();
		}
		turnRight();
		turnRight();
		stepUp();
		if (rand>0.5) {
			for (int i=0; i<5; i++) {
				Color color1 = darkGreen;
				paintCorner(color1);
				if (frontIsClear()) {
					move();
				}
			}
		}  else if (rand<0.5){
			for (int i=0; i<5; i++) {
				Color color1 = Color.ORANGE;
				paintCorner(color1);
				if (frontIsClear()) {
					move();
				}
			}	
		}
		lastTwoLinesSetup();
		if (frontIsClear() && (leftIsClear())) {
			for (int q=0; q<10; q++) {
				paintCorner(incredibleColor1());
				if (frontIsClear()) {
					move();
				}
			}
		}
		turnLeft();
		move();
		turnLeft();
		if (frontIsClear() && (leftIsClear())) {
			for (int q=0; q<10; q++) {
				paintCorner(incredibleColor1());
				if (frontIsClear()) {
					move();
				}
			}
		}
	}

	//Same as case One except with a row of randomized colors underneath the pattern.
	//Starts by facing east and finishes the program facing east while blocked by the right wall.
	private void caseTwo () {
		double rand = Math.random();
		for (int a=0; a<10; a++) {
			paintCorner(incredibleColor1());
			if(frontIsClear()) {
				move();
			}
		}
		turnAround();
		movingToWall();
		turnRight();
		move();
		turnRight();
		scenarioOne();
		nextBoxTwo();
		square();
		establishPattern();
		if (rand>0.5) {
			nextBox2(incredibleColor1());
		}  else if (rand<0.5){
			nextBox2(incredibleColor1());
		}
		turnAround();
		for (int i=0; i<5; i++) {
			move();
		}
		turnRight();
		turnRight();
		stepUp();
		if (rand>0.5) {
			for (int i=0; i<5; i++) {
				paintCorner(incredibleColor1());
				if (frontIsClear()) {
					move();
				}
			}
		}  else if (rand<0.5){
			for (int i=0; i<5; i++) {
				paintCorner(incredibleColor1());
				if (frontIsClear()) {
					move();
				}
			}	
		}

		lastTwoLinesSetup();
		if (frontIsClear() && (rightIsClear())) {
			for (int q=0; q<10; q++) {
				paintCorner(incredibleColor1());
				if (frontIsClear()) {
					move();
				}
			} 
		}
	}

	//Helps setup the basic pattern for Case One and all other cases.
	//Starts by facing east and finishes by facing east.
	private void scenarioOne() {
		double rand = Math.random();
		if (rand<0.5) {
			paintRow(incredibleColor1());
		}
		else if (rand>0.5) {
			paintRow(incredibleColor1());
		}
		if (rand>0.5) {
			insideBox(incredibleColor1());
		}
		else if (rand<0.5) {
			insideBox(incredibleColor1());
		}

		if (rand>0.5) {
			nextBox(incredibleColor1());
		}
		else if (rand<0.5) {
			nextBox(incredibleColor1());
		}

		nextBox2(incredibleColor1());
		turnLeft();
		move();
		turnLeft();
		move();
		for(int i=0; i<5; i++) {
			paintCorner(incredibleColor1());
			if (frontIsClear()) {
				move();
			}
		}
	}

	//Consists of the basic case one except it changes the pattern so that it becomes sideways.
	//Starts program facing east and finishes case by facing south while blocked by a wall.
	private void caseThree() {
		movingToWall();
		turnLeft();
		caseOne();

	}

	//Consist of the basic case one except with a square that appears with different colors on the screen
	//Karel starts the program facing east and finishes the program facing east while front is clear.
	private void caseSeven() {
		caseOne();
		turnLeft();
		movingToWall();
		turnLeft();
		stepUp();
		stepUp();
		square();
	}

	//First runs through case One, turns around, case two except it draws a random pattern in the middle.
	//Karel starts the program facing east and finishes the program facing south while blocked.
	private void caseFour() {
		movingToWall();
		turnLeft();
		double rand = Math.random();
		if (rand>0.5) {
			caseOne();
			turnAround();
			movingToWall();
			turnRight();
			move();
			turnRight();
			stepUp();
			turnAround();
			move();
			turnAround();
			while (frontIsClear()) {
				caseTwoSpecial();		
			}
		}
		if (rand<0.5) {
			caseTwo();
			turnAround();
		}
	}

	//Case Two Special is used for case four as it only goes through half of case two until the random pattern specified has been made.
	//Program starts by facing east and finishes by facing east. 
	private void caseTwoSpecial () {
		for (int a=0; a<10; a++) {
			Color color1 = darkGreen;
			paintCorner(color1);
			if(frontIsClear()) {
				move();
			}
		}
		turnAround();
		movingToWall();
		turnRight();
		move();
		turnRight();
		stepUp();
		scenarioOne();
	}

	//Case five goes through case One and makes a square in the middle of the screen
	//Karel starts by facing east and finishes the private void facing east in the middle of the square.
	private void caseFive() {
		caseOne();
		turnLeft();
		movingToWall();
		turnAround();
		turnRight();
		move();
		move();
		square();
	}

	//Case Six builds seven rows of Pascal's triangle.
	//Karel starts the program facing east and finishes the program facing east while blocked by the wall.
	private void caseSix() {
		move();
		stepOneCaseSix();
		move();
		stepTwoCaseSix();
		move();
		stepThreeCaseSix();
		move();
		move();
		stepFourCaseSix();
		move();
		stepFiveCaseSix();
		move();
		stepSixCaseSix();
		stepSevenCaseSix();
		putBeeper();
		turnAround();
		for (int q=0; q<10; q++) {
			paintCorner(incredibleColor1());
			if (frontIsClear()) {
				move();
			}
		}
		turnRight();
		move();
		turnRight();
		for (int q=0; q<10; q++) {
			paintCorner(incredibleColor1());
			if (frontIsClear()) {
				move();
			}
		}
		turnAround();
		movingToWall();
		turnRight();
		move();
		turnRight();
		for (int q=0; q<10; q++) {
			paintCorner(incredibleColor1());
			if (frontIsClear()) {
				move();
			}
		}
	}

	//Makes a mosaic with different randomized colors
	//Karel starts the program facing east and finishes the program facing east unblocked on the left side of the screen.
	private void mosaic() {
		for (int a=0; a<10; a++) {
			for (int q=0; q<10; q++) {
				paintCorner(incredibleColor1());
				if (frontIsClear()) {
					move();
				}
			}
			turnAround();
			movingToWall();
			turnAround();
			verticalStepUp();
		}
	}

	//Makes a pattern with three squares and other randomized colors.
	//Karel starts the program by facing east and after the pattern is completed, Karel faces east.
	private void caseEight() {
		caseOne();
		turnLeft();
		movingToWall();
		turnAround();
		for (int i=0;i<5; i++) {
			move();
		}
		turnRight();
		square();

	}

	//A stepUp which is specifically used for changing rows in the mosaic program.
	//In this program Karel starts facing east and finishes the program after changing rows facing east.
	private void verticalStepUp() {
		turnLeft();
		if(frontIsClear()) {
			move();
		}
		turnRight();
	}

	//Simplifies Pascal triangle program steps by having a separate program for painting Rows.
	//This simple program starts by facing east and finishes by facing east while blocked by the wall.
	private void paintingRowsPascal() {
		for (int q=0; q<10; q++) {
			paintCorner(incredibleColor1());
			if (frontIsClear()) {
				move();
			}
		}
	}

	//Used to simplify code of each of the steps of Pascal's triangle.
	//Karel starts facing east while blocked by the wall. Then, she turns around, paints Corner while moving to the wall, and goes to the next row.
	//Finally, she finishes the program facing east.
	private void changingStepsPascalTriangle() {
		turnAround();
		paintingRowsPascal();
		movingToWall();
		turnAround();
		stepUp();
		move();
	}

	//Builds the first step of Pascal's triangle.
	//Starts by facing east and after building the first row the program faces east on the second row.
	private void stepOneCaseSix() {
		move();
		putBeeper();
		move();
		for (int i=0; i<7; i++) {
			putBeeper();
		}
		move();
		for (int i=0; i<21; i++) {
			putBeeper();
		}
		move();
		for (int a=0; a<2; a++) {
			for (int i=0; i<35; i++) {
				putBeeper();
			}
			move();
		}
		for (int i=0; i<21; i++) {
			putBeeper();
		}
		move();
		for (int i=0; i<7; i++) {
			putBeeper();
		}
		move ();
		putBeeper();
		changingStepsPascalTriangle();
	}

	//Builds the second step of the Pascal's triangle.
	//Starts by facing east and after building the first second row, the program faces east on the third row.
	private void stepTwoCaseSix() {
		putBeeper();
		move();
		for (int i=0; i<6; i++) {
			putBeeper();
		}
		move();
		for (int i=0; i<15; i++) {
			putBeeper();
		}
		move();
		for (int a=0; a<20; a++) {
			putBeeper();
		}
		move();
		for (int i=0; i<15; i++) {
			putBeeper();
		}
		move();
		for (int i=0; i<6; i++) {
			putBeeper();
		}
		move();
		putBeeper();
		changingStepsPascalTriangle();
	}

	//Builds the third step of Pascal's triangle
	//Starts by facing east and after building the third row the program faces east on the fourth row.
	private void stepThreeCaseSix() {
		move();
		putBeeper();
		move();
		for (int i=0; i<5; i++) {
			putBeeper();
		}
		move();
		for (int a=0; a<2; a++) {
			for (int i=0; i<10; i++) {
				putBeeper();
			}
			move();
		}
		for (int a=0; a<5; a++) {
			putBeeper();
		}
		move();
		putBeeper();
		changingStepsPascalTriangle();
	}

	//Builds the fourth step of Pascal's triangle
	//Starts by facing east and after building the fourth row the program faces east on the fifth row.
	private void stepFourCaseSix() {
		move();
		putBeeper();
		move();
		for (int i=0; i<4; i++) {
			putBeeper();
		}
		move();
		for (int i=0; i<6; i++) {
			putBeeper();
		}
		move();
		for (int a=0; a<4; a++) {
			putBeeper();
		}
		move();
		putBeeper();
		changingStepsPascalTriangle();
		for (int b=0; b<3; b++) {
			move();
		}
	}

	//Builds the fifth step of Pascal's triangle
	//Starts by facing east and after building the fifth step, the program faces east on the sixth row.
	private void stepFiveCaseSix() {
		putBeeper();
		move();
		for (int a=0; a<2; a++) {
			for (int i=0; i<3; i++) {
				putBeeper();
			}
			move();
		}
		putBeeper();
		turnAround();
		paintingRowsPascal();
		movingToWall();
		turnAround();
		stepUp();
		for (int i=0; i<5; i++) {
			move();
		}
	}

	//Builds the sixth step of Pascal's triangle
	//Starts by  facing east and after building the sixth step, the program faces east on the seventh row. 
	private void stepSixCaseSix() {
		putBeeper();
		move();
		for (int i=0; i<2; i++) {
			putBeeper();
		}
		move();
		putBeeper();
		turnAround();
		paintingRowsPascal();
		movingToWall();
		turnAround();
		stepUp();
		for (int i=0; i<7; i++) {
			move();
		}
	}

	//Builds the seventh step of Pascal's triangle
	//Starts by facing east and after building the seventh step, the program goes to the eighth row where just one beeper is placed to represent the top of Pascal's triangle.
	private void stepSevenCaseSix() {
		putBeeper();
		move();
		putBeeper();
		turnAround();
		paintingRowsPascal();
		movingToWall();
		turnAround();
		stepUp();
		for (int i=0; i<8; i++) {
			move();
		}
	}

	//After the basic design of case one and two are done. This private void setup for the last two stages of the program where the last two lines are made.
	private void lastTwoLinesSetup() {
		turnRight();
		for (int i=0; i<3; i++){
			move();
		}
		turnRight();
	}

	//The program is used for the pattern on the left after the first square is made.
	private void establishPattern () {
		turnAround();
		movingToWall();
		setUp();
		unevenBox();
		turnAround();
		move();
		turnAround();

	}

	//Makes the design for the top of the 4x3 boxes.
	//Program starts by facing east. After the top has been made, the program still faces east.
	private void unevenBox() {
		double rand = Math.random();
		paintCorner(Color.BLACK);
		if (rand>0.5) {
			for (int i=0; i<5; i++) {
				paintCorner(BLACK);
				move();
			}
		}
		else if (rand<0.5) {
			for (int i=0; i<5; i++) {
				paintCorner(ORANGE);
				move();
			}
		}
	}

	//Program helps make the code for establish pattern easier.
	//Program starts by facing east while blocked and finishes facing west.
	private void setUp() {
		turnRight(); 
		move();
		move();
		turnRight();
	}

	//This private void used in most programs for making code easier.
	private void movingToWall () {
		while (frontIsClear()) {
			move();
		}
	}

	//Provides the transition from the square pattern to the side pattern.
	//Program starts by facing east while front is blocked and finishes facing east while front is clear.
	private void nextBoxTwo() {
		turnLeft();
		move();
		move();
		move();
		turnLeft();
		for (int i=0; i<4; i++) {
			move();
		}
		turnAround();
	}

	//Builds the basic square pattern for Case 1, case 2 case 3, case 4, case 5.
	//Program starts by facing east. The program finishes at the center of the square also facing east while front is clear.
	private void square() {
		double rand = Math.random();
		paintCorner(Color.BLACK);
		if (rand<0.5) {
			paintRow(incredibleColor1());
		}
		else if (rand>0.5) {
			paintRow(incredibleColor1()); 
		}
		if (rand>0.25) {
			insideBox(incredibleColor1());
		}
		else if (rand<0.25) {
			insideBox(incredibleColor1());
		}
	}

	//Builds the basic side pattern for case 2. Case 2 is unique as it draws a line before completing a pattern.
	private void nextBox2(Color cornerColor) {
		turnRight();
		move();
		paintCorner(cornerColor);
		turnRight();
		for(int i=0; i<5; i++) {
			paintCorner(cornerColor);
			if (frontIsClear()) {
				move();
			}
		}
	}

	//Builds the basic side pattern for all cases except case 2. 
	private void nextBox(Color cornerColor) {
		for(int i=0;i<3;i++) {
			move();
		}
		for (int i=0; i<5;i++) {
			paintCorner(cornerColor);
			if (frontIsClear()) {
				move();
			}
		}
	}

	//Forms the inside of square program. Used for all cases.
	//Program starts by facing east, fills the square, and faces east at the end.
	private void insideBox(Color cornerColor) {
		stepUp();
		for (int i=0; i<4; i++) {
			if (frontIsClear()) {
				for (int z=0; z<2; z++) {
					paintCorner(cornerColor);
					move();
				}
				turnLeft();
			}
		}
		stepUp();
		paintCorner(cornerColor);
	}

	//Used for generally all cases for painting purposes.
	private void paintRow(Color cornerColor) {
		for (int i=0; i<4; i++) {
			if (frontIsClear()) {
				for (int z=0; z<4; z++) {
					paintCorner(cornerColor);
					move();
				}
				turnLeft();
			}
		}
	}

	//Basic program for moving from line to line. Used in most private voids.
	//Starts by facing east, moves up, finishes by facing east.
	private void stepUp() {
		move();
		turnLeft();
		move();
		turnRight();
	}
}




