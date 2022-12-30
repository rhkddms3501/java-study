package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class EchoRequestHandler extends Thread {
	private String nickname;
	private Socket socket;
	private List<Writer> listWriters;

	public EchoRequestHandler(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		int remotePort = inetRemoteSocketAddress.getPort();
		// 2. 사용자 들어오면 서버에 "사용자 연결됨" + 사용자 ip + 포트
		log("connected by client[" + remoteHostAddress + ":" + remotePort + "]");

		try {

			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

			while (true) {
				String request = bufferedReader.readLine();
				if (request == null) {
					log("closed by client");
					break;
				}

//				**************************************************
//				*********************  여기 부분 작업  ****************
//				*******************  4. 프로토콜 분석  ********************
//				
				
				log(nickname + " : " + request);
				String[] tokens = request.split(":");
				if ("join".equals(tokens[0])) {
					doJoin(tokens[1], printWriter);
				} else if ("message".equals(tokens[0])) {
					doMessage(tokens[1]);
				}

				// 6. 서버에 사용자 id + 입력 받은 말 출력
//				log("received:" + request);
//				printWriter.println("확인" + request);

//				**************************************************
//				**************************************************
			}

		} catch (SocketException ex) {
			System.out.println("[server] suddenly closed by client");
		} catch (IOException ex) {
			log("error:" + ex);
		} finally {
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void doJoin(String nickName, Writer writer) {
		this.nickname = nickName;
		String data = nickName + "님 입장";
		addWriter(writer);
		broadcast(data);

		
//		printWriter.println("join:ok");
//		printWriter.flush();

	}

	private void broadcast(String data) {
		
		synchronized (listWriters) {
			for (Writer writer : listWriters) {
				PrintWriter printWriter = (PrintWriter) writer;
				printWriter.println(data);
				printWriter.flush();
			}
		}

	}

	private void addWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.add(writer);
		}
		for (int i = 0; i < listWriters.size(); i++) {
			
		}

	}

	private void doMessage(String string) {
		String data = nickname + " : " + string ;
		broadcast(data);
	}

	private void log(String message) {
		System.out.println("[EchoServer#" + getId() + "] " + message);
	}
}