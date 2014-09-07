package util;

import java.util.Random;

public class RandUtil {
	
	private static Random rnd = new Random();

	public static final void ShuffleArray(int[] array) {
		for (int i = array.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = array[index];
			array[index] = array[i];
			array[i] = a;
		}
	}
}
