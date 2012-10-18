/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

package contest2010;

import stanford.karel.*;

public class CourtneyKarelCompetition extends SuperKarel {

	// You fill in this part
	public void run () {
		letterO();
		turnLeft();
		move();
		letterI();
		move();
		letterO();
		turnRight();
		turnRight();
		turnLeft();
		clearFirstRow();
		turnRight();
		move();
		turnRight();
		repairSecondRow();
		for (int i=0;i<3;i++){
			turnLeft();
		}
		turnRight();
		for(int i=0;i<4;i++){
			move();
		}
		letterS();
		turnAround();
		for (int i=0;i<3;i++){
			move();
		}
		turnLeft();
		move();
		turnRight();
		turnAround();
		letterZVertical();
		turnRight();
		while(frontIsClear()){
			move();
		}
		turnRight();
		move();
		turnRight();
		letterJOpposite();
		turnRight();
		move();
		turnRight();
		letterT();
		letterSVertical();
		turnLeft();
		for(int i=0;i<4;i++){
			move();
		}
		turnLeft();
		for(int i=0;i<6;i++){
			move();
		}
		turnRight();
		turnRight();
		move();
		turnLeft();
		move();
		letterLSitting();
		turnRight();
		move();
		letterI();
		secondClear();
		repairFromSecondClear();
		turnLeft();
		move();
		turnLeft();
		move();
		move();
		turnRight();
		letterLSitting();
		turnLeft();
		move();
		turnLeft();
		letterIStanding();
		turnRight();
		move();
		move();
		turnRight();
		letterSSecondVertical();
		turnRight();
		move();
		turnAround();
		turnRight();
		letterTDown();
		turnLeft();
		move();
		turnLeft();
		turnRight();
		endingBlock();
		failSign();
	}
	private void failSign(){
		turnAround();
		turnRight();
		for(int i=0;i<2;i++){
			paintCorner(BLACK);
			move();
		}
		paintCorner(BLACK);
		turnLeft();
		for(int i=0;i<3;i++){
			paintCorner(BLACK);
			move();
		}
		paintCorner(BLACK);
		turnAround();
		move();
		turnRight();
		for(int i=0;i<2;i++){
			move();
			paintCorner(BLACK);
		}
		move();
		turnRight();
		move();
		turnLeft();
		move();
		paintCorner(BLACK);
		turnLeft();
		for(int i=0;i<2;i++){
			move();
			paintCorner(BLACK);
		}
		move();
		paintCorner(BLACK);
		turnRight();
		for(int i=0;i<2;i++){
			move();
			paintCorner(BLACK);
		}
		turnRight();
		for(int i=0;i<3;i++){
			move();
			paintCorner(BLACK);
		}
		turnAround();
		move();
		turnLeft();
		move();
		paintCorner(BLACK);
		for(int i=0;i<2;i++){
			move();
			move();
			turnLeft();
		}
		turnAround();
		move();
		turnLeft();
		for(int i=0;i<3;i++){
			paintCorner(BLACK);
			move();
		}
		paintCorner(BLACK);
		turnLeft();
		for(int i=0;i<3;i++){
			move();
		}
		turnLeft();
		for(int i=0;i<3;i++){
			paintCorner(BLACK);
			move();
		}
		paintCorner(BLACK);
		turnAround();
		for(int i=0;i<3;i++){
			move();
		}
		turnLeft();
		for(int i=0;i<2;i++){
			move();
			paintCorner(BLACK);
		}
	}
	private void endingBlock(){
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
	}
	private void letterTDown(){
		paintCorner(MAGENTA);
		move();
		turnLeft();
		move();
		paintCorner(MAGENTA);
		turnAround();
		move();
		paintCorner(MAGENTA);
		move();
		paintCorner(MAGENTA);
	}
	private void letterSSecondVertical(){
		paintCorner(GREEN);
		move();
		paintCorner(GREEN);
		turnLeft();
		move();
		paintCorner(GREEN);
		turnRight();
		move();
		paintCorner(GREEN);
	}
	private void letterIStanding(){
		for (int i=0;i<3;i++){
			paintCorner(CYAN);
			move();
		}
		paintCorner(CYAN);
	}
	private void secondClear(){
		paintCorner(null);
		for (int i=0;i<3;i++){
			move();
			paintCorner(null);
		}
		turnAround();
		for(int i=0;i<3;i++){
			move();
		}
		paintCorner(null);
		for(int i=0;i<4;i++){
			move();
			paintCorner(null);
		}
	}
	private void repairFromSecondClear(){
		turnRight();
		move();
		turnRight();
		for (int i=0;i<5;i++){
			move();
		}
		for(int i=0;i<2;i++){
			move();
			paintCorner(null);
		}
		for(int i=0;i<2;i++){
			turnLeft();
			move();
			paintCorner(null);
		}
		turnRight();
		move();
		paintCorner(null);
		turnRight();
		turnRight();
		for(int i=0;i<3;i++){
			move();
		}
		turnLeft();
		paintCorner(MAGENTA);
		move();
		paintCorner(GREEN);
		turnLeft();
		move();
		paintCorner(GREEN);
		turnLeft();
		move();
		paintCorner(GREEN);
		turnRight();
		move();
		paintCorner(GREEN);
	}
	private void repairSecondRow(){
		for (int i=0;i<2;i++){
			paintCorner (YELLOW);
			move();
		}
		for (int i=0;i<4;i++){
			move();
		}
		paintCorner (YELLOW);
		move();
		paintCorner (YELLOW);
	}
	private void clearFirstRow(){

		for (int i=0;i<7;i++){
			paintCorner (null);
			move();
		}
		paintCorner (null);
		turnAround();
		turnLeft();
		move();
		turnRight();
		while(frontIsClear()){
			paintCorner (null);
			move();
		}
		paintCorner (null);
	}
	private void letterO(){
		paintCorner(YELLOW);
		turnLeft();
		move();
		for (int i=0;i<2;i++){
			paintCorner(YELLOW);
			turnRight();
			move();
		}
		paintCorner(YELLOW);
	}
	private void letterI(){
		for (int i=0;i<3;i++){
			paintCorner(CYAN);
			move();
		}
		paintCorner(CYAN);
	}
	private void letterJ(){
		for (int i=0;i<2;i++){
			paintCorner(BLUE);
			move();
		}
		paintCorner(BLUE);
		turnLeft();
		move();
		paintCorner(BLUE);
	}
	private void letterS(){
		paintCorner(GREEN);
		move();
		paintCorner(GREEN);
		turnLeft();
		move();
		paintCorner(GREEN);
		turnRight();
		move();
		paintCorner(GREEN);
	}
	private void letterT(){
		paintCorner(MAGENTA);
		move();
		paintCorner(MAGENTA);
		turnLeft();
		move();
		paintCorner(MAGENTA);
		turnRight();
		move();
		turnRight();
		move();
		paintCorner(MAGENTA);
		turnLeft();
	}
	private void letterZ(){
		turnLeft();
		move();
		paintCorner(RED);
		turnRight();
		move();
		paintCorner(RED);
		turnRight();
		move();
		paintCorner(RED);
		turnLeft();
		move();
		paintCorner(RED);
	}
	private void letterL(){
		paintCorner(ORANGE);
		turnLeft();
		move();
		paintCorner(ORANGE);
		turnRight();
		move();
		turnRight();
		move();
		turnLeft();
		paintCorner(ORANGE);
		move();
		paintCorner(ORANGE);
	}
	private void letterZVertical(){
		paintCorner(RED);
		turnLeft();
		move();
		paintCorner(RED);
		turnRight();
		move();
		paintCorner(RED);
		turnLeft();
		move();
		paintCorner(RED);
	}
	private void letterJOpposite (){
		paintCorner(BLUE);
		turnRight();
		move();
		paintCorner(BLUE);
		turnLeft();
		move();
		paintCorner(BLUE);
		move();
		paintCorner(BLUE);
	} 
	private void letterSVertical(){
		turnLeft();
		move();
		paintCorner(GREEN);
		move();
		paintCorner(GREEN);
		turnLeft();
		move();
		paintCorner(GREEN);
		turnRight();
		move();
		paintCorner(GREEN);
	}
	private void letterLSitting(){
		paintCorner(ORANGE);
		move();
		paintCorner(ORANGE);
		turnRight();
		move();
		paintCorner(ORANGE);
		move();
		paintCorner(ORANGE);
	}
}




