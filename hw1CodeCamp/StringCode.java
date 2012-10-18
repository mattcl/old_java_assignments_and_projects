import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
	    int max = 0, current = 1;
	    for (int i = 0; i < str.length(); i++) {
	        if (str.startsWith(Character.toString(str.charAt(i)), i+1)) {
	            current++;
	        } else {
	            if (current > max) max = current;
	            if (str.substring(i+1).length() < max) break;
	            current = 1;
	        }
	    }
		return max;
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
	    if (str.length() != 0) {
	        if (!Character.isDigit(str.charAt(0))) 
	            return Character.toString(str.charAt(0)) + blowup(str.substring(1));
	        else if (str.length() > 1) 
	            return blowupHelper(Integer.parseInt(Character.toString(str.charAt(0))), str.charAt(1)) + blowup(str.substring(1));
	    }
	    return "";
	}
	
	/**
	 *  Generates a string made up of num repetitions of
	 *  the passed character. I thought I remembered Java
	 *  having a String(char ch, int repetitions) constructor,
	 *  but whatever.
	 */
	private static String blowupHelper(int num, char ch) {
	    String repetitions = "";
	    for (int i = 0; i < num; i++) repetitions += ch;
	    return repetitions;
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
	    if (a.length() < len || b.length() < len) return false;
	    if (a.equals(b)) return true;
	    return hasAMatchingSubstring(b, len, substringHashSetHelper(a, len));
	}
	
	private static HashSet<String> substringHashSetHelper(String str, int len) {
	    HashSet<String> substrings = new HashSet<String>();
	    for(int i = 0; i < str.length() - len + 1; i++)
	        substrings.add(str.substring(i, i + len));
	    return substrings;
	}
	
	private static boolean hasAMatchingSubstring(String str, int len, HashSet<String> referenceSet) {
	    for (int i = 0; i < str.length() - len + 1; i++)
            if (referenceSet.contains(str.substring(i, i + len))) return true;
	    return false;
	}
}
