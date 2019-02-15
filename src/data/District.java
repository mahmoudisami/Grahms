package data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javafx.geometry.Side;

public class District {

	protected Station station;
	private int gain;
	private int cost;
	private int actualPeople;
	private int satisfaction;
	private ArrayList<Line> line;
	protected boolean isStation;

	public District(int gain,int cost) {
		this.gain = gain;
		this.cost = cost;
		satisfaction = 50;
		isStation = false;
	}
	
	public boolean createStation() {
		if(isStation) {
			return false; // Station deja existante
		}
		else {
			station = new Station(); // Creation de la nouvelle station
			isStation = true; 
			return true; // Construction ok
		}
	}
	
	public BufferedImage getImg() {
		return null;
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
	
	public void addPeople(int newPopulation) {
		actualPeople += newPopulation;
	}
	
	public int getMaintenanceCost() {
		return 0;
	}
	
	public int getSatisfaction() {
		return satisfaction;
	}
	
	public boolean isResidential() {
		return false;
	}
	
	public int getSize() {
		return 1;
	}
	
	public boolean isStation() {
		return isStation;
	}
	
	public Station getStation() {
		return station;
	}
	
	public void setSize(int newSize) {}
	
	public void setWorkingDistrict(District dist) {}
	
	public void setServicesDistrict(District dist) {}
	
    public void setFirstDistrict(District dist) {}
	
	public void setSecondDistrict(District dist) {}
	

}
