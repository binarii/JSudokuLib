package test;

import board.Board;
import solve.QuickSolve;
import util.PuzzleFileReader;
import util.Timer;

public class SolverTest {
	private static final String fileName = "puzzles/top1465.txt";

	public static void test() {
		Timer timer = new Timer();
		timer.startTimer();

		PuzzleFileReader reader = new PuzzleFileReader(fileName);
		QuickSolve solver = new QuickSolve();

		int totalCount = 0;
		int solveCount = 0;
		double totalTime = 0.0;
		double realTime = 0.0;

		while (reader.isReady()) {
			Board board = reader.readBoard();

			solver.solve(board);

			totalCount++;
			solveCount += board.isBoardSolved() ? 1 : 0;
			totalTime += solver.getSolveTime();
		}

		reader.close();

		timer.stopTimer();
		realTime = timer.getTimeMS();

		System.out.println("Test finished after " + totalTime + "ms");
		System.out.println("Test real time: " + realTime + "ms");
		System.out.println("Solved " + solveCount + "/" + totalCount + " puzzles");
	}

	public static void main(String[] args) {
		test();
	}
}
