package stanford.karel;

import stanford.karel.SuperKarel;
import java.awt.*;
import java.awt.event.*;


public class CortlandtContest extends SuperKarel implements KeyListener {

	private boolean snakeLeft;
	private boolean snakeRight;
	private boolean snakeForward;
	private boolean snakeDown;

	public void run() {

		getWorld().addKeyListener(this);
		for (int i=0; i<5; i++){
			takeStep();
		}

		while(true) 
			{
			if(snakeLeft) {
				snakeLeft = false;
				if (facingWest()) {
					moveOneSpace();
				}
				if (facingNorth()) {	
					snakeTurnLeft();
				}
				if (facingSouth()){
					snakeTurnRight();
				}
			}else if (snakeRight) {
				snakeRight = false;
				if (facingNorth()){
					snakeTurnRight();
				}
				if (facingSouth()){
					snakeTurnLeft();
				}
				if (facingEast()) {
					moveOneSpace();
				}
			}else if (snakeForward) {
				snakeForward = false;
				if (facingNorth()) {
					moveOneSpace();
				}
				if (facingWest()) {
					snakeTurnRight();
				}
				if  (facingEast()) {
					snakeTurnLeft();
				}
			}else if (snakeDown) {
				snakeDown = false;
				if (facingEast()){
					snakeTurnRight();
				}
				if (facingWest()) {
					snakeTurnLeft();
				}
				if (facingSouth()) {
					moveOneSpace();
				}
			}else moveOneSpace();}
		}
	




/* The beginning and end codes of making  move left*/
	private void snakeTurnLeft() {
		turnLeft();
		while (beepersPresent()){
			karelLeft();}
		move();
		move();
		while (beepersPresent()) {
			move();
		}
		moveBack();

	}

	/* The middle part of the code that makes the snake turn left, 
	 * it takes off a beeper at the end of the snake and puts one 
	 * down at the front so the snake can move one space. */
	private void karelLeft() {
		move();
		putBeeper();
		turnAround();
		move();
		while (beepersPresent()) {
			move();}
		turnAround();
		move();
		turnLeft();
		while (beepersPresent()) {
			move();}
		turnAround();
		move();
		pickBeeper();
		move();
		while (beepersPresent()) {
			move();}
		turnAround();
		move();
		turnRight();
		while (beepersPresent()) {
			move();}

		moveBack();

	}

	/* The beginning and end codes of making the snake turn right*/
	private void snakeTurnRight() {
		turnRight();
		while (beepersPresent()){
			karelRight();}
		move();
		move();
		while (beepersPresent()) {
			move();
		}
		moveBack();

	}
/* The middle part of the code that makes the snake turn right, 
 * it takes off a beeper at the end of the snake and puts one 
 * down at the front so the snake can move one space. */
 
	private void karelRight() {
		move();
		putBeeper();
		turnAround();
		move();
		while (beepersPresent()) {
			move();}
		turnAround();
		move();
		turnRight();
		while (beepersPresent()) {
			move();}
		turnAround();
		move();
		pickBeeper();
		move();
		while (beepersPresent()) {
			move();}
		turnAround();
		move();
		turnLeft();
		while (beepersPresent()) {
			move();}

		moveBack();
	}


	/* Makes Karel move the beeper snake one step forward
	 * by taking off a beeper at the back of the snake and putting one down at the front. */
	private void moveOneSpace() {
		move();
		putBeeper();
		turnAround();
		while (beepersPresent()){
			move();}
		turnAround();
		move();
		pickBeeper();
		move();
		while (beepersPresent()){
			move();}
		moveBack();


	}
	/* Karel moves and puts down a beeper.*/
	private void takeStep() {
		move();
		putBeeper();
	}





	/*moves Karel To Wall*/
	private void moveToWall() {
		while (frontIsClear()){
			move();

		}

	}

	/*moves Karel back one space*/
	private void moveBack() {
		turnAround();
		move();
		turnAround();


	}

	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			snakeLeft = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			snakeRight = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP){
			snakeForward = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN){
			snakeDown = true;
		}

	}
	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}


