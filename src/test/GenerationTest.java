package test;

import generate.PuzzleFactory;
import board.Board;
import solve.ShuffleSolve;
import solve.SolveMethod;
import util.BoardIO;
import util.Timer;

public class GenerationTest {

	public static int SOLVED_TEST_SIZE = 5000;
	public static int FACTORY_TEST_SIZE = 1000;

	public static void testPuzzleFactory(boolean print) {
		Timer timer = new Timer();
		timer.startTimer();

		Board board;
		PuzzleFactory factory = new PuzzleFactory();

		double totalTime = 0.0;
		double realTime = 0.0;

		for (int i = 0; i < FACTORY_TEST_SIZE; i++) {
			board = factory.generate(2, 0);

			totalTime += factory.getSolveTime();

			if (print) {
				String boardString = BoardIO.getString(board);

				System.out.println(boardString);
			}
		}

		timer.stopTimer();
		realTime = timer.getTimeMS();

		System.out.print("Testing puzzle factory with N = ");
		System.out.println(FACTORY_TEST_SIZE);
		System.out.println("Test finished after " + totalTime + "ms");
		System.out.println("Test real time: " + realTime + "ms\n");
	}

	public static void testSolvedGeneration(boolean print) {
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

			if (print) {
				String boardString = BoardIO.getString(board);

				System.out.println(boardString);
			}
		}

		timer.stopTimer();
		realTime = timer.getTimeMS();

		System.out.print("Testing solved puzzle generation with N = ");
		System.out.println(SOLVED_TEST_SIZE);
		System.out.println("Test finished after " + totalTime + "ms");
		System.out.println("Test real time: " + realTime + "ms\n");
	}

	public static void main(String[] args) {
		boolean print = false;
		testSolvedGeneration(print);
		testPuzzleFactory(print);
	}
}
