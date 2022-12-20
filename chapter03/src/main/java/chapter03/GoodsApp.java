package chapter03;

public class GoodsApp {
	
	// 실행하는 class라 main 사용
	public static void main(String[] args) {
		
		Goods camera = new Goods();
		
		camera.setName("nikon");
		camera.setPrice(120000);
		camera.setCountStock(30);
		camera.setCountSold(50);

		camera.printInfo();
	}

}
