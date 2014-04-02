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
			if (mask != 0) {
				board.set(i, mask);
			}
		}
	}

	public static String getString(Board board) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < Board.GRID_SIZE; i++) {
			char c = Bitmask.toChar(board.getValue(i));
			builder.append(c);
		}

		return builder.toString();
	}

	public static String prettyPrint(Board board) {
		StringBuilder builder = new StringBuilder();

		for (int row = 0; row < Board.UNIT_SIZE; row++) {
			// Print the row dividers every 3 rows
			if (row > 0 && row % Board.BOX_SIZE == 0) {
				builder.append("------+-------+------\n");
			}

			for (int col = 0; col < Board.UNIT_SIZE; col++) {
				// Print the column divider every 3 characters
				if (col > 0 && col % Board.BOX_SIZE == 0) {
					builder.append("| ");
				}

				// Get the proper cell and print character
				int index = row * Board.UNIT_SIZE + col;
				char c = Bitmask.toChar(board.getValue(index));

				// If we don't have a value, print a spacer
				if (c != '0') {
					builder.append(c);
				} else {
					builder.append("-");
				}

				builder.append(" ");
			}
			builder.append("\n");
		}

		return builder.toString();
	}
}
