package moteur;

import java.util.ArrayList;
import java.util.HashMap;

import data.*;


public class FinancialCalculator {

	private int weeklyStationCost;
	private int weeklyDistrictGainCS;
	private int weeklyDistrictGainR;
	private int weeklyDistrictCost;
	private int cumulCS, cumulResid;
	
	public FinancialCalculator() {
		weeklyDistrictCost = 0;
		weeklyDistrictGainCS = 0;
		weeklyDistrictGainR = 0;
	}
	
	public int calculatorGainResidential(District map[][]) {
		int nl = map.length;
		int nc = map[0].length;
		for(int i=0; i<nl; i++) {
			for(int j=0; j<nc; j++) {
				if(map[i][j] != null && map[i][j].isResidential()) {
					cumulResid += map[i][j].getActualPeople();
				}
			}
		}
		weeklyDistrictGainR = (int)(cumulResid*Const.GAIN_RES);
		cumulResid = 0;
		return weeklyDistrictGainR;
	}
	
	public void cumulMoney(District map[][], HashMap<District, HashMap<Integer, Integer>> commercialWorkerByDistrict, HashMap<District, HashMap<Integer, Integer>> serviceWorkerByDistrict) {
		int nbrLine = map.length;
		int nbrRow = map[0].length;
		for(int i=0; i<nbrLine; i++) {
			for(int j=0; j<nbrRow; j++) {
				if(map[i][j] != null && map[i][j].isResidential()) {
					for(int index =0; index<3; index ++) {
						if(commercialWorkerByDistrict.get(map[i][j]) != null) {
							cumulCS += commercialWorkerByDistrict.get(map[i][j]).get(index);
						}
						if(serviceWorkerByDistrict.get(map[i][j]) != null) {
							cumulCS += serviceWorkerByDistrict.get(map[i][j]).get(index);
						}
					}
				}
			}
		}
	}
	
	public int calculatorGainCS() {
		weeklyDistrictGainCS = (int)(cumulCS*Const.GAIN_CS);
		cumulCS = 0;
		return weeklyDistrictGainCS;
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
	
	public int lineCalculatorCost(ArrayList<Line> allLines) {
		int count = 0;
		for(int index = 0; index < allLines.size(); index ++) {
			if(allLines.get(index).isVisible()) {
				count += allLines.get(index).getDistance();
			}
		}
		return count*Const.MAINTENANCE_COST_LINE;
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
