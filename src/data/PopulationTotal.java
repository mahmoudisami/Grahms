package data;

public class PopulationTotal {
	private int popTotal = 0;
	private District[][] map;
	private int nbrLine = 18, nbrRow = 12;
	public PopulationTotal() {
		this.popTotal = 0;
	}
	
	public int getPopulationTotal() {
		return popTotal;
	}
	
	/*public void setPopulationTotal() {
		int nbPopTotal =0;
		for(int i=0; i<nbrLine; i++) {
			for(int j=0; j<nbrRow; j++) {
				if(map[i][j].isResidential()) {
					nbPopTotal = nbPopTotal + map[i][j].getActualPeople();
				
				}else {
					nbPopTotal = 0;
				}
			}
					popTotal = nbPopTotal;
		}
	}*/
	
	public void addPopulationTotal(int values) {
		popTotal = popTotal + values;
	}
	public void wdPopulationTotal(int values) {
		popTotal = popTotal - values;
	}
}
