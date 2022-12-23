package prob5;

import java.util.Arrays;

public class MyStack {
	private String[] buffer;
	private int current;
	
	public MyStack(int i) {
		this.buffer = new String[i];
		this.current = -1;
	}

	public boolean isEmpty() {
		return (current == -1);
	}
	public void push(String string) {
			current++;
			if(current >= buffer.length){
				buffer = newBuffer(buffer);
			}
			buffer[current] = string;		
	}
	public String pop() throws MyStackException {
		if(current < 0) {
			throw new MyStackException();
		}
		String result = buffer[current];
		buffer[current] = null;
		current--;
		return result;
	}
	private String[] newBuffer(String[] buffer) {
		String[] newBuffer = new String[current+1];
		newBuffer = Arrays.copyOf(buffer, buffer.length+1);
		return newBuffer;
	}	
}