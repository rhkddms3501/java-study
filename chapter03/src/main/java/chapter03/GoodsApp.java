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
		
		// 정보은닉 (데이터보호)
		camera.setPrice(-1);
		Goods goods2 = new Goods();
		Goods goods3 = new Goods();
		
		System.out.println(Goods.countOfGoods);
		
		camera.setPrice(4000000);
		System.out.println(camera.calcDiscountPrice((float)4.5));
	}

}

