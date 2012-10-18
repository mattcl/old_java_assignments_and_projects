package contest2010;

//Stefan Layanto

import stanford.karel.*;

public class KarelContestStef extends SuperKarel {

	public void run () {
		if(!cornerColorIs(WHITE)){
			if(noBeepersPresent()){
				p1turn() ;
				try {
					Thread.sleep (1000) ;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rollTheDice1() ;
				try {
					Thread.sleep (1000) ;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				goToPlayer1() ;
				movePlayer1() ;
				try {
					Thread.sleep (1000) ;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (!cornerColorIs(WHITE)) {
					goBackToCorner () ;
					putBeeper() ;
				}
				eraseP1Turn() ;
			}else{
				p2Turn() ;
				try {
					Thread.sleep (1000) ;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rollTheDice2() ;
				try {
					Thread.sleep (1000) ;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				goToPlayer2() ;
				movePlayer2() ;
				try {
					Thread.sleep (1000) ;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (!cornerColorIs(WHITE)) {
					goBackToCorner () ;
					pickBeeper() ;
				}
			}
			eraseP2Turn() ;
		}
	}


	private void eraseP2Turn() {
		turnLeft() ;
		move(7) ;
		turnRight() ;
		move(24) ;
		for(int i=0;i<9;i++) {
			paintCorner(WHITE) ;
			move() ;
		}
		paintCorner(WHITE) ;
		turnRight() ;
		for(int i=0;i<6;i++) {
			move() ;
			paintCorner(WHITE) ;
		}
		turnRight() ;
		paintCorner(WHITE) ;
		for(int i=0;i<9;i++) {
			move() ;
			paintCorner(WHITE) ;
		}
		turnRight() ;
		paintCorner(WHITE) ;
		for(int i=0;i<5;i++) {
			move() ;
			paintCorner(WHITE) ;
		}
		turnLeft() ;
		moveToWall() ;
		turnLeft() ;
		moveToWall() ;
		turnLeft() ;	

	}


	private void eraseP1Turn() {
		turnLeft() ;
		move(6) ;
		turnRight() ;
		move(11) ;
		for(int i=0;i<9;i++) {
			paintCorner(WHITE) ;
			move() ;
		}
		paintCorner(WHITE) ;
		turnRight() ;
		for(int i=0;i<6;i++) {
			move() ;
			paintCorner(WHITE) ;
		}
		turnRight() ;
		paintCorner(WHITE) ;
		for(int i=0;i<9;i++) {
			move() ;
			paintCorner(WHITE) ;
		}
		turnRight() ;
		paintCorner(WHITE) ;
		for(int i=0;i<5;i++) {
			move() ;
			paintCorner(WHITE) ;
		}
		turnLeft() ;
		moveToWall() ;
		turnLeft() ;
		moveToWall() ;
		turnLeft() ;
	}


	private void p2Turn() {
		turnLeft() ;
		move(7) ;
		turnRight() ;
		move(24) ;
		for(int i=0;i<9;i++) {
			paintCorner(RED) ;
			move() ;
		}
		paintCorner(RED) ;
		turnRight() ;
		for(int i=0;i<6;i++) {
			move() ;
			paintCorner(RED) ;
		}
		turnRight() ;
		paintCorner(RED) ;
		for(int i=0;i<9;i++) {
			move() ;
			paintCorner(RED) ;
		}
		turnRight() ;
		paintCorner(RED) ;
		for(int i=0;i<5;i++) {
			move() ;
			paintCorner(RED) ;
		}
		turnLeft() ;
		moveToWall() ;
		turnLeft() ;
		moveToWall() ;
		turnLeft() ;	
	}


	private void p1turn() {
		turnLeft() ;
		move(6) ;
		turnRight() ;
		move(11) ;
		for(int i=0;i<9;i++) {
			paintCorner(RED) ;
			move() ;
		}
		paintCorner(RED) ;
		turnRight() ;
		for(int i=0;i<6;i++) {
			move() ;
			paintCorner(RED) ;
		}
		turnRight() ;
		paintCorner(RED) ;
		for(int i=0;i<9;i++) {
			move() ;
			paintCorner(RED) ;
		}
		turnRight() ;
		paintCorner(RED) ;
		for(int i=0;i<5;i++) {
			move() ;
			paintCorner(RED) ;
		}
		turnLeft() ;
		moveToWall() ;
		turnLeft() ;
		moveToWall() ;
		turnLeft() ;
	}


	private void movePlayer1() {
		pickBeeper() ;
		for (int i=0; i< p1Roll;i++) {
			try {
				Thread.sleep (250) ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(cornerColorIs(GRAY)) {
				putBeeper() ;
				goBackToCorner () ;
				goToCentreBox() ;
				clearCentreBox() ;
				writePlayer1Wins() ;
				EndReturn() ;
				paintCorner(WHITE) ;
				break;
			}else{
				if(cornerColorIs(DARK_GRAY)) {
					turnLeft () ;
					move() ;
				}
				if (cornerColorIs(LIGHT_GRAY)) {
					turnRight () ;
					move() ;
				}
				move() ;
			}

		}
		putBeeper () ;
		if(cornerColorIs(BLUE)) {
			try {
				Thread.sleep (2000) ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pickBeeper() ;
			for (int i=0; i< 5;i++) {
				try {
					Thread.sleep (250) ;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(cornerColorIs(DARK_GRAY)) {
					turnLeft () ;
					move() ;
				}
				if (cornerColorIs(LIGHT_GRAY)) {
					turnRight () ;
					move() ;
				}
				move() ;
			}
			putBeeper() ;
			try {
				Thread.sleep (1000) ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			goBackToCorner() ;
			blueArrow() ;
			turnRight() ;
			turnRight() ;
			moveToWall() ;
			turnRight() ;
			moveToWall() ;
			turnAround() ;
			goToPlayer1() ;
		}


		if(cornerColorIs(RED)) {
			try {
				Thread.sleep (2000) ;
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pickBeeper() ;
			turnAround() ;
			for (int i=0; i< 5;i++) {
				try {
					Thread.sleep (250) ;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(cornerColorIs(DARK_GRAY)) {
					turnRight () ;
					move() ;
				}
				if (cornerColorIs(LIGHT_GRAY)) {
					turnLeft() ;
					move() ;
				}
				move() ;
			}
			turnAround() ;
			putBeeper() ;
			try {
				Thread.sleep (1000) ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			goBackToCorner() ;
			redArrow() ;
			turnRight() ;
			turnRight() ;
			moveToWall() ;
			turnRight() ;
			moveToWall() ;
			turnAround() ;
			goToPlayer1() ;
		}
	}



	private void redArrow() {
		turnLeft() ;
		move(12) ;
		turnRight() ;
		move(10) ;
		paintCorner(BLACK) ;
		turnRight() ;
		move() ;
		paintCorner(BLACK) ;
		move() ;
		paintCorner(BLACK) ;
		move() ;
		paintCorner(BLACK) ;
		move() ;
		paintCorner(BLACK) ;
		turnAround() ;
		move(3) ;
		turnLeft() ;
		move() ;
		paintCorner(BLACK) ;
		turnAround() ;
		move(2) ;
		paintCorner(BLACK) ;
		move() ;
		turnRight() ;
		move() ;
		paintCorner(BLACK) ;
		turnRight() ;
		move(4) ;
		paintCorner(BLACK) ;
		try {
			Thread.sleep (2000) ;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		turnRight() ;
		move(2) ;
		turnRight() ;
		move(2) ;
		paintCorner(WHITE) ;
		turnRight() ;
		move() ;
		paintCorner(WHITE) ;
		move() ;
		paintCorner(WHITE) ;
		move() ;
		paintCorner(WHITE) ;
		move() ;
		paintCorner(WHITE) ;
		turnAround() ;
		move(3) ;
		turnLeft() ;
		move() ;
		paintCorner(WHITE) ;
		turnAround() ;
		move(2) ;
		paintCorner(WHITE) ;
		move() ;
		turnRight() ;
		move() ;
		paintCorner(WHITE) ;
		turnRight() ;
		move(4) ;
		paintCorner(WHITE) ;
		if(notFacingEast()) ;{
			turnRight() ;
		}
	}

	private void blueArrow() {
		turnLeft() ;
		move(12) ;
		turnRight() ;
		move(5) ;
		paintCorner(BLACK) ;
		turnRight() ;
		move() ;
		paintCorner(BLACK) ;
		move() ;
		paintCorner(BLACK) ;
		move() ;
		paintCorner(BLACK) ;
		move() ;
		paintCorner(BLACK) ;
		turnAround() ;
		move(3) ;
		turnLeft() ;
		move() ;
		paintCorner(BLACK) ;
		turnAround() ;
		move(2) ;
		paintCorner(BLACK) ;
		move() ;
		turnRight() ;
		move() ;
		paintCorner(BLACK) ;
		turnRight() ;
		move(4) ;
		paintCorner(BLACK) ;
		try {
			Thread.sleep (2000) ;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		turnRight() ;
		move(2) ;
		turnRight() ;
		move(2) ;
		paintCorner(WHITE) ;
		turnRight() ;
		move() ;
		paintCorner(WHITE) ;
		move() ;
		paintCorner(WHITE) ;
		move() ;
		paintCorner(WHITE) ;
		move() ;
		paintCorner(WHITE) ;
		turnAround() ;
		move(3) ;
		turnLeft() ;
		move() ;
		paintCorner(WHITE) ;
		turnAround() ;
		move(2) ;
		paintCorner(WHITE) ;
		move() ;
		turnRight() ;
		move() ;
		paintCorner(WHITE) ;
		turnRight() ;
		move(4) ;
		paintCorner(WHITE) ;
		if(notFacingEast()) ;{
			turnRight() ;
		}
	}


	private void movePlayer2() {
		pickBeeper() ;
		for (int i=0; i< p2Roll;i++) {
			try {
				Thread.sleep (250) ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(cornerColorIs(GRAY)) {
				putBeeper() ;
				goBackToCorner () ;
				goToCentreBox() ;
				clearCentreBox() ;
				writePlayer2Wins() ;
				EndReturn() ;
				paintCorner(WHITE) ;
				break;
			}else{
				if(cornerColorIs(DARK_GRAY)) {
					turnLeft () ;
					move() ;
				}
				if (cornerColorIs(LIGHT_GRAY)) {
					turnRight () ;
					move() ;
				}
				move() ;
			}

		}
		putBeeper () ;
		if(cornerColorIs(BLUE)) {
			try {
				Thread.sleep (2000) ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pickBeeper() ;
			for (int i=0; i< 5;i++) {
				if(cornerColorIs(DARK_GRAY)) {
					turnLeft () ;
					move() ;
				}
				if (cornerColorIs(LIGHT_GRAY)) {
					turnRight () ;
					move() ;
				}
				move() ;
			}
			putBeeper() ;
			try {
				Thread.sleep (1000) ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			goBackToCorner() ;
			blueArrow() ;
			turnRight() ;
			turnRight() ;
			moveToWall() ;
			turnRight() ;
			moveToWall() ;
			turnAround() ;
			goToPlayer2() ;
		}
		if(cornerColorIs(RED)) {
			try {
				Thread.sleep (2000) ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pickBeeper() ;
			turnAround() ;
			for (int i=0; i< 5;i++) {
				if(cornerColorIs(DARK_GRAY)) {
					turnRight () ;
					move() ;
				}
				if (cornerColorIs(LIGHT_GRAY)) {
					turnLeft() ;
					move() ;
				}
				move() ;
			}
			turnAround() ;
			putBeeper() ;
			try {
				Thread.sleep (1000) ;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			goBackToCorner() ;
			redArrow() ;
			turnRight() ;
			turnRight() ;
			moveToWall() ;
			turnRight() ;
			moveToWall() ;
			turnAround() ;
			goToPlayer2() ;
		}
	}



	private void goToCentreBox() {
		turnLeft() ;
		move(13) ;
		turnRight() ;
		move(17) ;
	}


	private void EndReturn() {
		turnLeft() ;
		moveToWall() ;
		turnLeft() ;
		moveToWall() ;
		turnLeft() ;
		moveToWall() ;
		turnLeft() ;
	}


	private void writePlayer1Wins() {
		Line1P1() ;
		Line2P1() ;
		Line3P1() ;
		Line4P1() ;
	}

	private void writePlayer2Wins() {
		Line1P2() ;
		Line2P2() ;
		Line3P2() ;
		Line4P2() ;
	}

	private void Line4P2() {
		turnAround() ;
		move(22) ;
		turnLeft() ;
		move() ;
		turnLeft() ;
		paintCorner(BLACK) ;
		move(4) ;
		for (int i=0;i<3;i++){
			paintCorner(BLACK) ;
			move() ;
		}
		move(3) ;
		for (int i=0;i<2;i++){
			paintCorner(BLACK) ;	
			move(2) ;
		}
		move() ;
		for (int i=0;i<3;i++){
			paintCorner(BLACK) ;
			move() ;
		}
		move() ;
		paintCorner(BLACK) ;
		move(3) ;
		paintCorner(BLACK) ;
	}


	private void Line3P2() {
		turnAround() ;
		move(22) ;
		turnLeft() ;
		move() ;
		turnLeft() ;
		for (int i=0;i<3;i++){
			paintCorner(BLACK) ;
			move () ;
		}
		move() ;
		paintCorner(BLACK);
		move() ;
		paintCorner(BLACK) ;
		move(4) ;
		for (int i=0;i<6;i++){
			paintCorner(BLACK) ;
			move(2) ;
		}
		paintCorner(BLACK) ;
		move() ;
		paintCorner(BLACK) ;
	}


	private void Line2P2() {
		turnAround() ;
		move(22) ;
		turnLeft();
		move() ;
		turnLeft() ;
		paintCorner(BLACK) ;
		move(2) ;
		paintCorner(BLACK) ;
		move(4) ;
		paintCorner(BLACK) ;
		move(3) ;
		for (int i=0;i<5;i++){
			paintCorner(BLACK) ;
			move(2) ;
		}
		paintCorner(BLACK) ;
		move() ;
		paintCorner(BLACK) ;
		move(2) ;
		paintCorner(BLACK) ;
	}


	private void Line1P2() {
		move() ;
		turnRight() ;
		move () ;
		turnLeft() ;
		for (int i=0;i<3;i++){
			paintCorner(BLACK) ;
			move() ;
		}
		move() ;
		paintCorner(BLACK) ;
		move() ;
		paintCorner(BLACK) ;
		move() ;
		paintCorner(BLACK) ;
		move(3) ;
		for (int i=0;i<3;i++){
			paintCorner(BLACK) ;
			move(2) ;
		}
		for (int i=0;i<3;i++){
			paintCorner(BLACK) ;
			move() ;
		}
		move() ;
		paintCorner(BLACK) ;
		move(3) ;
		paintCorner(BLACK) ;

	}

	private void Line4P1() {
		turnAround() ;
		move(22) ;
		turnLeft() ;
		move() ;
		turnLeft() ;
		paintCorner(BLACK) ;
		move(4) ;
		for (int i=0;i<3;i++){
			paintCorner(BLACK) ;
			move() ;
		}
		move(3) ;
		for (int i=0;i<2;i++){
			paintCorner(BLACK) ;	
			move(2) ;
		}
		move() ;
		for (int i=0;i<3;i++){
			paintCorner(BLACK) ;
			move() ;
		}
		move() ;
		paintCorner(BLACK) ;
		move(3) ;
		paintCorner(BLACK) ;
	}


	private void Line3P1() {
		turnAround() ;
		move(22) ;
		turnLeft() ;
		move() ;
		turnLeft() ;
		for (int i=0;i<3;i++){
			paintCorner(BLACK) ;
			move () ;
		}
		move(2) ;
		paintCorner(BLACK) ;
		move(4) ;
		for (int i=0;i<6;i++){
			paintCorner(BLACK) ;
			move(2) ;
		}
		paintCorner(BLACK) ;
		move() ;
		paintCorner(BLACK) ;
	}


	private void Line2P1() {
		turnAround() ;
		move(22) ;
		turnLeft();
		move() ;
		turnLeft() ;
		paintCorner(BLACK) ;
		move(2) ;
		paintCorner(BLACK) ;
		move(2) ;
		paintCorner(BLACK) ;
		move() ;
		paintCorner(BLACK) ;
		move(4) ;
		for (int i=0;i<5;i++){
			paintCorner(BLACK) ;
			move(2) ;
		}
		paintCorner(BLACK) ;
		move() ;
		paintCorner(BLACK) ;
		move(2) ;
		paintCorner(BLACK) ;
	}


	private void Line1P1() {
		move() ;
		turnRight() ;
		move () ;
		turnLeft() ;
		for (int i=0;i<3;i++){
			paintCorner(BLACK) ;
			move() ;
		}
		move(2) ;
		paintCorner(BLACK) ;
		move(4) ;
		for (int i=0;i<3;i++){
			paintCorner(BLACK) ;
			move(2) ;
		}
		for (int i=0;i<3;i++){
			paintCorner(BLACK) ;
			move() ;
		}
		move() ;
		paintCorner(BLACK) ;
		move(3) ;
		paintCorner(BLACK) ;

	}


	private void goToPlayer1() {
		turnLeft() ;
		move(19) ;
		turnRight() ;
		while(noBeepersPresent()){
			if(cornerColorIs(DARK_GRAY)) {
				turnLeft () ;
				move() ;
			}
			if (cornerColorIs(LIGHT_GRAY)) {
				turnRight () ;
				move() ;
			}
			move() ;
		}
	}


	private void goToPlayer2() {
		turnLeft() ;
		move(18) ;
		turnRight() ;
		while(noBeepersPresent()){
			if(cornerColorIs(DARK_GRAY)) {
				turnLeft () ;
				move() ;
			}
			if (cornerColorIs(LIGHT_GRAY)) {
				turnRight () ;
				move() ;
			}
			move() ;
		}
	}


	private void rollTheDice1() {
		cleanArea () ;
		faceEast() ;
		revertToOriginalPosition () ;
		paintAreaIfUnpainted () ;
		rollDiceP1 () ;
		goToSecondDice () ;
		try {
			Thread.sleep (1000) ;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		roll2ndDiceP1 () ;
		revertToOriginalPosition () ;
		p1Roll = roll1P1 + roll2P1 ;

	}


	private void rollTheDice2() {
		cleanArea () ;
		faceEast() ;
		revertToOriginalPosition () ;
		paintAreaIfUnpainted () ;
		rollDiceP2 () ;
		goToSecondDice () ;
		try {
			Thread.sleep (1000) ;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		roll2ndDiceP2 () ;
		revertToOriginalPosition () ;
		p2Roll = roll1P2 + roll2P2 ;

	}


	private void rollDiceP1() {
		GoToDiceCorner () ;
		setNumber1P1 () ;
	}


	private void roll2ndDiceP1() {
		GoToDiceCorner () ;
		setNumber2P1 () ;
	}


	private void rollDiceP2() {
		GoToDiceCorner () ;
		setNumber1P2 () ;
	}


	private void roll2ndDiceP2() {
		GoToDiceCorner () ;
		setNumber2P2 () ;
	}


	private void setNumber1P1() {
		roll1P1 = 0 ;
		if (random(.5)) {
			if(random(.5)) {
				if(random(.5)) {
					one () ;
					roll1P1 = 1 ;
				}else{
					two () ;
					roll1P1 = 2 ;
				}
			}else{
				if(random(.5)) {
					three () ;
					roll1P1 = 3 ;
				}else{
					four() ;
					roll1P1 = 4 ;
				}
			}
		}else{
			if(random(.5)) {
				if(random(.5)) {
					five () ;
					roll1P1 = 5 ;
				}else{
					six () ;
					roll1P1 = 6 ;
				}
			}else{
				setNumber1P1() ;
			}
		}
	}


	private void setNumber2P1() {
		roll2P1 = 0 ;
		if (random(.5)) {
			if(random(.5)) {
				if(random(.5)) {
					one () ;
					roll2P1 = 1 ;
				}else{
					two () ;
					roll2P1 = 2 ;
				}
			}else{
				if(random(.5)) {
					three () ;
					roll2P1 = 3 ;
				}else{
					four() ;
					roll2P1 = 4 ;
				}
			}
		}else{
			if(random(.5)) {
				if(random(.5)) {
					five () ;
					roll2P1 = 5 ;
				}else{
					six () ;
					roll2P1 = 6 ;
				}
			}else{
				setNumber2P1() ;
			}
		}
	}


	private void setNumber1P2() {
		roll1P2 = 0 ;
		if (random(.5)) {
			if(random(.5)) {
				if(random(.5)) {
					one () ;
					roll1P2 = 1 ;
				}else{
					two () ;
					roll1P2 = 2 ;
				}
			}else{
				if(random(.5)) {
					three () ;
					roll1P2 = 3 ;
				}else{
					four() ;
					roll1P2 = 4 ;
				}
			}
		}else{
			if(random(.5)) {
				if(random(.5)) {
					five () ;
					roll1P2 = 5 ;
				}else{
					six () ;
					roll1P2 = 6 ;
				}
			}else{
				setNumber1P2() ;
			}
		}
	}


	private void setNumber2P2() {
		roll2P2 = 0 ;
		if (random(.5)) {
			if(random(.5)) {
				if(random(.5)) {
					one () ;
					roll2P2 = 1 ;
				}else{
					two () ;
					roll2P2 = 2 ;
				}
			}else{
				if(random(.5)) {
					three () ;
					roll2P2 = 3 ;
				}else{
					four() ;
					roll2P2 = 4 ;
				}
			}
		}else{
			if(random(.5)) {
				if(random(.5)) {
					five () ;
					roll2P2 = 5 ;
				}else{
					six () ;
					roll2P2 = 6 ;
				}
			}else{
				setNumber2P2() ;
			}
		}
	}


	private void six() {
		putBeeper() ;
		turnLeft() ;
		move() ;
		putBeeper() ;
		move() ;
		putBeeper() ;
		turnRight() ;
		move(2) ;
		putBeeper() ;
		turnRight() ;
		move() ;
		putBeeper() ;
		move() ;
		putBeeper() ;
		turnLeft() ;
	}


	private void five() {
		putBeeper() ;
		stepUp() ;
		putBeeper() ;
		stepUp() ;
		putBeeper() ;
		turnRight () ;
		move(2) ;
		putBeeper() ;
		turnAround() ;
		move(2) ;
		turnLeft() ;
		move(2);
		putBeeper() ;
		turnAround () ;
		move(2) ;
	}


	private void four() {
		putBeeper() ;
		goUp (2) ;
		putBeeper () ;
		move(2) ;
		putBeeper () ;
		turnRight () ;
		move (2) ;
		putBeeper () ;
		turnLeft () ;
	}


	private void three() {
		putBeeper () ;
		stepUp () ;
		putBeeper () ;
		stepUp () ;
		putBeeper () ;
	}


	private void two() {
		putBeeper () ;
		stepUp () ;
		stepUp () ;
		putBeeper () ;		
	}


	private void one() {
		stepUp () ;
		putBeeper () ;
		move () ;
	}


	private void goBackToCorner() {
		turnAround() ;
		if(cornerColorIs(DARK_GRAY)) {
			move() ;
		}
		if (cornerColorIs(LIGHT_GRAY)) {
			move() ;
		}
		while (!cornerColorIs(WHITE)) {
			if(cornerColorIs(DARK_GRAY)) {
				turnRight () ;
				move() ;
			}
			if (cornerColorIs(LIGHT_GRAY)) {
				turnLeft () ;
				move() ;
			}
			move() ;
		}
		moveToWall() ;
		turnLeft() ;
		moveToWall() ;
		turnLeft() ;
	}


	private void goToTopLeftCorner() {
		turnLeft() ;
		move (4) ;
		turnLeft() ;
		move (8) ;
		turnAround () ;		
	}


	private void paintBlack() {
		paintLineBlack() ;
		goToTopLeftCorner() ;
		paintLineBlack() ;
		turnAround() ;
		move (8) ;
		turnAround () ;
		paintColumnBlack() ;
		turnLeft () ;
		move (4) ;
		turnRight () ;
		move(4) ;
		paintColumnBlack() ;
		turnLeft () ;
		move (4) ;
		turnRight () ;
		move(4) ;
		paintColumnBlack() ;
		revertToOriginalPosition() ;
	}


	private void paintColumnBlack() {
		turnRight() ;
		for(int i=0;i<4;i++){
			paintCorner(BLACK) ;
			move() ;
		}
		paintCorner (BLACK) ;
		turnLeft () ;
	}


	private void paintLineBlack() {
		for (int i=0; i <8;i++){
			paintCorner(BLACK) ;
			move() ;
		}
		paintCorner (BLACK) ;
	}


	private void paintAreaIfUnpainted() {
		while(cornerColorIs(null)) {
			paintBlack() ;
			paintDiceWhite() ;
			revertToOriginalPosition() ;
			paintCorner(BLACK) ;
		}		
	}


	private void faceEast() {
		if(notFacingEast()){
			turnRight() ;
		}
	}

	private void cleanArea () {
		clearBoard () ;	
		turnRight() ;
		move () ;
		turnAround () ;
		move () ;
		turnRight () ;
		goToOtherSide () ;
	}


	private void clearLine() {
		for (int i=0;i<8;i++) {
			if (beepersPresent()) {
				pickBeeper () ;
				move () ;
			}else{
				move () ;
			}
		}
	}
	private void clearCentreBox() {
		for(int i=0;i<5;i++){
			for (int j=0; j< 24; j++) {
				paintCorner (WHITE) ;
				move () ;
			}
			paintCorner (WHITE) ;
			turnAround() ;
			move(24) ;
			turnAround() ;
			turnRight() ;
			move() ;
			turnLeft() ;
		}
		for (int i=0; i< 24; i++) {
			paintCorner (WHITE) ;
			move () ;
		}
		paintCorner (WHITE) ;
		turnAround() ;
		move(24) ;
		turnRight() ;
		move(5);
		turnRight() ;
	}


	private void goToOtherSide() {
		turnAround () ;
		moveToWall () ;
		turnAround () ;
	}


	private void clearBoard() {
		turnLeft () ;
		for (int i=0;i<4;i++){	
			move () ;
			turnRight () ;
			clearLine () ;
			goToOtherSide () ;
			turnLeft () ;
		}
	}


	private void goToSecondDice() {
		goToGround() ;
		move () ;
	}


	private void goToGround() {
		turnRight() ;
		moveToWall () ;
		turnLeft () ;
	}


	private void revertToOriginalPosition() {
		goToCorner() ;
		turnAround () ;
	}


	private void goToCorner() {
		for(int i=0;i<2;i++){
			turnRight () ;
			moveToWall () ;
		}
	}


	private void moveToWall() {
		while (frontIsClear()) {
			move () ;
		}
	}


	private void paintDiceWhite() {
		stepUp () ;
		paintSingleDice () ;
		moveToNextDice () ;
		paintSingleDice () ;
	}


	private void moveToNextDice() {
		move () ;
		turnRight () ;
		move (2) ;
		turnLeft () ;
	}


	private void paintSingleDice() {
		for (int i=0; i<2;i++){
			paintLine (3) ;
			goBack (3) ;
			goUp (1) ;
		}
		paintLine (3) ;
	}


	private void paintLine(int amount1) {
		for (int i=0; i< amount1; i++) {
			paintCorner (WHITE) ;
			move () ;
		}
	}


	private void goBack(int amount2) {
		turnAround () ;
		for (int i=0; i< amount2; i++) {
			move () ;
		}
		turnAround () ;
	}


	private void goUp(int amount3) {
		turnLeft () ;
		for (int i=0; i< amount3; i++) {
			move () ;
		}
		turnRight () ;
	}


	private void move(int amount4) {
		for (int i=0; i< amount4; i++) {
			move () ;
		}
	}


	private void stepUp() {
		turnLeft () ;
		move () ;
		turnRight () ;
		move () ;
	}


	private void GoToDiceCorner() {
		stepUp () ;

	}
	private static int roll1P1;
	private static int roll2P1;
	private static int roll1P2;
	private static int roll2P2;
	private static int p1Roll;
	private static int p2Roll;
}