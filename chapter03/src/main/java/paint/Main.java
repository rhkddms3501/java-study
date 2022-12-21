package paint;

public class Main {

	public static void main(String[] args) {
		Point point = new Point();
		
		point.setX(10);
		point.setY(10);
		
		Point point2 = new Point(5,5);
		
		point.show();
		point2.show();
		
		point.disapear();
		
		point.show(true);
	

	}

}
