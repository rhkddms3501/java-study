package prob05;

import java.util.Random;
import java.util.Scanner;

public class Prob05 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner( System.in );
		int min = 1;
		int max = 100;
		int count = 1;

		while( true ) {
			
			/* 게임 작성 */

			// 정답 램덤하게 만들기
			Random random = new Random();
			int correctNumber = random.nextInt( 100 ) + 1;
			System.out.println("수를 결정했습니다. 맞추어 보세요" + " " + correctNumber);
			
			while(true) {
				System.out.println(min + " ~ " + max);
				System.out.print(count + ">>");
				
				int answer = scanner.nextInt();
				
				if(answer > correctNumber) {
					System.out.println("더 낮게");
					max = answer;
				}else if(answer < correctNumber) {
					System.out.println("더 크게");
					min = answer;
				}else {
					System.out.println("정답입니다!");
					break;
				}
				count++;
			}
			//새 게임 여부 확인하기
			System.out.print( "다시 하겠습니까(y/n)>>" );
			String answer = scanner.next();
			if( "y".equals( answer ) == false ) {
				break;
			}
		}
		
		scanner.close();
		
		
	}

}