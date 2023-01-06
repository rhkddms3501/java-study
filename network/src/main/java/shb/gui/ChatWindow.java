package shb.gui;

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
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ChatWindow {
	private Frame frame;
	private Panel pannel;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;
	private String name;

	BufferedReader bufferedReader = null;
	PrintWriter printWriter = null;
	private Socket socket;

	public ChatWindow(String name, Socket socket) {
		this.name = name;
		this.socket = socket;

		frame = new Frame(name);
		pannel = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		textArea = new TextArea(30, 80);
	}

	public void show() {
		// Button
		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.WHITE);
		buttonSend.addActionListener(new ActionListener() {
			// @Override
			public void actionPerformed(ActionEvent actionEvent) {
				sendMessage();
			}
		});

		// buttonSend.addActionListener((/*ActionEvent*/ e) -> {
		// });

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

//		textField.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//			}
//
//		});

		// Pannel
		pannel.setBackground(Color.LIGHT_GRAY);
		pannel.add(textField);
		pannel.add(buttonSend);
		frame.add(BorderLayout.SOUTH, pannel);

		// TextArea
		textArea.setEditable(false);
		frame.add(BorderLayout.CENTER, textArea);

		// Frame
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				finish();
			}

		});
		frame.setVisible(true);
		frame.pack();

		// IOStream 받아오기
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
					true);

			// ChatClientThread 생성 & 실행
			new ChatClientThread(socket).start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void finish() {
		// quit pritocol 구현
		printWriter.println("quit");

		// clean-up
		try {
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			System.out.println("error:" + e);
		}
		
		// exit java(Application)
		System.exit(0);

	}

	private void sendMessage() {
		String message = textField.getText();
		System.out.println("메시지 프로토콜 구현 : " + message);


		// ChatClientThread에서 서버로부터 받은 메시지가 있다 치고~
		printWriter.println("message:" + base64Encoding(message));
//		updateTextArea(name + " : " + message);
		
		
		textField.setText("");
		textField.requestFocus();
	}

	private void updateTextArea(String message) {
		textArea.append(message);
		textArea.append("\n");
	}

	private class ChatClientThread extends Thread {
		private Socket socket;

		public ChatClientThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
				
				while (true) {
					String message = bufferedReader.readLine();

					if (message == null) {
						System.out.println("error");
						break;
					}
					System.out.println(message);
					updateTextArea(message);
				}
			} catch (IOException e) {
				System.out.println("채팅종료");
			}
		}
	}

	public static String base64Encoding(String message) {
		String encodedString = Base64.getEncoder().encodeToString(message.getBytes(StandardCharsets.UTF_8));
		return encodedString;
	}
}
