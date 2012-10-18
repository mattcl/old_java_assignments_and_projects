package contest2010;

import stanford.karel.SuperKarel;

//Alex Gs Karel Contest
public class AlexContest extends SuperKarel {
	//	Karel draws out the phrase "GO JAVA" and then draws a smiling face
	public void run(){
		makeGO();
		makeBackwardsJAVA();
		makeSmiley();
	}
	private void makeBackwardsJAVA() {
		makeOrangeA();
		makeV();
		makeGreenA();
		makeJ();
	}
	private void makeGO() {
		makeG();
		makeO();
	}
	private void makeG () {
		move();
		turnLeft();
		for (int i=0; i< 20; i++) {
			move();
		}
		while (frontIsClear()){
			paintCorner (BLACK);
			if (frontIsClear()) {
				move();
			}
		}
		turnRight();
		paintCorner (BLACK);
		for (int i=0; i<11; i++) {
			paintCorner (BLACK);
			if (frontIsClear()) {
				move();
			}
		}
		turnRight();
		paintCorner (BLACK);
		for (int i=0; i< 3; i++) {
			move();
			paintCorner (BLACK);
		}
		move3Right();
		move3Black();
		paintCorner(BLACK);
		turnAround();
		move3Right();
		for (int i=0; i<3; i++) {
			paintCorner(BLACK);
			move();
		}
		turnRight();
		for (int i=0; i< 11;i++){
			paintCorner(BLACK);
			move();
		}
	}
	private void makeO() {
		turnAround();
		for (int i=0; i< 16; i++)
			move();
		for (int i=0; i< 11; i++) {
			yellowSquare();
		}
		leftYellow9();
		turnLeft();
		for (int i=0; i< 11; i++) {
			yellowSquare();
		}
		leftYellow9();
	}
	private void move3Right() {
		move3();
		turnRight();
	}
	private void move3Black() {
		for (int i =0; i<3; i++) {
			move();
			paintCorner(BLACK);
		}	
	}
	private void makeOrangeA() {
		diagonalMove();
		turnAround();
		for (int i=0; i<2;i++) {
			orangeSquare();
		}
		paintCorner(ORANGE);
		turnLeft();
		for (int i=0; i<6; i++) {
			orangeSquare();
		}
		turnLeft();
		for (int i=0; i<2;i++) {
			orangeSquare();
		}
		paintCorner(ORANGE);
		turnAround();
		for (int i=0; i<3; i++) {
			move();
		}
		turnRight();
		move();
		orangeStep();
		orangeLeft();
		for (int i=0; i<2; i++) {
			orangeSquare();
			orangeRight();
		}
		orangeSquare();
		orangeStep();
		paintCorner(ORANGE);
	}
	private void makeV() {
		turnAround();
		for (int i=0; i<6;i++) {
			move();
		}
		turnLeft();
		for (int i=0; i< 3; i++) {
			move();
		}
		paintCorner(RED);
		stepDownLeft();
		for (int i=0; i<3; i++) {
			makeDownV();
		}
		paintCorner(RED);
		turnAround();
		stepDownLeft();
		turnRight();
		redStairs();
		redTurn();
		move();
		redStep();
		turnRight();
		redStairs();
		redSquare();
		turnLeft();
		for (int i=0; i< 4; i++) {
			move();
		}
		turnAround();
	}
	private void makeGreenA() {
		for (int i=0; i<2;i++) {
			greenSquare();
		}
		paintCorner(GREEN);
		turnLeft();
		for (int i=0; i<6; i++) {
			greenSquare();
		}
		turnLeft();
		for (int i=0; i<2;i++) {
			greenSquare();
		}
		paintCorner(GREEN);
		turnAround();
		for (int i=0; i<3; i++) {
			move();
		}
		turnRight();
		move();
		greenStep();
		greenLeftSquare();
		for (int i=0; i<2; i++) {
			greenRightSquare();
		}
		greenStep();
		paintCorner(GREEN);
	}
	private void makeJ() {
		turnLeft();
		moveToWall();
		turnRight();
		for (int i=0; i< 5;i++) {
			move();
		}
		paintCorner(BLUE);
		turnAround();
		move();
		jSquare ();
		rightJStep();
		bluePivotTurn();
		stepDownLeft();
		paintCorner(BLUE);
		goUp();
		turnLeft();
		move();
		jSquare ();
		jLine();
		turnRight();
		jDescend();
		move();
		paintCorner(BLUE);
		stepRight();
		bluePivotRight();
		stepDownLeft();
		turnRight();
		paintCorner(BLUE);
		jDescend();
		stepDownLeft();
		for (int i=0; i<2; i++){
			jSquare();
		}
		paintCorner(BLUE);
		for (int i=0; i<6; i++) {
			jDescend();
		}
		move();
		paintCorner(BLUE);
		for (int i=0; i<4; i++) {
			jDescend();
		}
		turnLeft();
		move();
		for (int i=0; i<3; i++) {
			jSquare();
		}
		paintCorner(BLUE);
		stepDownLeft();
		for (int i=0; i<2; i++) {
			blueCorner();
		}
		bluePivotLeft();
		move();
		paintCorner(BLUE);
		goRight();
		paintCorner(BLUE);
		stepDownLeft();
		for (int i=0; i<4; i++) {
			jSquare();
		}
		turnRight();
		stepDownLeft();
		for (int i =0; i<7; i++) {
			jSquare();
		}
		goDown();
		turnRight();
		jSquare();
		turnLeft();
		for (int i=0; i<3;i++){
			jSquare();
		}
		goDown();
		bluePivotRight();
		stepDownLeft();
		for (int i=0; i<5;i++) {
			jSquare();
		}
		goDown();
		paintCorner(BLUE);
		goRight();
		paintCorner(BLUE);
	}
	private void makeSmiley() {
		turnAround();
		for (int i=0; i< 17; i++){
			move();
		}
		turnRight();
		for (int i=0; i<16; i++) {
			move();
		}
		for (int i=0; i<2; i++) {
			blackMove3();
		}
		turnRight();
		for (int i=0; i<2;i++) {
			move();
		}
		blackSquare();
		blackRight();
		smileySmile();
		for (int i=0; i<4; i++) {
			blackSquare();
		}
		goUp();
		smileySmile();
		blackSquare();
		paintCorner(BLACK);
	}
	private void leftYellow9() {
		turnLeft();
		for (int i=0; i< 9; i++) {
			yellowSquare();
		}
	}
	private void stepRight() {
		goUp();
		goDown();
	}
	private void blackMove3() {
		paintCorner(BLACK);
		for (int i=0; i<3; i++) {
			move();
		}
	}
	private void greenLeftSquare(){
		greenLeft();
		greenSquare();
	}
	private void greenRightSquare(){
		greenRight();
		greenSquare();
	}
	private void goUp(){
		turnRight();
		move();
	}
	private void goDown() {
		turnLeft();
		move();
	}
	private void blackRight() {
		paintCorner(BLACK);
		goRight();
	}
	private void makeDownV() {
		paintCorner(RED);
		move();
		paintCorner(RED);
		turnRight();
		stepDownLeft();
	}
	private void redStep() {
		paintCorner(RED);
		stepDownLeft();
	}
	private void redStairs() {
		redSquare();
		redStep();
	}
	private void redTurn() {
		paintCorner(RED);
		turnRight();
	}
	private void stepDownLeft(){
		move();
		turnLeft();
		move();
	}
	private void blackSquare() {
		paintCorner(BLACK);
		move();
	}
	private void blueCorner() {
		paintCorner(BLUE);
		turnLeft();
		goRight();
	}
	private void bluePivotLeft() {
		paintCorner(BLUE);
		turnLeft();
	}
	private void bluePivotRight(){
		paintCorner(BLUE);
		turnRight();
	}
	private void bluePivotTurn(){
		bluePivotLeft();
		bluePivotRight();
	}
	private void goRight() {
		move();
		turnRight();
		move();
	}
	private void jLine() {
		paintCorner(BLUE);
		stepDownLeft();
		paintCorner(BLUE);
	}
	private void jSquare (){
		paintCorner(BLUE);
		move();
	}
	private void jStep(){
		jSquare ();
		jLine();
	}
	private void jDescend() {
		stepDownLeft();
		paintCorner(BLUE);
		turnRight();
	}
	private void rightJStep() {
		jStep();
		goRight();
		jStep();
		goRight();
	}
	private void orangeStep() {
		orangeLeft();
		orangeRight();
	}
	private void orangeLeft(){
		paintCorner(ORANGE);
		turnLeft();
		move();
	}
	private void orangeRight(){
		paintCorner(ORANGE);
		turnRight();
		move();
	}
	private void orangeSquare(){
		paintCorner(ORANGE);
		move();
	}
	private void greenStep(){
		greenLeft();
		greenRight();
	}
	private void greenLeft() {
		paintCorner(GREEN);
		turnLeft();
		move();
	}
	private void greenRight(){
		paintCorner(GREEN);
		turnRight();
		move();
	}
	private void greenSquare(){
		paintCorner(GREEN);
		move();
	}
	private void redSquare() {
		paintCorner(RED);
		move();
	}
	private void yellowSquare(){
		paintCorner(YELLOW);
		move();
	}
	private void smileySmile () {
		paintCorner(BLACK);
		stepDownLeft();
		blackRight();
	}
	private void move3() {
		for (int i=0; i<3; i++) {
			move();
		}
	}
	private void diagonalMove(){
		turnLeft();
		moveToWall();
		turnRight();
		moveToWall();
	}
	private void moveToWall() {
		while (frontIsClear()) {
			move();
		}
	}
}