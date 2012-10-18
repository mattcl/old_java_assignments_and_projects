import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.
	
	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 7 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");
	
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;
	
	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(easyGrid);
		
		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}
	
	
	

	/**
	 * Sets up based on the given ints.
	 */
	private static final Set<Integer> generalSet = new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)); 
	
	private int[][] grid;
	private int[][] solution;
	private Set<Integer>[] refSquares;
	private Set<Integer>[] refRows;
	private Set<Integer>[] refColumns; 
	
	private List<Spot> spots;
	private boolean solutionFound;
	
	private long elapsedTime;
	
	public Sudoku(int[][] ints) {
		grid = copyGrid(ints);
		solution = copyGrid(ints);
		solutionFound = false;
		elapsedTime = 0;
		generateRefRows();
		generateRefColumns();
		generateRefSquares();
		generateSpots();
	}
	
	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 * 
	 * wrapper function
	 */
	public int solve() {
	    elapsedTime = System.currentTimeMillis();
		int solutions = recursiveSolve(new LinkedList<Spot>(spots));
		elapsedTime = System.currentTimeMillis() - elapsedTime;
		return solutions;
	}
	
	public String getSolutionText() {
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(Arrays.toString(solution[0]));
	    for(int i = 1; i < solution.length; i++)
	        buffer.append("\n" + Arrays.toString(solution[i]));
	    String solutionText = new String(buffer);
	    // use a regular expression to strip the commas and brackets
	    return solutionText.replaceAll(",|\\[|\\]", "");
	}
	
	public long getElapsed() {
		return elapsedTime;
	}
	
	// -------------------- Private ------------------- //
	
	// generates a copy of the passed two-dimensional array
	private int[][] copyGrid(int[][] arr) {
	    if(arr.length == 0) return null;
	    int[][] copy = new int[arr.length][arr[0].length];
	    for(int i = 0; i < arr.length; i++)
	        System.arraycopy(arr[i], 0, copy[i], 0, arr[i].length);
	    return copy;
	}
	
	// generates the spots for the particular grid
	private void generateSpots() {
	    spots = new LinkedList<Spot>();
	    for(int row = 0; row < SIZE; row++)
	        for(int col = 0; col < SIZE; col++)
	            if(grid[row][col] == 0)
	                spots.add(new Spot(row, col));
	    Collections.sort(spots); // get the spots into the right order for the solve() method
	}
	
	// recursive helper method for the solve() method
	private int recursiveSolve(LinkedList<Spot> workingSpots) {
	    // base case, we found a solution
	    if(workingSpots.isEmpty()) {
	        if(!solutionFound) {
	            solution = copyGrid(grid);
	            solutionFound = true;
	        }
	        return 1;
	    }
	    int numSolutions = 0;
	    Spot currentSpot = workingSpots.removeFirst();
	    Set<Integer> spotValues = currentSpot.possibleValues();
	    
	    if(!spotValues.isEmpty()) {
	        
	        // iterate through all of the possible values
    	    Iterator<Integer> itr = spotValues.iterator();
    	    while(itr.hasNext()) {
    	        currentSpot.setValue(itr.next());
    	        numSolutions += recursiveSolve(workingSpots);
    	        currentSpot.reset(); // undo so we can test a different case
    	    }
	    }
	    // put the spot back 
	    workingSpots.addFirst(currentSpot);
	    return numSolutions;
	}
	
	// The following methods are only called during the instantiation of a Sudoku object.
	private void generateRefRows() {
	    refRows = (Set<Integer>[]) new Set[SIZE];
	    for(int row = 0; row < SIZE; row++) {
	        refRows[row] = new HashSet<Integer>();
	        for(int col = 0; col < SIZE; col++)
	            if(grid[row][col] != 0 && !refRows[row].add(grid[row][col]))
	                throw new RuntimeException("Grid integrity exception generating reference rows");
	    }
	}
	
	private void generateRefColumns() {
	    refColumns = (Set<Integer>[]) new Set[SIZE];
        for(int col = 0; col < SIZE; col++) {
            refColumns[col] = new HashSet<Integer>();
            for(int row = 0; row < SIZE; row++)
                if(grid[row][col] != 0 && !refColumns[col].add(grid[row][col]))
                    throw new RuntimeException("Grid integrity exception generating reference columns");
        }
    }
	
	private void generateRefSquares() {
	    int rowStart = 0;
	    int colStart = 0;
	    refSquares = (Set<Integer>[]) new Set[PART * PART];
	    for(int i = 0; i < PART * PART; i++) {
	        refSquares[i] = new HashSet<Integer>();
	        for(int row = rowStart; row < rowStart + PART; row++)
	            for(int col = colStart; col < colStart + PART; col++)
	               if(grid[row][col] != 0 && !refSquares[i].add(grid[row][col]))
	                   throw new RuntimeException("Grid integrity exception generating reference squares");
	        rowStart += PART;
	        if(rowStart/PART % PART == 0) {
	            rowStart = 0;
	            colStart +=PART;
	        }
	    }
	}
	
	// --------------- Inner spot class --------------- //
	private class Spot implements Comparable<Sudoku.Spot>{
        private int row;
        private int col;
        private int square;
        
        // constructor
        public Spot(int row, int col) {
            this.row = row;
            this.col = col;
            computeSquare();
        }
        
        // returns the spot's row
        public int getRow() {
            return row;
        }
        
        // returns the spot's column
        public int getColumn() {
            return col;
        }
        
        // returns the square the spot resides in
        public int getSquare() {
            return square;
        }
        
        // returns the spot's value
        public int getValue() {
            return grid[row][col];
        }
        
        // sets the spot's value
        public void setValue(int value) {
            refSquares[square].add(value);
            refColumns[col].add(value);
            refRows[row].add(value);
            grid[row][col] = value;
        }
        
        // resets the spot to an empty state, re-adding the contained value
        // to the appropriate row, column and square
        public void reset() {
            if(isEmpty()) return;
            refSquares[square].remove(getValue());
            refColumns[col].remove(getValue());
            refRows[row].remove(getValue());
            grid[row][col] = 0;
        }
        
        // returns if the current spot is empty
        public boolean isEmpty() {
            return (grid[row][col] == 0);
        }
        
        // returns a set containing all of the possible values
        public Set<Integer> possibleValues() {
            HashSet<Integer> possibleValues = new HashSet<Integer>(generalSet);
            possibleValues.removeAll(refSquares[square]);
            possibleValues.removeAll(refRows[row]);
            possibleValues.removeAll(refColumns[col]);
            return possibleValues;
        }
        
        public int compareTo(Sudoku.Spot spot) {
            return possibleValues().size() - spot.possibleValues().size();
        }
        
        // --------- Private -------- //
        
        // computes the square the spot resides in
        private void computeSquare() {
            int modifier = 2;
            if(row < PART) modifier = 0;
            else if(row < PART * 2) modifier = 1;
            square = (col / PART) * PART + modifier;
        }
    }
}
