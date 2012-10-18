/* Karel Contest EPGY Session 1 Summer 2010
 * Title: Karel Plays With His New iPad
 * Programmer: Jack Thakar
 * Description: Karel starts in a pre-built world of a turned-off iPad. Karel will turn on his iPad,
 * display various apps in a random sequence, open the Doodle Jump app and then turn off the iPad.
 * Length: 570 lines (shortened from 900+)
 * This program DOES NOT loop automatically. However, the program can be run again after Karel stops moving.
 * I regret to say that this program was written on a computer running Mac OS X because Windows 7 is WAY better.
 */

package contest2010;

import stanford.karel.*;
import java.awt.Color;

public class ContestKarel extends SuperKarel {
	//Runs a loop in which the iPad boots up, springboard starts, and then the iPad shuts down.
	public void run() {
		bootUp(BLACK);
		lockScreen(DARK_GRAY);
		springboardStart();
		passTime(50);
		doodleJumpGame();
		powerOff(DARK_GRAY, BLACK, DARK_GRAY);
	}
	//Shuts down the iPad.
	private void powerOff(Color c, Color d, Color e) {
		lockOrPower(c, c, c, c, d);
		takeAStep();
		for (int i=0; i<5; i++) {
			multiPaint(33, WHITE);
			upRows(1);
		}
		upRows(31);
		arrowSlide(RED, WHITE, d);
		goHome();
		paintScreen(e,e,e,e,e,e);
	}
	//Plays Doodle Jump
	private void doodleJumpGame() {
		paintScreen(WHITE, WHITE, WHITE, WHITE, WHITE, WHITE);
		upRows(13);
		multiMove(13);
		multiPaint(8, GREEN);
		upRows(4);
		for (int i=0; i<5; i++) {
			for (int j=0; j<5; j++) {
				buildDoodler();
				colorApp(WHITE);
				upRows(2);
			}
			for (int j=0; j<5; j++) {
				buildDoodler();
				colorApp(WHITE);
				downRows(4);
			}
		}
		buildDoodler();
		goHome();
	}
	//Builds the Doodle Jump doodler.
	private void buildDoodler() {
		upRows(1);
		for (int i=0; i<3; i++) doubMultiPaint(4+i%2, GREEN, 1, WHITE);;
		downRows(1);
		paintPattern(5, WHITE, BLACK);
		upRows(4);
	}
	//Creates the lockscreen after booting.
	private void lockScreen(Color c) {
		lockOrPower(GRAY, ORANGE, CYAN, BLUE, c);
		upRows(41);
		for (int i=0; i<2; i++) {
			makeTheClock(GRAY, c);
			downRows(1);
			paintPattern(21, GRAY, c);
			multiPaint(13, c);
			downRows(1);
		}
		makeTheClock(GRAY, c);
		goHome();
		takeAStep();
		arrowSlide(LIGHT_GRAY, DARK_GRAY, c);
		goHome();
	}
	//Makes the lockscreen 1:11 clock.
	private void makeTheClock(Color b, Color c) {
		paintPattern(21, b, c);
		multiPaint(17, c);
		multiPaint(15, b);
		multiPaint(14, c);
	}
	//Moves the unlock or power down arrow across the screen.
	private void arrowSlide(Color a, Color b, Color c) {
		for (int i=0; i<7; i++) {
			arrowButton(a, b);
			arrowButton(c, c);
			multiMove(3);
		}
	}
	//Prepares the screen for the lock or powerdown screen.
	private void lockOrPower(Color c, Color d, Color e, Color f, Color g) {
		paintScreen(c, c, d, e, f, f);
		blackBox(g);
		upRows(36);
		blackBox(g);
	}
	//Creates an arrow button intended for sliders.
	private void arrowButton(Color c, Color d) {
		multiPaint(11, c);
		arrowRow(11, 7, 6, c, d);
		arrowRow(11, 8, 3, c, d);
		arrowRow(11, 7, 6, c, d);
		upRows(1);
		multiPaint(11, c);
		downRows(4);
	}
	//Completes one row of the arrow button.
	private void arrowRow(int a, int b, int c, Color e, Color d) {
		upRows(1);
		multiPaint(a, e);
		multiPaint(b, d);
		multiPaint(c, e);
	}
	//Creates a box of the chosen color intended for lock and shutdown screens.
	private void blackBox(Color c) {
		paintRow(c);
		paintSection(6, c);
		goHome();
	}
	//Runs the iPad bootup sequence.
	private void bootUp(Color c) {
		paintScreen(c, c, c, c, c, c);
		appleLogo(LIGHT_GRAY);
		passTime(20);
		appleLogo(c);
	}
	//Inserts a pause in the cycle by making Karel move across the screen and back the chosen # of times.
	private void passTime(int times) {
		for (int i=0; i<times*2; i++) {
			moveToWall();
			turnAround();
		}
	}
	//Builds (or deconstructs) the Apple logo. Intended for the boot sequence.
	private void appleLogo(Color c) {
		upRows(0);
		multiMove(16);
		downRows(25);
		multiPaint(5, c);
		turnLeft();
		takeAStep();
		turnRight();
		multiPaint(5, c);
		upRows(1);
		multiPaint(4, c);
		upRows(1);
		multiPaint(4, c);
		upRows(1);
		multiPaint(5, c);
		takeAStep();
		multiPaint(5, c);
		move();
		for (int i=0; i<2; i++) {
			takeAStep();
			paintCorner(c);
		}
		goHome();
	}
	//Runs the springboard startup sequence in which the background and apps appear.
	private void springboardStart() {
		paintScreen(LIGHT_GRAY, GRAY, ORANGE, CYAN, BLUE, DARK_GRAY);
		installApps();
	}
	//Paints screen in the six given colors.
	private void paintScreen(Color c, Color d, Color e, Color f, Color g, Color h) {
		paintRow(c);
		paintSection(3, c);
		paintSection(10,d);
		paintSection(3, e);
		paintSection(7, f);
		paintSection(18, g);
		paintSection(1, h);
		goHome();
	}
	//Paints an individual section of the screen
	private void paintSection(int times, Color c) {
		for (int i=0; i<times; i++) {
			upRows(1);
			if (cornerColorIs(c)) {
			}else paintRow(c);
		}
	}
	//Paints an individual row.
	private void paintRow(Color c) {
		paintCorner(c);
		while (frontIsClear()) {
			if (beepersPresent()) pickBeeper();
			move();
			paintCorner(c);
		}
		moveBackToWall();
	}
	//Colors an entire 5x5 region the given color.
	private void colorApp(Color c) {
		for (int i=0; i<5; i++) {
			multiPaint(5, c);
			downRows(1);
		}
		upRows(5);
	}
	//Runs the app install sequence.
	private void installApps() {
		upRows(38);
		multiMove(2);
		for (int i=0; i<3; i++) randomApps(1+4*i, 2+4*i, 3+4*i, 4+4*i);
		appRow4();
		downRows(1);
		randomApps(20, 21, 22, 23);
		goHome();
	}
	//Starts the app build sequence for the given numbered app.
	private void addApp(int i) {
		if (i==1) appMaps();
		if (i==2) appNotes();
		if (i==3) appContacts();
		if (i==4) appCalendar();
		if (i==5) appAppStore();
		if (i==6) appiTunes();
		if (i==7) appYouTube();
		if (i==8) appVideos();
		if (i==9) appiBooks();
		if (i==10) appKeynote();
		if (i==11) appPages();
		if (i==12) plusSignApp(LIGHT_GRAY, GRAY, DARK_GRAY);
		if (i==13) appDoodleJump();
		if (i==14) appFlightControl();
		if (i==15) appFacebook();
		if (i==16) appTwitter();
		if (i==17) appMagicPiano();
		if (i==18) appPandora();
		if (i==19) appNetflix();
		if (i==20) appiPod();
		if (i==21) appPhotos();
		if (i==22) appMail();
		if (i==23) plusSignApp(CYAN, WHITE, RED);
	}
	//Randomizes the order of the given app numbers.
	private void randomApps(int a, int b, int c, int d) {
		if (random (.25)) randomRow(a, b, c, d);
		else {
			if (random (.33)) randomRow(b, d, a, c);
			else {
				if (random()) randomRow(c, a, d, b);
				else randomRow(d, c, b, a);
			}
		}
		nextAppRow();
	}
	//Creates the apps in the random order given my randomApps.
	private void randomRow(int a, int b, int c, int d) {
		addApp(a);
		nextApp();
		addApp(b);
		nextApp();
		addApp(c);
		nextApp();
		addApp(d);
	}
	//Moves Karel up a row.
	private void upRows(int number) {
		if (number == 0) {
			turnLeft();
			moveToWall();
			turnRight();
		}	
		for (int i=0; i<number; i++) {
			turnLeft();
			move();
			turnRight();
		}
	}
	//Moves Karel down a row.
	private void downRows(int number) {
		if (number == 0) {
			turnRight();
			moveToWall();
			turnLeft();
		}	
		for (int i=0; i<number; i++) {
			turnRight();
			multiMove(1);
			turnLeft();
		}
	}
	//Everything not otherwise commented until you see ***** is an individual app or app row.
	private void appMaps() {
		colorApp(LIGHT_GRAY);
		upRows(1);
		doubMultiPaint(2, YELLOW, 1, LIGHT_GRAY);
		doubMultiPaint(5, BLUE, 3, ORANGE);
		doubMultiPaint(2, YELLOW, 1, LIGHT_GRAY);
		doubMultiPaint(2, RED, 1, LIGHT_GRAY);
		doubMultiPaint(2, YELLOW, 1, LIGHT_GRAY);
		upRows(4);
	}
	private void appNotes() {
		colorApp(YELLOW);
		multiPaint(5, GRAY);
	}
	private void appContacts() {
		colorApp(GRAY);
		multiPaint(4, ORANGE);
		downRows(1);
		multiPaint(4, ORANGE);
		doubMultiPaint(4, ORANGE, 3, GRAY);
		multiPaint(2, ORANGE);
		for (int i=0; i<2; i++) {
			downRows(1);
			multiPaint(4, ORANGE);
		}
		upRows(4);
	}
	private void appCalendar() {
		colorApp(WHITE);
		multiPaint(5, RED);
		downRows(2);
		for (int i=0; i<2; i++) {
			paintPattern(5, WHITE, BLACK);
			downRows(1);
		}
		upRows(4);
	}
	private void appAppStore() {
		colorApp(CYAN);
		doubMultiPaint(3, WHITE, 2, CYAN);
		doubMultiPaint(4, WHITE, 1, CYAN);
		doubMultiPaint(5, WHITE, 4, CYAN);
		paintCorner(WHITE);
		upRows(3);
	}
	//Makes Karel take one step up.
	private void takeAStep() {
		move();
		upRows(1);
	}
	private void appiTunes() {
		colorApp(MAGENTA);
		doubMultiPaint(5, WHITE, 1, MAGENTA);
		for (int i=0; i<2; i++) {
			doubMultiPaint(5, WHITE, 4, MAGENTA);
			multiPaint(2, WHITE);
			paintCorner(MAGENTA);
		}
		doubMultiPaint(5, WHITE, 3, MAGENTA);
		multiPaint(2, WHITE);
		upRows(4);
	}
	private void appYouTube() {
		colorApp(ORANGE);
		for (int i=0; i<3; i++) doubMultiPaint(4, LIGHT_GRAY, 1, ORANGE);
		downRows(1);
		throwBeeper(1);
		throwBeeper(3);
		upRows(4);
	}
	private void appVideos() {
		colorApp(CYAN);
		paintPattern(5, BLACK, WHITE);
		downRows(1);
		paintPattern(5, BLACK, WHITE);
		upRows(1);
	}
	private void appiBooks() {
		colorApp(ORANGE);
		for (int i=0; i<3; i++) {
			doubMultiPaint(4, WHITE, 1, ORANGE);
			throwBeeper(2);
		}
		upRows(3);
	}
	private void appKeynote() {
		colorApp(BLUE);
		for (int i=0; i<2; i++) doubMultiPaint(4, ORANGE, 1, BLUE);
		for (int i=0; i<2; i++) doubMultiPaint(3, GRAY, 2, BLUE);
		upRows(4);
	}
	private void appPages() {
		colorApp(BLUE);
		for (int i=0; i<2; i++) doubMultiPaint(4-i, GRAY, 3-i, BLUE);
		for (int i=0; i<2; i++) doubMultiPaint(4, MAGENTA, 1, BLUE);
		upRows(4);
	}
	private void appRow4() {
		int app1;
		int app2;
		int app3;
		if (random()) app1 = 19;
		else app1 = 14;
		if (random()) app2 = 15;
		else app2 = 16;
		if (random()) app3 = 17;
		else app3 = 18;
		randomApps(app1, app2, app3, 13);
	}
	private void appDoodleJump() {
		colorApp(WHITE);
		downRows(1);
		buildDoodler();
	}
	private void appFlightControl() {
		colorApp(BLUE);
		upRows(1);
		doubMultiPaint(4, WHITE, 3, BLUE);
		doubMultiPaint(4, WHITE, 3, BLUE);
		paintCorner(WHITE);
		downRows(1);
		multiPaint(5, WHITE);
		doubMultiPaint(4, WHITE, 3, BLUE);
		paintCorner(WHITE);
		doubMultiPaint(4, WHITE, 3, BLUE);
		upRows(4);
	}
	private void appFacebook() {
		colorApp(BLUE);
		doubMultiPaint(4, WHITE, 2, BLUE);
		doubMultiPaint(3, WHITE, 2, BLUE);
		doubMultiPaint(4, WHITE, 1, BLUE);
		doubMultiPaint(3, WHITE, 2, BLUE);
		upRows(4);
	}
	private void appTwitter() {
		colorApp(CYAN);
		doubMultiPaint(3, WHITE, 2, CYAN);
		doubMultiPaint(4, WHITE, 1, CYAN);
		doubMultiPaint(3, WHITE, 2, CYAN);
		doubMultiPaint(3, WHITE, 1, CYAN);
		upRows(4);
	}
	private void appMagicPiano() {
		colorApp(WHITE);
		multiPaint(5, GREEN);
		downRows(1);
		multiPaint(5, ORANGE);
		for (int i=0; i<2; i++) {
			downRows(1);
			paintPattern(5, BLACK, WHITE);
		}
		upRows(3);
	}
	private void appPandora() {
		colorApp(WHITE);
		doubMultiPaint(4, CYAN, 1, WHITE);
		downRows(1);
		paintPattern(5, WHITE, CYAN);
		doubMultiPaint(4, CYAN, 1, WHITE);
		doubMultiPaint(2, CYAN, 1, WHITE);
		upRows(4);
	}
	private void appNetflix() {
		colorApp(RED);
		downRows(2);
		for (int i=1; i<4; i++) throwBeeper(i);
		upRows(2);
	}
	private void appiPod() {
		colorApp(ORANGE);
		doubMultiPaint(4, WHITE, 1, ORANGE);
		downRows(1);
		paintPattern(5, ORANGE, WHITE);
		for (int i=0; i<2; i++) doubMultiPaint(4, WHITE, 1, ORANGE);
		upRows(4);
	}
	private void appPhotos() {
		plusSignApp(CYAN, YELLOW, BLACK);
		downRows(3);
		doubMultiPaint(3, GREEN, 2, CYAN);
		upRows(4);
	}
	private void appMail() {
		colorApp(CYAN);
		for (int i=0; i<2; i++) {
			downRows(1);
			multiPaint(5, WHITE);
			throwBeeper(i);
			throwBeeper(4-i);
		}
		downRows(1);
		multiPaint(5, WHITE);
		throwBeeper(2);
		upRows(3);
	}
	//***** Moves to the next app in the row.
	private void nextApp() {
		multiMove(9);
	}
	//Moves to the next app row.
	private void nextAppRow() {
		moveBackToWall();
		downRows(8);
		multiMove(2);
	}
	//Moves Karel in his current direction the given amount of times.
	private void multiMove(int times) {
		for (int i=0; i<times; i++) if (frontIsClear()) move();
	}
	//Paints the given amount of squares in the current direction.
	private void multiPaint(int times, Color c) {
		paintCorner(c);
		for (int i=0; i<times-1; i++) {
			move();
			paintCorner(c);
		}
		moveBackwards(times-1);
	}
	//Paints an alternating pattern between the two given colors. Note: must give an odd length.
	private void paintPattern(int times, Color c, Color d) {
		paintCorner(c);
		move();
		for (int i=0; i<(times-1)/2; i++) {
			paintCorner(d);
			move();
			paintCorner(c);
			move();
		}
		moveBackwards(times);
	}
	//Places a beeper the given amount of spaces away from Karel.
	private void throwBeeper(int times) {
		multiMove(times);
		putBeeper();
		moveBackwards(times);
	}
	//Returns an East-facing Karel to 1-1.
	private void goHome() {
		moveBackToWall();
		downRows(0);
	}
	//Used in apps the utilize a plus sign.
	private void plusSignApp(Color c, Color d, Color e) {
		colorApp(c);
		doubMultiPaint(3, d, 2, c);
		downRows(1);
		multiPaint(4, d);
		paintPattern(3, e, d);
		paintCorner(c);
		doubMultiPaint(3, d, 2, c);
		upRows(3);
	}
	//Does a double multiPaint so that the second color overlaps part of the first color.
	private void doubMultiPaint(int a, Color b, int c, Color d) {
		downRows(1);
		multiPaint(a, b);
		multiPaint(c, d);
	}
	//Moves Karel backwards the given number of times.
	private void moveBackwards(int times) {
		turnAround();
		multiMove(times);
		turnAround();
	}
	//Moves Karel backwards to the nearest wall.
	private void moveBackToWall() {
		turnAround();
		moveToWall();
		turnAround();
	}
	//Moves Karel to the nearest wall in his current direction.
	private void moveToWall() {
		while (frontIsClear()) move();
	}
}