package contest2010;

import stanford.karel.SuperKarel;

/* This project is by Stephen Bulley 
 */
public class SteveContest extends SuperKarel {
	public void run(){
		writeWords();
		makeRamps();
		doTricks();
	}

	private void doTricks() {
		tricks();
	}

	private void tricks() {
		runFirstRamp();
		flip20();
		runSecondRamp();
		flip5();
		flip5();
		flip();
		flip();
		turnLeft();
		flip5();
		finishTricks();
	}

	private void finishTricks() {
		move3();
		stepDownBackwards();
		move();
		stepDownBackwards();
		stepDownBackwards();
		move3();
		turnRight();
		stepUp();
		stepUp();
		move8();
		turnAround();
		move8();
		turnLeft();
		stepDown();
		stepDown();
		move3();
		stepUp();
		move();
		stepUp();
		stepUp();
		flip5();
		turnRight();
		goToWall();
		turnLeft();
		goToWall();
		
	}

	private void runSecondRamp() {
		move();
		stepDown();
		stepDown();
		stepDown();
		move3();
		stepUp();
		move();
		stepUp();
		stepUp();
		turnLeft();
		move10();
		turnAround();
		move10();
		turnRight();
		move();
		turnLeft();
		move();
		turnRight();
		move();
		stepDownBackwards();
		move3();
		stepDown();
		stepDown();
		stepDown();
		turnRight();
		
	}

	private void runFirstRamp() {
		turnLeft();
		move10();
		turnLeft();
		move15();
		turnAround();
		move();
		turnRight();
		move15();
		stepUp();
		move15();
		stepUp();
		move5();
		stepUp();
		stepUp();
		stepUp();
		turnLeft();
		move3();
		stepUp();
		move();
		stepUp();
		stepUp();
		
	}

	private void flip20(){
		flip5();
		flip5();
		flip5();
		flip5();
	}
	
	private void flip5() {
		flip();
		flip();
		flip();
		flip();
		flip();
	}

	private void flip() {
		move();
		baseOfFlip();
		move();
		baseOfFlip();
		baseOfFlip();
		baseOfFlip();
		}
	private void baseOfFlip(){
		move();
		turnLeft();
	}
	private void makeRamps() {
		goToRamps();
		firstRamp();
		secondRamp();
		turnLeft();
		move7();
		baseOfFlip();
		move20();
		move20();
		turnLeft();
		secondRamp();
	}
	private void secondRamp() {
		turnLeft();
		movePaint();
		turnRight();
		baseOfFlip();
		movePaint();
		turnRight();
		baseOfFlip();
		movePaint();
		turnRight();
		baseOfFlip();
		movePaint();
		paint2();
		paint2();
		turnLeft();
		move();
		turnRight();
		paintCorner(BLUE);
		movePaint();
		stepPaint();
		stepPaint();
	}
	
	private void firstRamp() {
		height();
		move();
		turnLeft();
		movePaint();
		turnRight();
		height();
		turnLeft();
		move();
		turnRight();
		move();
		paint6();
		turnLeft();
		movePaint();
		turnRight();
		baseOfFlip();
		movePaint();
		turnRight();
		baseOfFlip();
		movePaint();
		turnRight();
		baseOfFlip();
		movePaint();
		paint2();
		paint2();
		turnLeft();
		move();
		turnRight();
		paintCorner(BLUE);
		movePaint();
		stepPaint();
		stepPaint();
		move20();
		turnLeft();
		move();
		move20();
		turnAround();
		move();
	}
	private void movePaint(){
		move();
		paintCorner(BLUE);
	}
	private void stepPaint(){
		stepUp();
		paintCorner(BLUE);
	}

	private void goToRamps(){
		goToWall();
		turnRight();
		move();
		turnRight();
		baseOfFlip();
		move();
		turnRight();
	}
	
	private void writeWords() {
		writeK();
		writeA();
		turnLeft();
		move7();
		baseOfFlip();
		move8();
		baseOfFlip();
		writeR();
		writeE();
		writeL();
		goToR();
		writeR();
		writeO();
		writeX();
		underlining();
		cleanUpLetters();

	}

	private void cleanUpLetters() {
		for(int i=0; i<1000; i++){
			turnRight();
		}
		baseOfFlip();
		for(int i=0; i<24;i++){
			while(frontIsClear()){
				paintCorner(BLACK);
				move();
			}
			paintCorner(BLACK);
			turnLeft();
			baseOfFlip();
			while(frontIsClear()){
				paintCorner(BLACK);
				move();
			}
			paintCorner(BLACK);
			turnRight();
			move();
			turnRight();
		}
	}

	private void underlining() {
		turnRight();
		move2();
		turnRight();
		height();
		height();
		move3();
		turnRight();
		move20();
		turnRight();
		height();
		height();
		height();
	}

	private void writeX() {
		move2();
		baseOfFlip();
		pieceOfX();
		turnRight();
		stepUp();
		paintUp();
		paintUp();
		turnLeft();
		pieceOfX();
		turnRight();
		stepPaint();
		turnAround();
		move7();
		baseOfFlip();
		otherPieceOfX();
		turnLeft();
		stepDown();
		paintCorner(BLUE);
		stepDown();
		stepDown();
		turnRight();
		otherPieceOfX();
		turnLeft();
		stepDown();
		paintCorner(BLUE);
	}

	private void writeO() {
		move2();
		baseOfFlip();
		height();
		turnRight();
		sideOfR();
		turnRight();
		height();
		turnRight();
		sideOfR();
		turnAround();
		move5();
		move5();

	}

	private void goToR() {
		turnRight();
		move();
		turnRight();
		goToWall();
		turnAround();
		move3();
		turnRight();
		goToWall();
		turnAround();
		move8();
	}

	private void writeL() {
		move3();
		turnRight();
		move();
		turnAround();
		height();
		turnAround();
		move5();
		move8();
		baseOfFlip();
		middleOfE();


	}

	private void writeE() {
		move();
		baseOfFlip();
		paintCorner(BLUE);
		height();
		paintCorner(BLACK);
		turnRight();
		sideOfR();
		turnAround();
		move5();
		baseOfFlip();
		move7();
		turnLeft();
		middleOfE();
		turnAround();
		move3();
		baseOfFlip();
		move8();
		turnLeft();
		sideOfR();
	}

	private void writeR() {

		height();
		turnRight();
		sideOfR();
		move();
		turnRight();
		sideOfR();
		turnRight();
		sideOfR();
		turnLeft();
		movePaint();
		turnLeft();
		slantDown();
	}

	private void writeA() {
		move2();
		slantA();
		turnLeft();
		movePaint();
		movePaint();
		turnRight();
		movePaint();
		move();
		turnRight();
		slantB();
		turnRight();
		move3();
		turnRight();
		move7();
		turnLeft();
		across();
	}

	private void across() {
		paint2();
		paint2();
		paintCorner(BLUE);


	}

	private void slantB() {
		for(int i=0;i<4;i++){
			baseSlantB();
		}
		paint2();
	}

	private void baseSlantB() {
		paint2();
		paintCorner(BLUE);
		turnLeft();
		movePaint();
		turnRight();
		movePaint();
	}

	private void slantA() {
		paintCorner(BLUE);
		for(int i=0;i<4;i++){
			baseSlantA();
		}

	}

	private void baseSlantA() {
		turnLeft();
		move();
		paint2();
		paintCorner(BLUE);
		turnRight();
		movePaint();	
	}

	private void writeK() {
		turnLeft();
		goToWall();
		turnAround();
		move3();
		turnLeft();
		move3();
		turnRight();
		height();
		turnAround();
		move7();
		turnRight();
		move();
		slantUp();
		turnRight();
		move8();
		turnRight();
		move8();
		turnAround();
		slantDown();
	}

	private void slantUp() {
		for(int i=0; i<8; i++){
			paintUp();
		}
	}

	private void paintUp() {
		paintCorner(BLUE);
		stepUp();
	}

	private void stepUp() {
		turnLeft();
		move();
		turnRight();
		move();		
	}

	private void slantDown() {
		for(int i=0; i<7; i++){
			stepDown();
			paintCorner(BLUE);
		}
	}

	private void stepDown() {
		move();
		turnRight();
		baseOfFlip();		
	}
	private void stepDownBackwards(){
		baseOfFlip();
		move();
		turnRight();
	}

	private void goToWall() {
		while(frontIsClear()){
			move();
		}
	}
	private void move2(){
		move();
		move();
	}
	private void move3(){
		move2();
		move();
	}
	private void move5() {
		move3();
		move2();
	}
	private void move7(){
		move5();
		move2();
	}
	private void move8(){
		move5();
		move3();
	}
	private void height(){
		for(int i=0; i<15; i++){
			paintAPiece();
		}
	}
	private void paintAPiece(){
		move();
		paintCorner(BLUE);
	}
	private void sideOfR(){
		for(int i=0; i<6; i++){
			paintAPiece();
		}

	}
	private void middleOfE(){
		for(int i=0; i<4; i++){
			paintAPiece();
		}
	}
	private void pieceOfX(){
		paint2();
		paintCorner(BLUE);
		turnRight();
		stepPaint();
		stepPaint();
		turnLeft();
		movePaint();
	}
	private void otherPieceOfX(){
		paint2();
		paintCorner(BLUE);
		turnLeft();
		stepDown();
		paintCorner(BLUE);
		stepDown();
		paintCorner(BLUE);
		turnRight();
		movePaint();
	}
	private void paint2(){
		paintCorner(BLUE);
		movePaint();
		move();
	}
	private void move20(){
		move5();
		move5();
		move5();
		move5();
	}
	private void move10(){
		move5();
		move5();
	}
	private void move15(){
		move5();
		move10();
	}
	private void paint6(){
		paint2();
		paint2();
		paint2();
	}
}