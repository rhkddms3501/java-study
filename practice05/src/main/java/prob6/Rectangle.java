 package prob6;

public class Rectangle extends Shape implements Resizable {
	
	private double w;
	private double h;	

	public Rectangle(double w, double h) {
		super();
		this.w = w;
		this.h = h;
	}

	@Override
	protected Double getArea() {
		return (this.w * this.h);
	}

	@Override
	protected Double getPerimeter() {
		
		return (this.w + this.h) * 2;
	}

	@Override
	public void resize(double s) {
		this.w *= s;
		this.h *= s;
		
	}

}
