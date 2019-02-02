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
			return false; // Station deja  existante
		}
		else {
			station = new Station(); // Creation de la nouvelle station
			return true; // Construction ok
		}
	}
	
	public String getImg() {
		return "path";
	}
	
	public int getGain() {
		return gain;
	}

	public int getCost() {
		return cost;
	}

	public int getActualPeople() {
		return actualPeople;
	}
	
	public int getMaintenanceCost() {
		return 0;
	}
	
}
