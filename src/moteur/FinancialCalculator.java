package moteur;

import data.District;

public class FinancialCalculator {

	private int totalStationCost;
	private int totalDistrictGain;
	private int totalDistrictCost;
	
	public FinancialCalculator() {
		totalDistrictCost = 0;
		totalDistrictGain = 0;
	}
	
	public int districtCalculatorGain(District tab[][], int dim) {
		totalDistrictGain = 0;
		for(int i=0; i<dim; i++) {
			for(int j=0; j<dim; j++) {
				if(tab[i][j] != null) {
					totalDistrictGain += tab[i][j].getGain();
				}
			}
		}
		return totalDistrictGain;
	}
	
	public int districtCalculatorCost(District tab[][], int dim) {
		totalDistrictCost = 0;
		for(int i=0; i<dim; i++) {
			for(int j=0; j<dim; j++) {
				if(tab[i][j] != null) {
					totalDistrictCost += tab[i][j].getCost();
				}
			}
		}
		return totalDistrictCost;
	}
	
}
