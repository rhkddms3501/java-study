package shb;

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
	private String nickName;
	private Socket socket;

	List<Writer> listWriters;
	BufferedReader bufferedReader = null;
	PrintWriter printWriter = null;

	public ChatServerThread(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {

		// 1. Remote Host Infermation
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		int remotePort = inetRemoteSocketAddress.getPort();
		ChatServer.log("connected by client[" + remoteHostAddress + ":" + remotePort + "]");
		
		// 2. 스트림 얻기
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),	true);

			// 3. 요청 처리
			while (true) {
				String request = bufferedReader.readLine();
				
				if (request == null) {
					System.out.println("request" + request);
					ChatServer.log("클라이언트로부터 연결 끊김");
					doQuit(printWriter);
					break;
				}				

				// 4. 프로토콜 분석
				String[] tokens = request.split(":");
//				System.out.println("@@@@@@@" + base64Decording(tokens[1]));
				
				
				if ("join".equals(tokens[0])) {
					System.out.println("조인확인");
					doJoin(tokens[1], printWriter);
				} else if ("message".equals(tokens[0])) {
					
					doMessage(tokens[1]);
				} else if ("quit".equals(tokens[0])) {
					doQuit(printWriter);
				} else {
					ChatServer.log("에러:알 수 없는 요청(" + tokens[0] + ")");
				}
			}
		} catch (SocketException e) {
				doQuit(printWriter);
				ChatServer.log("클라이언트로부터 연결 끊김");
		} catch (IOException e) {
			doQuit(printWriter);
			e.printStackTrace();
		} finally {
			try {
				if(socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (IOException e) {
				ChatServer.log("에러:" + e);
			}
		}
	}

	private void doJoin(String nickName, Writer writer) {
		System.out.println("두조인 확인");
		this.nickName = nickName;
		
		String data = nickName + "님이 참여하였습니다.";
		broadcast(data);

		// writer pool에 저장
		addWriter(writer);
		
		// ack
		printWriter.println("join:ok");
		//printWriter.flush();
	}

	private void broadcast(String data) {
		System.out.println("브로드캐스트 확인");
		synchronized(listWriters) {
			for(Writer writer : listWriters) {
				PrintWriter printWriter = (PrintWriter)writer;
				printWriter.println(data);
				//printWriter.flush();
			}
		}
	}

	private void addWriter(Writer writer) {
		System.out.println("애드 리스트 확인 ");
		synchronized (listWriters) {
			listWriters.add(writer);
		}
	}

	private void doQuit(Writer writer) {
		
		removeWriter(writer);
		
		String data = nickName + "님이 퇴장하였습니다.";
		broadcast(data);
		
	}

	private void removeWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.remove(writer);
		}
		
	}

	private void doMessage(String string) {
		broadcast(nickName + ":" + base64Decording(string));		
		System.out.println("두 메세지 확인" + base64Decording(string));
	}
	
	public String base64Decording(String message) {
		byte[] decodedBytes = Base64.getDecoder().decode(message);
		//System.out.println(nickName + " : " + new String(decodedBytes, StandardCharsets.UTF_8));
		return new String(decodedBytes, StandardCharsets.UTF_8);	
	}
}
