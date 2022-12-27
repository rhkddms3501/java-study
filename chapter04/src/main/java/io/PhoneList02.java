package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PhoneList02 {

	public static void main(String[] args) {

		Scanner scanner = null;

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

			scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String name = scanner.next();
				String phone1 = scanner.next();
				String phone2 = scanner.next();
				String phone3 = scanner.next();

				System.out.println(name + " : " + phone1 + "-" + phone2 + "-" + phone3);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}
}
