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
	
	public int districtCalculatorGain(District map[][]) {
		int nl = map.length;
		int nc = map[0].length;
		weeklyDistrictGain = 0;
		for(int i=0; i<nl; i++) {
			for(int j=0; j<nc; j++) {
				if(map[i][j] != null) {
					weeklyDistrictGain += map[i][j].getGain();
				}
			}
		}
		return weeklyDistrictGain;
	}
	
	public int districtCalculatorCost(District map[][]) {
		int nl = map.length;
		int nc = map[0].length;
		weeklyDistrictCost = 0;
		for(int i=0; i<nl; i++) {
			for(int j=0; j<nc; j++) {
				if(map[i][j] != null) {
					weeklyDistrictCost += map[i][j].getMaintenanceCost();
				}
			}
		}
		return weeklyDistrictCost;
	}
	
	public int stationCalculatorCost(District map[][]) {
		int nl = map.length;
		int nc = map[0].length;
		weeklyStationCost = 0;
		for(int i=0; i<nl; i++) {
			for(int j=0; j<nc; j++) {
				if(map[i][j] != null && map[i][j].isStation()) {
					weeklyStationCost += map[i][j].getStation().getMaintenanceCost();
				}
			}
		}
		return weeklyStationCost;
	}
	
}
