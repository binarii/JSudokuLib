package test;

import board.Board;
import solve.ShuffleSolve;
import solve.SolveMethod;
import util.BoardIO;
import util.Timer;

public class GenerationTest {

	public static int SOLVED_TEST_SIZE = 5000;

	public static void testSolvedGeneration() {
		Timer timer = new Timer();
		timer.startTimer();

		Board board = new Board();
		SolveMethod solver = new ShuffleSolve();

		double totalTime = 0.0;
		double realTime = 0.0;

		for (int i = 0; i < SOLVED_TEST_SIZE; i++) {
			board.reset();

			solver.solve(board);
			totalTime += solver.getSolveTime();

			String boardString = BoardIO.getString(board);

			System.out.println(boardString);
		}

		timer.stopTimer();
		realTime = timer.getTimeMS();

		System.out.println("Test finished after " + totalTime + "ms");
		System.out.println("Test real time: " + realTime + "ms");
	}

	public static void main(String[] args) {
		testSolvedGeneration();
	}
}
