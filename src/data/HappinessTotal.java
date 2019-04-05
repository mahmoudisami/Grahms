package data;

public class HappinessTotal {
	private static int happinessTotal = 0;
	public HappinessTotal() {
		
	}
	
	/*
	public int getHappinessTotal() {
		for(int i=0; i<nbrLine; i++) {
			for(int j=0; j<nbrRow; j++) {
				if(map[i][j].isResidential()) {
					counter++;
					somme = map[i][j].getSatisfaction() + somme;
				}
			}
		}
				
		return happinessTotal = somme/counter;
				
	}
	*/
	public static int getHappinessTotal() {
		return happinessTotal;
	}
	public static void setHappinessTotal(int values) {
		happinessTotal = values;
	}
}
