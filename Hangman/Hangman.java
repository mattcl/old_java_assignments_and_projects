/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.util.*;

public class Hangman extends ConsoleProgram {

	public static Random generator = new Random();
	
	public HangmanLexicon lexicon;
	
	public HangmanCanvas canvas;
	
	public String SecretWord;
	public char[] GuessedWord;
	
	public int numGuessesLeft;
	
    public void run() {
		setUpObjects();
		while(true) {
			setUpVariables();
			println("Welcome to Hangman!");
			while(true) {
				printGuessedWord();
				println("You have "+numGuessesLeft+" guesses left.");
				getGuess();
				updateCanvasGuessedWord();
				if(checkWinCondition()) break;
			}
			if(!playAgain()) break;
			canvas.reset();
		}
		exit();
	}
    
    public void setUpObjects() {
    	lexicon = new HangmanLexicon("HangmanLexicon.txt");
    	canvas = new HangmanCanvas();
    	setLayout(new GridLayout(1,2));
    	add(canvas);
    	validate();
    	canvas.reset();
    }
    
    public void setUpVariables() {
    	setSecretWord();
    	updateCanvasGuessedWord();
    	numGuessesLeft = 8;
    }
    
    public void setSecretWord() {
    	SecretWord = lexicon.getWord(generator.nextInt(lexicon.getWordCount()));
    	GuessedWord = new char[SecretWord.length()];
    	for(int i = 0; i < GuessedWord.length; i++) {
    		GuessedWord[i] = '-';
    	}
    }
    
    public boolean secretWordContains(char ch) {
    	if(Character.isLetter(ch)) {
    		return SecretWord.contains(Character.toString(ch).toUpperCase());
    	}
    	return false;
    }
    
    public void getGuess() {
    	while(true) {
    		String guess = readLine("Your guess: ");
    		guess = guess.toUpperCase();
    		if(guess.length() == 0) {
    			println("Invalid guess! Please enter a valid guess!");
    		}else if(guess.length() == 1 && secretWordContains(guess.charAt(0))) {
    			changeGuessedWord(guess.charAt(0));
    			println("That guess is correct.");
    			break;
    		} else if(guess.equalsIgnoreCase(SecretWord)) {
    			guessedWholeWord();
    			break;
    		} else if(guess.length() > 1) {
    			println("Invalid guess! Please enter a valid guess!");
    		} else {
    			println("There are no "+guess+"\'s in the word.");
    			updateCanvasIncorrectGuess(guess.charAt(0));
    			numGuessesLeft--;
    			break;
    		}
    	}
    }
    
    public String getGuessedWord() {
    	return new String(GuessedWord);
    }
    
    public void printGuessedWord() {
    	println("The word now looks like this: "+getGuessedWord());
    }
    
    public void changeGuessedWord(char ch) {
    	ch = Character.toUpperCase(ch);
    	for(int i = 0; i < SecretWord.length(); i++) {
    		if(SecretWord.charAt(i) == ch) {
    			GuessedWord[i] = ch;
    		}
    	}
    }
    
    public void guessedWholeWord() {
    	GuessedWord = SecretWord.toCharArray();
    }
    
    public boolean wordIsGuessed() {
    	for(int i = 0; i < GuessedWord.length; i++) {
    		if(GuessedWord[i] == '-') return false;
    	}
    	return true;
    }
    
    public boolean checkWinCondition() {
    	if(wordIsGuessed()) {
    		println("You guessed the word: "+SecretWord);
    		println("You win!");
    		canvas.displayEndgame(SecretWord,Color.green);
    		return true;
    	} else if(numGuessesLeft < 1) {
    		println("You are completely hung.");
    		println("the word was: "+SecretWord);
    		println("You lose :(");
    		canvas.displayEndgame(SecretWord, Color.red);
    		int n = 0;
    		while(n<40) {
    			canvas.move();
    			canvas.repaint();
    			this.pause(10);
    			n++;
    		}
    		n = 0;
    		while(n<70) {
    			canvas.moveHead();
    			canvas.repaint();
    			this.pause(10);
    			n++;
    		}
    		canvas.addX();
    		canvas.repaint();
    		return true;
    	}
    	return false;
    }
    
    public boolean playAgain() {
    	println();
    	println();
    	while(true) {
    		String ans = readLine("Would you like to play again? (y/n): ");
    		if(ans.equalsIgnoreCase("y")) {
    			println(); println(); println(); println(); println();
    			return true;
    		}
    		else if(ans.equalsIgnoreCase("n")) return false;
    	}
    }
    
    public void updateCanvasGuessedWord() {
    	canvas.displayWord(getGuessedWord());
    }
    
    public void updateCanvasIncorrectGuess(char ch) {
    	canvas.noteIncorrectGuess(ch);
    }

}
