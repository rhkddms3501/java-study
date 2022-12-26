package collection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class LinkedListTest {

	public static void main(String[] args) {
		List<String> list = new LinkedList<>();

		synchronized (list) {
			list.add("짱구");
			list.add("짱아");
			list.add("흰둥이");

			// 순회1
			for (int i = 0; i < list.size(); i++) {
				String s = list.get(i);
				System.out.println(s);
			}

			// 삭제
			list.remove(2);

			// 순회2
			Iterator<String> it = list.iterator();
			while (it.hasNext()) {
				String s = it.next();
				System.out.println(s);
			}

			// 순회3
			for (String s : list) {
				System.out.println(s);
			}
		}

	}

}
