package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scanner = null;
		try {
			while (true) {
				scanner = new Scanner(System.in);
				System.out.print("> ");
				String domain = scanner.next();
				
				if (domain.equals("exit")) {
					System.out.println("종료");
					break;
				}
				
				InetAddress[] inetAddress = InetAddress.getAllByName(domain);
				for (int i = 0; i < inetAddress.length; i++) {
					System.out.println(inetAddress[i].getHostName() + " : " + inetAddress[i].getHostAddress());
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}

}
