package collection;

import java.util.Stack;

public class StackTest {

	public static void main(String[] args) {
		Stack<String> s = new Stack<>();

		s.push("짱구");
		s.push("짱아");
		s.push("흰둥이");
		
		while(!s.isEmpty()) {
			String str = s.pop();
			System.out.println(str);
		}
		
		// 비어있는 경우 Exception 발생
//		s.pop();
		
		s.push("짱구");
		s.push("짱아");
		s.push("흰둥이");
		
		System.out.println(s.pop());
		System.out.println(s.peek());
		System.out.println(s.pop());
	}

}
