package chat.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ChatWindow {

	private Frame frame;
	private Panel pannel;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;
	private Socket socket;
	PrintWriter printWriter = null;
	BufferedReader bufferedReader = null;

	public ChatWindow(String name, Socket socket) {
		frame = new Frame("채팅방 (" + name + ")");
		pannel = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		textArea = new TextArea(30, 80);
		this.socket = socket;
	}

	public void show() {
		// Button
		buttonSend.setBackground(Color.GRAY);
		//buttonSend.setForeground(Color.WHITE);
		buttonSend.setForeground(Color.BLACK);
		buttonSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				sendMessage();
			}
		});

//		buttonSend.addActionListener((e) -> {
//			
//		});

		// Textfield
		textField.setColumns(80);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char keyCode = e.getKeyChar();
				if (keyCode == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
		});

		// Pannel
		//pannel.setBackground(Color.LIGHT_GRAY);
		pannel.setBackground(Color.WHITE);
		//pannel.setBackground(new Color(155, 187, 212));
		textArea.setBackground(new Color(155, 187, 212));
		buttonSend.setBackground(new Color(249, 224, 0));
		pannel.add(textField);
		pannel.add(buttonSend);
		frame.add(BorderLayout.SOUTH, pannel);

		// TextArea
		textArea.setEditable(false);
		frame.add(BorderLayout.CENTER, textArea);

		// Frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				finish();
			}
		});
		frame.setVisible(true);
		frame.pack();

		// IOStream 받아오기
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);

			// ChatClientThread 생성하고 실행
			new ChatClientThread(socket).start();
		} catch (UnsupportedEncodingException e) {
			log("errer: " + e);
		} catch (IOException e) {
			log("errer: " + e);
		}
	}

	private void finish() {
		// quit protocol 구현
		printWriter.println("quit");

		// clean-up
		try {
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			log("errer: " + e);
		}
		// exit java(Application)
		System.exit(0);
	}

	private void sendMessage() {
		String message = textField.getText();

		if ("quit".equals(message)) {
			finish();
		} else if (message.equals("")) {
			printWriter.println("message:" + encodedBase64(" "));
		} else {
			printWriter.println("message:" + encodedBase64(message));
		}

		textField.setText("");
		textField.requestFocus();
	}

	private void updateTextArea(String message) {
		textArea.append(message);
		textArea.append("\n");
	}

	private String encodedBase64(String message) {
		return Base64.getEncoder().encodeToString(message.getBytes(StandardCharsets.UTF_8));
	}

	private class ChatClientThread extends Thread {
		private BufferedReader bufferedReader;
		private Socket socket;

		public ChatClientThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			// String messgae = br.readLine();
			//
			//
			updateTextArea("채팅방에 입장하였습니다.");
			try {

				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
				while (true) {
					String data = bufferedReader.readLine();

					if (data == null) {
						System.out.println("closed by client");
						break;
					}
					System.out.println(data);

					// ChatClientThread 에서 서버로 부터 받은 메세지가 있다 치고~
					updateTextArea(data);
				}
			} catch (SocketException e) {
				System.out.println("채팅이 종료되었습니다.");
			}catch (UnsupportedEncodingException e) {
				log("errer: " + e);
			} catch (IOException e) {
				log("errer: " + e);
			}
		}
	}
	private static void log(String message) {
		System.out.println("[Client] " + message);
	}
}
