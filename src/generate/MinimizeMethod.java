package generate;

import solve.BacktrackSolve;
import solve.QuickSolve;
import board.Board;

public abstract class MinimizeMethod {

	private int m_clueCount;

	public void minimize(Board board) {
		int solutionCount = 0;
		int size = getIndexingLength();
		int rate = getSymmetryCount();
		int[] digCells = new int[rate];

		BacktrackSolve solver = new QuickSolve();
		Board boardCpy = new Board(board);

		solver.setMaxSolutionCount(2);

		for (int i = 0; (i < size) && (board.getFilledCount() > m_clueCount); i++) {
			boardCpy.copy(board);

			digCells = applySymmetry(i, digCells);
			for (int j = 0; j < rate; j++) {
				if (boardCpy.getValue(digCells[j]) != 0) {
					boardCpy.remove(digCells[j]);
				}
			}

			solutionCount = solver.solve(boardCpy);

			if (solutionCount == 1) {
				for (int j = 0; j < rate; j++) {
					if (board.getValue(digCells[j]) != 0) {
						board.remove(digCells[j]);
					}
				}
			}
		}
	}

	public void setMinClueCount(int count) {
		m_clueCount = count;
	}

	protected abstract int getSymmetryCount();

	protected abstract int getIndexingLength();

	protected abstract int[] applySymmetry(int index, int[] digCells);
}
