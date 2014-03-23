package util;

public class Bitmask {
	public static int getLSB(int mask) {
		return mask & -mask;
	}
}
