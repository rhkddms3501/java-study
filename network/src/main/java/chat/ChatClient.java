package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	private static final String SERVER_IP = "127.0.0.1";

	public static void main(String[] args) {
		Socket socket = null;
		Scanner scanner = null;

//		PrintWriter printWriter = null;
//		BufferedReader bufferedReader = null;
		
		
		try {
			// 1. 키보드 연결
			scanner = new Scanner(System.in);
			// 2. socket 생성
			socket = new Socket();

			// 3. 연결
			socket.connect(new InetSocketAddress(SERVER_IP, ChatServer.PORT));
			
			// 4. reader / writer 생성
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			
			// 5. join 프로토콜
			System.out.print("닉네임을 입력하세요. >> ");
			String nickname = scanner.nextLine();
			printWriter.println("join: " + nickname + "\r\n");
//			printWriter.flush();
			
			// 6. ChatClintReceiveThread 시작
			
			// 7.키보드 입력 처리
			while(true) {
				System.out.println(">>");
				String input = scanner.nextLine();
				
				if("quit".equals(input)) {
					printWriter.println("quit:" + nickname + "\r\n");
					break;
				}else {
					printWriter.println("message:" + input + "\r\n");
				}
			}
		} catch (IOException e) {
			log("error: " + e);
		}finally {
			try {
				if(socket != null && !socket.isClosed()) {
					socket.close();
				}
				if(scanner != null) {
					scanner.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static void log(String message) {
		System.out.println("[EchoServer#] " + message);
	}
}
