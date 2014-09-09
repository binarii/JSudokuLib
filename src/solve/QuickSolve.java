package solve;

import util.Bitmask;
import board.Board;

public class QuickSolve extends BacktrackSolve {

	public QuickSolve() {
		super();
	}

	public QuickSolve(int maxSolutions) {
		super(maxSolutions);
	}

	@Override
	protected void preSolve(Board board) {
		boolean done = false;
		int count;

		while (!done) {
			done = true;

			for (int i = 0; i < Board.GRID_SIZE; i++) {
				if (board.getValue(i) != 0) {
					continue;
				}

				// Update and get possibilities for cell i
				board.updateCandidates(i);
				int possible = board.getCandidates(i);

				// Get the bit count for the cell
				count = Bitmask.getBitCount(possible);

				// If cell has one possibility play it
				if (count == 1) {
					board.set(i, possible);
					done = false;
				}
			}
		}
	}

	@Override
	protected void postSolve(Board board) {

	}

	@Override
	protected int chooseCell(Board board, CellValuePair pair, int depth) {
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
