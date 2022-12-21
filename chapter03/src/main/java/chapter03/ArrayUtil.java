package chapter03;

public class ArrayUtil {

	public static double[] intToDouble(int[] is) {
		double[] result = new double[is.length];
		
		for (int i = 0; i < is.length; i++) {
			result[i] += is[i];
		}
		return result;
	}

	public static int[] doubleToInt(double[] ds) {
		int[] result = new int[ds.length];
		
		for (int i = 0; i < ds.length; i++) {
			result[i] += ds[i];
		}
		return result;
	}

	public static int[] concat(int[] is, int[] is2) {
		int length = is.length + is2.length;
		int[] result = new int[length];
		
		for (int i = 0; i < length; i++) {
			if(i < length/2) result[i] = is[i];
			else result[i] = is2[i-length/2];
		}
		return result;
	}

}
