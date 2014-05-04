package generate.minimizers;

import board.Board;
import generate.MinimizeMethod;

public class MinimizeVertical extends MinimizeMethod {

	@Override
	protected int getSymmetryCount() {
		return 2;
	}

	@Override
	protected int getIndexingLength() {
		return (Board.UNIT_SIZE + 1) / 2 * Board.UNIT_SIZE;
	}

	@Override
	protected int[] applySymmetry(int index, int[] digCells) {
		// I find it minimizes better if the traversal is swapped
		// like this
		int x = index / Board.UNIT_SIZE;
		int y = index % Board.UNIT_SIZE;

		digCells[0] = (y * Board.UNIT_SIZE) + x;

		x = Board.UNIT_SIZE - x - 1;

		digCells[1] = (y * Board.UNIT_SIZE) + x;

		return digCells;
	}
}
