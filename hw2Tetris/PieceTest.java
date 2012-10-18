import junit.framework.TestCase;

import java.util.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest extends TestCase {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece[] pieces;
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece l1_1, l1_2, l1_3, l1_4;
	private Piece l2_1, l2_2, l2_3, l2_4;
	private Piece s1_1, s1_2;
	private Piece s2_1, s2_2;
	private Piece square;
	private Piece stick1, stick2;

	protected void setUp() throws Exception {
		super.setUp();
		pieces = Piece.getPieces();
		
		pyr1 = pieces[Piece.PYRAMID];
		pyr2 = pyr1.fastRotation();
		pyr3 = pyr2.fastRotation();
		pyr4 = pyr3.fastRotation();
		
		l1_1 = pieces[Piece.L1];
		l1_2 = l1_1.fastRotation();
		l1_3 = l1_2.fastRotation();
		l1_4 = l1_3.fastRotation();
		
		l2_1 = pieces[Piece.L2];
        l2_2 = l2_1.fastRotation();
        l2_3 = l2_2.fastRotation();
        l2_4 = l2_3.fastRotation();
		
        s1_1 = pieces[Piece.S1];
        s1_2 = s1_1.fastRotation();
        
        s2_1 = pieces[Piece.S2];
        s2_2 = s2_1.fastRotation();
        
        square = pieces[Piece.SQUARE];
        
        stick1 = pieces[Piece.STICK];
        stick2 = stick1.fastRotation();
	}
	
	// ----------- Pyramid Tests ------------- //
	
	public void testPyramidWidth() {
	    assertEquals(3, pyr1.getWidth());
        assertEquals(2, pyr2.getWidth());
        assertEquals(3, pyr3.getWidth());
        assertEquals(2, pyr4.getWidth());
	}
	
	public void testPyramidHeight() {
        assertEquals(2, pyr1.getHeight());
        assertEquals(3, pyr2.getHeight());
        assertEquals(2, pyr3.getHeight());
        assertEquals(3, pyr4.getHeight());
	}
	
	public void testPyramidSkirt() {
        assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
        assertTrue(Arrays.equals(new int[] {1, 0}, pyr2.getSkirt()));
        assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
        assertTrue(Arrays.equals(new int[] {0, 1}, pyr4.getSkirt()));
	}
	
	public void testPyramidNextPointer() {
        assertTrue(pyr1 == pyr4.fastRotation());
	}
	
	// ----------- L1 Tests ------------- //
    
    public void testL1Width() {
        assertEquals(2, l1_1.getWidth());
        assertEquals(3, l1_2.getWidth());
        assertEquals(2, l1_3.getWidth());
        assertEquals(3, l1_4.getWidth());
    }
    
    public void testL1Height() {
        assertEquals(3, l1_1.getHeight());
        assertEquals(2, l1_2.getHeight());
        assertEquals(3, l1_3.getHeight());
        assertEquals(2, l1_4.getHeight());
    }
    
    public void testL1Skirt() {
        assertTrue(Arrays.equals(new int[] {0, 0}, l1_1.getSkirt()));
        assertTrue(Arrays.equals(new int[] {0, 0, 0}, l1_2.getSkirt()));
        assertTrue(Arrays.equals(new int[] {2, 0}, l1_3.getSkirt()));
        assertTrue(Arrays.equals(new int[] {0, 1, 1}, l1_4.getSkirt()));
    }
    
    public void testL1NextPointer() {
        assertTrue(l1_1 == l1_4.fastRotation());
    }
    
    // ----------- L2 Tests ------------- //
    
    public void testL2Width() {
        assertEquals(2, l2_1.getWidth());
        assertEquals(3, l2_2.getWidth());
        assertEquals(2, l2_3.getWidth());
        assertEquals(3, l2_4.getWidth());
    }
    
    public void testL2Height() {
        assertEquals(3, l2_1.getHeight());
        assertEquals(2, l2_2.getHeight());
        assertEquals(3, l2_3.getHeight());
        assertEquals(2, l2_4.getHeight());
    }
    
    public void testL2Skirt() {
        assertTrue(Arrays.equals(new int[] {0, 0}, l2_1.getSkirt()));
        assertTrue(Arrays.equals(new int[] {1, 1, 0}, l2_2.getSkirt()));
        assertTrue(Arrays.equals(new int[] {0, 2}, l2_3.getSkirt()));
        assertTrue(Arrays.equals(new int[] {0, 0, 0}, l2_4.getSkirt()));
    }
    
    public void testL2NextPointer() {
        assertTrue(l2_1 == l2_4.fastRotation());
    }
    
    // ----------- S1 Tests ------------- //
    
    public void testS1Width() {
        assertEquals(3, s1_1.getWidth());
        assertEquals(2, s1_2.getWidth());
    }
    
    public void testS1Height() {
        assertEquals(2, s1_1.getHeight());
        assertEquals(3, s1_2.getHeight());
    }
    
    public void testS1Skirt() {
        assertTrue(Arrays.equals(new int[] {0, 0, 1}, s1_1.getSkirt()));
        assertTrue(Arrays.equals(new int[] {1, 0}, s1_2.getSkirt()));
    }
    
    public void testS1NextPointer() {
        assertTrue(s1_1 == s1_2.fastRotation());
    }
	
    // ----------- S2 Tests ------------- //
    
    public void testS2Width() {
        assertEquals(3, s2_1.getWidth());
        assertEquals(2, s2_2.getWidth());
    }
    
    public void testS2Height() {
        assertEquals(2, s2_1.getHeight());
        assertEquals(3, s2_2.getHeight());
    }
    
    public void testS2Skirt() {
        assertTrue(Arrays.equals(new int[] {1, 0, 0}, s2_1.getSkirt()));
        assertTrue(Arrays.equals(new int[] {0, 1}, s2_2.getSkirt()));
    }
    
    public void testS2NextPointer() {
        assertTrue(s2_1 == s2_2.fastRotation());
    }
    
    // ----------- Stick Tests ------------- //
    
    public void testStickWidth() {
        assertEquals(1, stick1.getWidth());
        assertEquals(4, stick2.getWidth());
    }
    
    public void testStickHeight() {
        assertEquals(4, stick1.getHeight());
        assertEquals(1, stick2.getHeight());
    }
    
    public void testStickSkirt() {
        assertTrue(Arrays.equals(new int[] {0}, stick1.getSkirt()));
        assertTrue(Arrays.equals(new int[] {0, 0, 0, 0}, stick2.getSkirt()));
    }
    
    public void testStickNextPointer() {
        assertTrue(stick1 == stick2.fastRotation());
    }
    
    // ----------- Square Tests ------------- //
    
    public void testSquareWidth() {
        assertEquals(2, square.getWidth());
    }
    
    public void testSquareHeight() {
        assertEquals(2, square.getHeight());
    }
    
    public void testSquareSkirt() {
        assertTrue(Arrays.equals(new int[] {0, 0}, square.getSkirt()));
    }
    
    public void testSquareNextPointer() {
        assertTrue(square == square.fastRotation());
    }
    
    public void testUniquePieces() {
        // 
        
        // similar piece test
        assertFalse(s1_1.equals(s2_1));
        assertFalse(l1_1.equals(l1_2));
    }
	
}