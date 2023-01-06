package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ChatClientTread extends Thread {

	private BufferedReader bufferedReader;
	private Socket socket;

	public ChatClientTread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

			while (true) {
				String data = bufferedReader.readLine();

				if (data == null) {
					System.out.println("closed by client");
					break;
				}
				System.out.println(data);
			}
		} catch (SocketException ex) {
			log("채팅이 종료되었습니다.");
			//log("error : " + ex);
		} catch (IOException e) {
			log("error : " + e);
		} finally {
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (IOException ex) {
				log("error : " + ex);
			}
		}
	}

	private static void log(String message) {
		System.out.println("[Client] " + message);
	}
}
