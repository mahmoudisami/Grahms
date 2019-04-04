package moteur;

import data.Const;
import data.District;
import data.Money;

public class PeopleCalculator {

		public PeopleCalculator() {
			
		}
		
		public int upgradeDistrict(District dist, int size, Money money) { // Upgrade district size if conditions are OK
			if(size == 4) {
				return 1;
			}
			if(dist.getSatisfaction() > Const.SATISFACTION_THRESHOLD) {
				if(money.getMoney()<Const.UPGRADE_PRICE) {
					return 2;
				}
				else {
					money.withDraw(Const.UPGRADE_PRICE);
					dist.setSize(size);
					System.out.println("size = " +size);
					return 0;
				}
			}
			return 3;
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
