package prob6;

import java.util.Scanner;

public class Algo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int k = sc.nextInt();
		int d = sc.nextInt();
		
		long start = System.nanoTime();
		
		Solution s = new Solution();
		
		long answer = s.solution(k, d);
		System.out.println(answer);
		
		long end = System.nanoTime();
		System.out.println("실행시간 : " + (end - start)/10000);

	}
	
	
}

