// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

public class CharGrid {

    
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 * 
	 * ** I'm making the assumption that "grid" means same width for all rows **
	 */
	public int charArea(char ch) {
	    if (grid.length == 0) return 0;
	    int startX = grid[0].length, endX = -1;
	    int startY = grid.length, endY = -1;

	    for (int i = 0; i < grid.length; i ++) {
	        String str = new String(grid[i]);
	        int candidate = str.indexOf(ch);
	        if (candidate != -1) {
	            if (candidate < startX) startX = candidate;
	            if (i < startY) startY = i;
	            // no need to worry about this returning -1, since we know
	            // the character exists in the string
	            candidate = str.lastIndexOf(ch);
	            if (candidate > endX) endX = candidate;
                if (i > endY) endY = i;
	        }
	    }
	    // This can only occur if the character does not appear in the grid
	    if (endX == -1) return 0;
	    
	    return (endX - startX + 1) * (endY - startY + 1);
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		if (grid.length == 0 || grid.length < 3 || grid[0].length < 3) return 0;
	    int count = 0;
	    // start the loop at the second index in each dimension, since there
	    // can be no + centered on the outer edges
	    for (int i = 1; i < grid.length - 1; i++)
	        for (int j = 1; j < grid[0].length - 1; j++)
	            if(grid[i][j] != ' ' && isPlusCenter(i, j)) count++;
		return count;
	}
	
	/**
	 * Determines if the passed row, column represent the center of
	 * a + in the grid. 
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	private boolean isPlusCenter(int row, int col) {
	    int initialCount = searchCol(row, col, -1);
	    return (initialCount > 0 &&
	            initialCount == searchCol(row, col, 1) &&
	            initialCount == searchRow(row, col, -1) &&
	            initialCount == searchRow(row, col, 1));
	}
	
	private int searchCol(int row, int col, int adjustment) {
	    int mod_col = col + adjustment;
	    if(mod_col >= 0 && mod_col < grid[0].length && grid[row][col] == grid[row][mod_col]) 
	        return 1 + searchCol(row, mod_col, adjustment);
	    return 0;
	}
	
	private int searchRow(int row, int col, int adjustment) {
        int mod_row = row + adjustment;
        if(mod_row >= 0 && mod_row < grid.length && grid[row][col] == grid[mod_row][col]) 
            return 1 + searchRow(mod_row, col, adjustment);
        return 0;
    }
}
