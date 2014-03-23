package util;

public class Bitmask {
	private static int[] bitCountArray;

	static {
		bitCountArray = new int[1 << 10];

		for (int i = 0; i < (1 << 10); i++) {
			bitCountArray[i] = bitCountCalc(i);
		}
	}

	public static int getLSB(int mask) {
		return mask & -mask;
	}

	public static int toInt(int mask) {
		if (mask == 0) {
			return 0;
		}
		return getBitCount(getLSB(mask) - 1);
	}

	public static char toChar(int mask) {
		return (char) ('0' + toInt(mask));
	}

	public static int getBitmask(char c) {
		if ((c > '0') && (c <= '9')) {
			return (1 << (c - '0'));
		} else if ((c >= 'a') && (c <= 'z')) {
			return (1 << (c - 'a' + 10));
		}
		return 0;
	}

	public static int bitCountCalc(int val) {
		return Integer.bitCount(val);
	}

	public static int getBitCount(int mask) {
		return bitCountArray[mask];
	}
}
