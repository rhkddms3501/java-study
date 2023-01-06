package shb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatClientThread extends Thread {
	private BufferedReader bufferedReader;
	//private PrintWriter printWriter;

	Socket socket = null;

	public ChatClientThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			//printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

			while (true) {
				String message = bufferedReader.readLine();

				if (message == null) {
					System.out.println("채팅창을 나갑니다.");
					break;
				}
				
				System.out.println(message);
			}
		} catch (IOException e) {
			System.out.println("채팅종료");
		}finally {
			try {
				if(socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (IOException e) {
				ChatClient.log("에러:" + e);
			}
		}
	}
}
