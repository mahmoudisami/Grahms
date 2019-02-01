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
	
	public String getImg(int size, String img1, String img2, String img3) {
		if(size == 1) {
			return img1;
		}
		else if(size == 2) {
			return img2;
		}
		else {
			return img3;
		}
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
	
}
