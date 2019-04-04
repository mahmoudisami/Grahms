package data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class District {

	protected Station station;
	private int cost;
	protected int actualPeople;
	private int satisfaction;
	private ArrayList<Line> line = new ArrayList<>();
	protected boolean isStation;
	private ArrayList<AccessibleDistrict> accessibleDistrictList = new ArrayList<>();
	private int updateStatusGotStation;
	private int updateStatusNoStation;
	private int updateStatusGotStationNearby;
	private int updateStatusNoStationNearby;
	public District(int cost) {
		this.cost = cost;
		isStation = false;
		accessibleDistrictList.clear();
		actualPeople = 50;
		satisfaction = 50;
		updateStatusGotStation = 0;
		updateStatusNoStation = 0;
		updateStatusGotStationNearby = 0;
		updateStatusNoStationNearby = 0;
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

	public int getCost() {
		return cost;
	}

	public int getActualPeople() {
		return actualPeople;
	}
	
	public void addPeople(int newPopulation) {
		actualPeople += newPopulation;
	}
	
	public void setPeople(int newPopulation) {
		actualPeople = newPopulation;
	}
	
	public int getMaintenanceCost() {
		return 0;
	}
	
	public int getSatisfaction() {
		return satisfaction;
	}
	
	public void setSatisfaction(int num) {
		if(satisfaction >= 0 && satisfaction <=100 ) {
			satisfaction = satisfaction + num;
		} 
	}

	public void setUpdateStatusGotStationTrue() {
		updateStatusGotStation = 1;
	}
	public void setUpdateStatusGotStationFalse() {
		updateStatusGotStation = 0;
	}
	public int getUpdateStatusGotStation() {
		return updateStatusGotStation;
	}
	
	public void setUpdateStatusNoStationTrue() {
		updateStatusNoStation = 1;
	}
	public void setUpdateStatusNoStationFalse() {
		updateStatusNoStation = 0;
	}
	public int getUpdateStatusNoStation() {
		return updateStatusNoStation;
	}
	
	public void setUpdateStatusGotStationNearbyTrue() {
		updateStatusGotStationNearby = 1;
	}
	public void setUpdateStatusGotStationNearbyFalse() {
		updateStatusGotStationNearby = 0;
	}
	public int getUpdateStatusGotStationNearby() {
		return updateStatusGotStationNearby;
	}
	
	public void setUpdateStatusNoStationNearbyTrue() {
		updateStatusNoStationNearby = 1;
	}
	public void setUpdateStatusNoStationNearbyFalse() {
		updateStatusNoStationNearby = 0;
	}
	public int getUpdateStatusNoStationNearby() {
		return updateStatusNoStationNearby;
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
	
	public void addAccessibleDistrict(District dist, int distance, boolean line) {
		AccessibleDistrict aDistrict = new AccessibleDistrict(dist, distance);
		if(line) {
			Line newLine = new Line(this,dist,distance, false, null);
			addLine(newLine);	
		}
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
