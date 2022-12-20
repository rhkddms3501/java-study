package prob2;

public class Prob2 {
	public static void main(String[] args) {
		/* 코드 작성 */
		int number = 10;
		
		for(int i = 1; i <= 9; i++) {
			for(int j = i; j <= number; j++) {
				System.out.print(j + " ");
				
			}
			System.out.println();
			number++;
		}
	}
}
