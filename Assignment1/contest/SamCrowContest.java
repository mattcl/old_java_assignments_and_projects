package contest;

/*
Sam Crow's entry in the competition thing. Simulates a random audio waveform visualizer.

Another way to interpret it is a simulation of grass.

as of 7/16/2009 1501: works in a 50x50 world.
more functionality planned.
as of unknown: hangs and slowdowns occur when running at maximumm speed. added log prints, no more hangs or slowdowns.
as of 7/17/2009 0858: working on high-limit yellow/red indications
as if 7/17/2009 0947: high-limit yellow/red indications work. row of beepers in world defines point where yellow
indications start. working on improvements: more than 1 corner of yellow bar, defined by beeper row 2
as of 7/17/2009 start of yellow bars defined by first beeper row. start of red bars defined by 2nd beeper row.
issue: after first beeper is hit in the draw sequence, it draws a yellow bar all the way to the 2nd beeper row
and then draws a random-length red bar. working on making yellow bar size random to allow yellow-level clipping, not just
red-level.
as of 7/17/2009 1015: now only requires one beeper row. length of red and yellow bars are random, simulating an advanced
clipping detection system: yellow exceeds the limits of the system components, red exceeds the limits of the microphone(s)/
speaker(s). added comments.
as of 7/17/2009 1024: opened the world file and set the speed to "999.99". 1.00 seems to be the highest that
it will accept.
as of 7/17/2009 1025: changed speed to "1.00". no noticeable change in speed detected. My hypothesis at
<time>7/17/2009 1024</time> seems to be correct.
as of 7/17/2009: unable to think of how to make this program better.
as of 7/17/2009 1351: made background black and saved it as part of the level for asthetically pleasing background without
sacraficing prep time. made the drawing of a bar in the far right column a reality.
as of 7/17/2009 1412: unable to think of how to make this program better again.
note: send to epgyhomework@gmail.com

 * User's Manual:
 * run in the world "SamCrowContest.w".
 * Once the program is started, it will loop forever (unless a collision is detected or the process is terminated(which you will need to do
 * 	when you are finished evaluating its function)).
 * This program can cause the application it is running under to "hang" and not respond. If this occurs and you are using a PC, find the section
 * in the bar at the bottom of the screen that says "SamCrowContest", right-click on it, select close, wait for a prompt that asks you if you want
 * to end the program now or not, press end now, repeat the last 2 steps a few times if it doesn't work the first time, wait for a prompt that tells
 * you that you ended the unresponsive program, and click on the button that says something like "don't send". If this occurs and you are using a mac,
 * press [apple][option][esc], select SamCrowContest or whatever application it is running under, press force quit, and press force quit again.
 * If the whole computer freezes and you are using a PC, hold [control][alt][delete (or back space)] or hold the power button. if the whole computer
 * freezes and you are using a mac, hold the power button. Forcing the program to stop is not dangerous because the source
 * code was saved immediately before the program was started and there is a backup of it on the computer that this program
 * was written on.
 * 
 * The following Disclaimer is a joke.
 * 
 * Disclaimer:
 *
 * section 1: definitions and scope
 * This file ("SamCrowContest.java") and the accompanying world file ("SamCrowContest.w"), hereafter referred to as "software", is provided 
 * by Sam Crow, his family, friends, and computer, hereafter reffered to as "Supplier" to
 * the Education Program for Gifted Youth and its employees, affiliates, licensors, and investors, hereafter referred to as "the EPGY", for free. However,
 * no warranty is given (expressed or implied) for the functionality or safeness of the software.
 * 
 * section 2: risks
 * The software is liable to fail, cause the
 * program that it is running under or computer that it is running on to hang, freeze, and/or crash, destroy data, and/or anything else that anyone
 * can think of. of course, failiure and program hangs are the only probable problems.
 * 
 * section 3: stuff you really should read if you are going to read any of this
 * By using the software, the EPGY agrees to not blame the Supplier for anything that happens as a result of the EPGY's use of the software.
 * 
 * section 4: conclusion
 * If you are not willing to take the risks defined in section 2, please destroy all of your copies of the software.
 This is the end of the joke.
 */
import stanford.karel.*;
public class SamCrowContest extends SuperKarel {
	public void run() {
		while(true){
			while(frontIsClear()){
				deleteAll();
				randomBar();
			}
			deleteAll();
			randomBar();
//			deleteUp();
			turnAround();
			moveToWall();
			turnAround();
		}

	}
	private void moveToWall(){
		//moves forward until a wall is encountered.
		while(frontIsClear()){
			move();
		}
	}
	private void deleteAll(){
		//changes all non-null colors in its row to null.
		turnLeft();
		while(cornerColorIs(GREEN) || cornerColorIs(YELLOW) || cornerColorIs(RED)){
			paintCorner(BLACK);
			if(beepersPresent() && cornerColorIs(RED)){
				pickBeeper();
			}
			if(frontIsClear()){
				move();
			}
		}
		turnAround();
		moveToWall();
		turnLeft();
//		if(frontIsClear()){
//		move();
//		}
//		if(frontIsClear()){
//		move();
//		}
	}
	private void randomBar(){
		//quasi-randomly draws an amplitude bar for its frequency.
		turnLeft();
		paintCorner(GREEN);
		move();
		randomColor();
		while(frontIsClear()){
			if(beepersPresent()){	//if it has reached the beeper row indicating the clipping start point
				paintCorner(YELLOW);
				move();
				while(noBeepersPresent() && random(0.75)){	//draws a yellow bar of random length
					paintCorner(YELLOW);
					if(frontIsClear()){
						move();
					}
				}
				paintCorner(RED);
				while(frontIsClear() && random()){	//draws a red bar of random length
					move();
					paintCorner(RED);
				}
			}
			if(cornerColorIs(GREEN)){	//if it's on part of a green bar, it goes to the corner above it and paints it green or not.
				move();
				randomColor();
			}else{	//otherwise, it goes back to the bottom
				turnAround();
				moveToWall();
			}
		}
		turnLeft();				//goes to
		if(frontIsClear()){		//the next
			move();				//row
		}
//		if(frontIsClear()){
//		move();
//		}
	}
	private void randomColor(){		//quasi-randomly determines the size of the green bar
		System.out.println("Pie");
		if(random(0.95)){
			paintCorner(GREEN);
		}else{
			paintCorner(BLACK);
		}
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