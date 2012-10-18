import java.awt.*;

import stanford.karel.*;

/* nicki liang */
public class karelcontest2 extends SuperKarel {
	public void run(){
		screenTurnOn();
		
	}
	private void screenTurnOn(){
		fillScreen(BLACK);
		moveToMiddle();
		spiralOut();
		//	loadScreenSaver();
	}
	private void fillScreen(Color cornerColor){
		/*leaves karol facing north*/
		while(frontIsClear()){
			paintRow(cornerColor);
			moveToNextRow();
		}

	}
	private void paintRow(Color cornerColor){
		while(frontIsClear()){
			paintCorner(cornerColor);
			move();
		}
		paintCorner(cornerColor);
	}
	private void moveToNextRow(){
		turnAround();
		moveToWall();
		turnRight();
		if(frontIsClear()){
			move();
			turnRight();
		}
	}
	private void moveToWall(){
		while(frontIsClear()){
			move();
		}
	}
	private void moveToMiddle(){
		/*leaves karol facing south*/
		turnRight();
		for(int i=0; i<25; i++){
			move();
		}
		turnRight();
		for(int i=0; i<25; i++){
			move();
		}
	}
	public void spiralOut(){
		spiral();
		moveToMiddle();
		//		macApple();
	}
	public void spiral(){
		paintCorner(WHITE);
		turnAround();
		for(int i=0; i<2; i++){
			tripleStep();
		}
		move();
		paintCorner(WHITE);
		tripleStep();		
		stepOne();


	}
	public void tripleStep(){
		move();
		paintCorner(WHITE);
		turnRight();
	}
	public void stepOne(){

	}

}