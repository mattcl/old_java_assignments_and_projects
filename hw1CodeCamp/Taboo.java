
/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/

import java.util.*;

public class Taboo<T> {
	
    private ArrayList<T> rules;
    
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {
	    this.rules = new ArrayList<T>(rules);
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		Set<T> returnSet = new HashSet<T>();
	    for (int i = rules.indexOf(elem); i != -1 && i < rules.size(); i++)
	        if (rules.get(i).equals(elem) && i+1 < rules.size())
	            returnSet.add(rules.get(i+1));
	    if (returnSet.size() == 0) return Collections.emptySet();
	    return returnSet;
	}
	
	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * 
	 * The handout said "start to finish," but I'm going
	 * to take this to mean "start to finish on the initial
	 * iteration."
	 * 
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
	    reduceHelper(list, 0, null);
	}
	
	private void reduceHelper(List<T> list, int pos, T last) {
	    if(pos < list.size()) {
	        if (last != null && noFollow(last).contains(list.get(pos))) {
	            list.remove(pos);
	            reduceHelper(list, pos, last);
	        } else {
	            reduceHelper(list, pos + 1, list.get(pos));
	        }
	    }
	}
}
