package here;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ChatClientTread extends Thread {

	private BufferedReader bufferedReader;
	private PrintWriter printWriter;
	private Socket socket;

	public ChatClientTread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);

			while (true) {
				String data = bufferedReader.readLine();

				if (data == null) {
					System.out.println("closed by client");
					break;
				}
				System.out.println(data);
			}
		} catch (SocketException ex) {
			System.out.println("채팅이 종료되었습니다.");
//			log("error : " + ex);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static void log(String message) {
		System.out.println("[Client] " + message);
	}
}
