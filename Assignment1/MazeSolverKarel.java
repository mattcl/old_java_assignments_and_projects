import stanford.karel.*;

/*This program solves any maze (made out of walls, pathways one square wide) 
 * by checking all possible paths until it finds a beeper. It colors the correct path
 * green, and any incorrect paths that it checked red.
 */
public class MazeSolverKarel extends SuperKarel {
	
	public void run() {
			paintCorner(GREEN);
			followPath();
			paintCorner(GREEN);
	}
	/*Follows any single straight path until a corner, dead end, or intersection.*/
	private void followPath(){
		if(noBeepersPresent()){
			while(frontIsClear() && leftIsBlocked() && rightIsBlocked() && noBeepersPresent()){
				makeMove();
			}
			if(frontIsBlocked() && leftIsBlocked() && rightIsBlocked() && noBeepersPresent()){
				turnAround();
				paintCorner(RED);
				followPath();
			}else{
				checkPaths();
			}
		}
	}
	/*Checks all possible paths recursively*/
	private void checkPaths(){
		if(noBeepersPresent()){
			if(leftIsClear()){
				if(frontIsBlocked()&&rightIsBlocked()&&cornerColorIs(RED)){
					turnLeft(); 
					makeMove();
					paintCorner(RED);
				}else{
					turnLeft(); 
					checkColors();
				}
				followPath();
			}
			if(frontIsClear()){
				checkColors();
				followPath();
			}
			if(rightIsClear()){
				if(frontIsBlocked()&&leftIsBlocked()&&cornerColorIs(RED)){
					turnRight();
					makeMove();
					paintCorner(RED);
				}else{
					turnRight();
					checkColors();
				}
				followPath();
			}
		}
	}
	/*Checks if surrounding paths have already been tested, and paints the square accordingly.*/
	private void checkColors(){
		paintCorner(RED);
		if(leftIsClear()){
			turnLeft();
			move();
			if(cornerColorIs(null)){
				moveBack();
				turnLeft();
				paintCorner(GREEN);
			}else{
				moveBack();
				turnLeft();
			}
		}
		if(rightIsClear()){
			turnRight();
			move();
			if(cornerColorIs(null)){
				moveBack();
				turnRight();
				paintCorner(GREEN);
			}else{
				moveBack();
				turnRight();
			}
		}
		if(frontIsClear()){
			move();
			if(cornerColorIs(null)) {
				moveBack();
				paintCorner(GREEN);
				turnAround();
			}else{
				moveBack();
				turnAround();
			}
		}
		makeMove();
	}
	/*Just gets rid of some code duplication*/
	private void moveBack(){
		turnAround();
		move();
	}
	/*Moves only if beeper has not been reached, and colors the path correctly.*/
	private void makeMove(){
		if(cornerColorIs(GREEN)){
			if(noBeepersPresent()){
				move();
				paintCorner(GREEN);
			}
		}
		else{
			if(noBeepersPresent()){
				move();
				paintCorner(RED);
			}
		}
		
//		if(()) {
//		    if(cornerColorIs(GREEN)) {
//		        move();
//		        paintCorner(GREEN);
//		    } else {
//		        move();
//		        paintCorner(RED);
//		    }
//		}noBeepersPresent
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
