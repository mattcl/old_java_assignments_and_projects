/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */
import acm.util.*;
import java.util.*;
import java.io.*;
public class HangmanLexicon {
	private ArrayList<String> words;
	public HangmanLexicon(String filename) {
		words = new ArrayList<String>();
		importFromFile(filename);
	}
	private void importFromFile(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String word;
			while((word = reader.readLine()) != null) words.add(word);	
			
			while(true) {
				word = reader.readLine();
				if(word == null) break;
				words.add(word);
			}
		} catch(Exception e) {
			System.out.println("Error: "+e.toString());
		}
	}
/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return words.size();
	}
/** Returns the word at the specified index. */
	public String getWord(int index) {
		return words.get(index);
	}
}
