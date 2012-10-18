import java.util.*;

// Board.java

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private int[] widths;
	private int[] refWidths;
	private int[] heights;
	private int[] refHeights;
	private int maxHeight;
	private int refMaxHeight;
	private boolean[][] grid;
	private boolean[][] refGrid;
	private boolean DEBUG = true;
	boolean committed, calledClear;
	
	private int numCallsToPlace;
	private int numCallsToUndo;
	private int numCallsToClear;
	private int numCallsToCommit;
	
	
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		widths = new int[height];
		heights = new int[width];
		maxHeight = 0;
		grid = new boolean[width][height];
		
		refWidths = new int[height];
		refHeights = new int[width];
		refMaxHeight = 0;
		refGrid = new boolean[width][height];
		
		committed = true;
		calledClear = false;
		
		if(DEBUG) {
		    numCallsToPlace = 0;
		    numCallsToUndo = 0;
		    numCallsToClear = 0;
		    numCallsToCommit = 0;
		}
	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {	 
	    return maxHeight;
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
		    ArrayList<String> errors = new ArrayList<String>();
		    checkHeights(errors);
		    checkWidths(errors);
		    
		    if(!errors.isEmpty()) {
    		    StringBuilder buff = new StringBuilder();
    		    buff.append("Sanity check:\n");
    		    buff.append("---------------------------------------\n");
    		    buff.append("Board info: width = " + width + " height = " + height + "\n");
    		    buff.append("Committed?: " + isTrue(committed) + "\n");
    		    buff.append("Calls to place: " + numCallsToPlace + "\n");
    		    buff.append("Calls to clear rows: " + numCallsToClear + "\n");
    		    buff.append("Calls to undo: " + numCallsToUndo + "\n");
    		    buff.append("Calls to commit: " + numCallsToCommit + "\n\n");
    		    
    		    buff.append("Max height: " + maxHeight + "\n");
    		    buff.append("Heights array: " + Arrays.toString(heights) + "\n");
    		    buff.append("Widths array:  " + Arrays.toString(widths) + "\n\n");
    		    
    		    buff.append("Board state:\n");
    		    buff.append("------------\n");
    		    buff.append(toString() + "\n\n");
    		    
    		    buff.append("The following errors were detected - Total: " + errors.size() + "\n");
    		    buff.append("-------------------------------------------------");
                System.out.println(buff.toString());
                
                for(String error : errors) 
                    System.err.println(error);
                
                throw new RuntimeException("Sanity check detected the above errors");
		    }
		}
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
	    if(x + piece.getWidth() > width || x < 0) throw new RuntimeException("Drop height called with invalid x");
		int dropHeight = 0, workingHeight = 0;
	    int[] skirt = piece.getSkirt();
		for (int i = 0; i < skirt.length; i++) {
		    workingHeight = heights[x+i] - skirt[i];
		    if (workingHeight > dropHeight)
		        dropHeight = workingHeight;
		}
	    return dropHeight;
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		return heights[x];
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		 return widths[y];
	}
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		if(x < 0 || x >= width || y < 0 || y >= height) return true;
	    return grid[x][y];
	}
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
	    if(DEBUG) numCallsToPlace++;
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
		backupDataStructures();
		
		int result = PLACE_OK;
		
		
		if(x < 0 || y < 0 || x + piece.getWidth() > width || y + piece.getHeight() > height) {
		    result = PLACE_OUT_BOUNDS;
	    } else {
		    List<TPoint> pieceBody = Arrays.asList(piece.getBody()); // I'd rather use the for-each loop
		    for (TPoint point : pieceBody) {
		        int placeX = x + point.x;
                int placeY = y + point.y;
		        if(grid[placeX][placeY]) {
                    result = PLACE_BAD;
                    break; // no sense filling in the rest if one was bad
                } else {
                    grid[placeX][placeY] = true;
                    
                    // update the widths and check for a filled row
                    if(++widths[placeY] >= width) result = PLACE_ROW_FILLED;
                    
                    // update the heights
                    if(heights[placeX] < placeY + 1) heights[placeX] = placeY + 1;
                    if(heights[placeX] > maxHeight) maxHeight = heights[placeX];
                }
		    }
	    }
		if(result != PLACE_BAD) sanityCheck();

		committed = false;
		return result;
	}
	
	
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
	    if(DEBUG) numCallsToClear++;
	    if (calledClear) throw new RuntimeException("clear rows commit problem");
	    backupDataStructures();
	    int rowsCleared = 0;
	    // Using a buffer seemed like the simplest solution since I would only have 
	    // to set values to "true," not "true" and "false."
		boolean[][] buffer = new boolean[width][height];
        int bufferLogicalHeight = 0;
        for (int y = 0; y < maxHeight; y++) {
            if(widths[y] == width) {
                rowsCleared++;
                widths[y] = 0;
            } else {
                insertRowIntoBuffer(y, buffer, bufferLogicalHeight);
                widths[y - rowsCleared] = widths[y];
                
                // make sure not to undo the previous step
                if(y-rowsCleared != y) widths[y] = 0;
                bufferLogicalHeight++;
            }
        }
        
        // set the grid to point to the buffer
        grid = buffer;
        
        // change all of the heights in one pass
        System.arraycopy(computeHeightsFromGrid(), 0, heights, 0, width);
        
        // the logical height for the buffer is essential the new maxHeight
        maxHeight = bufferLogicalHeight;
        
		sanityCheck();
		committed = false;
		calledClear = true;
		return rowsCleared;
	}



	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
	    if(DEBUG) numCallsToUndo++;
		if(!committed) {
		    // swap main board
		    boolean[][] boardTemp = grid;
		    grid = refGrid;
		    refGrid = boardTemp;
		    
		    // swap widths
		    int[] widthTemp = widths;
		    widths = refWidths;
		    refWidths = widthTemp;
		    
		    // swap heights
		    int[] heightTemp = heights;
		    heights = refHeights;
		    refHeights = heightTemp;
		    maxHeight = refMaxHeight;
		    commit(); // must reset to the committed state
		}
	}
	
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
	    if(DEBUG) numCallsToCommit++;
		committed = true;
        calledClear = false;
	}


	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
	
	// ----------------- Private Methods ------------------ //
	/**
     * places the passed row from the grid into the passed buffer object.
     */
    private void insertRowIntoBuffer(int y, boolean[][] buffer, int rowInBuffer) {
        for (int x = 0; x < buffer.length; x++)
            buffer[x][rowInBuffer] = grid[x][y];
    }
    
    /*
     * makes the backup copies of the main data structures 
     */
    private void backupDataStructures() {
        if(committed) {
            for (int i = 0; i < width; i++)
                System.arraycopy(grid[i], 0, refGrid[i], 0, height);
        
            System.arraycopy(widths, 0, refWidths, 0, height);
            System.arraycopy(heights, 0, refHeights, 0, width);
            refMaxHeight = maxHeight;
        }
    }
    
    /*
     * reduces all of the heights in the heights array by the passed value
     */
    private void reduceHeights(int value) {
        for(int i = 0; i < heights.length; i++)
            heights[i] -= value;
    }
    
    /*
     * returns a "YES" or "NO" string in response to a boolean
     */
    private String isTrue(boolean question) {
        return (question ? "YES" : "NO");
    }
    
    /*
     * computes the heights from the grid and stores the computed max height
     * in the last place of the returned array. 
     */
    private int[] computeHeightsFromGrid() {
        int[] computed = new int[width+1];
        for(int x = 0; x < width; x++)
            for(int y = height - 1; y >= 0; y--)
                if(grid[x][y]) {
                    computed[x] = y + 1;
                    if (y+1 > computed[width]) computed[width] = y+1;
                    break;
                }
        return computed;
    }
    
    /*
     * computes the widths from the grid 
     */
    private int[] computeWidthsFromGrid() {
        int[] computed = new int[height];
        for(int y = 0; y < computed.length; y++)
            for(int x = 0; x < width; x++)
                if(grid[x][y]) computed[y]++;
        return computed;
    }
    
    /*
     * checks the heights against the grid
     */
    private void checkHeights(ArrayList<String> errors) {
        int[] computedHeights = computeHeightsFromGrid();
        int computedMaxHeight = computedHeights[width];
        
        if(computedMaxHeight != maxHeight) {
            StringBuilder error = new StringBuilder();
            error.append("Discrepancy in computed and reference max heights:\n");
            error.append("Expected " + computedMaxHeight + " but value was " + maxHeight + "\n");
            errors.add(error.toString());
        }
        
        for(int x = 0; x < width; x++) {
            if(heights[x] != computedHeights[x]) {
                StringBuilder error = new StringBuilder();
                error.append("Discrepancy in heights array at index: " + x + "\n");
                error.append("Expected " + computedHeights[x] + " but value was " + heights[x] + "\n");
                errors.add(error.toString());
            }
        }
    }
    
    /*
     * checks the widths against the grid
     */
    private void checkWidths(ArrayList<String> errors) {
        int[] computedWidths = computeWidthsFromGrid();
        
        for(int y= 0; y < height; y++) {
            if(widths[y] != computedWidths[y]) {
                StringBuilder error = new StringBuilder();
                error.append("Discrepancy in widths array at index: " + y + "\n");
                error.append("Expected " + computedWidths[y] + " but value was " + widths[y] + "\n");
                errors.add(error.toString());
            }
        }
    }
}


