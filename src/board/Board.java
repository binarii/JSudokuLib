package board;

import java.util.Arrays;

public class Board extends BoardConstants {

	private int m_setCount; // Number of filled cells
	private final int[] m_gameBoard; // Stores the value placed at each cell
	private final int[] m_boardMask; // Mask values not allowed in a cell
	private final int[] m_candidates; // Stores candidate values at cell i
	private final int[] m_rowConflicts; // Stores the conflicts in row i
	private final int[] m_colConflicts; // Stores the conflicts in col i
	private final int[] m_boxConflicts; // Stores the conflicts in box i

	public Board() {
		m_setCount = 0;
		m_gameBoard = new int[GRID_SIZE];
		m_boardMask = new int[GRID_SIZE];
		m_candidates = new int[GRID_SIZE];
		m_rowConflicts = new int[UNIT_SIZE];
		m_colConflicts = new int[UNIT_SIZE];
		m_boxConflicts = new int[UNIT_SIZE];
	}

	public Board(Board board) {
		this();
		copy(board);
	}

	public final void reset() {
		m_setCount = 0;
		Arrays.fill(m_gameBoard, 0);
		Arrays.fill(m_boardMask, 0);
		Arrays.fill(m_candidates, 0);
		Arrays.fill(m_rowConflicts, 0);
		Arrays.fill(m_colConflicts, 0);
		Arrays.fill(m_boxConflicts, 0);
	}

	public final void copy(Board board) {
		m_setCount = board.m_setCount;
		System.arraycopy(board.m_gameBoard, 0, m_gameBoard, 0, m_gameBoard.length);
		System.arraycopy(board.m_boardMask, 0, m_boardMask, 0, m_boardMask.length);
		System.arraycopy(board.m_candidates, 0, m_candidates, 0, m_candidates.length);
		System.arraycopy(board.m_rowConflicts, 0, m_rowConflicts, 0, m_rowConflicts.length);
		System.arraycopy(board.m_colConflicts, 0, m_colConflicts, 0, m_colConflicts.length);
		System.arraycopy(board.m_boxConflicts, 0, m_boxConflicts, 0, m_boxConflicts.length);
	}

	public final void remove(int cell) {
		CellRef ref = getCellRef(cell);
		int val = ~m_gameBoard[cell];

		m_gameBoard[cell] = 0;
		m_boardMask[cell] = 0;
		m_rowConflicts[ref.row] &= val;
		m_colConflicts[ref.col] &= val;
		m_boxConflicts[ref.box] &= val;
		m_setCount--;
	}

	public final void set(int cell, int value) {
		CellRef ref = getCellRef(cell);

		m_gameBoard[cell] = value;
		m_boardMask[cell] = BITMASK;
		m_rowConflicts[ref.row] |= value;
		m_colConflicts[ref.col] |= value;
		m_boxConflicts[ref.box] |= value;
		m_setCount++;
	}

	public final void updateCandidates(int cell) {
		CellRef ref = getCellRef(cell);

		m_candidates[cell] = 
				((~(m_boardMask[cell] | 
		            m_rowConflicts[ref.row] |
		            m_colConflicts[ref.col] | 
		            m_boxConflicts[ref.box])) & BITMASK);

	}

	public final boolean isBoardValid() {

		// Iterate all the units (27 standard)
		for (int i = 0; i < UNIT_COUNT; ++i) {
			int once = 0;
			int twice = 0;

			// Iterate each cell in the unit
			for (int j = 0; j < UNIT_SIZE; ++j) {

				// Get the board position
				int cell = iterate(i, j);

				// Get possible mask and value mask
				int boardVal = getValue(cell);

				// Seen twice if current board val and seen once
				twice |= (once & boardVal);

				// Seen once if the current value
				once |= boardVal;
			}

			// Board invalid if any value seen twice
			if (twice != 0) {
				return false;
			}
		}
		return true;
	}

	public final void mask(int cell, int mask) {
		m_boardMask[cell] |= mask;
	}

	public final void clearMask(int cell) {
		m_boardMask[cell] = 0;
	}

	public final int getValue(int cell) {
		return m_gameBoard[cell];
	}

	public final int getCandidates(int cell) {
		return m_candidates[cell];
	}

	public final boolean isBoardFull() {
		return m_setCount == GRID_SIZE;
	}

	public final boolean isBoardSolved() {
		return isBoardFull() && isBoardValid();
	}

	public final int getFilledCount() {
		return m_setCount;
	}
}
