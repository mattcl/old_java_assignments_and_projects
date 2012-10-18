package contest2010;

import stanford.karel.*;

/* GAME OF LIFE
 * =============
 * Henry Swanson
 */

public class Karel_Challenge extends SuperKarel{

	private static boolean[] neighbors = new boolean[9]; //in order: upLeft, upCenter, upRight, cenLeft, cenCenter, cenRight, lowLeft, lowCenter, lowRight

	public void run(){
		workOnWorld(1);
		createBorder();
		while(true){
			workOnWorld(2);
			workOnWorld(3);
			stall();
		}
	}

	private void workOnWorld(int operation){
		while(rightIsClear()){
			workOnRow(operation);
			turnLeft();
			move();
			turnLeft();
		}
		turnLeft();
		moveToWall();
		turnRight();
	}

	private void workOnRow(int operation){
		if(operation == 2 && leftIsClear()){ //resets all neighbors to false at beginning of row
			for(int i=0;i<9;i++){
				neighbors[i]=false;
			}
			scanNextRow();
		}
		while(frontIsClear()){
			workOnSquare(operation);
		}
		turnAround();
		moveToWall();
	}

	private void workOnSquare(int operation) {
		if(operation==1){
			paintCorner(WHITE);
		} else if(operation==2 && !cornerColorIs(BLACK)){
			examineCell();
		} else if (operation==3){
			if(beepersPresent()){
				pickBeeper();
			} else if(cornerColorIs(null)){
				putBeeper();
				paintCorner(WHITE);
			}
		}
		move();
	}

	private void examineCell(){
		for(int i=0;i<9;i++){ //shifts the known neighbors as karel moves
			if(i%3!=2){ //ignores the right most neighbors
				neighbors[i] = neighbors[i+1];
			}
		}
		scanNextRow();
		byte count = 0;
		for(int i=0;i<9;i++){ //counts number of neighbors
			if(neighbors[i]) count++;
		}
		if(beepersPresent() && (count==3 || count==4)){ //has to include the center beeper. so not 2+3 to live, but 3+4
			putBeeper();
		}else if(noBeepersPresent() && count==3){
			paintCorner(null);
		}
	}

	private void scanNextRow(){
		move();
		neighbors[5] = beepersPresent();
		turnLeft();
		move();
		neighbors[2] = beepersPresent();
		turnAround();
		move();
		move();
		neighbors[8] = beepersPresent();
		turnRight();
		move();
		turnRight();
		move();
		turnRight();
	}

	private void createBorder(){
		while(!cornerColorIs(BLACK)){
			paintCorner(BLACK);
			if(frontIsBlocked()){
				turnRight();
			}
			move();
		}
		turnRight();
	}

	private void moveToWall(){
		while(frontIsClear()){
			move();
		}
	}

	private void stall(){
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) { //try, catch, and e.printStackTrace eclipse suggested for me, and it seems to work
			e.printStackTrace();
		}
	}
}