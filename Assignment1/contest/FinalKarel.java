package contest;
/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

/*
 * Name: Adam Zucker
 * Comments: Requires 40 X 40 map since this is a picture
 * Section Leader: epgyhomework@gmail.com
 */

public class FinalKarel extends SuperKarel 
{

	public void run() 
	{
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 13; j++) 
			{
				paintCorner(GREEN);
				move();
			}
			for(int j = 0; j < 14; j++) 
			{
				paintCorner(GREEN);
				move();
			}
			for(int j = 0; j < 13; j++) 
			{
				paintCorner(GREEN);
				if(frontIsClear()) move();
			}
			turnAround();
			moveToWall();
			turnRight();
			move();
			turnRight();
		}


		for(int i = 0; i < 16; i++)
		{
			for(int j = 0; j < 13; j++) 
			{
				paintCorner(LIGHT_GRAY);
				move();
			}
			for(int j = 0; j < 6; j++) 
			{
				paintCorner(DARK_GRAY);
				move();
			}
			for(int j = 0; j < 2; j++)
			{
				paintCorner(DARK_GRAY);
				putBeeper();
				move();
			}
			for(int j = 0; j < 6; j++) 
			{
				paintCorner(DARK_GRAY);
				move();
			}
			for(int j = 0; j < 13; j++) 
			{
				paintCorner(LIGHT_GRAY);
				if(frontIsClear()) move();
			}
			turnAround();
			moveToWall();
			turnRight();
			move();
			turnRight();
		}

		paintRow(LIGHT_GRAY);
		turnAround();
		moveToWall();
		turnRight();
		move();
		turnRight();

		for(int i = 0; i < 2; i++)
		{
			for(int j = 0; j < 40; j++) 
			{
				paintCorner(LIGHT_GRAY);
				putBeeper();
				if(frontIsClear())move();
			}
			turnAround();
			moveToWall();
			turnRight();
			move();
			turnRight();
		}

		paintRow(LIGHT_GRAY);
		turnAround();
		moveToWall();
		turnRight();
		move();
		turnRight();

		for (int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 13; j++) 
			{
				paintCorner(LIGHT_GRAY);
				move();
			}
			for(int j = 0; j < 14; j++) 
			{
				paintCorner(CYAN);
				move();
			}
			paintCorner(CYAN);
			for(int j = 0; j < 13; j++) 
			{
				paintCorner(LIGHT_GRAY);
				if(frontIsClear())move();
			}
			turnAround();
			moveToWall();
			turnRight();
			move();
			turnRight();
		}

		for(int j = 0; j < 13; j++) 
		{
			paintCorner(GRAY);
			move();
		}
		for(int j = 0; j < 14; j++) 
		{
			paintCorner(CYAN);
			move();
		}
		paintCorner(CYAN);
		for(int j = 0; j < 13; j++) 
		{
			paintCorner(GRAY);
			if(frontIsClear())move();
		}
		turnAround();
		moveToWall();
		turnRight();
		move();
		turnRight();

		paintCorner(CYAN);
		move();
		for(int j = 0; j < 11; j++) 
		{
			paintCorner(GRAY);
			move();
		}
		for(int j = 0; j < 16; j++) 
		{
			paintCorner(CYAN);
			move();
		}
		paintCorner(CYAN);
		for(int j = 0; j < 11; j++) 
		{
			paintCorner(GRAY);
			if(frontIsClear())move();
		}
		paintCorner(CYAN);
		turnAround();
		moveToWall();
		turnRight();
		move();
		turnRight();

		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		for(int j = 0; j < 9; j++) 
		{
			paintCorner(GRAY);
			move();
		}
		for(int j = 0; j < 18; j++) 
		{
			paintCorner(CYAN);
			move();
		}
		paintCorner(CYAN);
		for(int j = 0; j < 9; j++) 
		{
			paintCorner(GRAY);
			if(frontIsClear())move();
		}
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		turnAround();
		moveToWall();
		turnRight();
		move();
		turnRight();

		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		for(int j = 0; j < 7; j++) 
		{
			paintCorner(GRAY);
			move();
		}
		for(int j = 0; j < 20; j++) 
		{
			paintCorner(CYAN);
			move();
		}
		paintCorner(CYAN);
		for(int j = 0; j < 7; j++) 
		{
			paintCorner(GRAY);
			if(frontIsClear())move();
		}
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);

		turnAround();
		moveToWall();
		turnRight();
		move();
		turnRight();

		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		for(int j = 0; j < 5; j++) 
		{
			paintCorner(GRAY);
			move();
		}
		for(int j = 0; j < 22; j++) 
		{
			paintCorner(CYAN);
			move();
		}
		paintCorner(CYAN);
		for(int j = 0; j < 5; j++) 
		{
			paintCorner(GRAY);
			if(frontIsClear())move();
		}

		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		turnAround();
		moveToWall();
		turnRight();
		move();
		turnRight();

		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		for(int j = 0; j < 3; j++) 
		{
			paintCorner(GRAY);
			move();
		}
		for(int j = 0; j < 24; j++) 
		{
			paintCorner(CYAN);
			move();
		}
		paintCorner(CYAN);
		for(int j = 0; j < 3; j++) 
		{
			paintCorner(GRAY);
			if(frontIsClear())move();
		}

		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);

		turnAround();
		moveToWall();
		turnRight();
		move();
		turnRight();

		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();

		paintCorner(GRAY);
		move();
		for(int j = 0; j < 26; j++) 
		{
			paintCorner(CYAN);
			move();
		} 
		paintCorner(CYAN);
		paintCorner(GRAY);
		if(frontIsClear())move();

		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);

		turnAround();
		moveToWall();
		turnRight();
		move();
		turnRight();

		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();

		paintCorner(LIGHT_GRAY);
		move();
		paintCorner(CYAN);
		for(int j = 0; j < 26; j++) 
		{
			move();
			paintCorner(CYAN);
		} 
		paintCorner(LIGHT_GRAY);
		if(frontIsClear())
		{
			move();
			paintCorner(CYAN);
		}

		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		move();
		paintCorner(CYAN);
		turnAround();
		moveToWall();
		turnRight();
		move();
		turnRight();

		for (int j = 0; j < 3; j++)
		{
			for(int i = 1; i < 7; i++)
			{
				paintCorner(CYAN);
				move();
			}
			paintCorner(LIGHT_GRAY);
			move();
			paintCorner(BLACK);
			move();
			paintCorner(YELLOW);
			move();
			paintCorner(RED);
			for(int k = 0; k < 24; k++) 
			{
				move();
				paintCorner(CYAN);
			} 
			paintCorner(LIGHT_GRAY);
			move();
			paintCorner(BLACK);
			move();
			paintCorner(YELLOW);
			move();
			paintCorner(RED);
			move();
			paintCorner(CYAN);
			move();
			paintCorner(CYAN);
			move();
			paintCorner(CYAN);
			turnAround();
			moveToWall();
			turnRight();
			move();
			turnRight();
		}

		for(int i = 0; i < 2; i++) 
		{
			paintRow(CYAN);
			turnAround();
			moveToWall();
			turnRight();
			move();
			turnRight();
		}
		for (int i = 0; i < 20; i++)
		{
			paintCorner(CYAN);
			move();
		}
		for(int i = 0; i < 5; i++)
		{
			paintCorner(WHITE);
			move();
		}
		paintRow(CYAN);
		turnAround();
		moveToWall();
		turnRight();
		move();
		turnRight();

		for (int j = 0; j < 2; j++)
		{
			for (int i = 0; i < 19; i++)
			{
				paintCorner(CYAN);
				move();
			}
			for(int i = 0; i < 7; i++)
			{
				paintCorner(WHITE);
				move();
			}
			paintRow(CYAN);
			turnAround();
			moveToWall();
			turnRight();
			move();
			turnRight();
		}
		for (int i = 0; i < 20; i++)
		{
			paintCorner(CYAN);
			move();
		}
		for(int i = 0; i < 5; i++)
		{
			paintCorner(WHITE);
			move();
		}
		paintRow(CYAN);
		turnAround();
		moveToWall();
		turnRight();
		move();
		turnRight();

		paintRow(CYAN);
		turnAround();
		moveToWall();
		turnLeft();

		paintCorner(YELLOW);
		move();
		paintCorner(YELLOW);
		move();
		paintCorner(YELLOW);
		move();
		paintCorner(YELLOW);
		turnAround();
		moveToWall();
		turnRight();
		move();
		turnRight();
		paintCorner(YELLOW);
		move();
		paintCorner(YELLOW);
		move();
		paintCorner(YELLOW);
		move();
		paintCorner(YELLOW);
		turnAround();
		moveToWall();
		turnRight();
		move();
		turnRight();
		paintCorner(YELLOW);
		move();
		paintCorner(YELLOW);
		move();
		paintCorner(YELLOW);
		move();
		paintCorner(YELLOW);
		turnAround();
		moveToWall();
		turnRight();
		move();
		turnRight();
		paintCorner(YELLOW);
		move();
		paintCorner(YELLOW);
		move();
		paintCorner(YELLOW);
		move();
		paintCorner(YELLOW);
		move();
		move();
		paintCorner(YELLOW);
		turnAround();
		move();
		move();
		turnRight();
		move();
		move();
		paintCorner(YELLOW);
		turnAround();
		moveToWall();
		turnLeft();
		move();
		move();
		paintCorner(YELLOW);
		turnAround();
		moveToWall();
		turnRight();
		move(); 
		move();
		move(); 
		move();
		move();
		paintCorner(YELLOW);
		turnAround();
		moveToWall();
		
		
	}
	private void moveToWall() 
	{
		while(frontIsClear()) move();
	}

	private void paintRow(java.awt.Color cornerColor) 
	{
		while(frontIsClear())
		{
			paintCorner(cornerColor);
			move();
		}
		paintCorner(cornerColor);
	}

	// Note: This is an unfortunate hack to correct a
	// shortfall in our new Eclipse plugin. Don't worry
	// about (you won't be tested on it and aren't expected
	// to understand what's going on). However, don't
	// delete it, or you won't be able to run your Karel
	// program.
	public static void main(String[] args) {
		String[] newArgs = new String[args.length + 1];
		System.arraycopy(args, 0, newArgs, 0, args.length);
		newArgs[args.length] = "code=" + new SecurityManager(){
			public String className() {
				return this.getClassContext()[1].getCanonicalName();
			}
		}.className();
		SuperKarel.main(newArgs);
	}
}