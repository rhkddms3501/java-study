package here;

import java.io.IOException;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

	public static final int PORT = 8000;

	public static void main(String[] args) {

		List<Writer> listWriters = new ArrayList<Writer>();
		ServerSocket serverSocket = null;

		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();

			// 2. 바인딩
			serverSocket.bind(new InetSocketAddress("0.0.0.0", PORT));

			// 3. 요청 대기
			while (true) {
				System.out.println("연결 대기중...");
				Socket socket = serverSocket.accept();
				new ChatServerThread(socket, listWriters).start();
			}
		} catch (IOException e) {
			log("error : " + e);
		} finally {
			try {
				if (serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
					System.out.println("서버 종료...");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void log(String message) {
		System.out.println("[Server#" + Thread.currentThread().getId() + "] " + message);
	}
}
