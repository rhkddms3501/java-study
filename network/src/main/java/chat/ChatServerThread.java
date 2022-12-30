package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class ChatServerThread extends Thread {
	private String nickname;
	private Socket socket;
	List<Writer> listWriters;
	
	
	public ChatServerThread(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {
		//1. Remote Host Information
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		int remotePort = inetRemoteSocketAddress.getPort();
		
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		
		try {
			//2. 스트림 얻기
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			
			//3. 요청 처리
			while(true) {
				String data = bufferedReader.readLine();
				if(data == null) {
					log("클라이언트로 부터 연결 끊김");
					doQuit(printWriter);
					break;
				}

				//4. 프로토콜 분석
				String[] tokens = data.split(":");
				
				if("join".equals(tokens[0])) {
					doJoin(tokens[1], printWriter);
				}else if("message".equals(tokens[0])) {
					doMessage(tokens[1]);
				}else if("quit".equals(tokens[0])) {
					doQuit(printWriter);
				}else {
					log("에러 : 알수 없는 요청 ( " + tokens[0] + " )");
				}
				
				
				
			}
				
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	private void doJoin(String nickName, Writer writer) {
		this.nickname = nickName;
		

		String data = nickName + "님이 참여하였습니다.";
		broadcast(data);
		
		/* writer pool에 저장 */
		addWriter(writer);
		
		// ack
		printWriter.println("join:ok");
//		printWriter.flush();		
		
		
	}

	private void broadcast(String data) {
		synchronized (listWriters) {
			for(Writer writer : listWriters) {
				PrintWriter printWriter = (PrintWriter)writer;
				printWriter.println(data);
//				printWriter.flush();
			}
		}
	}

	private void addWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.add(writer);
		}
	}

	private void doMessage(String string) {
		printWriter.println(nickname + " : " + string);
	}

	private void doQuit(Writer writer) {
		removeWriter(writer);
		
		String data = nickname + "님이 퇴장 하였습니다.";
		broadcast(data);
	}

	private void removeWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.remove(writer);
		}
	}

	private void log(String message) {
		System.out.println("[EchoServer#" + getId() + "] " + message);
	}
}
