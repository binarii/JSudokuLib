package board;

import java.util.Arrays;

public class Board extends BoardConstants {

	private int m_setCount; // Number of filled cells
	private int[] m_gameBoard; // Stores the value placed at each cell
	private int[] m_boardMask; // Mask values not allowed in a cell
	private int[] m_candidates; // Stores candidate values at cell i
	private int[] m_rowConflicts; // Stores the conflicts in row i
	private int[] m_colConflicts; // Stores the conflicts in col i
	private int[] m_boxConflicts; // Stores the conflicts in box i

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

	public void reset() {
		m_setCount = 0;
		Arrays.fill(m_gameBoard, 0);
		Arrays.fill(m_boardMask, 0);
		Arrays.fill(m_candidates, 0);
		Arrays.fill(m_rowConflicts, 0);
		Arrays.fill(m_colConflicts, 0);
		Arrays.fill(m_boxConflicts, 0);
	}

	public void copy(Board board) {
		m_setCount = board.m_setCount;
		m_gameBoard = Arrays.copyOf(board.m_gameBoard, board.m_gameBoard.length);
		m_boardMask = Arrays.copyOf(board.m_boardMask, board.m_boardMask.length);
		m_candidates = Arrays.copyOf(board.m_candidates, board.m_candidates.length);
		m_rowConflicts = Arrays.copyOf(board.m_rowConflicts, board.m_rowConflicts.length);
		m_colConflicts = Arrays.copyOf(board.m_colConflicts, board.m_colConflicts.length);
		m_boxConflicts = Arrays.copyOf(board.m_boxConflicts, board.m_boxConflicts.length);
	}

	public void remove(int cell) {
		CellRef ref = getCellRef(cell);
		int val = ~m_gameBoard[cell];

		m_gameBoard[cell] = 0;
		m_boardMask[cell] = 0;
		m_rowConflicts[ref.row] &= val;
		m_colConflicts[ref.col] &= val;
		m_boxConflicts[ref.box] &= val;
		m_setCount--;
	}

	public void set(int cell, int value) {
		CellRef ref = getCellRef(cell);

		m_gameBoard[cell] = value;
		m_boardMask[cell] = BITMASK;
		m_rowConflicts[ref.row] |= value;
		m_colConflicts[ref.col] |= value;
		m_boxConflicts[ref.box] |= value;
		m_setCount++;
	}

	public void updateCandidates(int cell) {
		CellRef ref = getCellRef(cell);

		m_candidates[cell] = ((~(m_boardMask[cell] | m_rowConflicts[ref.row]
				| m_colConflicts[ref.col] | m_boxConflicts[ref.box])) & BITMASK);

	}

	public boolean isBoardValid() {

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

	public void mask(int cell, int mask) {
		m_boardMask[cell] |= mask;
	}

	public void clearMask(int cell) {
		m_boardMask[cell] = 0;
	}

	public int getValue(int cell) {
		return m_gameBoard[cell];
	}

	public int getCandidates(int cell) {
		return m_candidates[cell];
	}

	public boolean isBoardFull() {
		return m_setCount == GRID_SIZE;
	}

	public boolean isBoardSolved() {
		return isBoardFull() && isBoardValid();
	}

	public int getFilledCount() {
		return m_setCount;
	}
}
