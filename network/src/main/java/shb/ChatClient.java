package shb;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class ChatClient {
	private static final String SERVER_IP = "127.0.0.1";

	public static void main(String[] args) {
		Socket socket = null;
		Scanner scanner = null;
		PrintWriter printWriter;

		try {
			// 1. 키보드 연결
			scanner = new Scanner(System.in);

			// 2. socket 생성
			socket = new Socket();

			// 3. 연결
			socket.connect(new InetSocketAddress(SERVER_IP, ChatServer.PORT));
			log("connected");

			// 4. reader/writer 생성
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

			// 5. join 프로토콜
			System.out.print("닉네임>>");
			String nickName = scanner.nextLine();
			printWriter.println("join:" + nickName);
			//printWriter.flush();

			// 6. ChatClientThread 시작
			new ChatClientThread(socket).start();
			
			// 7. 키보드 입력처리
			while (true) {
				System.out.print(">>");
				String input = scanner.nextLine();

				if ("quit".equals(input)) {
					// 8. quit 프로토콜 처리
					printWriter.println(input);				
					break;
				} else {
					// 9. 메시지 처리
					printWriter.println("message:" + base64Encoding(input));
				}
			}
		} catch (IOException e) {
			log("error:" + e);
		} finally {
			// 10. 자원관리
			try {
				if(socket != null && !socket.isClosed()) {
					socket.close();
				}
				if(scanner != null) {
					scanner.close();
				}
			} catch (IOException e) {
				log("error:" + e);
			}
		}
	}

	public static void log(String message) {
		System.out.println("Client : " + message);
	}

	public static String base64Encoding(String message) {
		String encodedString = Base64.getEncoder().encodeToString(message.getBytes(StandardCharsets.UTF_8));
		return encodedString;
	}
}
