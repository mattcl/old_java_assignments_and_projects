import java.util.*;
import junit.framework.TestCase;

/*
 * This is a very hard test for ChunkList.
 * These tests push on the code with more or less deletion
 * going on by varying the parameters passed to parallelTest().
 */
public class ChunkListSuperTest extends TestCase {
	public void testSmall() {
		parallelTest(100, 0.05);
		parallelTest(100, 0.10);
		parallelTest(100, 0.20);
	}
	
	public void testMedium() {
		parallelTest(2000, 0.05);
		parallelTest(2000, 0.10);
		parallelTest(2000, 0.20);
	}
	
	public void testLotsDeletion() {
		parallelTest(2000, 0.45);
		parallelTest(2000, 0.50);
		parallelTest(2000, 0.55);
	}
	
	
	public void testLarge() {
		parallelTest(20000, 0.05);
	}
	
	public void testLarge2() {
		parallelTest(20000, 0.15);
	}
	
	public void testLarge3() {
		parallelTest(20000, 0.30);
	}
	
	
	/**
	 * Tests a ChunkList by running it in parallel with
	 * an ArrayList. For each operation, 50/50 either adds
	 * one element or randomly removes across the whole collection.
	 * Test in different ways by calling this with various
	 * parameter values.
	 * @param count number of operations
	 * @param removeProbability probability of removing one element
	 */
	public void parallelTest(int count, double removeProbability) {
		// fodder to put in the lists
		String[] strings = {"a", "b", "c", "d", "e", "f", "g"};

		Collection<String> a = new ArrayList<String>();
		Collection<String> b = new ChunkList<String>();

		Random rand = new Random(1);

		for (int i=0; i<count; i++) {
			if (a.size()==0 || rand.nextInt(2)==0) { // --add--
				String s = strings[rand.nextInt(strings.length)];
//				System.out.println("add:" + s);
				a.add(s);
				b.add(s);
			}
			else { // --remove--
				Iterator aIt = a.iterator();
				Iterator bIt = b.iterator();
				int j = 0;
//				System.out.print("remove");
				while (true) {
					boolean aHas = aIt.hasNext();
					boolean bHas = bIt.hasNext();
					
					assertEquals(aHas, bHas);
					if (!aHas) break;
					
					aIt.next();  // just advance the iterator, we don't care
					bIt.next();  // what it returns
					
					if (rand.nextDouble() <= removeProbability) {
						aIt.remove();
						bIt.remove();
						System.out.print(" " + j);
					}
					j++;
				}
//				System.out.println();
			}
			
			// After doing operations, compare the 2 collections
//			System.out.println("A size: " + a.size());
//			System.out.println("B size: " + b.size());
//			System.out.println(a.toString());
//			System.out.println(b.toString());
			assertTrue(sameCollection(a,b));
		}
	}
	

	/**
	 Utility. Returns true if two collections contain the same elements (.equals)
	 in the same order.
	*/
	public static boolean sameCollection(Collection a, Collection b) {
		if (a.size() != b.size()) return(false);
		Iterator ai = a.iterator();
		Iterator bi = b.iterator();
		while (ai.hasNext() && bi.hasNext()) {
			if (!ai.next().equals(bi.next())) return(false);
		}
		return(!ai.hasNext() && !bi.hasNext());
	}
}
