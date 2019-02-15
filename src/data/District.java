package data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javafx.geometry.Side;

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
		satisfaction = 50;
	}
	
	public boolean createStation() {
		if(isStation) {
			return false; // Station deja� existante
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
	
	public void setSize(int newSize) {}
	
	public void setWorkingDistrict(District dist) {}
	
	public void setServicesDistrict(District dist) {}
}
