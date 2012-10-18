// TabooTest.java
// Taboo class tests -- nothing provided.

import java.util.*;

import junit.framework.TestCase;

public class TabooTest extends TestCase {
    
    /*
     *  Extracted from AppearancesTest.java
     */
    private List<String> stringToList(String s) {
        List<String> list = new ArrayList<String>();
        for (int i=0; i<s.length(); i++) {
            list.add(String.valueOf(s.charAt(i)));
            // note: String.valueOf() converts lots of things to string form
        }
        return list;
    }
    
    public void testNoFollow1() {
        Taboo<String> taboo = new Taboo<String>(stringToList("acabc"));
        assertEquals(stringToList("cb"), new ArrayList(taboo.noFollow("a")));
        assertEquals(Collections.emptySet(), taboo.noFollow("x"));
    }
    
    public void testNoFollow2() {
        Taboo<String> taboo = new Taboo<String>(stringToList("aaaaa"));
        assertEquals(stringToList("a"), new ArrayList(taboo.noFollow("a")));
    }
   
    public void testReduce1() {
        Taboo<String> taboo = new Taboo<String>(stringToList("acab"));
        List<String> testList = stringToList("acbxca");
        taboo.reduce(testList);
        System.out.println(testList.size());
        assertEquals(stringToList("axc"), testList);
    }
    
    public void testReduce2() {
        Taboo<String> taboo = new Taboo<String>(stringToList("acab"));
        List<String> testList = stringToList("cbaxac");
        taboo.reduce(testList);
        System.out.println(testList.size());
        assertEquals(stringToList("cbaxa"), testList);
    }
    
}
