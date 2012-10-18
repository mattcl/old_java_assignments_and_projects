package contest2010;

import java.awt.*;

import stanford.karel.*;

/* nicki liang */
public class FIFAkarel extends SuperKarel {
	public void run(){
		screenTurnOn();
		spiralOut();
		getBack();
		paintRow(GREEN);
		moveToNextRow();
		secondRow();
		thirdRow();
		fourthRow();
		fifthRow();
		sixthRow();
		Rseven();
		Reight();
		Rnine();
		Rten();
		Releven();
		Rtwelve();
		Rthirteen();
		Rfourteen();
		Rfifteen();
		Rsixteen();
		Rseventeen();
		Reighteen();
		Rnineteen();
		Rtwenty();
		RtwentyOne();
		RtwentyTwo();
		RtwentyThree();
		RtwentyFour();
		RtwentyFive();
		RtwentySix();
		RtwentySeven();
		RtwentyEight();
		RtwentyNine();
		Rthirty();
		RthirtyOne();
		timer();
		changeColor();
		setUp();
		spiralOut();
	}

	private void screenTurnOn(){
		fillScreen();
	}
	private void spiralOut(){
		turnAround();
		for(int i=0; i<3; i++){
			paintRow(BLACK);
			turnLeft();
		}
		paintRow(BLACK);
		for(int i=0; i<43; i++){
			turnLeft();
			move();
			turnLeft();
			while (cornerColorIs(BLACK)){
				move();
			}
			turnRight();
			paintRow(BLACK);
		}
	}
	private void setUp(){
	turnAround();
	moveToWall();
	turnRight();
}
	private void getBack(){
		turnLeft();
		moveToWall();
		turnLeft();
		moveToWall();
		turnLeft();
	}
	private void fillScreen(){
		while(frontIsClear()){
			if(random(.083)){
				paintCorner(BLUE);
			}else if (random(.09)){
				paintCorner(CYAN);
			}else if(random(.1)){
				paintCorner(DARK_GRAY);
			}else if(random(.11)){
				paintCorner(GRAY);
			}else if(random(.125)){
				paintCorner(GREEN);
			}else if(random(.1428)){
				paintCorner(LIGHT_GRAY);
			}else if(random(.166)){
				paintCorner(MAGENTA);
			}else if(random(.2)){
				paintCorner(ORANGE);
			}else if(random(.25)){
				paintCorner(PINK);
			}else if(random(.33)){
				paintCorner(RED);
			}else if(random(.5)){
				paintCorner(WHITE);
			}else if(random(1)){
				paintCorner(YELLOW);
			}
			if(frontIsClear()){
				move();
			}
			if(frontIsBlocked()){
				if(random(.083)){
					paintCorner(BLUE);
				}else if (random(.09)){
					paintCorner(CYAN);
				}else if(random(.1)){
					paintCorner(DARK_GRAY);
				}else if(random(.11)){
					paintCorner(GRAY);
				}else if(random(.125)){
					paintCorner(GREEN);
				}else if(random(.1428)){
					paintCorner(LIGHT_GRAY);
				}else if(random(.166)){
					paintCorner(MAGENTA);
				}else if(random(.2)){
					paintCorner(ORANGE);
				}else if(random(.25)){
					paintCorner(PINK);
				}else if(random(.33)){
					paintCorner(RED);
				}else if(random(.5)){
					paintCorner(WHITE);
				}else if(random(1)){
					paintCorner(YELLOW);
				}
				moveToNextRow();
			}
		}
	}
	private void pNc(Color Cornercolor, int corners){
		for(int i=0; i<corners; i++){
			paintCorner(Cornercolor);
			if(frontIsClear()){
				move();
			}
		}
	}
	private void paintRow(Color Cornercolor){
		while(frontIsClear()){
			paintCorner(Cornercolor);
			if(frontIsClear()){
				move();
			}
		}
		paintCorner(Cornercolor);
	}
	private void moveToNextRow(){
		turnAround();
		while(frontIsClear()){
			move();
		}
		turnRight();
		if(frontIsClear()){
			move();
			turnRight();
		}
	}
	private void paintCcorner(Color Cornercolor){
		paintCorner(Cornercolor);
		if(frontIsClear()){
			move();
		}
	}
	private void tripple(){
		paintCcorner(WHITE);
		paintCcorner(BLUE);
		paintCcorner(WHITE);
	}	
	private void trippple(){
		paintCcorner(RED);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(RED);
	}
	private void fsteps(){
		pNc(WHITE,2);
		paintCcorner(YELLOW);
		pNc(WHITE,2);
	}
	private void moveToWall(){
		while(frontIsClear()){
			move();
		}
	}	
	private void secondRow(){
		pNc(BLUE,10);
		paintCcorner(WHITE);
		pNc(BLUE,3);
		tripple();
		pNc(BLUE,3);
		tripple();
		paintCcorner(BLUE);
		moveToNextRow();
	}
	private void daBloc(){
		pNc(ORANGE,2);
		pNc(BLACK,2);
	}
	private void thirdRow(){
		pNc(BLUE,10);
		pNc(WHITE,2);
		pNc(BLUE,2);
		tripple();
		paintCcorner(WHITE);
		pNc(BLUE,2);
		pNc(WHITE,3);
		paintCcorner(BLUE);
		moveToNextRow();
	}
	private void fourthRow(){
		paintCcorner(LIGHT_GRAY);
		pNc(YELLOW,2);
		fsteps();
		pNc(BLUE,2);
		paintCcorner(WHITE);
		pNc(BLUE,3);
		tripple();
		pNc(BLUE,3);
		tripple();
		paintCcorner(BLUE);
		moveToNextRow();
	}
	private void fifthRow(){
		paintCcorner(LIGHT_GRAY);
		paintCcorner(YELLOW);
		paintCcorner(LIGHT_GRAY);
		fsteps();
		paintCcorner(LIGHT_GRAY);
		paintCcorner(BLUE);
		pNc(WHITE,2);
		tripple();
		paintCcorner(BLUE);
		pNc(WHITE,2);
		tripple();
		pNc(WHITE,2);
		paintCcorner(BLUE);
		moveToNextRow();
	}
	private void sixthRow(){
		pNc(YELLOW,2);
		paintCcorner(LIGHT_GRAY);
		fsteps();
		pNc(LIGHT_GRAY,2);
		paintRow(BLUE);
		moveToNextRow();
	}
	private void Rseven(){
		pNc(LIGHT_GRAY,3);
		trippple();
		pNc(LIGHT_GRAY,6);
		paintRow(BLUE);
		moveToNextRow();
	}
	private void Reight(){
		pNc(DARK_GRAY,3);
		pNc(RED,3);
		pNc(BLACK,2);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(GREEN);
		pNc(LIGHT_GRAY,3);
		paintRow(BLUE);
		moveToNextRow();
	}
	private void Rnine(){
		paintCcorner(LIGHT_GRAY);
		pNc(DARK_GRAY,2);
		trippple();
		pNc(BLACK,2);
		paintCcorner(LIGHT_GRAY);
		pNc(GREEN,2);
		pNc(LIGHT_GRAY,3);
		paintRow(BLUE);
		moveToNextRow();
	}
	private void Rten(){
		pNc(DARK_GRAY,3);
		pNc(RED,3);
		pNc(BLACK,2);
		pNc(LIGHT_GRAY,2);
		paintCcorner(GREEN);
		pNc(LIGHT_GRAY,2);
		paintCcorner(CYAN);
		pNc(LIGHT_GRAY,2);
		paintRow(BLUE);
		moveToNextRow();
	}
	private void Releven(){
		pNc(DARK_GRAY,2);
		pNc(LIGHT_GRAY,5);
		pNc(BLACK,2);
		paintCcorner(LIGHT_GRAY);
		pNc(GREEN,2);
		paintCcorner(LIGHT_GRAY);
		pNc(CYAN,2);
		pNc(LIGHT_GRAY,2);
		paintRow(BLUE);
		moveToNextRow();
	}
	private void Rtwelve(){
		pNc(DARK_GRAY,3);
		pNc(LIGHT_GRAY,4);
		pNc(BLACK,2);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(LIGHT_GRAY);
		pNc(GREEN,2);
		paintCcorner(LIGHT_GRAY);
		pNc(CYAN,2);
		pNc(LIGHT_GRAY,2);
		paintRow(BLUE);
		moveToNextRow();	
	}
	private void Rthirteen(){
		pNc(LIGHT_GRAY,4);
		pNc(ORANGE,4);
		pNc(BLACK,2);
		paintCcorner(RED);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(GREEN);
		pNc(LIGHT_GRAY,2);
		pNc(CYAN,2);
		pNc(LIGHT_GRAY,3);
		paintRow(BLUE);
		moveToNextRow();
	}
	private void Rfourteen(){
		pNc(LIGHT_GRAY,2);
		pNc(ORANGE,3);
		paintCcorner(BLACK);
		pNc(ORANGE,2);
		pNc(BLACK,2);
		pNc(RED,2);
		pNc(GREEN,2);
		pNc(LIGHT_GRAY,2);
		pNc(CYAN,2);
		pNc(LIGHT_GRAY,3);
		paintRow(BLUE);
		moveToNextRow();
	}
	private void Rfifteen(){
		paintCcorner(LIGHT_GRAY);
		paintCcorner(ORANGE);
		pNc(BLACK,4);
		pNc(ORANGE,3);
		pNc(BLACK,2);
		paintCcorner(RED);
		paintCcorner(ORANGE);
		pNc(GREEN,2);
		pNc(LIGHT_GRAY,2);
		pNc(CYAN,3);
		pNc(LIGHT_GRAY,2);
		pNc(BLUE,2);
		moveToNextRow();	
	}
	private void Rsixteen(){
		daBloc();
		pNc(ORANGE,2);
		pNc(BLACK,5);
		paintCcorner(RED);
		paintCcorner(ORANGE);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(GREEN);
		pNc(LIGHT_GRAY,2);
		pNc(CYAN,4);
		pNc(LIGHT_GRAY,2);
		pNc(BLUE,2);
		moveToNextRow();
	}
	private void Rseventeen(){
		daBloc();
		pNc(ORANGE,2);
		pNc(BLACK,6);
		paintCcorner(ORANGE);
		pNc(LIGHT_GRAY,6);
		pNc(CYAN,4);
		paintCcorner(LIGHT_GRAY);
		moveToNextRow();
	}
	private void Reighteen(){
		daBloc();
		paintCcorner(ORANGE);
		pNc(BLACK,4);
		pNc(RED,2);
		pNc(ORANGE,2);
		pNc(LIGHT_GRAY,2);
		paintCcorner(DARK_GRAY);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(YELLOW);
		paintCcorner(LIGHT_GRAY);
		pNc(CYAN,4);
		paintCcorner(LIGHT_GRAY);
		moveToNextRow();
	}
	private void Rnineteen(){
		paintCcorner(LIGHT_GRAY);
		paintCcorner(ORANGE);
		pNc(BLACK,7);
		paintCcorner(RED);
		pNc(ORANGE,2);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(YELLOW);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(DARK_GRAY);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(YELLOW);
		pNc(LIGHT_GRAY,2);
		pNc(CYAN,3);
		paintCcorner(LIGHT_GRAY);
		moveToNextRow();
	}
	private void Rtwenty(){
		pNc(BLACK,10);
		pNc(ORANGE,2);
		pNc(LIGHT_GRAY,2);
		paintCcorner(YELLOW);
		paintCcorner(LIGHT_GRAY);
		pNc(DARK_GRAY,2);
		paintCcorner(YELLOW);
		paintCcorner(LIGHT_GRAY);
		pNc(CYAN,3);
		paintCcorner(LIGHT_GRAY);
	}
	private void RtwentyOne(){
		pNc(BLACK,5);
		pNc(ORANGE,2);
		pNc(RED,2);
		pNc(BLACK,2);
		paintCcorner(ORANGE);
		pNc(LIGHT_GRAY,2);
		paintCcorner(YELLOW);
		paintCcorner(DARK_GRAY);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(YELLOW);
		pNc(LIGHT_GRAY,3);
		pNc(CYAN,2);
		paintCcorner(LIGHT_GRAY);
		moveToNextRow();
	}
	private void RtwentyTwo(){
		pNc(BLACK,6);
		pNc(RED,2);
		pNc(BLACK,4);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(YELLOW);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(DARK_GRAY);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(YELLOW);
		pNc(LIGHT_GRAY,3);
		pNc(CYAN,2);
		paintCcorner(LIGHT_GRAY);
		moveToNextRow();
	}
	private void RtwentyThree(){
		pNc(BLACK,3);
		pNc(ORANGE,2);
		pNc(BLACK,2);
		paintCcorner(RED);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(ORANGE);
		pNc(BLACK,3);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(DARK_GRAY);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(YELLOW);
		pNc(LIGHT_GRAY,5);
		paintCcorner(CYAN);
		paintCcorner(LIGHT_GRAY);
		moveToNextRow();
	}
	private void RtwentyFour(){
		paintCcorner(GREEN);
		paintCcorner(LIGHT_GRAY);
		pNc(ORANGE,3);
		paintCcorner(RED);
		pNc(BLACK,2);
		paintCcorner(LIGHT_GRAY);
		pNc(ORANGE,2);
		pNc(BLACK,3);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(YELLOW);
		pNc(LIGHT_GRAY,3);
		pNc(BLUE,2);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(CYAN);
		paintCcorner(LIGHT_GRAY);
		moveToNextRow();
	}
	private void RtwentyFive(){
		pNc(GREEN,2);
		paintCcorner(LIGHT_GRAY);
		pNc(ORANGE,2);
		pNc(RED,2);
		pNc(BLACK,2);
		paintCcorner(LIGHT_GRAY);
		pNc(YELLOW,2);
		pNc(BLACK,3);
		pNc(LIGHT_GRAY,3);
		paintCcorner(BLUE);
		pNc(LIGHT_GRAY,2);
		paintCcorner(BLUE);
		pNc(LIGHT_GRAY,2);
		moveToNextRow();
	}
	private void RtwentySix(){
		paintCcorner(LIGHT_GRAY);
		pNc(GREEN,2);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(ORANGE);
		pNc(RED,2);
		pNc(LIGHT_GRAY,3);
		pNc(DARK_GRAY,2);
		paintCcorner(YELLOW);
		pNc(BLACK,2);
		pNc(LIGHT_GRAY,2);
		paintCcorner(BLUE);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(GREEN);
		paintCcorner(LIGHT_GRAY);
		pNc(BLUE,2);
		paintCcorner(LIGHT_GRAY);
		moveToNextRow();
	}
	private void RtwentySeven(){
		pNc(LIGHT_GRAY,2);
		pNc(GREEN,2);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(RED);
		pNc(LIGHT_GRAY,2);
		paintCcorner(BLACK);
		pNc(LIGHT_GRAY,2);
		paintCcorner(DARK_GRAY);
		paintCcorner(YELLOW);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(BLACK);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(BLUE);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(GREEN);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(GREEN);
		pNc(LIGHT_GRAY,2);
		paintCcorner(BLUE);
		moveToNextRow();
	}
	private void RtwentyEight(){
		pNc(LIGHT_GRAY,3);
		pNc(GREEN,2);
		pNc(LIGHT_GRAY,2);
		paintCcorner(BLACK);
		paintCcorner(RED);
		paintCcorner(BLACK);
		pNc(LIGHT_GRAY,6);
		paintCcorner(BLUE);
		paintCcorner(GREEN);
		pNc(LIGHT_GRAY,3);
		paintCcorner(GREEN);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(BLUE);
		moveToNextRow();
	}
	private void RtwentyNine(){
		pNc(LIGHT_GRAY,4);
		pNc(GREEN,2);
		paintCcorner(BLACK);
		paintCcorner(ORANGE);
		paintCcorner(BLACK);
		paintCcorner(RED);
		paintCcorner(BLACK);
		pNc(LIGHT_GRAY,6);
		paintCcorner(BLUE);
		paintCcorner(GREEN);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(GREEN);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(BLUE);
		paintCcorner(LIGHT_GRAY);
		moveToNextRow();
	}
	private void Rthirty(){
		pNc(LIGHT_GRAY,5);
		paintCcorner(GREEN);
		paintCcorner(LIGHT_GRAY);
		paintCcorner(BLACK);
		paintCcorner(ORANGE);
		paintCcorner(BLACK);
		pNc(LIGHT_GRAY,8);
		paintCcorner(BLUE);
		paintCcorner(GREEN);
		pNc(BLUE,2);
		pNc(LIGHT_GRAY,2);
		moveToNextRow();
	}
	private void RthirtyOne(){
		pNc(LIGHT_GRAY,8);
		paintCcorner(BLACK);
		paintRow(LIGHT_GRAY);
	}
	private void changeColor(){
		turnRight();
		turnAround();
		moveToWall();
		turnLeft();
		moveToWall();
		turnLeft();
		paintCorner(switchColor());
		while(frontIsClear()){
			move();
			Color col = switchColor();
			paintCorner(col);
			if(frontIsBlocked()){
				moveToNextRow();
				if(frontIsClear()){
					paintCorner(switchColor());
				}
			}
		}
	}
	private Color switchColor() {
		if(cornerColorIs(BLUE)) {
			return MAGENTA;
		} else if(cornerColorIs(RED)) {
			return BLUE;
		} else if(cornerColorIs(WHITE)) {
			return LIGHT_GRAY;
		}else if(cornerColorIs(YELLOW)) {
			return BLUE;
		}else if(cornerColorIs(LIGHT_GRAY)) {
			return CYAN;
		}else if(cornerColorIs(DARK_GRAY)) {
			return RED;
		}else if(cornerColorIs(GREEN)) {
			return WHITE;
		}else if(cornerColorIs(CYAN)) {
			return ORANGE;
		}else if(cornerColorIs(BLACK)) {
			return GREEN;
		}else if(cornerColorIs(ORANGE)) {
			return YELLOW;
		} else {
			return null;
		}
	}
	private void timer(){
		try {
			Thread.sleep(1800);
		} catch (InterruptedException ie) {
		}
	}
}
