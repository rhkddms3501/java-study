package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class PhoneList01 {

	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			File file = new File("phone.txt");

			// 파일이 존재하는지 여부를 확인
			if (!file.exists()) {
				System.out.println("File not found");
				return;
			}

			System.out.println("~~~~~~~~~~~~~~ 파일 정보 ~~~~~~~~~~~~~~");
			System.out.println();

			System.out.println("file.getAbsolutePath() : " + file.getAbsolutePath());
			System.out.println("file.length() : " + file.length() + " bytes");
			System.out.println("file.lastModified() : "
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified())));

			System.out.println();
			System.out.println("~~~~~~~~~~~~~~ 전화 번호 ~~~~~~~~~~~~~~");
			System.out.println();

			// 1. 기반 스트림 (FileInputStream)
			FileInputStream fis = new FileInputStream(file);

			// 2. 보조 스트림(1) ( byte | byte | byte | -> char )
			InputStreamReader isr = new InputStreamReader(fis, "utf-8");

			// 3. 보조 스트림(2) ( char | char | char | char |\n -> "charcharcharchar" )
			br = new BufferedReader(isr);
			
			// 4. 처리
			String line = null;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "\t ");
				int index = 0;
				while (st.hasMoreElements()) {
					String token = st.nextToken();
					
					if(index == 0) { // 이름
						System.out.print(token + " : ");
					}else if(index == 1) { // 전화번호(1)
						System.out.print(token + "-");
					}else if(index ==2) { // 전화번호(2)
						System.out.print(token + "-");
					}else { // 전화번호(3)
						System.out.print(token);
					}
					index++;
				}
				System.out.println();
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error : " + e);
		} catch (IOException e) {
			System.out.println("File Not Found : " + e);
		} finally {
			try {
				if (br != null) {
					br.close(); // 제일 마지막에것만 닫으면 된다.?
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
