package chapter04;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {

	public static void main(String[] args) {
		Date now = new Date();
		System.out.println(now);
		
		printDate01(now);
		printDate02(now);
		
		byte[] data = new byte[9];
		try {
			String s = new String(data, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}


	private static void printDate01(Date d) {
		//2022-12-26 13:01:04
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sdf.format(d);
		
		System.out.println(date);
		
	}
	private static void printDate02(Date d) {
		//	년 (+1900) 밀레니엄 버그?
		int year = d.getYear();
		//	월 (0~11, +1)
		int month = d.getMonth();
		//	일
		int date = d.getDate();
		//	시
		int hours = d.getHours();
		//	분
		int minutes = d.getMinutes();
		//	초
		int seconds = d.getSeconds();
		
		System.out.println(	(year + 1900) + "-" +
							(month + 1)  + "-" +
							date + " " +
							hours + ":" +
							minutes + ":" +
							seconds);
		
	}

}
