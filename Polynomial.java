public class Polynomial {
	double[] coefficients;
	public Polynomial() {
		this.coefficients = new double[]{0};
	}
	
	public Polynomial(double input[]) {
		this.coefficients = input;
	}
	
	public Polynomial add(Polynomial new_poly) {
		if(new_poly.coefficients.length >= this.coefficients.length) {
			for(int i = 0; i < this.coefficients.length; i++) {
				new_poly.coefficients[i] += this.coefficients[i];
			}
			return new_poly;
		}
		
		for(int i = 0; i < new_poly.coefficients.length; i++) {
			this.coefficients[i] += new_poly.coefficients[i];
		}
		return this;
	}
	
	public double evaluate(double value) {
		double result = 0;
		for(int i = 0; i < this.coefficients.length; i++) {
			result += this.coefficients[i] * Math.pow(value, i);
		}
		return result;
	}
	
	public boolean hasRoot(double root) {
		return evaluate(root) == 0; 
	}
}