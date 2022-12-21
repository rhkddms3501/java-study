package chapter03;

import mypackage.Goods2;

public class DiscountGoods2 extends Goods2 {
	
//	private int i = 1022222222222222;
	private Long i = 1022222222222222L;
	
//	private float discountrate = 0.5;
	private float discountrate = 0.5f;
	
	public float getDiscountPrice() {
		//	protected는 자식에서 접근 가능
		return discountrate*price;
	}

}
