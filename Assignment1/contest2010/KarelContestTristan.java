package contest2010;

import stanford.karel.*;

/*Tristan Yeung */

public class KarelContestTristan extends SuperKarel{

	public void run () {
		macstructure ();
		applesign ();
		camera ();
		turnonscreen();
		light ();
		applescreen ();
		circle ();
		lightoff ();
		appleoff ();
		circleoff ();
		bluescreen ();
		welcome ();
		loadingbar ();
		crash ();
		error ();
		finish ();
	}
	private void makewhite () {
		turnLeft ();
		move ();
		turnLeft ();
		for (int i=0; i<39 ;i++) {
			paintCorner (WHITE);
			move ();
		}
		paintCorner (WHITE);
		turnRight ();
		move ();
		turnRight ();
		for (int i=0; i<39 ;i++) {
			paintCorner (WHITE);
			move ();
		}
		paintCorner (WHITE);
	}
	private void innerwhite () {
		for (int i=0; i<33 ;i++) {
			paintCorner (WHITE);
			move ();
		}
		paintCorner (WHITE);
		turnRight ();
		move ();
		turnRight ();
		for (int i=0; i<33 ;i++) {
			paintCorner (WHITE);
			move ();
		}
		paintCorner (WHITE);
		turnLeft ();
		move ();
		turnLeft ();
	}
	private void stepup () {
		turnRight ();
		move ();
		turnRight ();
	}
	private void stepup2 () {
		turnLeft ();
		move ();
		turnLeft ();
	}
	private void macstructure () {
		turnLeft ();
		for (int i=0; i<17;i++) {
			move ();
		}
		turnRight ();
		for (int i=0; i<5;i++) {
			move ();
		}
		paintCorner (BLACK);
		for (int i=0; i<2;i++) {
			turnLeft ();
			move ();
		}
		paintCorner (BLACK);
		turnRight ();
		for (int i=0; i<27;i++) {
			paintCorner (BLACK);
			move ();
		}
		turnRight ();
		move ();
		for (int i=0; i<40;i++) {
			paintCorner (BLACK);
			move ();
		}
		turnRight ();
		move ();
		paintCorner (BLACK);
		for (int i=0; i<27;i++) {
			paintCorner (BLACK);
			move ();
		}
		turnRight ();
		move ();
		for (int i=0; i<40 ;i++) {
			paintCorner (BLACK);
			move ();
		}
		turnAround ();
		for (int i=0; i<3;i++) {
			move ();
		}
		turnLeft ();
		for (int i=0; i<10 ;i++) {
			move ();
		}
		for (int i=0; i<15 ;i++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		turnRight ();
		for (int i=0; i<35 ;i++) {
			move ();
			paintCorner (BLACK);

		}
		turnRight ();
		for (int i=0; i<15 ;i++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		turnRight ();
		for (int i=0; i<35 ;i++) {
			move ();
			paintCorner (BLACK);

		}
		move ();
		turnRight ();
		for (int i=0; i<17 ;i++) {
			paintCorner (WHITE);
			move ();
		}
		turnLeft ();
		move ();
		turnLeft ();
		for (int i=0; i<26 ;i++) {
			paintCorner (WHITE);
			move ();
		}
		paintCorner (WHITE);
		turnLeft ();
		for (int i=0; i<39 ;i++) {
			paintCorner (WHITE);
			move ();
		}
		paintCorner (WHITE);
		for (int i=0; i<4 ;i++) {
			makewhite ();
		}
		turnLeft ();
		for (int i=0; i<18 ;i++) {
			paintCorner (WHITE);
			move ();
		}
		paintCorner (WHITE);
		turnLeft ();
		move ();
		turnLeft ();
		for (int i=0; i<18 ;i++) {
			paintCorner (WHITE);
			move ();
		}
		paintCorner (WHITE);
		turnAround ();
		for (int i=0; i<2;i++) {
			move ();
		}
		turnLeft ();
		for (int i=0; i<2;i++) {
			move ();
		}
		for (int i=0; i<7 ;i++) {
			innerwhite ();

		}
		turnAround ();
		for (int i=0; i<2;i++) {
			move ();
			turnLeft ();
		}
		for (int i=0; i<35 ;i++) {
			paintCorner (WHITE);
			move ();
		}
		paintCorner (WHITE);
		for (int i=0; i<2;i++) {
			move ();
			turnRight ();
		}
		for (int i=0; i<36 ;i++) {
			paintCorner (WHITE);
			move ();
		}
		paintCorner (WHITE);
		turnRight ();
		for (int i=0; i<36 ;i++) {
			move ();
		}
		turnRight ();
		for (int i=0; i<10 ;i++) {
			move ();
		}
		for (int i=0; i<15 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		stepup ();
		move ();
		for (int i=0; i<14 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		stepup2 ();
		for (int i=0; i<2;i++) {
			move ();
		}
		for (int i=0; i<12 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		stepup ();
		for (int i=0; i<2;i++) {
			move ();
		}
		for (int i=0; i<10 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		stepup2 ();
		for (int i=0; i<2;i++) {
			move ();
		}
		for (int i=0; i<8 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		stepup ();
		for (int i=0; i<2;i++) {
			move ();
		}
		for (int i=0; i<6 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		stepup2 ();
		move ();
		for (int i=0; i<6 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		stepup ();
		move ();
		for (int i=0; i<6 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		stepup2 ();
		for (int i=0; i<2;i++) {
			move ();
		}
		for (int i=0; i<4 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
	}
	private void applesign () {
		turnRight ();
		for (int i=0; i<5 ;i++) {
			move ();
		}
		for (int i=0; i<3 ;i++) {
			paintCorner (GRAY);
			move ();
		}
		stepup ();
		for (int i=0; i<5 ;i++) {
			paintCorner (GRAY);
			move ();
		}
		stepup2 ();
		for (int i=0; i<6 ;i++) {
			paintCorner (GRAY);
			move ();
		}
		stepup ();
		for (int i=0; i<7 ;i++) {
			paintCorner (GRAY);
			move ();
		}
		stepup2 ();
		for (int i=0; i<3 ;i++) {
			move ();
			paintCorner (GRAY);
		}
		move ();
		for (int i=0; i<2 ;i++) {
			move ();
			paintCorner (GRAY);
		}
		turnRight ();
		move ();
		paintCorner (GRAY);
		turnRight ();
		for (int i=0; i<4 ;i++) {
			move ();
		}
		paintCorner (GRAY);
	}
	private void camera () {
		turnRight ();
		for (int i=0; i<2;i++) {
		move ();
		}
		turnRight ();
		for (int i=0; i<24 ;i++) {
			move ();
		}
		paintCorner (BLACK);
		putBeeper ();
	}
	private void turnonscreen () {
		turnAround ();
		for (int i=0; i<3 ;i++) {
			move ();
		}
		turnRight ();
		for (int i=0; i<17 ;i++) {
			move ();
		}
		turnAround ();
		for (int i=0; i<33 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		stepup ();
		for (int i=0; i<33 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		stepup2 ();
		for (int i=0; i<33 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		stepup ();
		for (int i=0; i<33 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		stepup2 ();
		for (int i=0; i<33 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		stepup ();
		for (int i=0; i<33 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		stepup2 ();
		for (int i=0; i<33 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		stepup ();
		for (int i=0; i<33 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		stepup2 ();
		for (int i=0; i<33 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		stepup ();
		for (int i=0; i<33 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		stepup2 ();
		for (int i=0; i<33 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		stepup ();
		for (int i=0; i<33 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		stepup2 ();
		for (int i=0; i<33 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		stepup ();
		for (int i=0; i<33 ;i++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		stepup2 ();
	}
	private void applescreen () {
		turnAround ();
		for (int i=0; i<17 ;i++) {
			move ();
		}
		turnLeft ();
		for (int i=0; i<12 ;i++) {
			move ();
		}
		turnRight ();
		paintCorner (DARK_GRAY);
		turnRight ();
		move ();
		turnRight ();
		move ();
		turnRight ();
		for (int i=0; i<3 ;i++) {
			paintCorner (DARK_GRAY);
			move ();
		}
		stepup2 ();
		move ();
		for (int i=0; i<2 ;i++) {
			paintCorner (DARK_GRAY);
			move ();
		}
		stepup ();
		for (int i=0; i<3 ;i++) {
			paintCorner (DARK_GRAY);
			move ();
		}

	}
	private void light () {
		for (int i=0; i<29 ;i++) {
			move ();
		}
		turnRight ();
		for (int i=0; i<5 ;i++) {
			move ();
		}
		putBeeper ();
	}
	private void circle () {
		turnLeft ();
		for (int i=0; i<4;i++) {
			move ();
		}
		turnLeft ();
		for (int i=0; i<2;i++) {
			move ();
		}
		paintCorner (GRAY);
		for (int i=0; i<2;i++) {
			turnRight ();
			move ();
		}
		paintCorner (GRAY);
		for (int i=0; i<2;i++) {
			turnLeft ();
			move ();
		}
		paintCorner (GRAY);
		for (int i=0; i<2;i++) {
			move ();
			turnLeft ();
		}
		paintCorner (GRAY);
		turnLeft ();
		for (int i=0; i<42 ;i++) {
			move ();
			turnRight ();
			move ();
			for (int j=0; j<30 ;j++) {
				paintCorner (LIGHT_GRAY);
			}
			for (int j=0; j<30 ;j++) {
				paintCorner (GRAY);
			}
		}
	}
	private void lightoff () {
		turnAround ();
		for (int i=0; i<9 ;i++) {
			move ();
		}
		turnLeft ();
		for (int i=0; i<13 ;i++) {
			move ();
		}
		pickBeeper ();
	}
	private void appleoff () {
		turnLeft ();
		for (int i=0; i<17 ;i++) {
			move ();
		}
		turnLeft ();
		for (int i=0; i<13 ;i++) {
			move ();
		}
		turnLeft ();
		for (int j=0; j<4 ;j++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		stepup2 ();
		for (int j=0; j<5 ;j++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		stepup ();
		for (int j=0; j<5 ;j++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
	}
	private void circleoff () {
		for (int j=0; j<5 ;j++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		turnRight ();
		move ();
		paintCorner (LIGHT_GRAY);
		turnRight ();
		move ();
		turnLeft ();
		move ();
		paintCorner (LIGHT_GRAY);
		for (int i=0; i<2;i++) {
			turnRight ();
			move ();
		}
		paintCorner (LIGHT_GRAY);
	}
	private void bluescreen () {
		turnRight ();
		for (int j=0; j<4 ;j++) {
			move ();
		}
		turnRight ();
		for (int j=0; j<17 ;j++) {
			move ();
		}
		turnAround ();
		for (int i=0; i<7 ;i++) {
			for (int j=0; j<33 ;j++) {
				paintCorner (CYAN);
				move ();
			}
			paintCorner (CYAN);
			stepup2 ();
			for (int j=0; j<33 ;j++) {
				paintCorner (CYAN);
				move ();
			}
			paintCorner (CYAN);
			stepup ();
		}
	}
	private void welcome () {
		turnRight ();
		for (int j=0; j<8 ;j++) {
			move ();
		}
		turnLeft ();
		move ();
		turnLeft ();
		for (int i=0; i<2;i++) {
			move ();
		}
		for (int j=0; j<5 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		turnAround ();
		for (int j=0; j<5 ;j++) {
			move ();
		}
		turnLeft ();
		for (int j=0; j<2 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		turnLeft ();
		for (int j=0; j<3 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		turnAround ();
		for (int j=0; j<3 ;j++) {
			move ();
		}
		turnLeft ();
		for (int j=0; j<2 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		turnLeft ();
		for (int j=0; j<5 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		turnRight ();
		for (int i=0; i<2;i++) {
			move ();
		}
		turnRight ();
		move ();
		turnLeft ();
		for (int j=0; j<3 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		turnAround ();
		for (int j=0; j<3 ;j++) {
			move ();
		}
		turnLeft ();
		for (int j=0; j<5 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		turnAround ();
		move ();
		turnRight ();
		for (int j=0; j<3 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		turnLeft ();
		for (int j=0; j<2 ;j++) {
			move ();
		}
		turnLeft ();
		move ();
		for (int j=0; j<2 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		turnRight ();
		for (int i=0; i<2;i++) {
			move ();
		}
		turnRight ();
		for (int j=0; j<4 ;j++) {
			move ();
		}
		turnRight ();
		for (int j=0; j<4 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		turnLeft ();
		move ();
		for (int j=0; j<2 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		move ();
		for (int j=0; j<3 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		turnAround ();
		for (int j=0; j<3 ;j++) {
			move ();
		}
		turnRight ();
		for (int j=0; j<4 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		turnRight ();
		for (int j=0; j<2 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		move ();
		move ();
		for (int j=0; j<2 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		turnRight ();
		for (int j=0; j<4 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		turnRight ();
		for (int j=0; j<2 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		turnRight ();
		for (int j=0; j<4 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		turnRight ();
		for (int j=0; j<4 ;j++) {
			move ();
		}
		turnRight ();
		for (int j=0; j<4 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		turnAround ();
		for (int j=0; j<4 ;j++) {
			move ();
		}
		turnRight ();
		for (int j=0; j<2 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		turnRight ();
		for (int j=0; j<4 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		turnAround ();
		for (int j=0; j<4 ;j++) {
			move ();
		}
		turnRight ();
		for (int j=0; j<2 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		turnRight ();
		for (int j=0; j<4 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		turnLeft ();
		for (int i=0; i<2;i++) {
			move ();
		}
		turnLeft ();
		for (int j=0; j<4 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		turnRight();
		for (int j=0; j<3 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		turnRight ();
		for (int j=0; j<2 ;j++) {
			move ();
		}
		turnRight ();
		move ();
		for (int j=0; j<3 ;j++) {
			paintCorner (BLACK);
			move ();
		}
		turnLeft ();
		for (int j=0; j<2 ;j++) {
			move ();
		}
		turnLeft ();
		move ();
		for (int j=0; j<3 ;j++) {
			paintCorner (BLACK);
			move ();
		}
	}
	private void loadingbar () {
		turnRight ();
		for (int j=0; j<6 ;j++) {
			move ();
		}
		turnRight ();
		for (int j=0; j<9 ;j++) {
			move ();
		}
		for (int j=0; j<14 ;j++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		turnRight ();
		for (int j=0; j<2 ;j++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		turnRight ();
		for (int j=0; j<14 ;j++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		paintCorner (LIGHT_GRAY);
		turnRight ();
		for (int j=0; j<2 ;j++) {
			paintCorner (LIGHT_GRAY);
			move ();
		}
		turnRight ();
		for (int j=0; j<14 ;j++) {
			move ();
		}
		for (int i=0; i<2;i++) {
			turnRight ();
			move ();
		}
		for (int j=0; j<12 ;j++) {
			paintCorner (WHITE);
			move ();
		}
		paintCorner (WHITE);
		turnAround ();
		for (int j=0; j<12 ;j++) {
			move ();
		}
		turnAround ();
		paintCorner (RED);
		move ();
		for (int j=0; j<3 ;j++) {
			time1 ();
			time2 ();
		}
		wait (900);
		for (int j=0; j<2 ;j++) {
			paintCorner (RED);
			move ();
		}
	}
	private void wait (int time) {
		try {
			Thread.sleep (time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void time1 () {
		wait (1000);
		for (int j=0; j<1 ;j++) {
			paintCorner (RED);
			move ();
		}
	}
	private void time2 () {
		wait (1500);
		for (int j=0; j<2 ;j++) {
			paintCorner (RED);
			move ();
		}
	}
	private void crash () {
		wait (1500);
		turnLeft ();
		for (int j=0; j<10 ;j++) {
			move ();
		}
		turnLeft ();
		for (int j=0; j<22 ;j++) {
			move ();
		}
		turnAround ();
		fillscreenrandom ();
	}
	private void randomcolors () {
		if (random(0.25)) {
			paintCorner (PINK);
		}else if(random(0.25)){
			paintCorner (CYAN);
		}else if(random(0.25)){
			paintCorner (LIGHT_GRAY);
		}else if(random(0.25)){
			paintCorner (WHITE);	
		}
	}
	private void fillscreenrandom () {
		for (int j=0; j<7 ;j++) {
			for (int i=0; i<33 ;i++) {
				randomcolors ();
				move ();
			}
			randomcolors ();
			stepup ();
			for (int i=0; i<33 ;i++) {
				randomcolors ();
				move ();
			}
			randomcolors ();
			stepup2 ();
		}
	}
	private void error () {
		turnLeft ();
		for (int i=0; i<6 ;i++) {
			move ();
		}
		turnRight ();
		for (int i=0; i<7 ;i++) {
			move ();
		}
		for (int i=0; i<3 ;i++) {
			paintCorner (BLACK);
			move ();
		}
		for (int j=0; j<4 ;j++) {
			move ();
			for (int i=0; i<3 ;i++) {
				paintCorner (BLACK);
				move ();
			}
		}
		stepup();
		move ();
		for (int i=0; i<8 ;i++) {
			paintCorner (BLACK);
			move ();
			move ();
		}
		for (int i=0; i<2;i++) {
			move ();
		}
		paintCorner (BLACK);
		stepup2 ();
		for (int i=0; i<2;i++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		for (int i=0; i<2 ;i++) {
			move ();
			move ();
			paintCorner (BLACK);
			move ();
			paintCorner (BLACK);
			move ();
			paintCorner (BLACK);
		}
		for (int i=0; i<2 ;i++) {
			move ();
		}
		paintCorner (BLACK);
		for (int i=0; i<2 ;i++) {
			move ();
		}
		paintCorner (BLACK);
		for (int i=0; i<2 ;i++) {
			move ();
		}
		for (int i=0; i<2;i++) {
			paintCorner (BLACK);
			move ();
		}
		paintCorner (BLACK);
		stepup ();
		for (int i=0; i<2;i++) {
			move ();
			paintCorner (BLACK);
		}
		for (int i=0; i<2 ;i++) {
			move ();
		}
		paintCorner (BLACK);
		for (int i=0; i<2 ;i++) {
			move ();
		}
		paintCorner (BLACK);
		for (int i=0; i<3 ;i++) {
			move ();
		}
		paintCorner (BLACK);
		move ();
		paintCorner (BLACK);
		for (int i=0; i<3 ;i++) {
			move ();
		}
		paintCorner (BLACK);
		move ();
		paintCorner (BLACK);
		for (int i=0; i<4 ;i++) {
			move ();
		}
		paintCorner (BLACK);
		stepup2 ();
		paintCorner (BLACK);
		for (int i=0; i<2 ;i++) {
			move ();
			paintCorner (BLACK);
		}
		for (int i=0; i<5 ;i++) {
			paintCorner (BLACK);
			move ();
			move ();
		}
		for (int i=0; i<2;i++) {
			paintCorner (BLACK);
			move ();
		}
		for (int i=0; i<2;i++) {
			paintCorner (BLACK);
			move ();
			move ();
		}
		paintCorner (BLACK);
		for (int i=0; i<8 ;i++) {
			move ();
		}
	}
	private void finish () {
		turnRight ();
		move ();
		turnLeft ();
	}
}