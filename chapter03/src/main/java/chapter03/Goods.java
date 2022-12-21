package chapter03;

public class Goods {
	public static int countOfGoods = 0;
	private String name;
	private int price;
	private int countStock;
	private int countSold;
	
	public Goods() {
//		클래스 실행마다 countOfGoods 증가 
//		Goods.countOfGoods = Goods.countOfGoods + 1;
//		같은 클래스에서는 생략 가능
		countOfGoods = countOfGoods + 1;
	}
	public Goods(String name, int price, int countStock, int countSold) {
		super();
		this.name = name;
		this.price = price;
		this.countStock = countStock;
		this.countSold = countSold;
		
		countOfGoods = countOfGoods + 1;
	}
	
	public int calcDiscountPrice(float discountRate) {
		return (int)(price * discountRate);
	}
	// 자기 정보 출력
	public void printInfo() {
		System.out.println("name : " + name + ", " + "price : " + price + ", " 
							+ "countStock : " + countStock + ", " + "countSold : " + countSold);
	}
	
	// private 접근 하기 위한 방법
	// source >> generate getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		if(price<0) {
			price = 0;
		}
		this.price = price;
	}
	public int getCountStock() {
		return countStock;
	}
	public void setCountStock(int countStock) {
		this.countStock = countStock;
	}
	public int getCountSold() {
		return countSold;
	}
	public void setCountSold(int countSold) {
		this.countSold = countSold;
	}
	
	
}
