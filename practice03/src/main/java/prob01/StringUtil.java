package prob01;

public class StringUtil {
	

	public static String concatenate(String[] str) {
		
		String result = str[0];
		
		for(int i = 1; i < str.length; i++) {
			result += str[i];
		}
		
		return result;
	}
}
