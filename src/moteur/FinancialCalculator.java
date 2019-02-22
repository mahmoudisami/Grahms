package moteur;

import data.*;


public class FinancialCalculator {

	private int weeklyStationCost;
	private int weeklyDistrictGain;
	private int weeklyDistrictCost;
	
	public FinancialCalculator() {
		weeklyDistrictCost = 0;
		weeklyDistrictGain = 0;
	}
	
	public int districtCalculatorGain(District grid[][], int dim) {
		weeklyDistrictGain = 0;
		for(int i=0; i<dim; i++) {
			for(int j=0; j<dim; j++) {
				if(grid[i][j] != null) {
					weeklyDistrictGain += grid[i][j].getGain();
				}
			}
		}
		return weeklyDistrictGain;
	}
	
	public int districtCalculatorCost(District grid[][], int dim) {
		weeklyDistrictCost = 0;
		for(int i=0; i<dim; i++) {
			for(int j=0; j<dim; j++) {
				if(grid[i][j] != null) {
					weeklyDistrictCost += grid[i][j].getMaintenanceCost();
				}
			}
		}
		return weeklyDistrictCost;
	}
	
	public int stationCalculatorCost(District grid[][], int dim) {
		weeklyStationCost = 0;
		for(int i=0; i<dim; i++) {
			for(int j=0; j<dim; j++) {
				if(grid[i][j] != null && grid[i][j].isStation()) {
					weeklyStationCost += grid[i][j].getStation().getMaintenanceCost();
				}
			}
		}
		return weeklyStationCost;
	}
	
}
