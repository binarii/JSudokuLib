package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import board.Board;

public class PuzzleFileReader {

	BufferedReader file;

	public PuzzleFileReader(String filename) {
		try {
			file = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			file = null;
			e.printStackTrace();
		}
	}

	public String readLine() {
		try {
			return file.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public Board readBoard() {
		Board board = new Board();
		String line = readLine();

		BoardIO.loadBoard(board, line);
		return board;
	}

	public boolean isReady() {
		try {
			return file.ready();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void close() {
		try {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
