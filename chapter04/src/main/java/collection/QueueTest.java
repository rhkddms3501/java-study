package collection;

import java.util.LinkedList;
import java.util.Queue;

public class QueueTest {

	public static void main(String[] args) {
		Queue<String> q = new LinkedList<>();
		
		q.offer("짱구	");
		q.offer("신형식");
		q.offer("봉미선");
		
		while(!q.isEmpty()) {
			String s = q.poll();
			System.out.println(s);
		}
		
		System.out.println("~~~~~~~~~~~~~~");
		
		q.offer("짱구	");
		q.offer("신형식");
		q.offer("봉미선");
		
		System.out.println(q.poll());
		System.out.println(q.peek());
		System.out.println(q.poll());
		System.out.println(q.poll());
		
		// 비어있으면 null
		System.out.println(q.poll());
		

	}

}
