package prob6;

public class RectTriangle extends Shape {
	
	private double w;
	private double h;
	
	

	public RectTriangle(double w, double h) {
		super();
		this.w = w;
		this.h = h;
	}

	@Override
	protected Double getArea() {
		return (this.w * this.h) / 2;
	}

	@Override
	protected Double getPerimeter() {
		
		return this.w + this.h + Math.sqrt(Math.pow(this.w, 2) + Math.pow(this.h, 2));
	}

}
