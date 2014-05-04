package generate;

import generate.minimizers.MinimizeDefault;
import generate.minimizers.MinimizeDiagonal;
import generate.minimizers.MinimizeHorizontal;
import generate.minimizers.MinimizeVertical;
import board.Board;
import solve.ShuffleSolve;
import solve.SolveMethod;
import util.Timer;

public class PuzzleFactory {
	public static final int ST_DEFAULT = 0;
	public static final int ST_DIAGONAL = 1;
	public static final int ST_VERTICAL = 2;
	public static final int ST_HORIZONTAL = 3;
	private static final int ST_TOTAL = 4;

	private Timer m_timer;
	private MinimizeMethod[] m_minimizers;

	public PuzzleFactory() {
		m_timer = new Timer();
		m_minimizers = new MinimizeMethod[ST_TOTAL];

		m_minimizers[ST_DEFAULT] = new MinimizeDefault();
		m_minimizers[ST_DIAGONAL] = new MinimizeDiagonal();
		m_minimizers[ST_VERTICAL] = new MinimizeVertical();
		m_minimizers[ST_HORIZONTAL] = new MinimizeHorizontal();
	}

	public double getSolveTime() {
		return m_timer.getTimeMS();
	}

	public Board generate(int symmType, int minClues) {
		m_timer.startTimer();

		Board board = makeBoard();
		board = minimizeBoard(board, symmType, minClues);

		m_timer.stopTimer();

		return board;
	}

	public Board makeBoard() {
		SolveMethod solver = new ShuffleSolve();
		Board board = new Board();

		solver.solve(board);

		return board;
	}

	public Board minimizeBoard(Board board, int symmType, int minClues) {
		m_minimizers[symmType].minimize(board);
		return board;
	}
}
