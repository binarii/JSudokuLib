package util;

import board.Board;

public class BoardIO {
	public static void loadBoard(Board board, String puzzle) {
		if (puzzle.length() != Board.GRID_SIZE) {
			return;
		}

		board.reset();
		char c;

		for (int i = 0; i < Board.GRID_SIZE; i++) {
			c = puzzle.charAt(i);
			int mask = Bitmask.getBitmask(c);
			board.set(i, mask);
		}
	}

	public static String getBoardString(Board board) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < Board.GRID_SIZE; i++) {
			char c = Bitmask.toChar(board.getValue(i));
			builder.append(c);
		}

		return builder.toString();
	}
}
