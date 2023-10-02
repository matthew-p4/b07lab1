
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
	double coefficients[];
	int exponents[];
	public Polynomial() {
		this.coefficients = new double[]{0};
		this.exponents = new int[]{0};
	}
	
	public Polynomial(double coeff_input[], int exponents_input[]) {
		coefficients = coeff_input;
		exponents = exponents_input;
	}
	
	public Polynomial(File file) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String text = reader.readLine();
		text = text.replace("-", "+-");
		String[] split_text = text.split("\\+");
		int maxL = split_text.length;
		
		double[] ret_coeff = new double[maxL];
		int [] ret_expo = new int[maxL];
		int count = 0;
		for(String i: split_text) {
			String[] temp = i.split("x");
			if(temp.length==1) {
				ret_coeff[count] = Double.parseDouble(temp[0]);
				ret_expo[count] = 0;
				count++;
			}
			else{
				ret_coeff[count] = Double.parseDouble(temp[0]);
				ret_expo[count] = Integer.parseInt(temp[1]);
				count++;
			}
		}
		reader.close();

		this.coefficients = ret_coeff;
		this.exponents = ret_expo;
		
	}
	
	public void update_list(Polynomial poly, double value, int exponent) {
		double new_coeff[] = new double[poly.coefficients.length + 1];
		int new_exponents[] = new int[poly.exponents.length + 1];
		boolean entered = false;
		int count = 0;
		
		for(int i = 0; i < poly.exponents.length; i++) {
			if(exponent < poly.exponents[count] && !entered) {
				new_exponents[i] = exponent;
				new_coeff[i] = value;
				entered = true;
			}
			else {
				new_exponents[i] = poly.exponents[count];
				new_coeff[i] = poly.coefficients[count];
				count++;
			}
		}
		if(entered == false) {
			new_exponents[new_exponents.length - 1] = exponent;
			new_coeff[new_coeff.length - 1] = value;
		}
		else {
			new_exponents[new_exponents.length - 1] = poly.exponents[count];
			new_coeff[new_coeff.length - 1] = poly.coefficients[count];
		}
		
		poly.coefficients = new_coeff;
		poly.exponents = new_exponents;
	}
	
	public Polynomial add(Polynomial new_poly) {
		if(new_poly.coefficients.length >= this.coefficients.length) {
			Polynomial result_poly = new Polynomial(new_poly.coefficients, new_poly.exponents);
			for(int i = 0; i < this.coefficients.length; i++) {
				if(result_poly.exponents[i] == this.exponents[i]) {
					result_poly.coefficients[i] += this.coefficients[i];
				}
				else {
					result_poly.update_list(result_poly, this.coefficients[i], this.exponents[i]);
				}
			}
			return result_poly;
		}
		
		Polynomial result_poly = new Polynomial(this.coefficients, this.exponents);
		for(int i = 0; i < new_poly.coefficients.length; i++) {
			if(result_poly.exponents[i] == new_poly.exponents[i]) {
				result_poly.coefficients[i] += new_poly.coefficients[i];
			}
			else {
				result_poly.update_list(result_poly, new_poly.coefficients[i], new_poly.exponents[i]);
			}
		}
		return result_poly;
		
	}
	
	public double evaluate(double value) {
		double result = 0;
		for(int i = 0; i < this.coefficients.length; i++) {
			result += this.coefficients[i]*(Math.pow(value, this.exponents[i]));
		}
		return result;
	}
	
	public boolean hasRoot(double root) {
		return evaluate(root) == 0; 
	}
	
	public Polynomial multiply(Polynomial poly) {
		Polynomial new_poly = new Polynomial();
		for(int i = 0; i < this.coefficients.length; i++) {
			double[] new_coeff = new double[poly.coefficients.length];
			int[] new_exponents = new int[poly.coefficients.length];
			for(int j = 0; j < poly.coefficients.length; j++) {
				new_coeff[j] = this.coefficients[i]*poly.coefficients[j];
				new_exponents[j] = this.exponents[i] + poly.exponents[j];
			}
			for(int x = 0; x < new_coeff.length; x++) {
				boolean entered = false;
				for(int y = 0; y < new_poly.coefficients.length; y++) {
					if(new_exponents[x] == new_poly.exponents[y]) {
						new_poly.coefficients[y] += new_coeff[x];
						entered = true;
					}
				}
				if(!entered) {
					new_poly.update_list(new_poly, new_coeff[x], new_exponents[x]);
				}
			}
		}
		return new_poly;
	}
	
	public void saveToFile(String file) throws IOException {
		String return_str = "";
		for(int i = 0; i < this.coefficients.length; i++) {
			return_str += String.valueOf(this.coefficients[i]);
			if(this.exponents[i] != 0) {
				return_str += "x" + String.valueOf(this.exponents[i]);
			}
			return_str += "+";

		}
		if(return_str.endsWith("+"))
			return_str = return_str.substring(0,return_str.length()-1);
		return_str = return_str.replace("+-", "-");
		FileWriter writer = new FileWriter(new File(file));
		writer.write(return_str);
		writer.close();
	}
}