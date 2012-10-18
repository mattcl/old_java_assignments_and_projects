/*
 * File: Breakout.java
 * -------------------
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
	private static final Color COLORS[] = {Color.RED , Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN};
	
	private static RandomGenerator generator = new RandomGenerator();
	
	private static final int[] xPnts = {-10, -10,  10, 10};
	private static final int[] yPnts = {-10,  10, -10, 10};
	
	private GRect paddle;
	private GOval ball;
	
	private int numBricksRemaining, numTurnsRemaining;
	
	private double vx, vy;
	
	private boolean hitPaddle;
	
	private long lastTimestamp;
	
	
/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
	    this.setSize(WIDTH, HEIGHT);
	    lastTimestamp = -1;
		addMouseListeners();
		setUpGame();
		while(true) {
			moveBall();
			checkCollisions();
		}
	}

	public void checkCollisions() {
		GObject collider = null;
		boolean hasCollision = false;
		boolean hitsPaddle = false;
		double x = ball.getX()+BALL_RADIUS;
		double y = ball.getY()+BALL_RADIUS;
		for(int i = 0; i < xPnts.length; i++) {
			collider = getElementAt(x+xPnts[i],y+yPnts[i]);
			if(collider != null && collider.equals(paddle)) {
				hitsPaddle = true;
			} else if(collider != null && !collider.equals(ball)) {
				remove(collider);
				hasCollision = true;
				break;
			}
		}
		if(hitsPaddle && vy > 0) changeDirectionY(); 
		else if(hasCollision) changeDirectionY();
		else hitPaddle = false;
	}
	
	public void moveBall() {
	    if(lastTimestamp < 0) {
	        lastTimestamp = System.currentTimeMillis();
	    } else {
	        long elapsedTime = System.currentTimeMillis() - lastTimestamp;
	        lastTimestamp = System.currentTimeMillis();
	        ball.move(vx*elapsedTime,vy*elapsedTime);
	        if(ball.getX() < 0) {
	            ball.setLocation(0, ball.getY());
	            changeDirectionX();
	        } else if(ball.getX()+2*BALL_RADIUS > WIDTH) {
	            ball.setLocation(WIDTH - 2*BALL_RADIUS, ball.getY());
                changeDirectionX();
	        } 
	        if(ball.getY() < 0) {
	            ball.setLocation(ball.getX(), 0);
	            changeDirectionY();
	        }
	    }
	}
	
	public void changeDirectionX() {
	    vx = -vx;
	}
	
	public void changeDirectionY() {
		vy = -vy;
	}
	
	public void mouseClicked(MouseEvent e) {
		setInitialVelocity();
	}
	
	public void mouseMoved(MouseEvent e) {
		double mouseX = e.getX();
		if(mouseX-PADDLE_WIDTH/2 > 0 && mouseX+PADDLE_WIDTH/2 < WIDTH) {
			paddle.setLocation(mouseX-PADDLE_WIDTH/2,paddle.getY());
		}
	}
	
	private void setUpGame() {
		numBricksRemaining = NBRICKS_PER_ROW * NBRICK_ROWS;
		numTurnsRemaining = NTURNS;
		createBricks();
		createPaddle();
		createBall();
	}
	
	private void createBricks() {
		for(int i = 0; i < NBRICKS_PER_ROW; i++) {
			for(int j = 0; j < NBRICK_ROWS; j++) {
				GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				brick.setColor(COLORS[j/2]);
				add(brick);
				brick.setLocation(i*(BRICK_WIDTH+BRICK_SEP),BRICK_Y_OFFSET+j*(BRICK_HEIGHT+BRICK_SEP));
			}
		}
	}
	
	private void createPaddle() {
		paddle = new GRect(PADDLE_WIDTH,PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.GRAY);
		add(paddle);
		paddle.setLocation((WIDTH-PADDLE_WIDTH)/2,HEIGHT-PADDLE_Y_OFFSET-PADDLE_HEIGHT);
	}
	
	private void createBall() {
		ball = new GOval(BALL_RADIUS*2,BALL_RADIUS*2);
		ball.setFilled(true);
		ball.setColor(Color.black);
		add(ball);
		ball.setLocation(WIDTH/2-BALL_RADIUS,HEIGHT/2-BALL_RADIUS);
		vx = 0;
		vy = 0;
	}
	
	private void setInitialVelocity() {
	    if(vx == 0 && vy == 0) {
            vx = generator.nextDouble(1.0,3.0)/10;
            if(generator.nextBoolean(0.5)) vx = -vx;
            vy = generator.nextDouble(1.0,3.0)/10;
            if(generator.nextBoolean(0.5)) vy = -vy;
        }
	}
}
