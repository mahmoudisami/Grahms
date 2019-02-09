package moteur;

import data.Const;
import data.District;

public class PeopleCalculator {

		public PeopleCalculator() {
			
		}
		
		public void upgradeDistrict(District[][] dist, int size) {	
			for(int i=0;i<size;i++) {
				for(int j=0;j<size;j++) {
					if(dist[i][j] != null) {
						if(dist[i][j].isResidential()) {
							if(dist[i][j].getActualPeople() >= Const.MEDIUM_SIZE_RES && dist[i][j].getSatisfaction() >= Const.SATISFACTION_THRESHOLD_MEDIUM && dist[i][j].getSize() == 1) {
								dist[i][j].setSize(2);
							}else if(dist[i][j].getActualPeople() >= Const.HIGH_SIZE_RES && dist[i][j].getSatisfaction() >= Const.SATISFACTION_THRESHOLD_HIGH && dist[i][j].getSize() == 2) {
								dist[i][j].setSize(3);
							}
						}
						
					}
				}
			}	
		}
		
		
		public void upgradeNumberPeople(District[][] dist, int size) {
			int nbr = 0;
			int newPopulation;
			for(int i=0; i<size;i++) {
				for(int j=0; j<size;i++) {
					if(dist[i][j] != null) {
						if(dist[i][j].isResidential()) {
							nbr = dist[i][j].getActualPeople();
							newPopulation = nbr/10;
							dist[i][j].addPeople(newPopulation);
						}
					}
				}
			}
		}
}
