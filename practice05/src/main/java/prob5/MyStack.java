package prob5;

public class MyStack {
		private int lenght;
		private String[] buffer;
		
	public MyStack(int i) {
		this.lenght = i;
		this.buffer = new String[i];
	}

	public boolean isEmpty() {
		if(this.buffer[0] == null) return true;
		return false;
	}
	public boolean isfull() {
		if(this.buffer[lenght-1] != null) return true;
		return false;
	}
	
	public void push(String string) throws MyStackException {
		if(!isfull()) {
			for(int i = 0; i < this.lenght; i++) {
				if(this.buffer[i] == null) this.buffer[i] = string;
			}
		}else {
			throw new MyStackException();
		}
	}
	
	public String pop() throws MyStackException {
		if(!isEmpty()) {
			for(int i = this.lenght; i >= 0; i--) {
				if(this.buffer[i] != null) return this.buffer[i];
			}
		}else {
			throw new MyStackException();
		}
		return null;
	}


}