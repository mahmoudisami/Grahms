package data;

public class HappinessTotal {
	private int happinessTotal = 0;
	private int somme;
	private int counter = 0;

	private District[][] map;
	private int nbrLine = 18, nbrRow = 12;
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
	public int getHappinessTotal() {
		return happinessTotal;
	}
	public void setHappinessTotal(int values) {
		happinessTotal = values;
	}
}
