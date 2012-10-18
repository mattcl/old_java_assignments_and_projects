import junit.framework.TestCase;


public class BoardTest extends TestCase {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;

	// This shows how to build things in setUp() to re-use
	// across tests.
	
	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	
	protected void setUp() throws Exception {
		b = new Board(3, 6);
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		
		b.place(pyr1, 0, 0);
	}
	
	// Check the basic width/height/max after the one placement
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}
	
	// Place sRotated into the board, then check some measures
	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}
	
	// Make  more tests, by putting together longer series of 
	// place, clearRows, undo, place ... checking a few col/row/max
	// numbers that the board looks right after the operations.
	
	public void testDropHeight1() {
	    b.commit();
	    Piece stick = new Piece(Piece.STICK_STR);
	    assertEquals(1, b.dropHeight(stick, 0));
	    assertEquals(2, b.dropHeight(stick, 1));
	    assertEquals(1, b.dropHeight(stick, 2));
	}
	
	public void testDropHeight2() {
	    b.commit();
	    assertEquals(2, b.dropHeight(s, 0));
	    assertEquals(2, b.dropHeight(sRotated, 0));
	    assertEquals(1, b.dropHeight(sRotated, 1));
	}
	
	public void testMaxHeight() {
	    b.commit();
	    assertEquals(2, b.getMaxHeight());
	    int result = b.place(sRotated, 1, 1);
	    assertEquals(4, b.getMaxHeight());
	    b.clearRows();
	    assertEquals(3, b.getMaxHeight());
	    b.commit();
	    
	    // place a piece then undo
	    b.place(new Piece(Piece.STICK_STR), 0, 0);
	    assertEquals(4, b.getMaxHeight());
	    b.undo();
	    assertEquals(3, b.getMaxHeight());
	    
	    // put the piece back
	    b.place(new Piece(Piece.STICK_STR), 0, 0);
        assertEquals(4, b.getMaxHeight());
        b.commit(); // i want to undo the clear rows later
	    b.clearRows();
        assertEquals(2, b.getMaxHeight());
        
        // undo and check that the max height was reset
        b.undo();
        assertEquals(4, b.getMaxHeight());
	}
	
	public void testGridWorks() {
	    b.commit();
	    assertFalse(b.getGrid(0, 1));
	    assertFalse(b.getGrid(2, 1));
	    assertTrue(b.getGrid(0, 0));
	    assertTrue(b.getGrid(1, 0));
	    assertTrue(b.getGrid(1, 1));
	    assertTrue(b.getGrid(2, 0));
	}
	
	public void testBadPlacements() {
	    // test place out of bounds
	    b.commit();
	    int result = b.place(pyr3, 3, 0);
	    assertEquals(Board.PLACE_OUT_BOUNDS, result);
	    
	    b.undo();
	    result = b.place(pyr3, 0, 6);
	    assertEquals(Board.PLACE_OUT_BOUNDS, result);
	    
	    b.undo();
        result = b.place(pyr3, 2, 0);
        assertEquals(Board.PLACE_OUT_BOUNDS, result);
        
        b.undo();
        result = b.place(pyr3, 0, 5);
        assertEquals(Board.PLACE_OUT_BOUNDS, result);
        
        // test place bd
        b.undo();
        result = b.place(pyr2, 0, 1);
        assertEquals(Board.PLACE_BAD, result);
	}
	
	public void testPlacePyramidAndStick() {
	    b = new Board(3, 6);
	    int result = b.place(pyr4, 0, 0);
	    assertEquals(Board.PLACE_OK, result);
	  
	    checkPyramidAndStickState1();
	    
	    b.commit();
	    result = b.place(new Piece(Piece.STICK_STR), 2, 0);
	    assertEquals(Board.PLACE_ROW_FILLED, result);
	    
	    checkPyramidAndStickState2();
        
	    b.commit();
        assertEquals(1, b.clearRows());
        
        checkPyramidAndStickState3();
        
        b.undo();
        
        checkPyramidAndStickState2();
	    
        result = b.place(new Piece(Piece.STICK_STR), 1, 2);
        assertEquals(Board.PLACE_ROW_FILLED, result);
        
        assertEquals(2, b.clearRows());
        b.commit();
        
	}
	
	// --------------- Private Methods ----------------- //
	
	private void checkPyramidAndStickState1() {
	    assertEquals(3, b.getColumnHeight(0));
        assertEquals(2, b.getColumnHeight(1));
        assertEquals(0, b.getColumnHeight(2));
        
        assertEquals(1, b.getRowWidth(0));
        assertEquals(2, b.getRowWidth(1));
        assertEquals(1, b.getRowWidth(2));
	}
	
	private void checkPyramidAndStickState2() {
	    assertEquals(3, b.getColumnHeight(0));
        assertEquals(2, b.getColumnHeight(1));
        assertEquals(4, b.getColumnHeight(2));
        
        assertEquals(2, b.getRowWidth(0));
        assertEquals(3, b.getRowWidth(1));
        assertEquals(2, b.getRowWidth(2));
	}
	
	private void checkPyramidAndStickState3() {
	    assertEquals(2, b.getColumnHeight(0));
        assertEquals(0, b.getColumnHeight(1));
        assertEquals(3, b.getColumnHeight(2));
        
        assertEquals(2, b.getRowWidth(0));
        assertEquals(2, b.getRowWidth(1));
        assertEquals(1, b.getRowWidth(2));
	}
}
