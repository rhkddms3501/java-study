package collection;

import java.util.Enumeration;
import java.util.Vector;

public class VectorTest01 {

	public static void main(String[] args) {
		Vector<String> v = new Vector<>();
		
		
		synchronized (v) {			
			v.addElement("짱구");
			v.addElement("짱아");
			v.addElement("흰둥이");
			
			//순회1
			for(int  i = 0; i < v.size(); i++) {
				String s = v.elementAt(i);
						System.out.println(s);
			}
			
			//삭제
			v.removeElementAt(2);
			
			//순회2
			Enumeration<String> e = v.elements();
			while(e.hasMoreElements()) {
				String s = e.nextElement();
				System.out.println(s);
			}
		}
	}

}
