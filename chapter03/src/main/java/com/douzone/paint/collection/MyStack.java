package com.douzone.paint.collection;

import java.util.Arrays;

public class MyStack<T> {
	private int current;
	private T[] buffer;
	
	@SuppressWarnings("unchecked")
	public MyStack(int i) {
		this.current = -1;
		this.buffer = (T[])new Object[i];
	}

	public boolean isEmpty() {
		return (current == -1);
	}
	public void push(T t) {
			current++;
			if(current >= buffer.length){
				buffer = newBuffer(buffer);
			}
			buffer[current] = t;		
	}
	public T pop() throws MyStackException {
		if(current < 0) {
			throw new MyStackException();
		}
		T result = buffer[current];
		buffer[current] = null;
		current--;
		return result;
	}
	@SuppressWarnings("unchecked")
	private T[] newBuffer(T[] buffer2) {
		T[] newBuffer = (T[])new Object[current+1];
		newBuffer = Arrays.copyOf(buffer2, buffer2.length+1);
		return newBuffer;
	}	
}