package prob5;

public class Prob5 {

	public static void main(String[] args) {
		
		for (int i = 0; i < 1000; i++) {
			
			String number = String.valueOf(i);
			boolean num = false;
			int count = 0;
			
			for(int j = 0; j < number.length(); j++) {
				if(number.charAt(j) == '3' || number.charAt(j) == '6' || number.charAt(j) == '9') {
					num = true;
					count++;
				}
			}
			
			if(num == true) {
				System.out.print(number + " ");
				for(int l = 0; l < count; l++) {
					System.out.print("짝");
				}
				System.out.println();
			}
		}
	}
}
