import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
	    HashMap<T, Integer> map = new HashMap<T, Integer>();
	    incrementMap(a, map);
	    decrementMap(b, map);
		return computeNumMatches(map);
	}

	// ----------- Private Methods ------------- //
	
	/**
	 * increments the values in the map by 1 (or inserts an initial value of 1)
	 */
	
	private static <T> void incrementMap(Collection<T> col, HashMap<T, Integer> map) {
	    for (T elem : col)
            if (map.containsKey(elem)) map.put(elem, map.get(elem) + 1);
            else map.put(elem, 1);
	}
	
	/**
	 * decrements the values in the map by 1 (only if the value exists)
	 */
	
	private static <T> void decrementMap(Collection<T> col, HashMap<T, Integer> map) {
        for (T elem : col)
            if (map.containsKey(elem)) map.put(elem, map.get(elem) - 1);
    }
	
	/**
	 * computes the number of elements that appear the same number of times
	 * in the map. (same number of times == 0 in the map)
	 */
	
	private static <T> int computeNumMatches(HashMap<T, Integer> map) {
	    int numMatches = 0;
	    for (Integer elem : map.values())
	        if(elem == 0) numMatches++;
	    return numMatches;
	}
}
