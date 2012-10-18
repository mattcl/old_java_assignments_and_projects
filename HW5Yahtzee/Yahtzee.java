import acm.io.*; import acm.program.*; import acm.util.*;
public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	public static void main(String[] args) { new Yahtzee().start(args); }
	public void run() {
		IODialog dialog = getDialog(); nPlayers = dialog.readInt("Enter number of players"); playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		display = new YahtzeeDisplay(getGCanvas(), playerNames); scores = new int[nPlayers][N_CATEGORIES]; used = new int[nPlayers][N_CATEGORIES];
		currentPlayer = 0; dice = new int[N_DICE];
		playGame();
	}
	private void playGame() {
		for(int i = 0; i < N_SCORING_CATEGORIES; i++)
			for(currentPlayer = 0; currentPlayer < nPlayers; currentPlayer++) {
				rollDiceSequence();
				selectCategorySequence();
			}
		computeTotalScores();
		determineWinner();
	}
	private void rollDiceSequence() {
		display.printMessage(playerNames[currentPlayer] + "'s turn! Click \"Roll Dice\" button to roll the dice");
		display.waitForPlayerToClickRoll(currentPlayer + 1);
		rollDice();
		display.printMessage("Select the dice you wish to re-roll and click \"Roll Again\"");
		display.waitForPlayerToSelectDice();
		reRollDice();
		display.printMessage("Select the dice you wish to re-roll and click \"Roll Again\"");
		display.waitForPlayerToSelectDice();
		reRollDice();
	}
	private void rollDice() {
		for(int i = 0; i < N_DICE; i++) dice[i] = rgen.nextInt(1, 6);
		display.displayDice(dice);
	}
	private void reRollDice() {
		for(int i = 0; i < N_DICE; i++) if(display.isDieSelected(i)) dice[i] = rgen.nextInt(1, 6);
		display.displayDice(dice);
	}
	private void selectCategorySequence() {
		int category;
		display.printMessage("Select a category for this roll");
		while(usedCategory(category = display.waitForPlayerToSelectCategory()));
		int score = checkAndScoreCategory(category);
		used[currentPlayer][category-1] = 1; scores[currentPlayer][category-1] = score; scores[currentPlayer][TOTAL-1] += score;
		display.updateScorecard(category,currentPlayer+1,score);
		display.updateScorecard(TOTAL,currentPlayer+1,scores[currentPlayer][TOTAL-1]);
	}
	private int checkAndScoreCategory(int category) {
		switch(category) {
		case ONES: case TWOS: case THREES: case FOURS: case FIVES: case SIXES: return addNs(category);
		case THREE_OF_A_KIND: return nOfAKind(3);
		case FOUR_OF_A_KIND: return nOfAKind(4);
		case FULL_HOUSE: return fullHouse();
		case SMALL_STRAIGHT: return straight(4);
		case LARGE_STRAIGHT: return straight(5);
		case CHANCE: return chance();
		case YAHTZEE: if(nOfAKind(5) > 0) return 50;
		default: return 0;
		}
	}
	private int addNs(int n) {
		int score = 0;
		for(int i = 0; i < N_DICE; i++) if(dice[i] == n) score += n;
		return score;
	}
	private int[] generateTrackingArray() {
		int[] tracking = new int[6];
		for(int i = 0; i < N_DICE; i++) tracking[dice[i]-1]++;
		return tracking;
	}
	private int nOfAKind(int n) {
		int[] tracking = generateTrackingArray();
		for(int i = 0; i < tracking.length; i++) if(tracking[i] >= n) return chance();
		return 0;
	}
	private int fullHouse() {
		int[] tracking = generateTrackingArray();
		for(int i = 0; i < tracking.length; i++) if((tracking[i] > 0 && tracking[i] < 2) || tracking[i] > 3) return 0;
		return 25;
	}
	private int straight(int n) {
		int[] tracking = generateTrackingArray();
		int counter = 0;
		for(int i = 0; i < tracking.length && counter < n; i++)
			if(tracking[i] == 0) counter = 0;
			else counter++;
		return (counter == n ? (n == 4 ? 30 : 40) : 0);
	}
	private int chance() {
		int sum = 0;
		for(int i = 0; i < N_DICE; i++) sum += dice[i];
		return sum;
	}
	private boolean usedCategory(int category) {
		for(int i = 0; i < N_CATEGORIES; i++) 
			if(used[currentPlayer][category-1] == 1) {
				display.printMessage("You've already selected that category!");
				return true;
			}
		return false;
	}
	private void computeTotalScores() {
		for(int i = 0; i < nPlayers; i++) { 
			for(int j = 0; j < UPPER_SCORE-1; j++) scores[i][UPPER_SCORE-1] = scores[i][j];
			if(scores[i][UPPER_SCORE-1] > 63) {
				scores[i][UPPER_BONUS-1] = 35;
				scores[i][TOTAL-1] += 35;
			}
			for(int j = THREE_OF_A_KIND-1; j < CHANCE; j++) scores[i][LOWER_SCORE-1] = scores[i][j];
			display.updateScorecard(UPPER_SCORE,i,scores[i][UPPER_SCORE-1]);
			display.updateScorecard(UPPER_BONUS,i,scores[i][UPPER_BONUS-1]);
			display.updateScorecard(LOWER_SCORE,i,scores[i][LOWER_SCORE-1]);
			display.updateScorecard(TOTAL,i,scores[i][TOTAL-1]);
		}
	}
	private void determineWinner() {
		int winner = 0;
		for(int i = 0; i < nPlayers; i++) if(scores[winner][TOTAL-1] < scores[i][TOTAL-1]) winner = i;
		display.printMessage(playerNames[winner] + " is the winner with a total score of " + scores[winner][TOTAL-1]);
	}
	private int nPlayers, currentPlayer;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();	
	private int[][] scores, used;
	private int[] dice;
}
