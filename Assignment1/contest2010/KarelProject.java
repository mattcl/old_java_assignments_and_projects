//****************************************************************************************************************************
//****Made By: Daniel Bishop**************************************************************************************************
//****************************************************************************************************************************
//****Thingy That Does Stuff**************************************************************************************************
//****************************************************************************************************************************
// INSTRUCTIONS
// Remember to check the console box at the bottom of the screen.
// World: 8x1
// Karel starts in corner 1x1, facing East.
// Choose a mode by putting the indicated # of Beepers on the corner.
// Mode selector should be on corner 2x1. Use either 0, 1, 2, 3, 4, or 5.
// Mode 0 will fond the square root of the number.
// Mode 1 will show all prime numbers up to the number.
// Mode 2 will show whether the number is prime.
// Mode 3 will start the guess a number game.
// Mode 4 will alphabetize things that you type.... maybe.
// Mode 5 will show Fibonacci numbers
// Put any number on corner 3x1. Larger numbers will take longer to complete.
//****************************************************************************************************************************

package contest2010;

import java.io.*;
import java.util.*;

public class KarelProject extends UtilityClass {
	// Integers used in many functions.
	private int count;
	private int square;
	private int mode;
	// Integers used in many functions.
//****************************************************************************************************************************
	// Main function
	public void run() {
		checkMode(); // Checks which mode to run
		countPile(); // Counts the beepers on the second pile
		modeZero(); // Runs mode 0
		modeOne(); // Runs mode 1
		modeTwo(); // Runs mode 2
		modeThree(); // Runs mode 3
		modeFour(); // Runs mode 4
		modeFive(); // Runs mode 5
	}
//****************************************************************************************************************************
	// Checks which value to assign mode.
	public void checkMode() {
		move();
		mode = 0;
		while (beepersPresent()) {
			pickBeeper();
			mode = mode + 1;
		}
	}
	// Mode 0 WORKS find square root
	public void modeZero() {
		if (mode == 0) {
			findSquareRootOfPile();
			showSquareRoot();
		}
	}
	// Mode 1 WORKS finds all prime numbers up to a number
	public void modeOne() {
		if (mode == 1) {
			printAllPrimes();
		}
	}
	// Mode 2 WORKS find if a number is prime
	public void modeTwo() {
		if (mode == 2) {
			if (isItPrime(count)) {
				System.out.println("This Number Is Prime!");
			} else {
				System.out.println("This Number Is Not Prime!");
			}
		}
	}
	// Mode 3 WORKS guess a number
	public void modeThree() {
		if (mode == 3) {
			int randomInt = 0 + (int) (Math.random()*10);
			System.out.println("Guess a # from 0 - 10");
			System.out.print("Enter your guess here: ");
			Scanner s = new Scanner(System.in);
			if (s.nextInt() == randomInt) {
				System.out.println("********");
				System.out.println("You Win!");
				System.out.println("********");
			} else {
				System.out.println("********");
				System.out.println("You Lose");
				System.out.println("The number was " + randomInt);
				System.out.println("********");
			}
		}
	}
	// Mode 4 WORKS alphabetizes
	public void modeFour() {
		if (mode == 4) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("Put words in here: ");
				String str;
				str = in.readLine();
				Scanner s = new Scanner(str);
				ArrayList<String> wordlist = new ArrayList<String>();
				while (s.hasNext()) {
					wordlist.add(s.next());
				}
				Collections.sort(wordlist);
				System.out.println(wordlist.toString());
			} catch(Exception ignored){}
		}
	}
	// Mode 5 WORKS Fibonacci numbers.
	// 112 123 235 358
	public void modeFive() {
		if (mode == 5) {
			System.out.print("How many Fibbonacci numbers? Please keep below 29 to avoid negative numbers. ");
			Scanner s = new Scanner(System.in);
			int times = (s.nextInt());
			long a = 1;
			long b = 1;
			long c = 2;
			System.out.println(a);
			System.out.println(b);
			System.out.println(c);
			for (int i = 0; i <= times; i++) {
				a = b + c;
				System.out.println(a);
				b = a + c;
				System.out.println(b);
				c = a + b;
				System.out.println(c);
			}
		}
	}
	// Determines whether the inputted number is prime or not.
	public boolean isItPrime(int num) {
		for (int i = 2; i <= Math.sqrt(num) + 1; i++) {
			int n = num%i;
			if (n == 0) {
				return false;
			}
		}
		return true;
	} 
	// Prints all the prime numbers up to the specified number in the console box.
	public void printAllPrimes() {	
		for (int i = 2; i < count; i++) {
			if (isItPrime(i)) {
				System.out.println(i);
			}
		}
	}
	// Finds the nearest pile of beepers.
	public void findPile() {
		while(noBeepersPresent()) {
			move();
		}
	}
	// Finds the square root of the number of beepers in the second pile.
	public void findSquareRootOfPile() {
		int square = (int) Math.round(Math.sqrt(count));
		System.out.print(square);
	}
	//Puts down the square root of a number in beepers
	public void showSquareRoot() {
		move();
		for (int i = 0; i < square; i++) {
			putBeeper();
		}
	}
	// Counts the beepers in the second pile.
	public void countPile() {
		move();
		while(beepersPresent()) {
			pickBeeper();
			count = count + 1;
		}
	}
}