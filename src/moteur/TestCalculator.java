package moteur;

import data.*;

public class TestCalculator {

	
	public static void main(String[] args) {
		int size = 5;
		District[][] grid = new District[size][size];
		FinancialCalculator calc = new FinancialCalculator();
		
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				grid[i][j] = null;
			}
		}
		
		grid[1][1] = new Residential();
		grid[2][2] = new Commercial();
		
		System.out.println("Gain avec Resid + Commercial: "+ calc.districtCalculatorGain(grid));
		
		grid[0][0] = new Services();
		
		System.out.println("Gain avec Resid + Commercial + Services: "+ calc.districtCalculatorGain(grid));
		
		
	}

}
