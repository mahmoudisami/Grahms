package data;

public class PopulationTotal {
	private int popTotal = 0;
	public PopulationTotal() {
		this.popTotal = 0;
	}
	
	public int getPopulationTotal() {
		return popTotal;
	}
	public void addPopulationTotal(int values) {
		popTotal = popTotal + values;
	}
	public void wdPopulationTotal(int values) {
		popTotal = popTotal - values;
	}
	public void setPopulation(int values) {
		popTotal = values;
	}
}
