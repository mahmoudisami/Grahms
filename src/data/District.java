package data;

import java.util.ArrayList;

public class District {

	private Station station;
	private boolean isStation;
	private int gain;
	private int cost;
	private int actualPeople;
	private int satisfaction;
	private ArrayList<Line> line;
	

	public District(int gain,int cost) {
		this.gain = gain;
		this.cost = cost;
		isStation = false; 
	}
	
	public boolean createStation() {
		if(isStation) {
			return false; // Station déjà existante
		}
		else {
			station = new Station(); // Création de la nouvelle station
			return true; // Construction ok
		}
	}
	
}
