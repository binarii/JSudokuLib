package solve;

import util.Bitmask;
import util.Timer;
import board.Board;

public abstract class BacktrackSolve implements SolveMethod {

	private Timer m_timer;
	private int m_maxSolutionCount;

	// Make it protected if subclass does special work
	protected int m_backtrackCount;
	private double m_solveTime;

	protected BacktrackSolve() {
		m_maxSolutionCount = 1;
		m_timer = new Timer();

		m_solveTime = 0.0;
		m_backtrackCount = 0;
	}

	@Override
	public int solve(Board board) {
		final int startDepth = 0;
		int solutionCount;

		startTest(board);

		solutionCount = backtrackSolve(board, startDepth);

		endTest(board);

		return solutionCount;
	}

	protected int backtrackSolve(Board board, int depth) {
		if (board.isBoardFull()) {
			return 1;
		}

		int solutionsFound = 0;
		CellValuePair pair = new CellValuePair(0, Integer.MAX_VALUE);

		// Let subclasses choose what cell to inspect
		int count = chooseCell(board, pair, depth);

		// If we haven't found one, backtrack
		if (count == 0) {
			m_backtrackCount++;
			return 0;
		}

		// Try some possibilities
		int i = Bitmask.getLSB(pair.value);

		while (pair.value > 0) {
			board.set(pair.cell, i);

			// Try a cell, then recurse
			solutionsFound += backtrackSolve(board, depth + 1);
			if (solutionsFound >= m_maxSolutionCount) {
				return solutionsFound;
			}

			board.remove(pair.cell);

			pair.value &= ~i;
			i = Bitmask.getLSB(pair.value);
		}

		if (solutionsFound == 0) {
			m_backtrackCount++;
		}

		return solutionsFound;
	}

	private void startTest(Board board) {
		// Start the timer
		m_timer.startTimer();

		// Initialize variables
		m_backtrackCount = 0;

		// Let subclasses pre-process
		preSolve(board);
	}

	private void endTest(Board board) {
		// Let the subclasses post-process
		postSolve(board);

		// End of the solve algorithm
		m_timer.stopTimer();
		m_solveTime = m_timer.getTimeMS();
	}

	@Override
	public double getSolveTime() {
		return m_solveTime;
	}

	public int getBacktrackCount() {
		return m_backtrackCount;
	}

	public void setMaxSolutionCount(int count) {
		m_maxSolutionCount = count;
	}

	protected class CellValuePair {
		public CellValuePair(int cell, int value) {
			this.cell = cell;
			this.value = value;
		}

		public int cell;
		public int value;
	}

	protected abstract void preSolve(Board board);

	protected abstract void postSolve(Board board);

	protected abstract int chooseCell(Board board, CellValuePair pair, int depth);
}
