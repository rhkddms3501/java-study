package chapter03;

import mypackage.Goods2;

public class Goods2App {

	public static void main(String[] args) {
		Goods2 g = new Goods2();
		
		//	public는 접근 제한 X.
		g.name = "camera";
		
		//	protected는 같은 패키지 내에서 접근 가능
		//	더 중요한 것은 자식 클래스에서 접근이 가능
//		g.price = 1000;
		
		//	default는 같은 패키지 내에서 접근 가능
//		g.countStock = 10;
		
		//	private는 같은 클래스에 내부에서만 접근 가능
//		g.countSold = 50;

	}

}
