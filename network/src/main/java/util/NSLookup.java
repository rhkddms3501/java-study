package util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NSLookup {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			try {
				InetAddress[] inetAddress = InetAddress.getAllByName("www.douzone.com");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
	}

}
