package prob02;

import java.util.Scanner;

// public final class >> 클래스 정의는 여기가 마지막..?
public class GoodsApp {
	//대입이 끝났다. 또 변수  a에 값 대입하면 에러 나오게 할꺼다.
	//	aka 상수
	private static final int COUNT_GOODS = 3;
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);

		Goods[] goods = new Goods[COUNT_GOODS];

		// 상품 입력
		for(int i = 0; i < COUNT_GOODS; i++) {
			String info = scanner.nextLine();
			String[] infos = info.split(" ");
			goods[i] = new Goods(infos[0], Integer.parseInt(infos[1]), Integer.parseInt(infos[2]));
		}
		// 상품 출력
		for(int i = 0; i < COUNT_GOODS; i++) {
			System.out.println(
					goods[i].getName() + "(가격:" +
					goods[i].getPrice() + "원)이 " +
					goods[i].getCountStock() + "개 입고 되었습니다.");
		}
		// 자원정리
		scanner.close();
	}
}
