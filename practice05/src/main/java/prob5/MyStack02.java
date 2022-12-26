package prob5;

import java.util.Arrays;

public class MyStack02 {
	private int current;
	private Object[] buffer;
	
	public MyStack02(int i) {
		this.buffer = new Object[i];
		this.current = -1;
	}

	public boolean isEmpty() {
		return (current == -1);
	}
	public void push(Object object) {
			current++;
			if(current >= buffer.length){
				buffer = newBuffer(buffer);
			}
			buffer[current] = object;		
	}
	public Object pop() throws MyStackException {
		if(current < 0) {
			throw new MyStackException();
		}
		Object result = buffer[current];
		buffer[current] = null;
		current--;
		return result;
	}
	private Object[] newBuffer(Object[] buffer2) {
		Object[] newBuffer = new Object[current+1];
		newBuffer = Arrays.copyOf(buffer2, buffer2.length+1);
		return newBuffer;
	}	
}