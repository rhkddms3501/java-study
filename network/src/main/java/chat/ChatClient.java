package chat;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class ChatClient {

	private static final String SERVER_IP = "127.0.0.1";

	public static void main(String[] args) {
		Scanner scanner = null;
		Socket socket = null;

		try {
			// 1. 키보드 연결
			scanner = new Scanner(System.in);

			// 2. socket 생성
			socket = new Socket();

			// 3. 연결
			socket.connect(new InetSocketAddress(SERVER_IP, ChatServer.PORT));

			// 4. reader/writer 생성
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);

			// 5. join 프로토콜
			System.out.print("닉네임 >> ");
			String nickname = scanner.nextLine();
			printWriter.println("join:" + nickname);
			//printWriter.flush();

			// 6. ClientThread 시작
			new ChatClientTread(socket).start();

			// 7. 키보드 입력 처리
			while (true) {
				System.out.print(">>");
				String input = scanner.nextLine();

				if ("quit".equals(input)) {
					// 8. quit 프로토콜 처리
					printWriter.println("quit");
					//printWriter.flush();
					break;
				} else if (input.equals("")) {
					// 9. 메시지 처리
					printWriter.println("message:" + encodedBase64(" "));
				} else {
					// 9. 메시지 처리
					printWriter.println("message:" + encodedBase64(input));
					//printWriter.flush();
				}
			}
		} catch (SocketException ex) {
			log("error : " + ex);
		} catch (IOException e) {
			log("error : " + e);
		} finally {
			// 10. 자원정리
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
				if (scanner != null) {
					scanner.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static String encodedBase64(String input) {
		return Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
	}

	private static void log(String message) {
		System.out.println("[Client] " + message);
	}
}
