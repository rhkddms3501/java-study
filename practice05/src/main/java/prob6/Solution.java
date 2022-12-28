package prob6;

public class Solution {

	public long solution(int k, int d) {
		
		
		
		long answer = 0;
		
		for(long i = 0; i <= d; i += k) {
			for(long j = 0; j <= d; j += k) {
				if(Math.pow(j, 2) <= Math.pow(d, 2) - Math.pow(i, 2)) {
					answer++;
				}
			}
		}

		return answer;
	}

}
