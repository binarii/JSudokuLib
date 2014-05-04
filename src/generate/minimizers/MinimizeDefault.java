package generate.minimizers;

import board.Board;
import generate.MinimizeMethod;

public class MinimizeDefault extends MinimizeMethod {

	@Override
	protected int getSymmetryCount() {
		return 1;
	}

	@Override
	protected int getIndexingLength() {
		return Board.GRID_SIZE;
	}

	@Override
	protected int[] applySymmetry(int index, int[] digCells) {
		digCells[0] = index;
		return digCells;
	}
}
