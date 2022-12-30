package chat;

import java.io.IOException;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	public static final int PORT = 8080;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		List<Writer> listWriters = new ArrayList<Writer>();
		try {
			//1. 서버 소켓 생성
			serverSocket = new ServerSocket();
			
			//2. 바인딩
			serverSocket.bind(new InetSocketAddress("0.0.0.0", PORT));
			log("연결 기다림...[port:" + PORT + "]");
			
			//3. 요청 대기
			while(true) {
				Socket socket = serverSocket.accept();
				new ChatServerThread(socket, listWriters).start();
			}
			
			
			
			
		} catch (IOException e) {
			log("error:" + e);
		}finally {
			try {
				if(serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void log(String message) {
		System.out.println("[EchoServer#" + Thread.currentThread().getId() + "] " + message);
	}
}
