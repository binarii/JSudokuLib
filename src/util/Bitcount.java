package util;

public class Bitcount {
	
	private static int[] bitCountArray;
	
	static {
		bitCountArray = new int[1 << 10];
		
		for(int i = 0; i < (1 << 10); i++) {
			bitCountArray[i] = bitCountCalc(i);
		}
	}
	
	public static int bitCountCalc(int val) {
		   return Integer.bitCount(val);
	}
	
	public static int getBitCount(int mask) {
		return bitCountArray[mask];
	}
}
