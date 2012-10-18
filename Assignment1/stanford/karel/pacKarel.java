/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

package stanford.karel;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * Name: Zachary Schaffer
 * This is PacKarel
 * The game is won by eating the dots
 * The arrow key move Karel and standard pacMan control type is used
 */

public class pacKarel extends SuperKarel implements KeyListener {
	public int d;
	//right arrow key
	public int w;
	//up arrow key
	public int a;
	//left arrow key
	public int s;
	//down arrow key
	public int b;
	//dots eaten
	public void run(){
		getWorld().addKeyListener(this);
		while(true){
			if(a>1){
				faceWest();
				eatToWall();
			}
			else if(d>1){
				faceEast();
				eatToWall();
			}else if(w>1){
				faceNorth();
				eatToWall();
			}else if(s>1){
				faceSouth();
				eatToWall();
			}
			if(b>338){
				paintCorner(Color.WHITE);
				break;
			}
		}
		smile();
	}
	//Makes a smily face
	private void smile(){
		setLocation(13,16);
		putBeeper();
		setLocation(15,16);
		putBeeper();
		setLocation(12,14);
		putBeeper();
		setLocation(13,13);
		putBeeper();
		setLocation(14,13);
		putBeeper();
		setLocation(15,13);
		putBeeper();
		setLocation(16,14);
		putBeeper();
		setLocation(1,1);
	}
	//moves to wall eating dots
	private void eatToWall(){
		if(frontIsClear()){
			if(cornerColorIs(null)){
				b++;
				System.out.println(b);	
			}
			paintCorner(Color.WHITE);
			grabBeeper();
			move();
		}
	}
	//picks up a beeper if a beeper is present
	private void grabBeeper(){
		if(beepersPresent()){
			pickBeeper();
		}
	}
	//makes pacKarel face west
	private void faceWest(){
		while(facingSouth()){
			if(rightIsClear()){
				turnRight();
			}
			else if(frontIsClear()){
				eatToWall();
			}
			else{
				turnLeft();
			}
		}
		while(facingEast()){
			turnAround();
		}
		while(facingNorth()){
			if(leftIsClear()){
				turnLeft();
			}
			else if(frontIsClear()){
				eatToWall();
			}
			else{
				turnLeft();
			}
		}
	}
	//makes pacKarel face East
	private void faceEast(){
		while(facingSouth()){
			if(leftIsClear()){
				turnLeft();
			}
			else if(frontIsClear()){
				eatToWall();
			}
			else{
				turnLeft();
			}
		}
		while(facingNorth()){
			if(rightIsClear()){
				turnRight();
			}
			else if(frontIsClear()){
				eatToWall();
			}
			else{
				turnLeft();
			}
		}
		while(facingWest()){
			turnAround();
		}
	}
	//makes pacKarel face North
	private void faceNorth(){
		if(facingSouth()){
			turnAround();
		}
		while(facingEast()){
			if(leftIsClear()){
				turnLeft();
			}
			else if(frontIsClear()){
				eatToWall();
			}
			else{
				turnLeft();
			}
		}
		while(facingWest()){
			if(rightIsClear()){
				turnRight();
			}
			else if(frontIsClear()){
				eatToWall();
			}
			else{
				turnLeft();
			}
		}
	}
	//makes pacKarel face South
	private void faceSouth(){
		if(facingNorth()){
			turnAround();
		}
		while(facingEast()){
			if(rightIsClear()){
				turnRight();
			}
			else if(frontIsClear()){
				eatToWall();
			}
			else{
				turnLeft();
			}
		}
		while(facingWest()){
			if(leftIsClear()){
				turnLeft();
			}
			else if(frontIsClear()){
				eatToWall();
			}
			else{
				turnLeft();
			}
		}
	}
	
	public void keyPressed(KeyEvent e) {
		//Responds to the up key being pressed
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			w=5;
			a=0;
			s=0;
			d=0;
		}
		//Responds to the left key being pressed
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			w=0;
			a=5;
			s=0;
			d=0;
		}
		//Responds to the down key being pressed
		else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			w=0;
			a=0;
			s=5;
			d=0;
		}
		//Responds to the right key being pressed
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			w=0;
			a=0;
			s=0;
			d=5;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}