package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import test.ChatClientTread;

public class EchoClient {
	private static final String SERVER_IP = "0.0.0.0";

	public static void main(String[] args) {
		Socket socket = null;
		Scanner scanner = null;
		
		try {
			// 1.
			scanner = new Scanner(System.in);
			
			// 2
			socket = new Socket();
			
			//3
			socket.connect(new InetSocketAddress(SERVER_IP, EchoServer.PORT));
			log("connected");

			//4
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

			//5
			System.out.print("닉네임 : ");
			String nickname = scanner.nextLine();
			printWriter.println("join:" + nickname);
			printWriter.flush();
			
			//6
			new ChatClientTread().start();
			
			
			//7
			while(true) {
				// 4. 입력 줄 표시 + 말 입력 받음
				
				
				System.out.print(">>");
				String line = scanner.nextLine();
				
				if("exit".equals(line)) {
					break;
				}else {
					printWriter.println("message:" + line);
					String data = bufferedReader.readLine();	
				}
				
				
				
//				if(data == null) {
//					log("closed by server");
//					break;
//				}
//				
				// 5. 자신이 입력한 말 출력
				System.out.println("확인 : ");
				String requestt = bufferedReader.readLine();
				System.out.println("RECEIVED : " + requestt);	
			}
		} catch(SocketException ex) {
			System.out.println("[server] suddenly closed by server");
		} catch (IOException e) {
			log("error:" + e);
		} finally {
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
		System.out.println("[EchoClient] " + message);
	}
	
}