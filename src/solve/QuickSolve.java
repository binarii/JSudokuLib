package solve;

import util.Bitmask;
import util.Timer;
import board.Board;

public class QuickSolve implements SolveMethod {

	private Timer m_timer;
	private double m_solveTime;
	private int m_maxSolutionCount;

	public QuickSolve() {
		m_solveTime = 0.0;
		m_maxSolutionCount = 1;
		m_timer = new Timer();
	}

	@Override
	public int solve(Board board) {
		m_timer.startTimer();
		int solutionCount = backtrackSolve(board);
		m_timer.stopTimer();

		m_solveTime = m_timer.getTimeMS();

		return solutionCount;
	}

	@Override
	public double getSolveTime() {
		return m_solveTime;
	}

	public void setMaxSolutionCount(int count) {
		m_maxSolutionCount = count;
	}

	private class CellValuePair {
		public CellValuePair(int cell, int value) {
			this.cell = cell;
			this.value = value;
		}

		public int cell;
		public int value;
	}

	private int backtrackSolve(Board board) {
		if (board.isBoardFull()) {
			return 1;
		}

		int solutionsFound = 0;
		CellValuePair pair = new CellValuePair(0, Integer.MAX_VALUE);

		// Try to find a single
		int count = findSingle(board, pair);

		// If we have found none, backtrack
		if (count == 0) {
			return 0;
		}

		// Try some possibilities
		int i = Bitmask.getLSB(pair.value);

		while (pair.value > 0) {
			board.set(pair.cell, i);

			solutionsFound += backtrackSolve(board);
			if (solutionsFound >= m_maxSolutionCount) {
				return solutionsFound;
			}

			board.remove(pair.cell);

			pair.value &= ~i;
			i = Bitmask.getLSB(pair.value);
		}

		return solutionsFound;
	}

	private int findSingle(Board board, CellValuePair pair) {
		int count;

		int savePos = -1;
		int saveCount = Board.UNIT_SIZE + 1;
		int saveVal = 0;

		for (int i = 0; i < Board.GRID_SIZE; i++) {
			// Update and get possibilities for cell i
			board.updateCandidates(i);

			if (board.getValue(i) != 0) {
				continue;
			}

			int possible = board.getCandidates(i);

			// Get the bit count for the cell
			count = Bitmask.getBitCount(possible);

			// If cell is open and it has no possibilities, illegal board
			if (count == 0) {
				return 0;
			}
			// If cell has one possibility play it
			else if (count == 1) {
				pair.cell = i;
				pair.value = possible;
				return 1;
			}
			// Else continue to try and find lowest count on a cell
			else if (count < saveCount) {
				savePos = i;
				saveVal = possible;
				saveCount = count;
			}
		}

		for (int i = 0; i < Board.UNIT_COUNT; i++) {
			int once = 0;
			int twice = 0;
			int all = 0;

			for (int j = 0; j < Board.UNIT_SIZE; j++) {
				// Get the board position
				int cell = Board.iterate(i, j);

				// Get possible mask and value mask
				int possible = board.getCandidates(cell);
				int boardVal = board.getValue(cell);

				all |= (possible | boardVal);
				twice |= (once & possible);
				once |= possible;
			}

			if (all != Board.BITMASK) { // hidden zero, board is illegal
				return 0;
			}

			once &= ~twice;

			if (once == 0) { // If none are seen only once, continue
				continue;
			}

			// Find the hidden single
			once = Bitmask.getLSB(once); // Get least set bit
			for (int j = 0; j < Board.UNIT_SIZE; j++) {

				pair.cell = Board.iterate(i, j);
				if ((board.getCandidates(pair.cell) & once) != 0) {
					pair.value = once;
					return 1;
				}
			}
		}

		pair.cell = savePos;
		pair.value = saveVal;
		return (saveCount > Board.UNIT_SIZE) ? 0 : saveCount;
	}

}
