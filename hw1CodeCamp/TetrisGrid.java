//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

public class TetrisGrid {
	
    private boolean[][] grid;
    private int logicalHeight;
    
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
	    this.grid = grid;
	    computeLogicalHeight();
	}
	
	
	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
	    boolean[][] buffer = new boolean[grid.length][grid[0].length];
	    int bufferLogicalHeight = 0;
	    for (int y = 0; y < logicalHeight; y++) {
	        if(!isFullRow(y)) {
	            insertRowIntoBuffer(y, buffer, bufferLogicalHeight);
	            bufferLogicalHeight++;
	        }
	    }
	    grid = buffer;
	    logicalHeight = bufferLogicalHeight;
	}
	
	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return grid; // YOUR CODE HERE
	}
	
	/**
	 * helper function for printing out a string representation of
	 * the current grid
	 */
	public void printGrid() {
	    for(int x = 0; x < grid.length; x++) {
	        System.out.print("{");
	        for (int y = 0; y < grid[0].length; y++) {
	            System.out.print(grid[x][y] ? "true " : "false ");
	        }
	        System.out.println("}");
	    }
	}
	
	// --------- Private Methods ---------- //
    
	/**
	 * Computes the logicalHeight for the grid. This is so the program only has to 
	 * scan up to the logical height, not traverse the entire grid unless in the
	 * worst-case.
	 */
    private void computeLogicalHeight() {
        logicalHeight = 0;
        for (int i = 0; i < grid.length; i++) {
            int runningHeight = 0;
            for (int j = 0; j < grid[0].length; j++)
                if (grid[i][j]) runningHeight = j + 1;
            if (runningHeight > logicalHeight)
                logicalHeight = runningHeight;
        }
    }
    
    /**
     * Checks for a full row of true values
     */
    private boolean isFullRow(int y) {
        for (int x = 0; x < grid.length; x++)
            if(!grid[x][y]) return false;
        return true;
    }
    
    /**
     * places the passed row from the grid into the passed buffer object.
     */
    private void insertRowIntoBuffer(int y, boolean[][] buffer, int rowInBuffer) {
        for (int x = 0; x < buffer.length; x++)
            buffer[x][rowInBuffer] = grid[x][y];
    }
}
