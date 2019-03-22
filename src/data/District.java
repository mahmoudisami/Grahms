package data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class District {

	protected Station station;
	private int gain;
	private int cost;
	protected int actualPeople;
	private int satisfaction;
	private ArrayList<Line> line = new ArrayList<>();
	protected boolean isStation;
	private ArrayList<AccessibleDistrict> accessibleDistrictList = new ArrayList<>();

	public District(int gain,int cost) {
		this.gain = gain;
		this.cost = cost;
		satisfaction = 50;
		isStation = false;
		accessibleDistrictList.clear();
		actualPeople = 50;
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
	
	public void deleteStation() {
		station = null;
		isStation = false; 
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
	
	public void changeActualPeople(int newActualPeople) {
		actualPeople += newActualPeople;
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
	
	public boolean isCommercial() {
		return false;
	}
	
	public boolean isService() {
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
	
	public void addAccessibleDistrict(District dist, int distance) {
		AccessibleDistrict aDistrict = new AccessibleDistrict(dist, distance);
		Line newLine = new Line(this,dist,distance, false, null);
		addLine(newLine);
		accessibleDistrictList.add(aDistrict);
	}
	
	public void addLine(Line newLine) {
		line.add(newLine);
	}
	
	public ArrayList<Line> getLines(){
		return line;
	}
	
	public void removeAccessibleDistrict(AccessibleDistrict aDist) {
		accessibleDistrictList.remove(aDist);
	}
	
	public ArrayList<AccessibleDistrict> getAccessibleDistrict() {
		return accessibleDistrictList;
	}
	

}
