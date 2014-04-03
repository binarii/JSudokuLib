package solve;

import util.RandUtil;
import board.Board;

public class ShuffleSolve extends BacktrackSolve {

	@Override
	protected void preSolve(Board board) {

	}

	@Override
	protected void postSolve(Board board) {

	}

	@Override
	protected int chooseCell(Board board, CellValuePair pair, int depth) {
		return 0;
	}

	@Override
	protected int backtrackSolve(Board board, int depth) {
		if (board.isBoardFull()) {
			return 1;
		}

		int solutionsFound = 0;
		CellValuePair pair = new CellValuePair(0, Integer.MAX_VALUE);

		pair.cell = depth;
		board.updateCandidates(pair.cell);
		pair.value = board.getCandidates(pair.cell);

		// If cell isn't empty, backtrack on further cells
		if (board.getValue(pair.cell) != 0) {
			return backtrackSolve(board, depth + 1);
		}

		// Could move to global per solve, but that limits
		// us to 9! different starting game boards
		int[] chooseOrder = new int[Board.UNIT_SIZE];
		for (int i = 0; i < chooseOrder.length; i++) {
			chooseOrder[i] = (1 << (i + 1));
		}
		RandUtil.ShuffleArray(chooseOrder);

		for (int i = 0; i < chooseOrder.length; i++) {
			if ((pair.value & chooseOrder[i]) != 0) {

				board.set(pair.cell, chooseOrder[i]);

				// Try a cell, then recurse
				solutionsFound += backtrackSolve(board, depth + 1);
				if (solutionsFound > 0) {
					return solutionsFound;
				}

				board.remove(pair.cell);
			}
		}

		if (solutionsFound == 0) {
			m_backtrackCount++;
		}

		return solutionsFound;
	}
}
