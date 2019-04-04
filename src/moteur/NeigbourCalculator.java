package moteur;

import java.util.ArrayList;

import data.AccessibleDistrict;
import data.Coordinates;
import data.District;

public class NeigbourCalculator {
	private int i,j;
	private int numStation;
	private int numDistrictNearby;
	private District[][] map;
	int width = 18;
	int height = 12;
	private ArrayList<AccessibleDistrict> accessibleNearbyDistrictList = new ArrayList<>();
	
	public NeigbourCalculator(int i, int j) {
		this.i = i;
		this.j = j;
		accessibleNearbyDistrictList.clear();
	}
	
	public void calculate() {
		int calNbRes = 0;
		int calNbServ = 0;
		int calNbShop = 0;
		int calNbStation = 0;
		boolean gotHisOwnStation = false;
		accessibleNearbyDistrictList = new ArrayList<AccessibleDistrict>();
		
		if((map[i][j] != null && map[i][j].isResidential())) {
			
			if(i-1 > 0 && j-1 > 0) {
				if((map[i-1][j-1] != null && map[i-1][j-1].isResidential())) {
					calNbRes++;
					saveDistrictNearby(map[i-1][j-1],1);
				}
				if((map[i-1][j-1] != null && map[i-1][j-1].isStation() && map[i-1][j-1].isResidential())) {
					calNbStation++;
				}
			}
			if ( i+1 < width && j-1 > 0) {
				if((map[i+1][j-1] != null && map[i+1][j-1].isResidential())) {
					calNbRes++;
					saveDistrictNearby(map[i+1][j-1],1);
				}
				if((map[i+1][j-1] != null && map[i+1][j-1].isStation() && map[i+1][j-1].isResidential())) {
					calNbStation++;
				}
			}
			if(i-1 > 0 && j+1 < height) {
				if((map[i-1][j+1] != null && map[i-1][j+1].isResidential())) {
					calNbRes++;
					saveDistrictNearby(map[i-1][j+1],1);
				}
				if((map[i-1][j+1] != null && map[i-1][j+1].isStation() && map[i-1][j+1].isResidential())) {
					calNbStation++;
				}
			}
			if( i-1 > 0) {
				if((map[i-1][j] != null && map[i-1][j].isResidential())) {
					calNbRes++;
					saveDistrictNearby(map[i-1][j],1);
				}
				if((map[i-1][j] != null && map[i-1][j].isStation() && map[i-1][j].isResidential())) {
					calNbStation++;
				}	
			}
			if(i+1 < width) {
				if((map[i+1][j] != null && map[i+1][j].isResidential())) {
					calNbRes++;
					saveDistrictNearby(map[i+1][j],1);
				}
				if((map[i+1][j] != null && map[i+1][j].isStation() && map[i+1][j].isResidential())) {
					calNbStation++;
				}
			}
			if( j-1 > 0) {
				if((map[i][j-1] != null && map[i][j-1].isResidential())) {
					calNbRes++;
					saveDistrictNearby(map[i][j-1],1);
				}
				if((map[i][j-1] != null && map[i][j-1].isStation() && map[i][j-1].isResidential())) {
					calNbStation++;
				}	
			}
			if(j+1 < height) {
				if((map[i][j+1] != null && map[i][j+1].isResidential())) {
					calNbRes++;
					saveDistrictNearby(map[i][j+1],1);
				}
				if((map[i][j+1] != null && map[i][j+1].isStation() && map[i][j+1].isResidential())) {
					calNbStation++;
				}
			}
			if(i+1 < width && j+1 < height) {
				if((map[i+1][j+1] != null && map[i+1][j+1].isResidential())) {
					calNbRes++;
					saveDistrictNearby(map[i+1][j+1],1);
				}
				if((map[i+1][j+1] != null && map[i+1][j+1].isStation() && map[i+1][j+1].isResidential())) {
					calNbStation++;
				}
			}
			setStationNearby(calNbStation);
			setNumDistrict(calNbRes);
		}
	}
	
	public void saveDistrictNearby(District dist, int distance) {
		AccessibleDistrict aDistrict = new AccessibleDistrict(dist, distance);
		accessibleNearbyDistrictList.add(aDistrict);
	}
	
	public ArrayList<AccessibleDistrict> getistrictNearbyCoord(){
		return accessibleNearbyDistrictList;
	}
	
	public int getStationNearby() {
		return numStation;
	}
	
	public void setStationNearby(int newNum) {
		numStation = newNum;
	}
	
	public int getNumDistrict() {
		return numDistrictNearby;
	}
	
	public void setNumDistrict(int newNum) {
		numDistrictNearby = newNum;
	}
	
}
