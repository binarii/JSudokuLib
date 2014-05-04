package generate.minimizers;

import board.Board;
import generate.MinimizeMethod;

public class MinimizeDiagonal extends MinimizeMethod {

	@Override
	protected int getSymmetryCount() {
		return 2;
	}

	@Override
	protected int getIndexingLength() {
		return (Board.GRID_SIZE + 1) / 2;
	}

	@Override
	protected int[] applySymmetry(int index, int[] digCells) {
		digCells[0] = index;
		digCells[1] = Board.GRID_SIZE - index - 1;
		return digCells;
	}
}
