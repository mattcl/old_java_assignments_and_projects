package contest;
/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

/*
 * Name: CLAUDIA DENNELR 
 * Section Leader: EPGY JAVA AND ROBOTICS
 */

/*In this progam, Karel animates the letter "C." A basketball bounces across a varsity
 * letter "C." Run on close to fastest program for best effects.
 * The screen should seem to be blinking. Use in a 45x45 world.
 */
public class ClaudiaDennlerKarelProject extends SuperKarel {

	public void run() {
//		while(true){
			makeC();
//			basketballBounce();
//			cTwo();
//			basketballBounce();
//			cThree();
//			basketballBounce();
//			cFour();
//			basketballBounce();
//			cSix();
			basketballBounce();
			cFive();
			turnLeft();
			swish();
		}
//	}

	/*Writes the word Swish!!!
	 * Assumes Karel is facing East.
	 * Leaves Karel facing East.
	 */
	private void swish(){
		swishOne();
		swishTwo();
		swishThree();
		swishFour();
		swishFive();
		returnToHome();
		for(int i=0; i<100000; i++){
			turnLeft();
		}
	}

	/*Writes the bottom part of the word swish!!!
	 * Assumes Karel is facing East.
	 * Leaves Karel facing East.
	 */
	private void swishFive(){
		for(int i=0; i<2; i++){
			move();
		}
		for(int i=0; i<4; i++){
			paintCorner(MAGENTA);
			move();
		}
		move();
		for(int i=0; i<2; i++){
			for(int x=0; x<2; x++){
				paintCorner(MAGENTA);
				move();
			}
			move();
		}
		for(int i=0; i<5; i++){
			paintCorner(MAGENTA);
			move();
		}
		move();
		for(int i=0; i<4; i++){
			paintCorner(MAGENTA);
			move();
		}
		move();
		paintCorner(MAGENTA);
		moveFour();
		paintCorner(MAGENTA);
		for(int i=0; i<2; i++){
			move();
		}
		for(int i=0; i<3; i++){
			paintCorner(MAGENTA);
			move();
			move();
		}
		turnAround();
		for(int i=0; i<34; i++){
			move();
		}
		advanceRowDown();

	}

	/*Creates one of the middle parts of the word swish!!!
	 * Assumes Karel is facing East.
	 * Leaves Karel facing East.
	 */
	private void swishFour(){
		for(int i=0; i<3; i++){
			move();
		}
		paintCorner(MAGENTA);
		for(int i=0; i<3; i++){
			move();
			move();
			paintCorner(MAGENTA);
		}
		moveFour();
		paintCorner(MAGENTA);
		for(int i=0; i<7; i++){
			move();
		}
		paintCorner(MAGENTA);
		for(int i=0; i<2; i++){
			move();
		}
		paintCorner(MAGENTA);
		moveFour();
		paintCorner(MAGENTA);
		turnAround();
		for(int i=0; i<28; i++){
			move();
		}
		advanceRowDown();
	}

	/*Creates one of the middle parts of the word swish!!!
	 * Assumes Karel is facing East.
	 * Leaves Karel facing East.
	 */
	private void swishThree(){
		for(int i=0; i<4; i++){
			paintCorner(MAGENTA);
			move();
		}
		move();
		for(int i=0; i<3; i++){
			paintCorner(MAGENTA);
			moveFour();
		}
		for(int i=0; i<4; i++){
			paintCorner(MAGENTA);
			move();
		}
		move();
		for(int i=0; i<5; i++){
			paintCorner(MAGENTA);
			move();
		}
		move();
		for(int i=0;i<3;i++){
			paintCorner(MAGENTA);
			move();
			move();
		}
		turnAround();
		for(int i=0; i<34; i++){
			move();
		}
		advanceRowDown();
	}

	/*Creates one of the middle parts of the word swish!!!
	 * Assumes Karel is facing East.
	 * Leaves Karel facing East.
	 */
	private void swishTwo(){
		paintCorner(MAGENTA);
		for(int i=0; i<5; i++){
			move();
		}
		for(int i=0; i<3; i++){
			paintCorner(MAGENTA);
			moveFour();
		}
		paintCorner(MAGENTA);
		for(int i=0; i<5; i++){
			move();
		}
		paintCorner(MAGENTA);
		moveFour();
		paintCorner(MAGENTA);
		move();
		move();
		for(int i=0;i<3;i++){
			paintCorner(MAGENTA);
			move();
			move();
		}
		turnAround();
		for(int i=0; i<34; i++){
			move();
		}
		advanceRowDown();
	}

	/*Creates the top part of the word swish!!!
	 * Assumes Karel is facing East.
	 * Leaves Karel facing East.
	 */
	private void swishOne(){
		for(int i=0; i<25; i++){
			move();
		}
		turnRight();
		for(int i=0; i<5; i++){
			move();
		}
		for(int i=0; i<4; i++){
			paintCorner(MAGENTA);
			move();
		}
		move();
		paintCorner(MAGENTA);
		moveFour();
		paintCorner(MAGENTA);
		move();
		move();
		for(int i=0; i<5; i++){
			paintCorner(MAGENTA);
			move();
		}
		move();
		for(int i=0; i<4; i++){
			paintCorner(MAGENTA);
			move();
		}
		move();
		paintCorner(MAGENTA);
		moveFour();
		paintCorner(MAGENTA);
		move();
		move();
		for(int i=0; i<3; i++){
			paintCorner(MAGENTA);
			move();
			move();
		}
		turnAround();
		for(int i=0; i<34; i++){
			move();
		}
		advanceRowDown();
	}

	private void moveFour(){
		for(int i=0; i<4; i++){
			move();
		}
	}

	/*Makes a basketball image bounce across the world.
	 * Assumes Karel is in bottom left corner facing East.
	 * Leaves Karel in same position.
	 */
	private void basketballBounce(){
		turnLeft();
		moveToWall();
		turnRight();
		makeNextBasketball();
		for(int i=0; i<43; i++){
			move();
		}
		moveRight();
		makeNextBasketball();
		for(int i=0; i<42; i++){
			move();
		}
		moveRight();
		makeNextBasketball();
		for(int i=0; i<41; i++){
			move();
		}
		moveRight();
		makeNextBasketball();
		for(int i=0; i<40; i++){
			move();
		}
		moveRight();
		move();
		makeNextBasketball();
		for(int i=0; i<39; i++){
			move();
		}
		moveRight();
		move();
		makeNextBasketball();
		for(int i=0; i<38; i++){
			move();
		}
		moveRight();
		move();
		makeNextBasketball();
		for(int i=0; i<37; i++){
			move();
		}
		moveRight();
		for(int i=0; i<2; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<36; i++){
			move();
		}
		moveRight();
		for(int i=0; i<2; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<35; i++){
			move();
		}
		moveRight();
		for(int i=0; i<3; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<34; i++){
			move();
		}
		moveRight();
		for(int i=0; i<4; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<33; i++){
			move();
		}
		moveRight();
		for(int i=0; i<4; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<32; i++){
			move();
		}
		moveRight();
		for(int i=0; i<5; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<31; i++){
			move();
		}
		moveRight();
		for(int i=0; i<6; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<30; i++){
			move();
		}
		moveRight();
		for(int i=0; i<6; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<29; i++){
			move();
		}
		moveRight();
		for(int i=0; i<7; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<28; i++){
			move();
		}
		moveRight();
		for(int i=0; i<8; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<27; i++){
			move();
		}
		moveRight();
		for(int i=0; i<8; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<26; i++){
			move();
		}
		moveRight();
		for(int i=0; i<9; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<25; i++){
			move();
		}
		moveRight();
		for(int i=0; i<10; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<24; i++){
			move();
		}
		moveRight();
		for(int i=0; i<10; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<23; i++){
			move();
		}
		moveRight();
		for(int i=0; i<11; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<22; i++){
			move();
		}
		moveRight();
		for(int i=0; i<12; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<21; i++){
			move();
		}
		moveRight();
		for(int i=0; i<12; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<20; i++){
			move();
		}
		moveRight();
		for(int i=0; i<13; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<19; i++){
			move();
		}
		moveRight();
		for(int i=0; i<14; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<18; i++){
			move();
		}
		moveRight();
		for(int i=0; i<14; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<17; i++){
			move();
		}
		moveRight();
		for(int i=0; i<15; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<16; i++){
			move();
		}
		moveRight();
		for(int i=0; i<16; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<15; i++){
			move();
		}
		moveRight();
		for(int i=0; i<16; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<14; i++){
			move();
		}
		moveRight();
		for(int i=0; i<17; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<13; i++){
			move();
		}
		moveRight();
		for(int i=0; i<18; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<12; i++){
			move();
		}
		moveRight();
		for(int i=0; i<18; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<11; i++){
			move();
		}
		moveRight();
		for(int i=0; i<19; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<10; i++){
			move();
		}
		moveRight();
		for(int i=0; i<20; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<10; i++){
			move();
		}
		moveRight();
		for(int i=0; i<21; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<11; i++){
			move();
		}
		moveRight();
		for(int i=0; i<22; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<12; i++){
			move();
		}
		moveRight();
		for(int i=0; i<22; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<13; i++){
			move();
		}
		moveRight();
		for(int i=0; i<23; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<14; i++){
			move();
		}
		moveRight();
		for(int i=0; i<24; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<15; i++){
			move();
		}
		moveRight();
		for(int i=0; i<24; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<16; i++){
			move();
		}
		moveRight();
		for(int i=0; i<25; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<17; i++){
			move();
		}
		moveRight();
		for(int i=0; i<26; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<18; i++){
			move();
		}
		moveRight();
		for(int i=0; i<26; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<18; i++){
			move();
		}
		moveRight();
		for(int i=0; i<27; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<19; i++){
			move();
		}
		moveRight();
		for(int i=0; i<28; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<20; i++){
			move();
		}
		moveRight();
		for(int i=0; i<28; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<21; i++){
			move();
		}
		moveRight();
		for(int i=0; i<29; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<22; i++){
			move();
		}
		moveRight();
		for(int i=0; i<30; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<23; i++){
			move();
		}
		moveRight();
		for(int i=0; i<31; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<22; i++){
			move();
		}
		moveRight();
		for(int i=0; i<32; i++){
			move();
		}
		makeNextBasketball();
		for(int i=0; i<21; i++){
			move();
		}
		moveRight();
		for(int i=0; i<32; i++){
			move();
		}
		makeNextBasketball();
		turnRight();
	}

	/*Makes a basketball and sets up for the next one to be made.
	 * Assumes Karel is facing a direction where there is at least room for an 11x11 
	 * basketball.
	 * Leaves Karel facing East in bottom left corner.
	 */
	private void makeNextBasketball(){
		makeBasketball();
		makeC();
		turnLeft();
	}

	/*Allow Karel to move right one space.
	 * Assumes Karel is facing in any direction with an unblocked space to the right of her.
	 * Leaves Karel in space to right of her.
	 */
	private void moveRight(){
		turnRight();
		move();
	}

	/*This method puts Karel back in the bottom left corner facing East.
	 * Assumes Karel is facing West anywhere in the World.
	 * Leaves Karel facing East in the bottom left corner.
	 */
	private void returnToHome(){
		turnAround();
		moveToWall();
		turnLeft();
		moveToWall();
		turnLeft();
	}

	/*This method lets Karel make a basketball shape on the screen.
	 * Assumes Karel is facing East with at least twelve rows below and to the east of her.
	 * Leaves Karel facing East in the bottom left corner.
	 */
	private void makeBasketball(){
		outsideRowOfBball();
		secondRowIn();
		thirdRowIn();
		for(int i=0; i<2; i++){
			commonMiddleRow();
		}
		basketballLine();
		commonMiddleRow();
		thirdRowIn();
		secondRowIn();
		outsideRowOfBball();
		returnToHome();
		for(int i=0; i<65000; i++){
			turnLeft();
		}
	}

	/*This method lets Karel make the second row in (on both sides)
	 *  of an eleven by eleven basketball.
	 * Assumes Karel is facing East with at least one row below her and twelve columns to the
	 * east.
	 * Leaves Karel facing East one position below where she started.
	 */
	private void secondRowIn(){
		for(int i=0; i<2; i++){
			move();
		}
		paintTwoCornersWhite();
		paintTwoCornersOrange();
		paintCorner(BLACK);
		move();
		paintTwoCornersOrange();
		paintTwoCornersWhite();
		turnAround();
		for (int i=0; i<11; i++){
			move();
		}
		advanceRowDown();
	}

	/*Makes a black line across the center of a baskeball.
	 * Assumes Karel is facing East with at least one row below her and twelve columns to 
	 * the east.
	 * Leaves Karel facing East one position below where she started.
	 */
	private void basketballLine(){
		move();
		paintCorner(WHITE);
		move();
		for(int i=0; i<9; i++){
			paintCorner(BLACK);
			move();
		}
		paintCorner(WHITE);
		turnAround();
		for(int i=0; i<11; i++){
			move();
		}
		advanceRowDown();
	}

	/*Makes the third line in (on both sides) of a basketball.
	 * Assumes Karel is facing East with at least one row below her and twelve columns to 
	 * the east.
	 * Leaves Karel facing East one position below where she started.
	 */
	private void thirdRowIn(){
		move();
		paintTwoCornersWhite();
		paintCorner(BLACK);
		move();
		paintTwoCornersOrange();
		paintCorner(BLACK);
		move();
		paintTwoCornersOrange();
		paintCorner(BLACK);
		move();
		paintCorner(WHITE);
		move();
		paintCorner(WHITE);
		turnAround();
		for(int i=0; i<11; i++){
			move();
		}
		advanceRowDown();
	}

	/*Makes the common row in general middle area of a basketball.
	 *  Assumes Karel is facing East with at least one row below her and twelve columns to 
	 * the east.
	 * Leaves Karel facing East one position below where she started.
	 */
	private void commonMiddleRow(){
		move();
		paintCorner(WHITE);
		move();
		paintTwoCornersOrange();
		paintCorner(BLACK);
		move();
		paintCorner(ORANGE);
		move();
		paintCorner(BLACK);
		move();
		paintCorner(ORANGE);
		move();
		paintCorner(BLACK);
		move();
		paintTwoCornersOrange();
		paintCorner(WHITE);
		turnAround();
		for(int i=0; i<11; i++){
			move();
		}
		advanceRowDown();
	}

	/*Makes the outside row of a baskeball in Karel's world.
	 * Assumes Karel is facing East with at least one row below her and twelve columns to 
	 * the east.
	 * Leaves Karel facing East one position below where she started.
	 */
	private void outsideRowOfBball(){
		for(int i=0; i<3; i++){
			move();
		}
		for(int i=0; i<7; i++){
			paintCorner(WHITE);
			move();
		}
		turnAround();
		for(int i=0; i<10; i++){
			move();
		}
		advanceRowDown();
	}

	/*Paints the next two spaces on the screen orange.
	 * Assumes Karel has at least two unblocked spaces in front of her.
	 * Leaves Karel facing same direction with painted spaces behind her.
	 */
	private void paintTwoCornersOrange(){
		for(int i=0; i<2; i++){
			paintCorner(ORANGE);
			move();
		}
	}

	/*Paints the next two spaces on the screen white.
	 * Assumes Karel has at least two unblocked spaces in front of her.
	 * Leaves Karel facing same direction with painted spaces behind her.
	 */
	private void paintTwoCornersWhite(){
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
	}

	/*Allows Karel to move down to the next row.
	 * Assumes Karel is facing West with at least one !blocked row below her.
	 * Leaves Karel on space below starting spot facing East.
	 */
	private void advanceRowDown(){
		turnLeft();
		move();
		turnLeft();
	}

	/*Makes a varsity style letter "C" on the screen with the colors of black, red, and white.
	 * Assumes Karel is facing East in the bottom left corner of a 45x45 world.
	 * Leaves Karel in same position.
	 */
	private void makeC(){
		makeBottomOfC();
		makeMiddleOfC();
		makeTopOfC();
		turnAround();
		moveToWall();
		turnLeft();
	}

	/*Allows Karel to change the varsity letter "C" mentioned above to the different colors
	 * of magenta, orange, and green.
	 * Assumes Karel is facing East in the bottom left corner of a 45x45 world.
	 * Leaves Karel in same position.
	 */
	private void cTwo(){
		for(int i=0; i<45; i++){
			while(frontIsClear()){
				if(cornerColorIs(BLACK)){
					paintCorner(MAGENTA);
				}
				if(cornerColorIs(WHITE)){
					paintCorner(ORANGE);
				}
				if(cornerColorIs(RED)){
					paintCorner(GREEN);
				}
				move();
			}	
			paintCorner(MAGENTA);
			turnAround();
			moveToWall();
			if(rightIsClear()){
				moveRight();
				turnRight();
			}else{
				turnLeft();
				moveToWall();
				turnLeft();
				for(int x=0; x<100000; x++){
					turnLeft();
				}
			}
		}
	}

	/*Allows Karel to change the varsity letter "C" mentioned above to the different colors
	 * of gray, cyan, and dark gray.
	 * Assumes Karel is facing East in the bottom left corner of a 45x45 world.
	 * Leaves Karel in same position.
	 */
	private void cThree(){
		for(int i=0; i<45; i++){
			while(frontIsClear()){
				if(cornerColorIs(BLACK)){
					paintCorner(GRAY);
				}
				if(cornerColorIs(WHITE)){
					paintCorner(CYAN);
				}
				if(cornerColorIs(RED)){
					paintCorner(DARK_GRAY);
				}
				move();
			}	
			paintCorner(GRAY);
			turnAround();
			moveToWall();
			if(rightIsClear()){
				moveRight();
				turnRight();
			}else{
				turnLeft();
				moveToWall();
				turnLeft();
				for(int x=0; x<100000; x++){
					turnLeft();
				}
			}
		}
	}

	/*Allows Karel to change the varsity letter "C" mentioned above to the different colors
	 * of yellow, orange, and blue.
	 * Assumes Karel is facing East in the bottom left corner of a 45x45 world.
	 * Leaves Karel in same position.
	 */
	private void cFour(){
		for(int i=0; i<45; i++){
			while(frontIsClear()){
				if(cornerColorIs(BLACK)){
					paintCorner(YELLOW);
				}
				if(cornerColorIs(WHITE)){
					paintCorner(ORANGE);
				}
				if(cornerColorIs(RED)){
					paintCorner(BLUE);
				}
				move();
			}	
			paintCorner(YELLOW);
			turnAround();
			moveToWall();
			if(rightIsClear()){
				moveRight();
				turnRight();
			}else{
				turnLeft();
				moveToWall();
				turnLeft();
				for(int x=0; x<100000; x++){
					turnLeft();
				}
			}
		}
	}

	/*Allows Karel to change the varsity letter "C" mentioned above to the different colors
	 * of yellow, black, and green.
	 * Assumes Karel is facing East in the bottom left corner of a 45x45 world.
	 * Leaves Karel in same position.
	 */
	private void cFive(){
		for(int i=0; i<45; i++){
			while(frontIsClear()){
				if(cornerColorIs(BLACK)){
					paintCorner(YELLOW);
				}
				if(cornerColorIs(WHITE)){
					paintCorner(BLACK);
				}
				if(cornerColorIs(RED)){
					paintCorner(GREEN);
				}
				move();
			}	
			paintCorner(YELLOW);
			turnAround();
			moveToWall();
			if(rightIsClear()){
				moveRight();
				turnRight();
			}else{
				turnLeft();
				moveToWall();
				turnLeft();
				for(int x=0; x<100000; x++){
					turnLeft();
				}
			}
		}
	}

	/*Allows Karel to change the varsity letter "C" mentioned above to the different colors
	 * of green, blue, and cyan.
	 * Assumes Karel is facing East in the bottom left corner of a 45x45 world.
	 * Leaves Karel in same position.
	 */
	private void cSix(){
		for(int i=0; i<45; i++){
			while(frontIsClear()){
				if(cornerColorIs(BLACK)){
					paintCorner(GREEN);
				}
				if(cornerColorIs(WHITE)){
					paintCorner(BLUE);
				}
				if(cornerColorIs(RED)){
					paintCorner(CYAN);
				}
				move();
			}	
			paintCorner(GREEN);
			turnAround();
			moveToWall();
			if(rightIsClear()){
				moveRight();
				turnRight();
			}else{
				turnLeft();
				moveToWall();
				turnLeft();
				for(int x=0; x<100000; x++){
					turnLeft();
				}
			}
		}
	}

	/*Makes the top section of a varsity style "C."
	 * Assumes Karel is facing East in a 45x45 world with at least 13 rows above her.
	 * Leaves Karel facing East at the right side of 13 rows higher from the starting
	 * position.
	 */
	private void makeTopOfC(){
		for(int i=0; i<12; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<17; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<11; i++){
			paintCorner(BLACK);
			move();
		}
		finishRowToNext();
		for(int i=0; i<13; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<15; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<12; i++){
			paintCorner(BLACK);
			move();
		}
		finishRowToNext();
		for(int i=0; i<14; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<13; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<13; i++){
			paintCorner(BLACK);
			move();
		}
		finishRowToNext();
		for(int i=0; i<15; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<11; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<14; i++){
			paintCorner(BLACK);
			move();
		}
		finishRowToNext();
		for(int i=0; i<16; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<9; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<15; i++){
			paintCorner(BLACK);
			move();
		}
		finishRowToNext();
		for(int i=0; i<17; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<11; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<16; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<7; i++){
			paintRow(BLACK);
		}
		paintCorner(BLACK);
	}

	/*Makes the bottom portion of a varsity style letter "C."
	 * Assumes Karel is the bottom left corner of a 45x45 facing East.
	 * Leaves Karel eleven rows up facing East on the left side.
	 */
	private void makeBottomOfC(){
		for(int i=0; i<6; i++){
			paintRow(BLACK);
		}
		for(int i=0; i<17; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<11; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<16; i++){
			paintCorner(BLACK);
			move();
		}
		finishRowToNext();
		for(int i=0; i<16; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<9; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<15; i++){
			paintCorner(BLACK);
			move();
		}
		finishRowToNext();
		for(int i=0; i<15; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<11; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<14; i++){
			paintCorner(BLACK);
			move();
		}
		finishRowToNext();
		for(int i=0; i<14; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<13; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<13; i++){
			paintCorner(BLACK);
			move();
		}
		finishRowToNext();
		for(int i=0; i<13; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<15; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<12; i++){
			paintCorner(BLACK);
			move();
		}
		finishRowToNext();
		for(int i=0; i<12; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<17; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<2; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<11; i++){
			paintCorner(BLACK);
			move();
		}
		finishRowToNext();
	}

	/*Makes Karel create the middle portion of a varsity letter "C."
	 * Assumes Karel is facing East on the left side of a 45x45 world with at least
	 * seventeen rows above her.
	 * Leaves Karel seventeen rows ahead of her in same position.
	 */
	private void makeMiddleOfC(){
		for(int i=0; i<3; i++){
			makeWideRow();
		}
		insideOuterRows();
		for(int i=0; i<3; i++){
			outsideMiddleRows();
		}
		insideMiddleRows();
		for(int i=0; i<5; i++){
			middleRows();
		}
		insideMiddleRows();
		for(int i=0; i<3; i++){
			outsideMiddleRows();
		}
		insideOuterRows();
		for(int i=0; i<3; i++){
			makeWideRow();
		}
	}

	/*Makes Karel make part of the middle of a varsity style letter "C."
	 * Assumes Karel is facing East on the left side of a 45x45 world with at least
	 * one row above her.
	 * Leaves Karel one row above starting position facing East.
	 */
	private void insideOuterRows(){
		for(int i=0; i<12; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<1; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<7; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<5; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<7; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<1; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<11; i++){
			paintCorner(BLACK);
			move();
		}
		finishRowToNext();
	}

	/*Makes Karel make part of the middle of a varsity style letter "C."
	 * Assumes Karel is facing East on the left side of a 45x45 world with at least
	 * one row above her.
	 * Leaves Karel one row above starting position facing East.
	 */
	private void insideMiddleRows(){
		for(int i=0; i<12; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<1; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<7; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<1; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<3; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<9; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<11; i++){
			paintCorner(BLACK);
			move();
		}
		finishRowToNext();
	}

	/*Makes Karel make part of the middle of a varsity style letter "C."
	 * Assumes Karel is facing East on the left side of a 45x45 world with at least
	 * one row above her.
	 * Leaves Karel one row above starting position facing East.
	 */
	private void middleRows(){
		for(int i=0; i<12; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<1; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<7; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<1; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<23; i++){
			paintCorner(BLACK);
			move();
		}
		finishRowToNext();
	}

	/*Makes Karel make part of the outside (both North and South) of a varsity style letter "C."
	 * Assumes Karel is facing East on the left side of a 45x45 world with at least
	 * one row above her.
	 * Leaves Karel one row above starting position facing East.
	 */
	private void outsideMiddleRows(){
		for(int i=0; i<12; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<1; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<7; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<1; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<3; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<1; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<7; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<1; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<11; i++){
			paintCorner(BLACK);
			move();
		}
		finishRowToNext();
	}

	/*Makes Karel make part of the outside (both North and South) of a varsity style letter "C."
	 * Assumes Karel is facing East on the left side of a 45x45 world with at least
	 * one row above her.
	 * Leaves Karel one row above starting position facing East.
	 */
	private void makeWideRow(){
		for(int i=0; i<12; i++){
			paintCorner(BLACK);
			move();
		}
		for(int i=0; i<1; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<19; i++){
			paintCorner(RED);
			move();
		}
		for(int i=0; i<1; i++){
			paintCorner(WHITE);
			move();
		}
		for(int i=0; i<11; i++){
			paintCorner(BLACK);
			move();
		}
		finishRowToNext();	
	}

	/*Allows Karel to finish a row with a black background and move to the next row.
	 * Assumes Karel is facing East on one null space with the front blocked and at
	 * least one row above her.
	 * Leaves Karel facing East on the left side of any size world on row above
	 * starting position.
	 */
	private void finishRowToNext(){
		paintCorner(BLACK);
		turnAround();
		moveToWall();
		advanceToNext();
	}

	/*Lets Karel paint a row one color of programmer's choice.
	 * Assumes Karel is facing East in any world.
	 * Leaves Karel facing East on the left side one row above starting position.
	 */
	private void paintRow(java.awt.Color cornerColor){
		while(frontIsClear()){
			paintCorner(cornerColor);
			move();
		}
		paintCorner(cornerColor);
		turnAround();
		moveToWall();
		advanceToNext();
	}

	/*Allows Karel to move to the wall she is facing and stop when the front is blocked.
	 * Assumes Karel is facing any direction anywhere in any size world.
	 * Leaves Karel facing wall.
	 */
	private void moveToWall() {
		while (frontIsClear()){
			move();
		}
	}

	/*Advances to the next row in Karel's world.
	 *Assumes Karel is facing West.
	 *Leaves Karel facing East one row above assumed position. 
	 */
	private void advanceToNext(){
		turnRight();
		if (frontIsClear()){
			move();
			turnRight();
		}
	}
	// Note: This is an unfortunate hack to correct a
	// shortfall in our new Eclipse plugin. Don't worry
	// about (you won't be tested on it and aren't expected
	// to understand what's going on). However, don't
	// delete it, or you won't be able to run your Karel
	// program.
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
