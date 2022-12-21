package mypackage;

public class Goods2 {
	public String name;		// 모든 접근 가능 (접근 제한 X)
	protected int price;	// 같은 패키지 + 자식 클래스에서 접근 가능
	int countStock;			// 아무것도 적지 않으면 default로 적용, 같은 패키지
	private int countSold;	// 클래스 내부에서만 접근 가능
	
	public void m() {
		countSold = 50;
	}
}
