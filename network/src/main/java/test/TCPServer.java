package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();

			// 2. 바인딩 (binding) (주소를 소켓에다 붙인다.)
			// Soket에 InetSocketAddress(IPAddress + Port)를 바인딩 한다.
			// IPAddress: 0.0.0.0 => 특정 호스트 IP에 바인딩 하지 않는다.
			serverSocket.bind(new InetSocketAddress("0.0.0.0", 5000));

			// 3. accept
			Socket socket = serverSocket.accept(); // blocking (멈춘다?)

			try {
				InetSocketAddress inetRemoteSocketAdress = (InetSocketAddress) socket.getRemoteSocketAddress();
				String remoteHostAddress = inetRemoteSocketAdress.getAddress().getHostAddress(); // remote 는 반대? 연결된? 쪽
																									// 마랗ㅁ
				int remotePort = inetRemoteSocketAdress.getPort();
				System.out.println("[ server ] connected by client[ " + remoteHostAddress + "," + remotePort + " ]");

				// 4. IO Stream 받아오기
				OutputStream os = socket.getOutputStream();
				InputStream is = socket.getInputStream();

				while (true) {
					// 5. 데이터 읽기
					byte[] buffer = new byte[256];

					int readByteCount = is.read(buffer); // blocking
					if (readByteCount == -1) {
						// 클라이언트가 정상적으로 종료( close() 호출 )
						System.out.println("[ server] closed by client");
						break;
					}
					String data = new String(buffer, 0, readByteCount, "utf-8");
					System.out.println("[ server ] received: " + data);

					// 6. 데이터 쓰기
					os.write(data.getBytes("utf-8"));
				}
			} catch (IOException ex) {
				System.out.println("[ server ] error: " + ex);
			} finally {
				try {
					if (socket != null && !socket.isClosed()) {
						socket.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();

				}
			}

		} catch (IOException e) {
			System.out.println("[ server ] error: " + e);
		} finally {
			try {
				if (serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}