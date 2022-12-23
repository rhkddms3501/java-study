package exception;

import java.io.IOException;

public class MyClass {
	public void danger() throws IOException, MyException {
		System.out.println("어떤 코드...1");
		System.out.println("어떤 코드...2");
		
		if(5-5==0) {
			throw new MyException();
		}
		if(3-3==0) {
			throw new IOException();
		}
		
		System.out.println("어떤 코드...3");
		System.out.println("어떤 코드...4");
		
	}
}
