package contest2010;

//the world to run it in is : 10megs10
import java.awt.Color;

import stanford.karel.*;
//Megs
public class KarelContestMegs extends SuperKarel {

	private Color randomColor;
	int number ;
	//you fill in this part

	public void run () {
		double r = Math.random();
		//assume Karel is on the bottom left corner facing east

		randomColor = generateColor();
		paintOutsideFrame(randomColor);

		while (frontIsClear()) {
			randomColor= generateColor ();
			moveToNextFrame ();
			while (cornerColorIs (randomColor)) {
				randomColor=generateColor ();
			}
			number = 7;
			paintAFrame ();
			noColorRepeat ();
			number = 5;
			paintAFrame ();
			noColorRepeat ();
			number = 3;
			paintAFrame ();
			noColorRepeat ();
			number =1;
			paintAFrame();
			//ends in the smallest square at the bottom left facing east.
			noColorRepeatOnOutside ();
			number=3;
			paintAFrame();
			noColorRepeatOnOutside ();
			number=5;
			paintAFrame();
			noColorRepeatOnOutside ();
			number=7;
			paintAFrame();
			noColorRepeatOnOutside ();
			paintOutsideFrame (randomColor);
		}
		//repeats on and on...
	}


	private Color generateColor(){
		//for choosing a random color
		double r = Math.random();
		if (r < 0.1) {
			return CYAN;

		}else if (r < 0.2)  {
			return (BLACK);

		}else if (r < 0.3)  {
			return (BLUE);

		}else if (r < 0.4)  {
			return (GREEN);

		}else if (r < 0.5)  {
			return (LIGHT_GRAY);

		}else if (r < 0.6)  {
			return (MAGENTA);

		}else if (r < 0.7)  {
			return (WHITE);

		}else if (r < 0.8)  {
			return (PINK);

		}else if (r < 0.9)  {
			return (RED);

		}else{
			return (YELLOW);
		}	

	}

	private void paintOutsideFrame(Color color) {
		//assume karel starts on the bottom left corner facing east
		paintCorner (color);
		paintRow (color);
		turnLeft();
		paintRow (color);
		turnLeft ();
		paintRow (color);
		turnLeft ();
		paintRow (color);
		turnLeft ();

	}
	private void moveToNextFrame () {
		move();
		turnLeft();
		move();
		turnRight();
	}
//makes it so that when painting over a color, it does not repaint the same color
	private void noColorRepeat () {
		randomColor= generateColor ();
		moveToNextFrame ();
		while (cornerColorIs (randomColor)) {
			randomColor=generateColor ();
		}
	}
	private void paintAFrame () {
		paintCorner (randomColor);
		for (int i=0; i<4; i++) {
			makeLine(number, randomColor);
			turnLeft ();
		}
	}
	//makes it so that when painting over a color, it does not repaint the same color
	private void noColorRepeatOnOutside () {
		randomColor= generateColor ();
		moveToOutsideRow ();
		while (cornerColorIs (randomColor)) {
			randomColor=generateColor ();
		}
	}

	private void makeLine (int times, Color color) {
		for (int i=0; i <times ; i++) {
			move ();
			paintCorner (color);
		}
	}

	private void moveToOutsideRow () {
		turnRight ();
		move ();
		turnRight ();
		move ();
		turnAround ();
	}

	private void paintRow (Color color) {
		while (frontIsClear()) {
			move();
			paintCorner (color);

		}
	}
}