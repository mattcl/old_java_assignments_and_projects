/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;
import java.awt.*;

public class HangmanCanvas extends GCanvas {

	private static Font guessedWordFont = new Font("Courier", Font.BOLD, 24);
	private static Font guessedLettersFont = new Font("Arial", Font.PLAIN, 14);
	
	private GLabel guessedWord, guessedLetters;
	private Scaffold scaffold;
	private Person person;
	
/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		createGuessLabel();
		createGuessedLettersLabel();
		createScaffold();
		createHangman();
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		guessedWord.setLabel(word);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		if(!guessedLetters.getLabel().contains(Character.toString(letter))) {
			guessedLetters.setLabel(guessedLetters.getLabel()+letter);
		}
		person.addLimb();
	}
	
	public void displayEndgame(String word, Color col) {
		guessedWord.setLabel(word);
		guessedWord.setColor(col);
	}
	
	private void createGuessLabel() {
		guessedWord = new GLabel("");
		guessedWord.setFont(guessedWordFont);
		add(guessedWord,100,430);
	}
	
	private void createGuessedLettersLabel() {
		guessedLetters = new GLabel("");
		guessedLetters.setFont(guessedLettersFont);
		add(guessedLetters,100,470);
	}
	
	private void createScaffold() {
		scaffold = new Scaffold(10,10);
		add(scaffold,10,10);
	}
	
	public void createHangman() {
		person = new Person(180,28);
		add(person,180,28);
	}
	
	public void move() {
		person.moveLimb();
	}
	public void moveHead() {
		person.moveHead();
	}
	public void addX() {
		person.addLimb();
	}
	
/* Constants for the simple version of the picture (in pixels) */
	/* constants for the scaffold moved to the Scaffold class */
	
	/* constants for the person moved to the Person class*/

}
