package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class ChatServerThread extends Thread {

	private String nickname;
	private Socket socket;
	List<Writer> listWriters;
	PrintWriter printWriter;
	BufferedReader bufferedReader;

	public ChatServerThread(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {

		try {
			// 1. Remote Host Information
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remotePort = inetRemoteSocketAddress.getPort();
			System.out.println("connected by client[" + remoteHostAddress + ":" + remotePort + "]");

			// 2. 스트림 얻기
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
					true);

			// 3. 요청처리
			while (true) {
				String request = bufferedReader.readLine();
				if (request == null) {
					System.out.println("클라이언트로 부터 연결 끊김");
					doQuit(printWriter);
					break;
				} else if ("quit".equals(request)) {
					System.out.println("클라이언트의 요청에 의한 종료");
				}

				// 4. 프로토콜 분석
				String[] tokens = request.split(":");

				if ("join".equals(tokens[0])) {
					doJoin(tokens[1], printWriter);
				} else if ("message".equals(tokens[0])) {
					doMessage(decodedBase64(tokens[1]));
				} else if ("quit".equals(tokens[0])) {
					doQuit(printWriter);
					break;
				} else {
					System.out.println("에러 : 알수 없는 요청 ( " + tokens[0] + " )");
				}
			}
		} catch (SocketException ex) {
			//log("error : " + ex);
			System.out.println("클라이언트로 부터 연결 끊김");
			doQuit(printWriter);
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

	private void doJoin(String nickName, Writer writer) {

		this.nickname = nickName;
		String data = nickName + "님이 참여하였습니다.";
		bradcast(data);

		// writer pool에 저장
		addWriter(writer);

		// ack
		printWriter.println("JOIN:OK");
		//printWriter.flush();
	}

	private void addWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.add(writer);
		}
	}

	private void bradcast(String data) {
		synchronized (listWriters) {
			for (Writer writer : listWriters) {
				PrintWriter printWriter = (PrintWriter) writer;
				printWriter.println(data);
				//printWriter.flush();
			}
		}
	}

	private void doMessage(String data) {
		String message = "[ " + nickname + " ] : " + data;
		bradcast(message);
	}

	private void doQuit(Writer writer) {
		removeWriter(writer);

		String data = nickname + "님이 퇴장 하였습니다.";
		bradcast(data);
	}

	private void removeWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.remove(writer);
		}
	}

	private String decodedBase64(String input) {
		byte[] decodedBytes = Base64.getDecoder().decode(input);
		System.out.println(nickname + " : " + new String(decodedBytes, StandardCharsets.UTF_8));
		return new String(decodedBytes, StandardCharsets.UTF_8);
	}

	private void log(String message) {
		System.out.println("[Server#" + getId() + "] " + message);
	}
}
