package here.gui;
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
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
	private Socket socket;
	
	PrintWriter printWriter = null;
	BufferedReader bufferedReader = null;
	
	public ChatWindow(String name, Socket socket) {
		frame = new Frame(name);
		pannel = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		textArea = new TextArea(30, 80);
		
		this.name = name;
		this.socket = socket;
	}

	public void show() {
		// Button
		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.WHITE);
		buttonSend.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent actionEvent ) {
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
				if(keyCode == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
		});

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
			
			
			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	private void finish() {
		//quit protocol 구현/
		
		
		//clean-up
		
		
		//exit java(Application)
		System.exit(0);
	}
	private void sendMessage() {
		String message = textField.getText();
		System.out.println("메세지 보내는 프로토콜 구현 : " + message );
		
		if("quit".equals(message)) {
			System.out.println("ChatWindow__sendMessage()__quit__입력");
			printWriter.println("quit");
			finish();
		}else {
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
		private PrintWriter printWriter;
		private Socket socket;
		public ChatClientThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			// String messgae = br.readLine();
			//
			//
			
			try {
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
				printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
				
				while (true) {
					String data = bufferedReader.readLine();

					if (data == null) {
						System.out.println("closed by client");
						break;
					}
					System.out.println(data);
//					printWriter.println(data);
					
					// ChatClientThread 에서 서버로 부터 받은 메세지가 있다 치고~
					updateTextArea(data);
				}
				
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			updateTextArea("안녕");
		}
	}
}
