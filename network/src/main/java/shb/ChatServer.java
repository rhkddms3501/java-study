package shb;

import java.io.IOException;
import java.io.Writer;
//import java.net.InetAddress;
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
		Socket socket = null;

		// 1. 서버 소켓 생성
		try {
			serverSocket = new ServerSocket();

			// 2. 바인딩
			serverSocket.bind(new InetSocketAddress("0.0.0.0", PORT));
			log("연결 기다림" + ":" + PORT);

			// 3. 요청 대기
			while (true) {
				socket = serverSocket.accept();
				new ChatServerThread(socket, listWriters).start();
			}
		} catch (IOException e) {
			log("error:" + e);
		} finally {
			try {
				if (serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				log("error:" + e);
			}
		}
	}
	
	public static void log(String string) {
		System.out.println("server : " + string);
	}

}
