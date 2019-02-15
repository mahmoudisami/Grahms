package moteur;

import data.*;


public class FinancialCalculator {

	private int totalStationCost;
	private int totalDistrictGain;
	private int totalDistrictCost;
	
	public FinancialCalculator() {
		totalDistrictCost = 0;
		totalDistrictGain = 0;
	}
	
	public int districtCalculatorGain(District grid[][], int dim) {
		totalDistrictGain = 0;
		for(int i=0; i<dim; i++) {
			for(int j=0; j<dim; j++) {
				if(grid[i][j] != null) {
					totalDistrictGain += grid[i][j].getGain();
				}
			}
		}
		return totalDistrictGain;
	}
	
	public int districtCalculatorCost(District grid[][], int dim) {
		totalDistrictCost = 0;
		for(int i=0; i<dim; i++) {
			for(int j=0; j<dim; j++) {
				if(grid[i][j] != null) {
					totalDistrictCost += grid[i][j].getCost();
				}
			}
		}
		return totalDistrictCost;
	}
	
}
