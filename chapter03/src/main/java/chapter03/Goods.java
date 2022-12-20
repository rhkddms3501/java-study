package chapter03;

public class Goods {
	//private는 외부에서 접근 불가.
	private String name;
	private int price;
	private int countStock;
	private int countSold;
	
	// 원래 생성자도 있어야한다.
	// 근데 생성자 안 써도 알아서 넣어 준것.
	
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
