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

    private ArrayList<String> lex;
    
    public HangmanLexicon(String filename) {
        lex = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while(true) {
                String line = reader.readLine();
                if(line == null)
                    break;
                lex.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return lex.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		return lex.get(index);
	}
}
