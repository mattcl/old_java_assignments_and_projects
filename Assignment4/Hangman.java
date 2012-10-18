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
import java.util.Arrays;
import java.util.Random;

public class Hangman extends ConsoleProgram {
    private static final int NUM_GUESSES = 8;
    private static Random generator = new Random();
    
    private HangmanLexicon lexicon;
    private String word;
    private char[] working;
    private String guessedLetters;
    private int guessesRemaining;

    public void run() {
        setup();
        while(true) {
            play();
            if(!playAgain())
                break;
        }
	}
    
    public void setup() {
        lexicon = new HangmanLexicon("ShorterLexicon.txt");
        println("Welcome to Hangman!");
    }
    
    public void play() {
        pickWord();
        guessesRemaining = NUM_GUESSES;
        while(true) {
            println("The word now looks like this " + workingString());
            printNumGuesses();
            String guessLine = getGuess();
            if(guessLine.equalsIgnoreCase(word)) {
                printWinMessage();
                break;
            }
            checkGuess(guessLine);
            if(guessesRemaining == 0) {
                printLossMessage();
                break;
            } else if(!workingString().contains("-")) {
                printWinMessage();
                break;
            }
            
        }
    }

    private void checkGuess(String guessLine) {
        char guess = guessLine.charAt(0);
        if(guessedLetters.indexOf(guess) != -1) {
            println("You have already guessed that letter");
            return;
        } else if(guessIsCorrect(guess)) {
            println("That guess is correct.");
            updateWorkingSet(guess);
        } else {
            println("There are no " + guess + "'s in the word.");
            guessesRemaining--;
        }
        guessedLetters += guess;
    }
    
    public void pickWord() {
        word = lexicon.getWord(generator.nextInt(lexicon.getWordCount()));
        working = new char[word.length()];
        Arrays.fill(working, '-');
        guessedLetters = "";
    }
    
    public String getGuess() {
        String guess = readLine("Your guess: ").toUpperCase();
        if(guess.equals("") || guess.charAt(0) < 'A' || guess.charAt(0) > 'Z' || (guess.length() > 1 && !guess.equalsIgnoreCase(word))) {
            println("Invalid guess!");
            return getGuess();
        }
        return guess;
    }
    
    public boolean guessIsCorrect(char ch) {
        return (word.indexOf(ch) != -1);
    }
    
    public void updateWorkingSet(char ch) {
        for(int i = 0; i < word.length(); i++)
            if(word.charAt(i) == ch)
                working[i] = ch;
    }
    
    public String workingString() {
        return new String(working);
    }
    
    public void printNumGuesses() {
        if(guessesRemaining > 1) {
            println("You have " + guessesRemaining + " guesses left.");
        } else {
            println("You have only one guess left.");
        }
    }
    
    public void printWinMessage() {
        println("You guessed the word: " + word + "\nYou win.");
    }
    
    public void printLossMessage() {
        println("You are completely hung.");
        println("The word was: " + word);
        println("You lose.");
    }
    
    public boolean playAgain() {
        println();
        String ans = readLine("Play again? (y/n)").toLowerCase();
        if(ans.equals("y"))
            return true;
        else if(ans.equals("n"))
            return false;
        else
            return playAgain();
    }

}
