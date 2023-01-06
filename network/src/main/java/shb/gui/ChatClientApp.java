package shb.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import shb.ChatServer;

public class ChatClientApp {
	private static final String SERVER_IP = "127.0.0.1";

	public static void main(String[] args) {
		String name = null;
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("대화명을 입력하세요.");
			System.out.print(">>> ");
			name = scanner.nextLine();

			if (!name.isEmpty()) {
				break;
			}
			System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
		}
		scanner.close();
		// 1. create socket

		// 2. connect to server
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_IP, ChatServer.PORT));
			System.out.println("ChatClientApp - connected");

			// 3. get iostream
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

			// 4. join protocol 진행
			printWriter.println("join:" + name);
//			System.out.println("join" + name);
			
			String line = bufferedReader.readLine();
			
			if ("join:ok".equals(line)) {
				System.out.println("조인오케? " + line);
				new ChatWindow(name, socket).show();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
