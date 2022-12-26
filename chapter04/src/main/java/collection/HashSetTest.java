package collection;

import java.util.HashSet;
import java.util.Set;

public class HashSetTest {

	public static void main(String[] args) {
		Set<String> s = new HashSet<>();
		
		String s1 = new String("맹구");
		String s2 = new String("맹구");
		
		s.add("철수");
		s.add("유리");
		s.add("훈이");
		s.add(s1);
//		s.add(s2);
		
		System.out.println(s.size());
		System.out.println(s.contains(s2));

		//순회1
		for (String str : s) {
			System.out.println(str);
		}
	}

}
